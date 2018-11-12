<template>
    <div style="background:#EFEFF4;width:100%;height:100%;">
        <div class="bg"></div>
        <div v-if=" ScanGoods == 'ScanGoods' " style="padding-bottom: 2.8rem;">
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
                    <p class="xiaoji">总价 <span> ¥{{data.total_amount/100}}</span></p>
                </div>
            </div>
            <div class="remark-box">
                <span>备注</span>
                <input type="text" placeholder="口味、偏好等要求" v-model="o_rem"/>
            </div>

            <!-- <div class="paylist-box">
                <p class="alreadyGoods" style="border: none;">支付方式</p>
                <div class="paylist-box">
                    <label>
                        <div class="paylist">
                            <div class="paylist-l">
                                <img src="http://pe1s.static.pdr365.com/1536323856539.jpg">
                                <div class="paylist-content">
                                    <p class="paylist-nm">微信支付</p>
                                </div>
                            </div>
                            <input class="paylist-input" checked="checked" name="sex" type="radio" />
                        </div>
                    </label>
                </div>
            </div> -->

            <!-- 观影标配 结束 -->
            <section class="myweui-cells weui-cells">
                <a class="weui-cell myweui-cell cell-height-88 weui-cell_access" href="javascript:;" style="color:#2c3e50;" @click="goCoupons">
                    <div class="weui-cell__bd">
                        <p>优惠券</p>
                    </div>
                    <div v-if="o_c_1000" class="weui-cell__ft" style="font-size: .3rem;">{{o_c_1000.batch_title}}</div>
                    <div v-else class="weui-cell__ft" style="font-size: .3rem;">点击选择</div>
                </a>
            </section>

            <!-- 会员卡 开始 -->
            <section class="vip-card" v-if="!CardData">
                <div class="weui-cells myweui-cells" @click="GoKaika">
                    <a class="weui-cell myweui-cell vip-cell weui-cell_access" href="javascript:;">
                        <div class="weui-cell__bd">
                            <div></div>
                            <p class="vip-cell-title" style="color: #2c3e50;">{{logo.proj_name}}会员卡</p>
                            <p class="vip-cell-con">开卡卖品最低<span>25元起</span></p>
                        </div>
                        <div class="weui-cell__ft">
                        </div>
                    </a>
                </div>
            </section>
            <!-- 会员卡结束 -->
            <!-- 选择支付方式  开始 -->
            <section class="myweui-cells weui-cells_checkbox" style="margin-top: .3rem;" v-else>
                <label v-if="CardData" class="weui-cell myweui-cell cell-height-88 weui-check__label" for="x10" style="background: #fff;" @click.prevent="changePrice('101001')">
                    <div class="weui-cell__bd">
                        <p style="font-size: 15px;">会员余额支付（余额：{{CardData[0].balance/100}}元）</p>
                    </div>
                    <div class="weui-cell__ft">
                        <input type="radio" class="weui-check" name="radio1" id="x10" :checked="pay_methods === '101001' ? true : false">
                        <span class="weui-icon-checked" style="transform:scale(.8)"></span>
                    </div>
                </label>
                <label class="weui-cell myweui-cell cell-height-88 weui-check__label" for="x20" style="background: #fff;" @click.prevent="changePrice('201101')">
                    <div class="weui-cell__bd">
                        <p style="font-size: 15px;">微信支付</p>
                    </div>
                    <div class="weui-cell__ft">
                        <input type="radio" name="radio1" class="weui-check" id="x20" :checked="pay_methods === '201101' ? true : false">
                        <span class="weui-icon-checked" style="transform:scale(.8)"></span>
                    </div>
                </label>
            </section>
            <!-- 选择支付方式  结束 -->
            <div class="make-order-container clear" style="bottom: 0px; border-top: 1px solid #e5e9ec;">
                <div class="make-order-left">
                    <div style="position:relative;width:0.96rem;height:0.5rem;text-align:center;">
                        <i class="iconfont icon-ai66" style="font-size:.5rem;color:#00c203;line-height:.96rem;"></i>
                        <!-- <span class="my-dot" v-show="count>0">{{count}}</span> -->
                    </div>
                    <div class="make-order-total-price" style="font-size: .4rem; font-weight: bold; color: #000;margin-left: .2rem;">¥ {{data.pay_amount/100}}</div>
                </div>
                <div class="make-order-btn" v-on:click="godo_ScangoodOrder" style="font-size: .28rem; font-weight: bold;">确认并支付</div>
            </div>
        </div>
        <div v-if=" ScanGoods == 'tcssGoods' " style="padding-bottom: 2.8rem;">
            <div class="store-weui-panel weui-panel weui-panel_access">
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
                <a class="weui-cell myweui-cell cell-height-88 weui-cell_access" href="javascript:;" style="color:#2c3e50;" @click="goCoupons">
                    <div class="weui-cell__bd">
                        <p>优惠券</p>
                    </div>
                    <div v-if="o_c_1000" class="weui-cell__ft" style="font-size: .3rem;">{{o_c_1000.batch_title}}</div>
                    <div v-else class="weui-cell__ft" style="font-size: .3rem;">点击选择</div>
                </a>
                <div class="weui-cell myweui-cell cell-height-88">
                    <div class="weui-cell__bd">
                        <p>总价</p>
                    </div>
                    <div class="weui-cell__ft" style="color: #00c203">{{data.total_amount/100}}元</div>
                </div>
            </section>
            
            <!-- 会员卡 开始 -->
            <section class="vip-card" v-if="!CardData">
                <div class="weui-cells myweui-cells" @click="GoKaika">
                    <a class="weui-cell myweui-cell vip-cell weui-cell_access" href="javascript:;">
                        <div class="weui-cell__bd">
                            <div></div>
                            <p class="vip-cell-title" style="color: #2c3e50;">{{logo.proj_name}}会员卡</p>
                            <p class="vip-cell-con">开卡卖品最低<span>25元起</span></p>
                        </div>
                        <div class="weui-cell__ft">
                        </div>
                    </a>
                </div>
            </section>
            <!-- 会员卡结束 -->
            <!-- 选择支付方式  开始 -->
            <section class="myweui-cells weui-cells_checkbox" style="margin-top: .3rem;">
                <label v-if="CardData" class="weui-cell myweui-cell cell-height-88 weui-check__label" for="x10" style="background: #fff;" @click.prevent="changePrice('101001')">
                    <div class="weui-cell__bd">
                        <p style="font-size: 15px;">会员余额支付（余额：{{CardData[0].balance/100}}元）</p>
                    </div>
                    <div class="weui-cell__ft">
                        <input type="radio" class="weui-check" name="radio1" id="x10" :checked="pay_methods === '101001' ? true : false">
                        <span class="weui-icon-checked" style="transform:scale(.8)"></span>
                    </div>
                </label>
                <label class="weui-cell myweui-cell cell-height-88 weui-check__label" for="x20" style="background: #fff;" @click.prevent="changePrice('201101')">
                    <div class="weui-cell__bd">
                        <p style="font-size: 15px;">微信支付</p>
                    </div>
                    <div class="weui-cell__ft">
                        <input type="radio" name="radio1" class="weui-check" id="x20" :checked="pay_methods === '201101' ? true : false">
                        <span class="weui-icon-checked" style="transform:scale(.8)"></span>
                    </div>
                </label>
            </section>
            <!-- 选择支付方式  结束 -->

            <!-- 确认订单 开始 -->
            <div class="confirm-btn-container" style="position: fixed; bottom: 0; margin-top: 0;z-index: 98;">
                <div class="btn-container">
                    <div class="confirm-btn-total-price" @click="isShow">
                        <p>
                            <span>合计:</span>
                            <span style="color: #00c203">{{data.pay_amount/100}}元</span>
                            <span class="iconfont icon-triangle-up" :class="trangle ? 'trangle-down' : 'trangle-up'" style="font-size:.26rem;color:#BCBEC1;"></span>
                        </p>
                    </div>
                    <div class="confirm-btn">
                    <a href="javascript:;" class="weui-btn weui-btn_primary" style="font-size:0.34rem; color: #fff;" v-on:click="godo_goodOrder">确认支付</a>
                    </div>
                </div>

                <!-- 确认订单详情 开始 -->
                <div class="order-details-card" style="display:none;">
                    <div class="order-details-card-title">
                        <p>结算明细</p>
                        <div class="cancel-card-show" @click="isShow">取消</div>
                    </div>
                    <div class="order-details-card-con">
                        <div class="card-con-title">
                            <span>商品</span>
                        </div>
                        <div class="card-con-item">
                            <p class="color-a9" v-for="(item,index) in data.items" :key="index">
                                <span>{{item.goods_name}}</span>
                                <span>{{item.unit_amount/100}}元 X {{item.buy_num}}</span>
                            </p>
                            <p class="color-a9" v-if="offer_show">
                                <span>优惠券</span>
                                <span>减：{{offer_amount}}元</span>
                            </p>
                        </div>
                    </div>
                </div>
                <!-- 确认订单详情 结束-->


            </div>
        </div>
        <div class="js_dialog" id="iosDialog2" v-if="tip_msg">
            <div class="weui-mask" style="z-index: 100;"></div>
            <div class="weui-dialog">
                <div class="weui-dialog__bd">{{tip_msg}}</div>
                <div class="weui-dialog__ft">
                    <a href="javascript:;" v-on:click="tip_msg=''" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
                </div>
            </div>
        </div>
        <div class="weui-mask" id="iosMask" style="display: none;z-index: 88;" @click="isShow"></div>
        <div id="loadingToast" v-if="loading">
            <div class="weui-mask_transparent"></div>
            <div class="weui-toast">
                <i class="weui-loading weui-icon_toast"></i>
                <p class="weui-toast__content">加载中</p>
            </div>
        </div>
        <div class="js_dialog" id="iosDialog1" v-if="Pwd_tip">
            <div class="weui-mask" style="z-index: 100;"></div>
            <div class="weui-dialog">
                <div class="weui-dialog__bd">请输入会员卡密码</div>
                <div class="weui-dialog__bd nified">
                    <input v-model="Pwd" style="border: 1px solid #ccc;width: 70%;height: .6rem;margin-bottom: .3rem;" type="password" name="">
                </div>
                <div class="weui-dialog__ft">
                    <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default" v-on:click="PwdTip">取消</a>
                    <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" v-on:click="setPwd">确定</a>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import Cookies from 'js-cookie'
    import {
    	mapState,
        mapActions,
    } from 'vuex'
    
    export default {
        data() {
            return {
                Pwd_tip: false,
                tnum: '',
                tableNum:'',
                on:'',
                data:'',
                orderDetails:'',
                other:'',
                tip_msg:"",
                loading:true,
                CardData:'',
                Pwd:'',
                ScanGoods:'',
                order_no: "", //订单id,
                o_c_1000: "",
                offer_amount: "", //优惠价格
                trangle: false,
                offer_show: false,
                o_rem: '',
                pay_methods: '101001'
            }
        },beforeRouteEnter:function(to, from, next){
            //当组件加载时自动调用此函数 函数结尾必须next();
            document.title="订单结算";
            next();
        },computed: {
        	...mapState(['logo']),
        },
        created() {
            let o_c_1000_str = sessionStorage.getItem("o_c_1000");
            if(o_c_1000_str) {
            	this.o_c_1000 = JSON.parse(o_c_1000_str);
            }
        },
        mounted: function () {
            this.ScanGoods = this.$route.params.Goods;
            this.on = Cookies.get('goodsOrderNo');
            this.tnum = Cookies.get('goosdOrderTnum');
            this.tableNum = Cookies.get('tableNum');
            this.godo_Order();
            this.GetAccountCard();
        },
        methods: {
            ...mapActions(['ClearCart']),
            showTip:function(msg,ts){
                var page = this;
                page.tip_msg = msg;
                if(ts){
                    clearTimeout(window.maxtm);
                    window.maxtm = setTimeout(function(){page.tip_msg="";},ts)
                }
            },
            //获取订单详情
            godo_Order:function(){
                var page = this;
                var token = Cookies.get('_lac_k_');
                let coupon_id = page.o_c_1000.id || "";
                var url = '/general_api/api/auth/ScanOrderDetais?token='+token+'&on=' + this.on + "&coupon_id=" + coupon_id+'&ts='+Date.parse(new Date()); //tnum换取微信支付数据
                fetch(url).then(r => r.json()).then(d => {
                    if (d.available) {
                        page.loading = false;
                        page.data = d.data;
                        page.order_no = d.data.order_no;
                        page.orderDetails = d.data.items;
                        if(d.pref_coupon === null) {
                        	sessionStorage.removeItem("o_c_1000");
                        } else if(d.pref_coupon === "") {
                        	weui.topTips('当前优惠券无法使用', 5000);
                            sessionStorage.removeItem("o_c_1000");
                            this.o_c_1000 = "";
                        } else {
                            page.offer_amount = d.pref_coupon.prep_amount / 100;
                            page.offer_show = true;
                        }
                    }else if(d.available == false && d.messages[0] == 'user_no_login') {
                        page.loading = false;
                        page.showTip('用户未登录');
                    }else{
                        page.showTip(d.messages[0]);
                        page.loading = false;
                    }
                });
                
            },
            //获取会员卡信息接口
            GetAccountCard:function(){
                var page = this;
                var token = Cookies.get('_lac_k_');
                var url = '/general_api/api/auth/GetAccountCard?token='+token+'&ts='+Date.parse(new Date());
                fetch(url).then(r => r.json()).then(d => {
                    if (d.available) {
                        page.CardData = d.obj;
                        if(d.obj) {
                            this.changePrice('101001');
                        }
                    }    
                    page.loading = false;
                });
               
            },
            PwdTip:function(){
                var page = this;
                page.Pwd_tip = false;
            },
            //扫码点餐支付
            godo_ScangoodOrder:function(){
                var page = this;
                page.loading = true;
                var tnum = page.tnum;
                var token = Cookies.get('_lac_k_');
                if (page.CardData == '-1' || !page.CardData) {
                    let coupon_id = page.o_c_1000.id || "";
                    let o_rem = page.o_rem;
                    if(!o_rem) {
                    	o_rem = '';
                    }
                    o_rem = encodeURIComponent(encodeURIComponent(o_rem));
                    var url = '/general_api/api/auth/GetWxObj?ct=201101&tnum=' + tnum + "&coupon_id=" + coupon_id + '&token='+token+'&o_rem='+o_rem+'&ts='+Date.parse(new Date()); //tnum换取微信支付数据
                    fetch(url).then(r => r.json()).then(d => {
                        console.log(d)
                        if (d.code == -1) {
                            page.loading = false;
                            page.showTip(d.text);
                        } else if (d.code == 0) {
                            page.loading = false;
                            page.other = d.other;
                            page.onBridgeReady();
                            this.ClearCart();
                        }else if (d.available == false && d.messages[0] == 'user_no_login') {
                            page.loading = false;
                            page.showTip('用户未登录');
                        }
                    });
                }else{
                    if (document.getElementById("x20").checked) {
                        let coupon_id = page.o_c_1000.id || "";
                        let o_rem = page.o_rem;
	                    if(!o_rem) {
	                    	o_rem = '';
	                    }
	                    o_rem = encodeURIComponent(encodeURIComponent(o_rem));
                        var url = '/general_api/api/auth/GetWxObj?ct=201101&token='+token+'&o_rem='+o_rem+"&coupon_id=" + coupon_id +'&tnum='+ tnum+'&ts='+Date.parse(new Date());
                        fetch(url).then(r => r.json()).then(d => {
                            console.log(d)
                            if (d.code == -1) {
                                page.loading = false;
                                page.showTip(d.text);
                            }else if (d.code == 0) {
                                page.loading = false;
                                page.other = d.other;
                                page.onBridgeReady();
                            }else if(d.available == false && d.messages[0] == 'user_no_login') {
                                page.showTip('用户未登录');
                            }
                        });
                    }else{
                        page.loading = false;
                        page.Pwd_tip = true;
                    }
                }
            },
            setPwd:function(){
                var page = this;
                var token = Cookies.get('_lac_k_');
                var tnum = page.tnum;
                let coupon_id = page.o_c_1000.id || "";
                let o_rem = page.o_rem;
                if(!o_rem) {
                	o_rem = '';
                }
                o_rem = encodeURIComponent(encodeURIComponent(o_rem));
                var url = '/general_api/api/auth/GetWxObj?ct=101001&token='+token+'&o_rem='+o_rem+"&coupon_id=" + coupon_id+'&tnum='+ tnum +'&pwd='+page.Pwd+'&unicode='+page.CardData[0].mc_num+'&ts='+Date.parse(new Date());
                page.loading = true;
                page.Pwd_tip = false;
                fetch(url).then(r => r.json()).then(d => {
                	page.loading = false;
                    if (d.code == '-1') {
                        page.loading = false;
                        page.Pwd_tip = false;
                        page.showTip(d.text);
                        page.Pwd = '';
                    }else if(d.code == '0'){
                        page.loading = false;
                        page.Pwd_tip = false;
                        page.showTip('支付成功');
                        page.Pwd = '';
                        setTimeout(function(){
                            if ( page.ScanGoods == 'tcssGoods' ) {
                                location.href="/general_api/tcss/index#/myTicket"
                            }else{
                               location.href="/general_api/tcss/index#/ScanGoodsOrder/"+page.on+"/"+page.tableNum 
                            }
                        },100)
                    }else if(d.available == false && d.messages[0] == 'user_no_login') {   
                        page.Pwd_tip = false;
                        page.Pwd = '';
                        page.showTip('用户未登录');
                    }else if (d.available == false && d.messages[0] == 'trade_status_invalid') {
                        page.Pwd_tip = false;
                        page.Pwd = '';
                        page.showTip('请勿重复支付');
                    }
                });  
            },
            //卖品支付
            godo_goodOrder:function(){
                var page = this;
                page.loading = true;
                var tnum = page.tnum;
                var token = Cookies.get('_lac_k_');
                if (page.CardData == '-1' || !page.CardData) {
                    let coupon_id = page.o_c_1000.id || "";
                    var url = '/general_api/api/auth/GetWxObj?ct=201101&tnum=' + tnum +"&coupon_id=" + coupon_id+ '&token='+token+'&ts='+Date.parse(new Date()); //tnum换取微信支付数据
                    fetch(url).then(r => r.json()).then(d => {
                        console.log(d)
                        if (d.code == -1) {
                            page.loading = false;
                            page.showTip(d.text);
                        } else if (d.code == 0) {
                            page.loading = false;
                            page.other = d.other;
                            page.onBridgeReady();
                            this.ClearCart();
                        }else if (d.available == false && d.messages[0] == 'user_no_login') {
                            page.loading = false;
                            page.showTip('用户未登录');
                        }
                    });
                }else{
                    if (document.getElementById("x20").checked) {
                        let coupon_id = page.o_c_1000.id || "";
                        var url = '/general_api/api/auth/GetWxObj?ct=201101&token='+token+'&tnum='+ tnum +"&coupon_id=" + coupon_id+'&ts='+Date.parse(new Date());
                        fetch(url).then(r => r.json()).then(d => {
                            console.log(d)
                            if (d.code == -1) {
                                page.loading = false;
                                page.showTip(d.text);
                            }else if (d.code == 0) {
                                page.loading = false;
                                page.other = d.other;
                                page.onBridgeReady();
                            }else if(d.available == false && d.messages[0] == 'user_no_login') {
                                page.showTip('用户未登录');
                            }
                        });
                    }else{
                        page.loading = false;
                        page.Pwd_tip = true;
                    }
                }
            },
            onBridgeReady: function () {
                var page = this;
                var other = page.other;
                WeixinJSBridge.invoke('getBrandWCPayRequest', {
                    "appId" : other.appId, //公众号名称，由商户传入
                    "timeStamp" : other.timeStamp, //时间戳，自1970年以来的秒数
                    "nonceStr" : other.nonceStr, //随机串
                    "package" : other.package,
                    "signType" : "MD5", //微信签名方式:
                    "paySign" : other.paySign
                //微信签名 
                }, function(res) {
                    if (res.err_msg == "get_brand_wcpay_request:ok") {
                        // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠
                        if ( page.ScanGoods == 'tcssGoods' ) {
                            location.href="/general_api/tcss/index#/myTicket"
                        }else{
                           location.href="/general_api/tcss/index#/ScanGoodsOrder/"+page.on+"/"+page.tableNum 
                        }
                        // this.$router.push({name: 'ScanGoodsOrder' , params: { on : this.on , tableNum : this.tableNum }})
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
            	location.href="/general_api/tcss/index/?#/purchasingCard/purchasing";
                //location.href="http://test.m.iplaysky.com/general_api/tcss/index#/purchasingCard/purchasing"
                // this.$router.push({name: 'purchasingCard'});
            },
            //底部浮窗展示
            isShow: function () {
                var self = this;
                this.trangle = !this.trangle;
                var $iosMask = $('#iosMask');
                var $iosCard = $('.order-details-card');
                if (this.trangle) {
                    $iosMask.fadeIn(200);
                    $iosCard.slideDown(400);
                } else {
                    $iosMask.fadeOut(200);
                    $iosCard.slideUp(400);
                }
            },
            // 前往选择优惠券
            goCoupons() {
                let num = this.order_no;
                let ScanGoods = this.ScanGoods;
                this.$router.push({name: "couponsForGoods", params: {order_no:num,Goods:ScanGoods}});
            },
            /**
             * changePrice - 选择支付方式修改价格
             * 
             * @param {String} ct - 支付方式  （会员卡支付 101001 , 微信支付 201101） 
             * @param {String} tnum - 交易编号
             * @param {String} unicode - 会员标识 
             * @param {String} coupons - 优惠券列表 (逗号分割)
             * @param {String} order_no - 订单编号
             * 
             */
            changePrice(ct)  {
                this.loading = true;
                this.pay_methods = ct;

                let unicode = ct === '101001' ? this.CardData[0].card_code : "";
                let coupon_id = this.o_c_1000.id || "";
                let token = Cookies.get('_lac_k_');
                let url = '/general_api/api/auth/change_price_goods?tnum=' + this.tnum + "&order_no=" + this.on + "&ct=" + ct + "&unicode=" + unicode + "&coupons=" + coupon_id + "&token=" + token + '&ts='+Date.parse(new Date());
                
                fetch(url).then(r => r.json()).then(d => {
                    this.loading = false;
                    if (d.available) {
                        this.data = d.data;
                        this.orderDetails = d.data.items;
                    }
                }).catch(e => {
                    this.loading = false;
                })
            },
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
    font-size: .14rem;
}
.xiaoji span{
    font-size: .36rem;
    color: #FF6231;
    font-weight: bold;
}
.remark-box{
    width: 100%;
    text-align: left;
    height: .88rem;
    line-height: .88rem;
    background: #fff;
    margin-bottom: 0.2rem;
}
.remark-box span{
    display: inline-block;
    font-size: .28rem;
    font-weight: bold;
    margin-left: .3rem;
}
.remark-box input{
    display: inline-block;
    width: 80%;
    font-size: .28rem;
    margin-left: .3rem;
    outline: none;
    border: none;
}
.paylist-box{

}
.paylist-box{
    width: 100%;
    background: #fff;
    margin-bottom: 0; 
    height: auto;
}
.paylist{
    width: auto;
    height:  1.1rem;
    line-height: 1.1rem;
    display:  flex;
    align-items:  center;
    border-bottom: 1px solid #e5e5e5;
    margin-left:  .3rem;  
    justify-content: space-between;
}
.paylist-l{
    display: flex;
    align-items: center;
}
.paylist img{
    width: .53rem;
    height: .5rem;
    border-radius: 50%;
    display: inline-block;
}
.paylist-content{
    width: 4rem;
    margin-left: .3rem;
    display: inline-block;   
}
.paylist-nm{
    line-height: 1;
    font-size: .32rem;    
}
.paylist-hint{
    line-height: 1;
    font-size: .2rem;
    color: #FF6231;
    margin-top: 0.1rem;   
}
.paylist-input{
    width: .3rem;
    height: .3rem;
    border: none;
    outline: none;
    margin-right: .3rem;  
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




.ball-wrapper .ball-container .horiz-ball {
    position: fixed;
    left: 25px;
    bottom: 20px;
    z-index: 100;
    transition: 0.4s all linear;
  }
  .ball-wrapper .ball-container .horiz-ball .ball {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background: #00c203;
  /* 小球本身做加速运动 通过贝塞尔来模拟 */
    transition: 0.4s all cubic-bezier(0.49, -0.29, 0.75, 0.41);
  }


  .order-details-card {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: auto;
    background: #fff;
    transform: translateY(-100%);
    font-size: .26rem;
}

.order-details-card-title {
    position: relative;
}

.order-details-card-title p {
    width: 100%;
    height: .9rem;
    line-height: .9rem;
    text-align: center;
}

.order-details-card-title::after {
    content: " ";
    position: absolute;
    left: 0;
    right: 0;
    height: 1px;
    color: #e5e5e5;
    bottom: 0;
    border-top: 1px solid #e5e5e5;
    -webkit-transform-origin: 0 0;
    transform-origin: 0 0;
    -webkit-transform: scaleY(.5);
    transform: scaleY(.5);
}

.cancel-card-show {
    position: absolute;
    top: 0;
    bottom: 0;
    right: .3rem;
    margin: auto;
    width: 4em;
    height: .9rem;
    line-height: .9rem;
    text-align: right;
    font-size: .26rem;
    color: #a9a9a9;
}
.order-details-card-con {
    padding: .3rem;
    box-sizing: border-box;
}
.card-con-title {
    margin-bottom: 1em;
}
.card-con-title span {
    display: inline-block;
}
.color-a9 {
    color: #a9a9a9;
}
.ml-2 {
    margin-left: .5em;
}
.card-con-item p{
    display: flex;
    justify-content: space-between;
}

.trangle-down {
    display: inline-block;
    transform: rotateZ(180deg);
    transition: all .5s;
}

.trangle-up {
    display: inline-block;
    transform: rotateZ(0deg);
    transition: all .5s;
}

.weui-mask,
.weui-mask_transparent {
    z-index: 99;
}


</style>