<template>
    <div style="background:#EFEFF4;width:100%;height:100%;">
        <!-- 观影标配 开始 -->
        <div class="store-weui-panel weui-panel weui-panel_access" style="margin-bottom: 2.8rem;">
            <div class="weui-panel__bd">
                <div class="weui-media-box weui-media-box_appmsg" v-for="item in orderDetails">
                    <div class="weui-media-box__hd">
                        <img class="weui-media-box__thumb" :src="'http://pe1s.static.pdr365.com/'+item.pic_url" alt="">
                    </div>
                    <div class="weui-media-box__bd">
                        <div class="select-goods-info">
                            <div class="select-goods-info-title">{{item.goods_name}}</div>
                            <div class="select-goods-info-vol">{{item.goods_name}}</div>
                            <div class="select-goods-info-price">
                                <span>{{item.pay_amount/100}}元</span>
                                <!-- <s>44.9元</s> -->
                            </div>
                        </div>
                    </div>
                    <div class="store-goods-cal">
                        <p>已售{{item.id}}</p>
                        <div class="select-goods-cal-item">
                            *{{item.buy_num}}
                            <!-- <div class="reduce-btn cal-btn"> <i class="iconfont icon-jianhao"></i> </div>
                            <input class="cal-input" value="1">
                            <div class="add-btn cal-btn"> <i class="iconfont icon-jiahao2"></i> </div> -->
                        </div>
                    </div>
                </div>
                <div class="weui-media-box weui-media-box_text">
                    <div class="weui-media-box__desc" style="display: flex;justify-content:space-between;">
                        <div class="store-order-slogan">
                            <i class="iconfont icon-duigou"></i>
                            <span>随时退、过期退</span>
                        </div>
                        <!-- <div class="store-order-deadline">有效期:2018.6.25-2018.8.25</div> -->
                    </div>
                </div>
            </div>
        </div>
        <!-- 观影标配 结束 -->
        <section class="myweui-cells weui-cells">
            <a class="weui-cell myweui-cell cell-height-88 weui-cell_access" href="javascript:;" style="color:#2c3e50;">
                <div class="weui-cell__bd">
                    <p>优惠券</p>
                </div>
                <div class="weui-cell__ft">无可用</div>
            </a>
            <div class="weui-cell myweui-cell cell-height-88">
                <div class="weui-cell__bd">
                    <p>小计</p>
                </div>
                <div class="weui-cell__ft" style="color: #00c203">{{data.pay_amount/100}}元</div>
            </div>
        </section>
        
        <!-- 会员卡 开始 -->
        <section class="vip-card" v-if="CardData == '-1'" style="padding-bottom: 2.8rem;">
            <div class="weui-cells myweui-cells" @click="GoKaika">
                <a class="weui-cell myweui-cell vip-cell weui-cell_access" href="javascript:;">
                    <div class="weui-cell__bd">
                        <div></div>
                        <p class="vip-cell-title" style="color: #2c3e50;">腾讯影院会员卡</p>
                        <p class="vip-cell-con">开卡卖品最低<span>25元起</span></p>
                    </div>
                    <div class="weui-cell__ft">
                    </div>
                </a>
            </div>
        </section>
        <!-- 会员卡结束 -->

        <!-- 确认订单 开始 -->
        <div class="confirm-btn-container" style="position: fixed; bottom: 0; margin-top: 0;">
            <div class="btn-container">
                <div class="confirm-btn-total-price">
                    <p>
                        <span>合计:</span>
                        <span style="color: #00c203">{{data.pay_amount/100}}元</span>
                    </p>
                </div>
                <div class="confirm-btn">
                <a href="javascript:;" class="weui-btn weui-btn_primary" style="font-size:0.34rem; color: #fff;" v-on:click="godo_goodOrder">确认支付</a>
                </div>
            </div>
        </div>
        <!-- 确认订单 结束 -->
        <div class="js_dialog" id="iosDialog2" v-if="tip_msg">
            <div class="weui-mask"></div>
            <div class="weui-dialog">
                <div class="weui-dialog__bd">{{tip_msg}}</div>
                <div class="weui-dialog__ft">
                    <a href="javascript:;" v-on:click="tip_msg=''" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
                </div>
            </div>
        </div>
         <div id="loadingToast" v-if="loading">
            <div class="weui-mask_transparent"></div>
            <div class="weui-toast">
                <i class="weui-loading weui-icon_toast"></i>
                <p class="weui-toast__content">加载中</p>
            </div>
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
                tnum: '',
                tableNum:'',
                on:'',
                data:'',
                orderDetails:'',
                other:'',
                tip_msg:"",
                loading:true,
                CardData: '',
            }
        },beforeRouteEnter:function(to, from, next){
            //当组件加载时自动调用此函数 函数结尾必须next();
            document.title="订单结算";
            next();
        },mounted: function () {
            this.tnum = this.$route.params.tnum;
            this.on =  Cookies.get('goodsOrderNo');
            this.godo_Order();
            this.GetAccountCard();
        },
        methods: {
            showTip:function(msg,ts){
                var page = this;
                page.tip_msg = msg;
                if(ts){
                    clearTimeout(window.maxtm);
                    window.maxtm = setTimeout(function(){page.tip_msg="";},ts)
                }
            },
            godo_Order:function(){
                var page = this;
                var token = Cookies.get('_lac_k_');
                var url = '/general_api/api/auth/ScanOrderDetais?token='+token+'&on=' + this.on+'&ts='+Date.parse(new Date()); //tnum换取微信支付数据
                fetch(url).then(r => r.json()).then(d => {
                    if (d.available) {
                        page.data = d.data;
                        page.orderDetails = d.data.items;
                        page.loading = false;
                    }else if (d.available == false && d.messages[0] == 'user_no_login') {
                        page.showTip('用户未登录');
                    }
                });
                
            },
            //获取会员卡信息接口
            GetAccountCard:function(){
                var page = this;
                console.log('11')
                var url = '/general_api/api/GetAccountCard?ts='+Date.parse(new Date());
                fetch(url).then(r => r.json()).then(d => {
                    // if (d.available) {
                    page.CardData = d.obj;
                    // }    
                    page.loading = false;
                });
            },
            //卖品支付
            godo_goodOrder:function(){
                var page = this;
                console.log('11')
                page.loading = true;
                var token = Cookies.get('_lac_k_');
                // this.$router.push({name: 'ScanGoodsOrder' , params: { on : this.on , tableNum : this.tableNum }})
                var url = '/general_api/api/auth/GetWxObj?ct=201101&tnum=' + page.tnum + '&token='+token+'&ts='+Date.parse(new Date()); //tnum换取微信支付数据
                fetch(url).then(r => r.json()).then(d => {
                    console.log(d)
                    if (d.code == -1) {
                        page.loading = false;
                        page.showTip(d.text);
                    } else if (d.code == 0) {
                        page.loading = false;
                        page.other = d.other;
                        page.onBridgeReady();
                    }else if (d.available == false && d.messages[0] == 'user_no_login') {
                        page.showTip('用户未登录');
                    }
                });
                
            },onBridgeReady: function () {
                var other = this.other;
                WeixinJSBridge.invoke('getBrandWCPayRequest', {
                    "appId" : other.appId, //公众号名称，由商户传入
                    "timeStamp" : other.timeStamp, //时间戳，自1970年以来的秒数
                    "nonceStr" : other.nonceStr, //随机串
                    "package" : other.package,
                    "signType" : "MD5", //微信签名方式:
                    "paySign" : other.paySign

                //微信签名 
                }, function(res) {
                    // alert(JSON.stringify(res))
                    if (res.err_msg == "get_brand_wcpay_request:ok") {
                        // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                        // 跳转到个人中心页面已显示出票信息
                        // alert('您已支付成功，请到个人中心查看卖品');
                        // this.$router.push({name: 'myTicket'})
                    } else if (res.err_msg == "get_brand_wcpay_request:cancel"){
                        // this.showTip(您取消了付款);
                        alert('您取消了付款');
                    } else if (res.err_msg == "get_brand_wcpay_request:fail") {
                        alert('支付失败');
                    }
                });
            },
            //开卡
            GoKaika:function(){
                location.href="/general_api/tcss/index#/purchasingCard/purchasing"
                // this.$router.push({name: 'purchasingCard'});
            },
        }
    }
</script>
<style>

</style>