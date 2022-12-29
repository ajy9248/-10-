const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,

  devServer: {
    //设置主机地址
    host : 'localhost',
    //设置默认端口
    port : 8080,
    //设置自动打开浏览器
    open : true,

    proxy: {
      '/prediction/*': {
        target: 'http://localhost:8084/',
        secure: false,
        changeOrigin: true,
        ws: false
      },
      '/student/*': {
        target: 'http://localhost:8084/',
        secure: false,
        changeOrigin: true,
        ws: false
      },
      '/teacher/*': {
        target: 'http://localhost:8084/',
        secure: false,
        changeOrigin: true,
        ws: false
      },
      '/score/*': {
        target: 'http://localhost:8084/',
        secure: false,
        changeOrigin: true,
        ws: false
      },
      '/datacenter/*': {
        target: 'http://localhost:8084/',
        secure: false,
        changeOrigin: true,
        ws: false
      }
    }
  }
})
