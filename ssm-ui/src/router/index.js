// 导入用来创建路由和确定路由模式的两个方法
import {
    createRouter,
    createWebHistory
} from 'vue-router'

import store from '@/store'
import storage from '@/util/storage.js'
/**
 * 定义路由信息
 * 
 */
const routes = [
	{
	    name: 'login',
	    path: '/login',
	    component: () => import('@/components/login'),
	},
	 {
	    name: 'main',
	    path: '/main',
	    component: () => import('@/components/main'),
		children:[
			{
				name: 'user',
				path: '/user',
				component: () => import('@/components/system/user'),
			},
			{
				name: 'log',
				path: '/log',
				component: () => import('@/components/system/log'),
			},
			
		]
	}
]

// 创建路由实例并传递 `routes` 配置
// 我们在这里使用 html5 的路由模式，url中不带有#，部署项目的时候需要注意。
const router = createRouter({
    history: createWebHistory(),
    routes, 
})


// 全局的路由守卫 ，会在每次路由跳转的时候执行
//这个可以取代app.vue中的跳转
router.beforeEach((to) => {
	//每次需要判断一下是否登录，如果没有登录则去路由到登录页面，否则放行
	//1.如果去的是登录页面就放行
	if(to.name === 'login'){
		return true
	}
	//2.检查是否登录，如果已经登录则放行
	console.log(store.getters.isLogin)
	if(!store.getters.isLogin){
		//去storage中查看,如果没有就去登录页面
		if(!storage.getSessionObject("loginUser")){
			router.push({name:'login'})
		}else{
			//重新加载用户信息
			store.dispatch("RECOVER_USER")
			//加载权限信息
			store.dispatch("GET_INFO")
		}
		
	}
	
	//3.没有登录就跳转到登录页面
    return true
})

// 讲路由实例导出
export default router