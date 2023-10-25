import Vue from 'vue'
import App from './App.vue'
import router from './router'
 //引入element-ui的全部组件
 import ElementUI from 'element-ui'
 //引入element-ui的css
 import 'element-ui/lib/theme-chalk/index.css'
 //使用elementUI
 Vue.use(ElementUI) 

 // 导入全局样式表
import './assets/css/global.css'
import axios from 'axios'
 
//配置URL路径
axios.defaults.baseURL = "http://127.0.0.1:8088/api/"

//配置axios拦截器
axios.interceptors.request.use(config => {
  // 为请求头对象，添加TOKEN验证的 Authorization 字段
  config.headers.Authorization = window.sessionStorage.getItem('token')
  return config
  // 最后必须 return config
})
//使用axios
Vue.prototype.$http = axios
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
