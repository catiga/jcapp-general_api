import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// import iView from 'iview'
import VueAwesomeSwiper from 'vue-awesome-swiper'
import {fetch as fetchPolyfill} from 'whatwg-fetch'

import './assets/js/rem'

//import './assets/iconfont/iconfont.css'
import 'swiper/dist/css/swiper.css'

import filters from '@/common/filter'
// import GLOBAL from '@/common/global.js'

store.dispatch('getLogo')

Object.keys(filters).forEach(key => {
	Vue.filter(key, filters[key])
})


Vue.use(VueAwesomeSwiper)
// Vue.use(iView)


Vue.config.productionTip = false

new Vue({
  router,
  store,	
  render: h => h(App)
}).$mount('#app')
