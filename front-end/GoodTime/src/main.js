import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueAwesomeSwiper from 'vue-awesome-swiper'
import {fetch as fetchPolyfill} from 'whatwg-fetch'

import './assets/js/rem'

import 'swiper/dist/css/swiper.css'

import filters from '@/common/filter'

Object.keys(filters).forEach(key => {
	Vue.filter(key, filters[key])
})


Vue.use(VueAwesomeSwiper)


Vue.config.productionTip = false

new Vue({
  router,
  store,	
  render: h => h(App)
}).$mount('#app')
