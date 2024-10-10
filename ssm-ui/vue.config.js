module.exports = {
    devServer: {
        port: 8088,  // 设置开发服务器的端口
        proxy: 'http://localhost:9090',  // 配置代理
        client: { 
            overlay: false  // 关闭浏览器中的红色错误提示层
        }
    }
}
