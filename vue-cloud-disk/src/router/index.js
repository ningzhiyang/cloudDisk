// 1. 引入vue和vue-router组件核心对象，并在vue中通过use注册vue-router组件
import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);
import Home from "../views/Home.vue"
import Login from "../views/Login.vue"
import Register from "../views/Register.vue"
import Right from "../components/Right.vue"
import MP4 from "../components/mp4.vue"
import ShareFile from "../components/ShareFile.vue"
import ShareFileCode from "../views/ShareFileCode.vue"
// 2. 暴露vue-router对象，并在vue-router里面编写路由，提供给main.js调用
export default new Router({
  // 设置路由模式为‘history’，去掉默认的#
  // base: '/vue/',
  mode: "history",
  // assetsPublicPath: './',
  routes:[
    // 路由列表
    {
      name:"Home",
      path: "/home",
      component: Home,
    },
	{
	  name:"MP4",
	  path: "/MP4",
	  component: MP4,
	},
	{
	  name:"ShareFileCode",
	  path: "/shareFileCode/:checkcode",
	  component: ShareFileCode
	},
	{
	  name:"ShareFile",
	  path: "/shareFile",
	  component: ShareFile
	},
	{
	  name:"Login",
	  path: "/",
	  component: Login,
	  alias:"/login"
	},
	{
	  name:"Register",
	  path: "/register",
	  component: Register
	},
	
  ]
})