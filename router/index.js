import Vue from 'vue'
import VueRouter from 'vue-router' 

//导入login组件
const Login = () => import('../components/Login.vue')
//导入注册组件
const Register = () => import('../components/Register.vue')
//导入主页面组件
const Home = () => import('../components/Home.vue')
//导入欢迎页组件
const Welcome = () => import('../components/Welcome.vue')
//导入个人信息组件
const User = () => import('../components/User.vue')
//导入设备管理页面
const Device = () => import('../components/Device.vue')
//导入新增报修页面
const Add = () =>import('../components/Repair/add.vue')
//导入报修记录页面
const Records = () =>import('../components/Repair/records.vue')
// 导入设备操作界面
const Operation = () =>import('../components/operation.vue')

Vue.use(VueRouter)


const router = new VueRouter({
  routes:[
    {
      path: '/',
      redirect:'/login'
    },
    {
      path: '/login',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: Login
    },
    {
      path:'/register',
      component:Register
    },
    {
      path:'/home',
      component:Home,
      redirect:'/welcome',
      children: [
        {path:'/welcome', component:Welcome},
        {path:'/user',component:User},
        {path:'/device',component:Device},
        {path:'/add',component:Add},
        {path:'/records',component:Records},
        {path:'/operation',component:Operation}
      ]
    }
  ]
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  //未登录情况下只可以跳转登录和注册页面
  if (to.path === '/login' || to.path === '/register') return next()
  // 获取token
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) return next('/login')
  else next()

})
export default router
