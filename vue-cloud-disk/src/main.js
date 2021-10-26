import Vue from 'vue'
import App from './App.vue'
import router from './router/index.js'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import qs from 'qs'
import md5 from 'js-md5'

import Video from 'video.js'
import 'video.js/dist/video-js.css'

Vue.prototype.$video = Video
Vue.prototype.$share = "http://www.shiningstars.cn/AyCloud/#/shareFileCode/"
Vue.prototype.$host = "http://localhost:8080/cloud/vue/"
Vue.prototype.$hostFile = "http://47.99.39.91/cloud/vue/file/"
// Vue.prototype.$axios = axios    //全局注册，使用方法为:this.$axios
Vue.prototype.$qs = qs           //全局注册，使用方法为:this.qs
Vue.prototype.$md5 = md5    
Vue.http = Vue.prototype.$http = axios  //全局注册，使用方法为:this.$axios
Vue.prototype.$appid= ""
Vue.prototype.$appsecret= ""
let appid= ""
let appsecret= ""
Vue.prototype.$sign=md5(appid + appsecret)

Vue.config.productionTip = false
Vue.use(ElementUI)

new Vue({
  render: h => h(App),
  router,
}).$mount('#app')
