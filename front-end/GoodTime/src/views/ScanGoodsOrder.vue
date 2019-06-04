<template>
    <div>
        <div class="bg"></div>
        <div class="ScanOrder-top">
            <img style="width: 100%; height: auto;" src="https://ex.piaodaren.com/img_server/_common/icon/scan_order_process.jpg">
        </div>
        <div class="confirmGoodsO-top">当前桌号 <span>{{tableNum}}</span></div>
        <div class="store-weui-panel weui-panel weui-panel_access" style="width: 100%;margin-top: .2rem;">
            <div class="weui-panel__bd" style="margin-bottom: .2rem;">
                <p class="alreadyGoods">已点餐品</p>
                <div class="weui-media weui-media-box_appmsg" v-for="item in orderDetails" style="margin-left:.3rem; padding: .3rem .3rem .3rem 0;">
                    <div class="weui-media-box__hd" style="width: 1rem;height: 1rem;">
                        <img class="weui-media-box__thumb" :src="'http://pe1s.static.pdr365.com/'+item.pic_url" alt="">
                    </div>
                    <div class="weui-media-box__bd" style="text-align: left;">
                        <div class="select-goods-info">
                            <div class="select-goods-info-title" style="font-size: .28rem;font-weight: bold;">{{item.goods_name}}</div>
                            <div class="select-goods-info-vol" style="font-size: .2rem;color: #8D9398;">{{item.goods_name}}</div>
                            <div class="select-goods-info-price" style="margin-top: .1rem;">
                                <span style="font-size: .24rem;color: #FF6231; font-weight: bold;">x {{item.buy_num}}</span>
                                <!-- <s>44.9元</s> -->
                            </div>
                        </div>
                    </div>
                    <div class="store-goods-cal">
                        <p style="font-size: .28rem;color: #000; font-weight: bold;">¥{{item.unit_amount/100}}</p>
                    </div>
                </div>
                <p class="xiaoji">小计 <span> ¥{{data.pay_amount/100}}</span></p>
            </div>
        </div>
        <!-- <div class="remark-box">
            <span>备注</span>
            <input type="text" placeholder="口味、偏好等要求" name="">
        </div> -->
        <div class="erCode">
            <img src="https://ex.piaodaren.com/img_server/_common/icon/yp_qrcode.jpeg">
            <p>关注"好时光部落"公众号领取更多优惠</p>
        </div>
        
    </div>
</template>
<script>
    import Cookies from 'js-cookie'
    import cartControl from '_c/cartControl/cartControl.vue'
    import shopCart from '_c/shopCart/shopCart.vue'
    import comboCart from '_c/comboCart/comboCart.vue'
    import {
        mapState,
        mapGetters,
        mapActions,
    } from 'vuex'
    export default {
        data() {
            return {
                tableNum:'',
                orderDetails:'',
                data:'',
                on:'',
            }
        },
        computed: {
            count() {
                return 0
            }
        },
        mounted:function(){
            this.tableNum = this.$route.params.tableNum;
            this.on = this.$route.params.on;
            this.godo_Order();
        },
        methods: {
            godo_Order:function(){
                var page = this;
                var token = Cookies.get('_lac_k_');
                var url = '/general_api/api/auth/ScanOrderDetais?token='+token+'&on=' + page.on + '&ts='+Date.parse(new Date()); //tnum换取微信支付数据
                fetch(url).then(r => r.json()).then(d => {
                    if (d.available) {
                        page.data = d.data;
                        page.orderDetails = d.data.items;
                    }
                });
                
            }
            
        }
    }
</script>
<style>
.confirmGoodsO-top{
    width: 100%;
    text-align: left;
    height: .88rem;
    line-height: .88rem;
    padding-left: .3rem;
    background: #fff;
    color: #000;
    font-size: .28rem;
    font-weight: bold;
}
.confirmGoodsO-top span{
    display: inline-block;
    color: #09BB07;
    font-weight: normal;
}
.alreadyGoods{
    line-height: .8rem;
    text-align: left;
    margin-left: .3rem;
    font-size: .3rem;
    color: #000;
    font-weight: bold;
    border-bottom: 1px solid #e5e5e5;
}
.xiaoji{
    width: auto;
    text-align: right;
    padding-right: .3rem;
    margin-left: .3rem;
    height: .8rem;
    line-height: .8rem;
    border-top: 1px solid #e5e5e5;
    font-size: .2rem;
}
.xiaoji span{
    font-size: .36rem;
    color: #FF6231;
    font-weight: bold;
}






 .clear::after {
     content: '';
     display: block;
     clear: both;
 }

.nav-tabs {
    position: relative;
    width: 100%;
    height: 1rem;
    background: #FAFAFA;
    z-index: 10;
}
.nav-tabs::after {
    content: " ";
    position: absolute;
    left: 0;
    right: 0;
    height: 1px;
    color: #e5e5e5;
    bottom: -3px;
    border-top: 1px solid #DADADA;
    -webkit-transform-origin: 0 0;
    transform-origin: 0 0;
    -webkit-transform: scaleY(.5);
    transform: scaleY(.5);
    z-index: 1;
}

.nav-tab-item {
    float: left;
    width: 33.3333%;
    line-height: 1rem;
    text-align: center;
    font-size: .3rem;
    color: #353535;
    border-bottom: 2px solid #FAFAFA;
    box-sizing: border-box;
}

.nav-tabs .active {
    color: #1aad19;   
    border-color: #1aad19;
}
.clear::after {
    content: '';
    display: block;
    clear: both;
}
.store-weui-panel {
    overflow: initial;
}
.store-goods-cal {
    color: #a9a9a9;
    font-size: .26rem;
}
.store-goods-cal p {
    text-align: center;
}
.make-order-container {
    position: fixed;
    display: flex;
    bottom: 50px;
    left: 0;
    width: 100%;
    height: .96rem;
    background: #fff;
}
.make-order-left {
    width:71%;
    height: 100%;
    padding-left: .3rem;
    box-sizing: border-box;
    border-bottom: 1px solid #DADADA;
}
.make-order-btn {
    width: 29%;
    height: 100%;
    background: #00c203;
    color: #fff;
    line-height: .96rem;
    text-align: center;
    font-size: .34rem;
}
.my-dot {
    position: absolute;
    top: 0.1em;
    right: -0.4em;
    background: #f43530;
    padding: .01em;
    width: 1.5em;
    height: 1.5em;
    border-radius: 50%;
    font-size: 0.16rem;
    text-align: center;
    color: #fff;
}
.make-order-left {
    display: flex;
}
.make-order-total-price {
    margin-left: .6rem;
    color: #00c203;
    font-size: .36rem;
    line-height: .96rem;
}
.make-order-total-old-price {
    margin-left: .2rem;
    line-height: .96rem;
    color: #a9a9a9;
}
.store-order-slogan {
    color: #6a7fa5;
    font-size: .28rem;
}
.store-order-deadline {
    color: #a9a9a9;
    font-size: .26rem;
}

.bg{
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    width: 100%;
    height: 100%;
    background: #EFEFF4;
    z-index: -1;
}
.erCode{
    width: 100%;
    height: 3.45rem;
    position: relative;
}
.erCode img{
    position: absolute;
    top: 0px;
    right: 0px;
    bottom: 0px;
    left: 0px;
    margin: auto auto;
    width: 2.2rem;
    height: 2.2rem
}
.erCode p{
    width: 100%;
    text-align: center;
    position: absolute;
    top: 3.7rem;
    right: 0px;
    bottom: 0px;
    left: 0px;
    margin: auto auto;
    font-size: .24rem;
}
.ScanOrder-top{
    width: 100%;
    height: 1.2rem;
    background: #FF4848;
}
</style>