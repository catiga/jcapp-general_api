import index from '@/views/index.vue'
import videoDetails from '@/views/videoDetails.vue'
import cinemaList from '@/views/cinemaList.vue'
import moviecinemaList from '@/views/moviecinemaList.vue'
import login from '@/views/login.vue'
import myCoupons from '@/views/myCoupons.vue'
import personalCenter from '@/views/personalCenter.vue'
import myTicket from '@/views/myTicket.vue'
import votingCode from '@/views/votingCode.vue'
import myCards from '@/views/myCards.vue'
import purchasingCard from '@/views/purchasingCard.vue'
import bindCard from '@/views/bindCard.vue'
import cardDetails from '@/views/cardDetails.vue'
import myIntegral from '@/views/myIntegral.vue'
import integrationRule from '@/views/integrationRule.vue'
import cityList from '@/views/cityList.vue'
import schedule from '@/views/schedule.vue'
import seats from '@/views/seats.vue'
import confirmOrder from '@/views/confirmOrder.vue'
import storeList from '@/views/storeList.vue'
import storeOrder from '@/views/storeOrder.vue'
import rechargeAmount from '@/views/rechargeAmount.vue'
import ScavengingPointMea from '@/views/ScavengingPointMea.vue'
import ConfirmGoodsOrder from '@/views/ConfirmGoodsOrder.vue'
import ScanGoodsOrder from '@/views/ScanGoodsOrder.vue'
import scanLogin from '@/views/scanLogin.vue'
import scanPurchasingCard from '@/views/scanPurchasingCard.vue'
import kaikaSuccess from '@/views/kaikaSuccess.vue'
import GoIngvideoDetails from '@/views/GoIngvideoDetails.vue'
import GoodsCode from '@/views/GoodsCode.vue'
import modifiedData from '@/views/modifiedData.vue'
import couponsForOrder from '@/views/couponsForOrder.vue'
import couponsForGoods from '@/views/couponsForGoods.vue'
import goodsDetails from '@/views/goodsDetails.vue'

export default [
      { path: '/',name: 'index', component: index },
      { path: '/index', name: 'storeList', component: storeList },
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
      { path: '/goodsDetails',name: 'goodsDetails',component: goodsDetails },
    ]


    // export default [
    //   { path: '/index',name: 'index', component: () => import('@/views/index.vue') },
    //   { path: '/', name: 'storeList', component: () => import('@/views/storeList.vue') },
    //   { path: '/cinemaList/:mid',name: 'cinemaList', component: () => import('@/views/cinemaList.vue') },
    //   { path: '/videoDetails/:mid/:index',name: 'videoDetails', component: () => import('@/views/videoDetails.vue') },
    //   { path: '/moviecinemaList/:mid',name: 'moviecinemaList', component: () => ('@/views/moviecinemaList.vue') },
    //   { path: '/login/:apid',name: 'login', component: () => import('@/views/login.vue') },
    //   { path: '/myCoupons/',name: 'myCoupons', component: () => import('@/views/myCoupons.vue') },
    //   { path: '/personalCenter/:st?',name: 'personalCenter', component: () => import('@/views/personalCenter.vue') },
    //   { path: '/myTicket/',name: 'myTicket', component: () => ('@/views/myTicket.vue') },
    //   { path: '/votingCode/:on',name: 'votingCode', component: () => ('@/views/votingCode.vue') },
    //   { path: '/myCards/',name: 'myCards', component: () => ('@/views/myCards.vue') },
    //   { path: '/purchasingCard/:CardNm',name: 'purchasingCard', component: () => import('@/views/purchasingCard.vue') },
    //   { path: '/bindCard/',name: 'bindCard', component: () => import('@/views/bindCard.vue') },
    //   { path: '/cardDetails/',name: 'cardDetails', component: () => import('@/views/cardDetails.vue') },
    //   { path: '/myIntegral/',name: 'myIntegral', component: () => import('@/views/myIntegral.vue') },
    //   { path: '/integrationRule/',name: 'integrationRule', component: () => import('@/views/integrationRule.vue') },
    //   { path: '/cityList/',name: 'cityList', component: () => import('@/views/cityList.vue') },
    //   { path: '/schedule/:cinemaId/:mid/:index', name: 'schedule',component: () => import('@/views/schedule.vue') },
    //   { path: '/seats/:planId/:lastUpdateTime/:hallId/:cinemaId/:plan_date', name: 'seats', component: () => import('@/views/seats.vue') },
    //   { path: '/confirmOrder/:tnum', name: 'confirmOrder', component: () => import('@/views/confirmOrder.vue') },
    //   { path: '/storeOrder/:tnum', name: 'storeOrder', component: () => import('@/views/storeOrder.vue') },
    //   { path: '/rechargeAmount/:CardNm', name: 'rechargeAmount', component: () => import('@/views/rechargeAmount.vue') },
    //   { path: '/ScavengingPointMea/:tableNum/:sid?', name: 'ScavengingPointMea', component: () => import('@/views/ScavengingPointMea.vue') },
    //   { path: '/ConfirmGoodsOrder/:Goods', name: 'ConfirmGoodsOrder', component: () => import('@/views/ConfirmGoodsOrder.vue') },
    //   { path: '/ScanGoodsOrder/:on/:tableNum', name: 'ScanGoodsOrder', component: () => import('@/views/ScanGoodsOrder.vue') },
    //   { path: '/scanLogin/:apid',name: 'scanLogin',component: () => import('@/views/scanLogin.vue') },
    //   { path: '/scanPurchasingCard',name: 'scanPurchasingCard',component: () => ('@/views/scanPurchasingCard.vue') },
    //   { path: '/kaikaSuccess',name: 'kaikaSuccess',component: () => import('@/views/kaikaSuccess.vue') },
    //   { path: '/GoIngvideoDetails',name: 'GoIngvideoDetails',component: () => import('@/views/GoIngvideoDetails.vue') },
    //   { path: '/GoodsCode/:on/:index/:apid',name: 'GoodsCode',component: () => import('@/views/GoodsCode.vue') },
    //   { path: '/modifiedData',name: 'modifiedData',component: () => import('@/views/modifiedData.vue') },
    //   { path: '/couponsForOrder/:order_no',name: 'couponsForOrder',component: () => import('@/views/couponsForOrder.vue') },
    //   { path: '/couponsForGoods/:order_no',name: 'couponsForGoods',component: () => import('@/views/couponsForGoods.vue') },
    // ]
