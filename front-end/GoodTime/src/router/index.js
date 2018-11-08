import Vue from 'vue'
import Router from 'vue-router'
import routes from './routers'
import store from '@/store'


Vue.use(Router)

const router = new Router({
    routes,
})

router.beforeEach((to, from, next) => {
    store.dispatch('getLogo');
    next();
})

export default router