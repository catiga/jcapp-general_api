import Vue from 'vue'
import Router from 'vue-router'
import index from './views/index.vue'
import videoDetails from './views/videoDetails.vue'
import cinemaList from './views/cinemaList.vue'
import moviecinemaList from './views/moviecinemaList.vue'
import login from './views/login.vue'
import myCoupons from './views/myCoupons.vue'
import personalCenter from './views/personalCenter.vue'
import myTicket from './views/myTicket.vue'
import votingCode from './views/votingCode.vue'
import myCards from './views/myCards.vue'
import purchasingCard from './views/purchasingCard.vue'
import bindCard from './views/bindCard.vue'
import cardDetails from './views/cardDetails.vue'
import myIntegral from './views/myIntegral.vue'
import integrationRule from './views/integrationRule.vue'
import cityList from './views/cityList.vue'
import schedule from './views/schedule.vue'
import seats from './views/seats.vue'
import confirmOrder from './views/confirmOrder.vue'
import storeList from './views/storeList.vue'
import storeOrder from './views/storeOrder.vue'
import rechargeAmount from './views/rechargeAmount.vue'
import ScavengingPointMea from './views/ScavengingPointMea'
import ConfirmGoodsOrder from './views/ConfirmGoodsOrder'
import ScanGoodsOrder from './views/ScanGoodsOrder'
import scanLogin from './views/scanLogin'
import scanPurchasingCard from './views/scanPurchasingCard'
import kaikaSuccess from './views/kaikaSuccess'
import GoIngvideoDetails from './views/GoIngvideoDetails'
import GoodsCode from './views/GoodsCode'
import modifiedData from './views/modifiedData'
import couponsForOrder from './views/couponsForOrder'
import couponsForGoods from './views/couponsForGoods'

Vue.use(Router)

export default new Router({
  routes: [
    { path: '/index',name: 'index', component: index },
    { path: '/', name: 'storeList', component: storeList },
    { path: '/cinemaList/:mid',name: 'cinemaList', component: cinemaList },
    
    { path: '/videoDetails/:mid/:index',name: 'videoDetails', component: videoDetails },
    { path: '/moviecinemaList/:mid',name: 'moviecinemaList', component: moviecinemaList },
    { path: '/login/:apid',name: 'login', component: login },
    { path: '/myCoupons/',name: 'myCoupons', component: myCoupons },
    { path: '/personalCenter/:st?',name: 'personalCenter', component: personalCenter },
    { path: '/myTicket/',name: 'myTicket', component: myTicket },
    { path: '/votingCode/:on',name: 'votingCode', component: votingCode },
    { path: '/myCards/',name: 'myCards', component: myCards },
    { path: '/purchasingCard/:CardNm',name: 'purchasingCard', component: purchasingCard },
    { path: '/bindCard/',name: 'bindCard', component: bindCard },
    { path: '/cardDetails/',name: 'cardDetails', component: cardDetails },
    { path: '/myIntegral/',name: 'myIntegral', component: myIntegral },
    { path: '/integrationRule/',name: 'integrationRule', component: integrationRule },
    { path: '/cityList/',name: 'cityList', component: cityList },
    { path: '/schedule/:cinemaId/:mid/:index', name: 'schedule',component: schedule },
    { path: '/seats/:planId/:lastUpdateTime/:hallId/:cinemaId/:plan_date', name: 'seats', component: seats },
    { path: '/confirmOrder/:tnum', name: 'confirmOrder', component: confirmOrder },
    { path: '/storeOrder/:tnum', name: 'storeOrder', component: storeOrder },
    { path: '/rechargeAmount/:CardNm', name: 'rechargeAmount', component: rechargeAmount },
    { path: '/ScavengingPointMea/:tableNum/:sid?', name: 'ScavengingPointMea', component: ScavengingPointMea },
    { path: '/ConfirmGoodsOrder/:Goods', name: 'ConfirmGoodsOrder', component: ConfirmGoodsOrder },
    { path: '/ScanGoodsOrder/:on/:tableNum', name: 'ScanGoodsOrder', component: ScanGoodsOrder },
    { path: '/scanLogin/:apid',name: 'scanLogin',component: scanLogin },
    { path: '/scanPurchasingCard',name: 'scanPurchasingCard',component: scanPurchasingCard },
    { path: '/kaikaSuccess',name: 'kaikaSuccess',component: kaikaSuccess },
    { path: '/GoIngvideoDetails',name: 'GoIngvideoDetails',component: GoIngvideoDetails },
    { path: '/GoodsCode/:on/:index/:apid',name: 'GoodsCode',component: GoodsCode },
    { path: '/modifiedData',name: 'modifiedData',component: modifiedData },
    { path: '/couponsForOrder/:order_no',name: 'couponsForOrder',component: couponsForOrder },
    { path: '/couponsForGoods/:order_no',name: 'couponsForGoods',component: couponsForGoods },
  ]
})
