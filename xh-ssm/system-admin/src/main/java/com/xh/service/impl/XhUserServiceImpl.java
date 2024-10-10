package com.xh.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xh.configuration.CustomObjectMapper;
import com.xh.constant.Constants;
import com.xh.core.RedisTemplate;
import com.xh.entity.XhLoginUser;
import com.xh.entity.XhMenu;
import com.xh.entity.XhRole;
import com.xh.entity.XhUser;
import com.xh.dao.XhUserDao;
import com.xh.exception.PasswordIncorrectException;
import com.xh.exception.UserNotFoundException;
import com.xh.service.XhUserService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户信息表(XhUser)表服务实现类
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
@Service("xhUserService")
@Slf4j
public class XhUserServiceImpl implements XhUserService {
    @Resource
    private XhUserDao xhUserDao;

    @Resource
    private RestTemplate restTemplate;

    //序列化工具
    @Resource
    private CustomObjectMapper objectMapper;

    //注入redisTemple
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public XhUser queryById(Long userId) {
        return this.xhUserDao.queryById(userId);
    }

    /**
     * 分页查询
     *
     * @param xhUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<XhUser> queryByPage(XhUser xhUser, PageRequest pageRequest) {
        long total = this.xhUserDao.count(xhUser);
        return new PageImpl<>(this.xhUserDao.queryAllByLimit(xhUser, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param xhUser 实例对象
     * @return 实例对象
     */
    @Override
    public XhUser insert(XhUser xhUser) {
        this.xhUserDao.insert(xhUser);
        return xhUser;
    }

    /**
     * 修改数据
     *
     * @param xhUser 实例对象
     * @return 实例对象
     */
    @Override
    public XhUser update(XhUser xhUser) {
        this.xhUserDao.update(xhUser);
        return this.queryById(xhUser.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long userId) {
        return this.xhUserDao.deleteById(userId) > 0;
    }

    //登录的接口
    @Override
    public XhLoginUser login(String userName, String password) throws JsonProcessingException {
        //前端的校验一般是不可靠的，所有在后端校验

        //1.登录,使用用户名查询用户，没有查询到，说明没有该账号
        XhUser xhUser = xhUserDao.queryByUserName(userName);

        if(xhUser == null) throw  new UserNotFoundException("执行登录操作: [" + userName + "] 该用户不存在");


        //2.如果查到比较密码， 密码不正确也不行
        if(!password.equals(xhUser.getPassword())){
            log.info("执行登录操作: [" + userName + "] 该用户密码输入不正确");
            throw new PasswordIncorrectException("执行登录操作: [" + userName + "] 该用户密码输入不正确");
        }

        //3.验证成功了之后执行:

        //（1）生成token
        String token = UUID.randomUUID().toString();
        //获取请求地址
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        UserAgent userAgent = new UserAgent(request.getHeader("User-Agent"));


        //通过ip获取所属的地址
        ResponseEntity<String> result = restTemplate.getForEntity("https://whois.pconline.com.cn/ipJson.jsp?ip="+request.getRemoteHost()+"&json=true", String.class);
        String body = result.getBody();

        //对body进行反序列化
        Map<String,String> map = objectMapper.readValue(body, new TypeReference<>() {});
        String location = (map.get("addr") + map.get("pro") + map.get("city") + map.get("region"));

        //（2封装一个XhLoginUser，保存在redis中
        XhLoginUser xhLoginUser = XhLoginUser.builder()
                .userId(xhUser.getUserId())
                .token(token)
                .ipaddr(request.getRemoteAddr())
                .loginTime(new Date())
                .os(userAgent.getOperatingSystem().getName())
                .browser(userAgent.getBrowser().getName())
                .loginLocation(location)
                .xhUser(xhUser)
                .build();
        //key进行处理，token：username：uuid
        //根据用户名生成一个前缀 token：userName
        String keyPrefix = Constants.TOKEN_PREFIX + userName + ":";
        //2.查询前缀的数据
        Set<String> keys = redisTemplate.keys(keyPrefix + "*");
        //3.删除原来的数据
        keys.forEach(key -> redisTemplate.remove(key));

        //4。把新的数据加入reids
        redisTemplate.setObject(keyPrefix + token,xhLoginUser,Constants.TOKEN_TIME);
        return xhLoginUser;
    }

    @Override
    public void logout() {
        //获取头部信息
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        //获取首部信息的token
        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);
        //删除redis中user的token信息
        redisTemplate.remove(Constants.TOKEN_PREFIX + token);
    }

    @Override
    public  HashMap<String,List<String>> getInfo() {
        //获取当前登录的对象
        XhLoginUser loginUser = getLoginUser();
        //2查询当前用户的角色和权限
        XhUser info = xhUserDao.getInfo(loginUser.getUserId());

        //3.处理权限和角色的相关信息
        //(1)roles:token :[admin.xx.yy] perms:token :[system:user:add,system:user:update]
        List<String> roleTags = info.getXhRoles().stream().map(XhRole::getRoleTag).collect(Collectors.toList());
        redisTemplate.setObject(Constants.ROLE_PREFIX + loginUser.getToken(),roleTags,Constants.TOKEN_TIME);

        //处理权限
        ArrayList<String> perms = new ArrayList<>();
        //[roleName:xxx,roleTag:xxx,perms:[{id,'xx',perm:'system'}]]

        info.getXhRoles().stream().map(XhRole::getXhMenus).forEach(menus -> {
            perms.addAll(menus.stream().map(XhMenu::getPerms).collect(Collectors.toList()));
        });
        redisTemplate.setObject(Constants.PERM_PREFIX + loginUser.getToken(),perms,Constants.TOKEN_TIME);

        //整合数据
        HashMap<String,List<String>> data = new HashMap<>();
        data.put("roles",roleTags);
        data.put("perms",perms);
        return data;
    }

    private XhLoginUser getLoginUser(){
        //获取头部信息
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 判断有没有Authorization这个请求头，拿到首部的请求头
        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);

        if(token == null){
            throw new RuntimeException("当前用户未登录！");
        }
        //        String tokenKey = Constants.TOKEN_PREFIX + request.getHeader("username") + ":" + token;
        Set<String> keys = redisTemplate.keys(Constants.TOKEN_PREFIX + "*" + token);
        if(keys == null || keys.size() == 0){
            throw new RuntimeException("当前用户未登录！");

        }
        String tokenKey = (String) keys.toArray()[0];
        //3.使用token去redis中查看，有没有对应的loginUser
        return redisTemplate.getObject(tokenKey, new TypeReference<>() {});

    }

    public static void main(String[] args) {
        //使用Java发送http请求
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://whois.pconline.com.cn/ipJson.jsp?&json=true", String.class);
        System.out.println(forEntity.getBody());
    }
}
