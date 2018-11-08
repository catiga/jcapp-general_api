<template>
    <div style="background:#EFEFF4;">
        <div class="order-card-box">
            <div class="order-card-con-bg">
                <div class="order-card-con">
                    <div class="order-card-title">
                        <p>
                            <span class="card-title-name">{{movieDetails.film_name}} <b class="card-title-sore">{{movieDetails.ticket_sum}}张</b></span>
                            <span class="card-title-right" style="width: 1.6rem;">付款剩余时间</span>
                        </p>
                    </div>
                    <div class="card-title-right-time">{{msg}}</div>
                    <div class="order-card-time">
                        <p>{{movieDetails.plan_date}} {{movieDetails.plan_time}}</p>
                    </div>
                    <div class="order-card-address">
                        <p>{{movieDetails.store_name}}</p>
                    </div>
                    <div class="order-card-seat">
                        <p>{{movieDetails.hall_name}} <span v-for="item in movieDetails.seats"> {{item.seat_sr}}排{{item.seat_sc}}座 </span> </p>
                    </div>
                    <div class="dashed"></div>
                    <div class="order-card-status">
                        <div class="t-cancel">
                            <i class="iconfont icon-cancel"></i>
                            <span>不可退票</span>
                        </div>
                        <div class="t-change">
                            <i class="iconfont icon-cancel"></i>
                            <span>不可改签</span>
                            <!-- <i class="iconfont icon-duigou"></i> -->
                            <!-- <span>开场前1小时可改签（有改签费）</span> -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <section class="myweui-cells weui-cells" style="margin-top: .3rem;">
            <a class="weui-cell myweui-cell cell-height-88 weui-cell_access" href="javascript:;" @click="goCoupons">
                <div class="weui-cell__bd">
                    <p style="font-size: .3rem;color: #2c3e50;">优惠券</p>
                </div>
                <div v-if="o_c" class="weui-cell__ft" style="font-size: .3rem;">{{o_c.batch_title}}</div>
                <div v-else class="weui-cell__ft" style="font-size: .3rem;">点击选择</div>
            </a>
            <div class="weui-cell myweui-cell cell-height-88">
                <div class="weui-cell__bd">
                    <p style="font-size: .3rem;">票价总计</p>
                </div>
                <div class="weui-cell__ft" style="color: #00c203;font-size: .3rem;">{{movieDetails.total_amount/100}}元</div>
            </div>
        </section>

        <section class="vip-card" v-if="CardData == '-1' || CardData == null ">
            <div class="weui-cells myweui-cells" style="margin-top: .3rem;" @click="GoKaika">
                <a class="weui-cell myweui-cell vip-cell weui-cell_access" href="javascript:;">
                    <div class="weui-cell__bd">
                        <div></div>
                        <p class="vip-cell-title" style="font-size: .3rem;">{{logo.proj_name}}会员卡</p>
                        <p class="vip-cell-con">开卡影票最低<span>25元起</span></p>
                    </div>
                    <div class="weui-cell__ft">
                    </div>
                </a>
            </div>
        </section>

        <!-- 选择支付方式  开始 -->
        <section class="myweui-cells weui-cells_checkbox" style="margin-top: .3rem;" v-else>
            <label v-if="CardData" class="weui-cell myweui-cell cell-height-88 weui-check__label" for="x11" style="background: #fff;" @click.prevent="changePrice('101001')">
                <div class="weui-cell__bd">
                    <p>会员余额支付（余额：{{CardData[0].balance/100}}元）</p>
                </div>
                <div class="weui-cell__ft">
                    <input type="radio" class="weui-check" name="radio1" id="x11" :checked="pay_methods === '101001' ? true : false">
                    <span class="weui-icon-checked" style="transform:scale(.8)"></span>
                </div>
            </label>
            <label class="weui-cell myweui-cell cell-height-88 weui-check__label" for="x12" style="background: #fff;" @click.prevent="changePrice('201101')">
                <div class="weui-cell__bd">
                    <p>微信支付</p>
                </div>
                <div class="weui-cell__ft">
                    <input type="radio" name="radio1" class="weui-check" id="x12" :checked="pay_methods === '201101' ? true : false">
                    <span class="weui-icon-checked" style="transform:scale(.8)"></span>
                </div>
            </label>
        </section>
        <!-- 选择支付方式  结束 -->

        <!-- 观影标配 开始 -->
       <!--  <div class="myweui-panel weui-panel weui-panel_access">
            <div class="weui-panel__hd" style="color: #000;">观影标配</div>
            <div class="weui-panel__bd">
                <div class="weui-media-box weui-media-box_appmsg">
                    <div class="weui-media-box__hd">
                        <img class="weui-media-box__thumb" src="http://pe1s.static.pdr365.com/tencent/icon/baomihua.png" alt="">
                    </div>
                    <div class="weui-media-box__bd">
                        <div class="select-goods-info">
                            <div class="select-goods-info-title">大桶爆米花</div>
                            <div class="select-goods-info-vol">85oz大桶爆米花</div>
                            <div class="select-goods-info-price">
                                <span>40.9元</span>
                                <s>44.9元</s>
                            </div>
                        </div>
                    </div>
                    <div class="select-goods-cal">
                        <p>观影用户推荐</p>
                        <div class="select-goods-cal-item">
                            <div class="reduce-btn cal-btn"> <i class="iconfont icon-jianhao"></i> </div>
                            <input class="cal-input" value="1">
                            <div class="add-btn cal-btn"> <i class="iconfont icon-jiahao2"></i> </div>
                        </div>
                    </div>
                </div>
                <div class="weui-media-box weui-media-box_appmsg">
                    <div class="weui-media-box__hd">
                        <img class="weui-media-box__thumb" src="http://pe1s.static.pdr365.com/tencent/icon/baomihua.png" alt="">
                    </div>
                    <div class="weui-media-box__bd">
                        <div class="select-goods-info">
                            <div class="select-goods-info-title"><span class="goods-info-title-logo">双人</span>双人套餐</div>
                            <div class="select-goods-info-vol">85oz大桶爆米花1桶+大杯可乐</div>
                            <div class="select-goods-info-price">
                                <span>40.9元</span>
                                <s>44.9元</s>
                            </div>
                        </div>
                    </div>
                    <div class="select-goods-cal">
                        <p>观影用户推荐</p>
                        <div class="select-goods-cal-item">
                            <div v-if="false" class="reduce-btn cal-btn">  <i class="iconfont icon-jianhao"></i> </div>
                            <input v-if="false" class="cal-input" value="0">
                            <div class="add-btn cal-btn"> <i class="iconfont icon-jiahao2"></i> </div>
                        </div>
                    </div>
                </div>
                <div class="weui-media-box weui-media-box_appmsg">
                    <div class="weui-media-box__hd">
                        <img class="weui-media-box__thumb" src="http://pe1s.static.pdr365.com/tencent/icon/baomihua.png" alt="">
                    </div>
                    <div class="weui-media-box__bd">
                        <div class="select-goods-info">
                            <div class="select-goods-info-title"><span class="goods-info-title-logo">单人</span>单人套餐</div>
                            <div class="select-goods-info-vol">85oz大桶爆米花1桶+大杯可乐</div>
                            <div class="select-goods-info-price">
                                <span>40.9元</span>
                                <s>44.9元</s>
                            </div>
                        </div>
                    </div>
                    <div class="select-goods-cal">
                        <p>观影用户推荐</p>
                        <div class="select-goods-cal-item">
                            <div v-if="false" class="reduce-btn cal-btn"> <i class="iconfont icon-jianhao"></i> </div>
                            <input v-if="false" class="cal-input" value="0">
                            <div class="add-btn cal-btn"> <i class="iconfont icon-jiahao2"></i> </div>
                        </div>
                    </div>
                </div>
            </div>
        </div> -->
        <!-- 观影标配 结束 -->

        <!-- 购票须知 开始 -->
        <div class="myweui-panel weui-panel weui-panel_access" style="padding-bottom:2.5rem;">
            <div class="weui-panel__hd" style="color:#000;">购票须知</div>
            <div class="weui-panel__bd">
                <div class="weui-media-box weui-media-box_text">
                    <p class="weui-media-box__desc myweui-media-box__desc">
                        1、由于设备故障等不可抗力因素，存在少量场次取消的情况，会进行退票退款<br /> 
                        2、由于影院系统不稳定等因素，存在出票失败的情况，会进行退款<br /> 
                        3、取票码不再通过短信发送，可在“钱包-卡券”或“我的-电影票”中查看<br /> 
                        4、下单即代表你同意<a href="javascript:void(0);" style="color: #00c203">《{{logo.proj_name}}用户服务协议》</a><br /> 
                        5、订单编号{{movieDetails.order_no}}
                    </p>
                </div>
            </div>
        </div>
        <!-- 购票须知 结束 -->

        <!-- 确认订单 开始 -->
        <div class="confirm-btn-container" style="position: fixed; bottom:0;">
            <div class="btn-container">
                <div class="confirm-btn-total-price" @click="isShow">
                    <p>
                        <span>合计:</span>
                        <span style="color: #00c203">{{movieDetails.pay_amount/100}}元</span>
                        <span class="iconfont icon-triangle-up" :class="trangle ? 'trangle-down' : 'trangle-up'" style="font-size:.26rem;color:#BCBEC1;"></span>
                    </p>
                </div>
                <div class="confirm-btn">
                <a href="javascript:;" class="weui-btn weui-btn_primary" style="font-size:0.34rem;color: #fff;" @click="GoPay">确认支付</a>
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
                        <span>电影票</span>
                        <!-- <span class="color-a9 ml-2">(含服务费1元/张)</span> -->
                    </div>
                    <div class="card-con-item">
                        <p class="color-a9">
                            <span>原价票</span>
                            <span>{{movieDetails.total_amount/movieDetails.ticket_sum/100}}元 X {{movieDetails.ticket_sum}}</span>
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
        <!-- 确认订单 结束 -->
        <div class="weui-mask" id="iosMask" style="display: none;z-index: 98;" @click="isShow"></div>
        <div id="loadingToast" v-if="loading">
            <div class="weui-mask_transparent"></div>
            <div class="weui-toast">
                <i class="weui-loading weui-icon_toast"></i>
                <p class="weui-toast__content">加载中</p>
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
    import {mapState,mapActions} from 'vuex'
    
    export default {
        data() {
            return {
                loading: true,
                Pwd_tip: false,
                on:'',
                movieDetails:'',
                CardData:'',
                trangle: false,
                isFixed: false,
                msg: '15:00',
                tnum:'',
                other:'',
                tip_msg:"",
                Pwd:'',
                order_no: "", //订单id,
                o_c: "",
                offer_amount: "", //优惠价格
                offer_show: false,
                pay_methods: '101001'
            }
        },
        beforeRouteEnter: function (to, from, next) {
            document.title = '确认订单';
            next();
        },
        created() {
            let o_c_str = sessionStorage.getItem("o_c");
            if(o_c_str) {
            	this.o_c = JSON.parse(o_c_str);
            }
        },
        computed: {
			...mapState(['logo']),
		},
        mounted: function () {
            this.on = Cookies.get('orderNo');
            this.tnum = this.$route.params.tnum;
            this.GetAccountCard();
            this.GetOrderDetails();//获取订单详情
            this.countTime('900');
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
            PwdTip:function(){
                var page = this;
                page.Pwd_tip = false;
            },
            GoPay:function(){
                var page = this;
                var tnum = page.tnum;
                page.loading = true;
                var token = Cookies.get('_lac_k_');
                if (page.CardData == '-1' || page.CardData == null ) {
                    let coupon_id = page.o_c.id || "";
                    var url = '/general_api/api/auth/GetWxObj?ct=201101&token='+token+'&tnum='+ tnum + "&coupon_id=" + coupon_id +'&ts='+Date.parse(new Date());
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
                    if (document.getElementById("x12").checked) {
                        let coupon_id = page.o_c.id || "";
                        var url = '/general_api/api/auth/GetWxObj?ct=201101&token='+token+"&coupon_id=" + coupon_id + '&tnum='+ tnum+'&ts='+Date.parse(new Date());
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
                let coupon_id = page.o_c.id || "";
                var url = '/general_api/api/auth/GetWxObj?ct=101001&token='+token+"&coupon_id=" + coupon_id+'&tnum='+ tnum +'&pwd='+page.Pwd+'&unicode='+page.CardData[0].mc_num+'&ts='+Date.parse(new Date());
                page.loading = true;
                page.Pwd_tip = false;
                fetch(url).then(r => r.json()).then(d => {
                	page.loading = false;
                    if (d.code == '-1') {
                        page.showTip(d.text);
                        page.Pwd_tip = false;
                        page.Pwd = '';
                    }else if(d.code == '0' && d.text == '交易成功' ){
                        page.loading = false;
                        page.Pwd_tip = false;
                        page.showTip('支付成功');
                        page.Pwd = '';
                        setTimeout(function(){
                            page.$router.push({name: 'votingCode', params: { on: page.on }})
                        },100)
                    }else if(d.code == '0' && d.other[0].code == '-1' ){
                        page.loading = false;
                        page.Pwd_tip = false;
                        page.showTip(d.other[0].err_code_desc);
                        page.Pwd = '';
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
            onBridgeReady:function(){
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
                    if (res.err_msg == "get_brand_wcpay_request:ok") {
                        // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                        // 跳转到个人中心页面已显示出票信息
                        location.href="/general_api/tcss/index#/myTicket"
                        // alert('您已支付成功');
                        // this.$router.push({name: 'votingCode', params: { on: this.on }})
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
                location.href="/general_api/tcss/index/?#/purchasingCard/purchasing"
                // this.$router.push({name: 'purchasingCard'});
            },
           // var maxtime = 60 * 60; //一个小时，按秒计算，自己调整!   
            countTime:function(time) {
                var page = this;
                var time = Number(time);
                if(time >=0){
                    var minutes = Math.floor(time / 60);
                    var seconds = Math.floor(time % 60);
                    var msg = minutes + ":" + seconds;
                    page.msg = msg;                 
                }else{
                    page.msg = '00:00'
                    // alert("时间到，结束!");
                    return;
                }
                setTimeout(function(){
                    time -= 1;
                    page.countTime(time);
                }, 1000)
             },
            //获取会员卡信息接口
            GetAccountCard:function(){
                var page = this;
                var token = Cookies.get('_lac_k_');
                var url = '/general_api/api/auth/GetAccountCard?token=' +token+ '&ts='+Date.parse(new Date());
                fetch(url).then(r => r.json()).then(d => {
                    // if (d.available) {
                        page.CardData = d.obj;
                    // }    
                    page.loading = false;
                });
               
            },
            GetOrderDetails:function(){
                var page = this;
                let coupon_id = this.o_c.id || "";
                var url = '/general_api/api/GetTicketOrderDetails?order_no='+page.on+ "&coupon_id=" + coupon_id +'&ts='+Date.parse(new Date());
                fetch(url).then(r => r.json()).then(d => {
                    if (d.code == 0) {
                        page.movieDetails = d.data;
                        page.order_no = d.data.order_no;
                        page.loading = false;
                        if(d.pref_coupon === null) {
                        	sessionStorage.removeItem("o_c");
                        } else if(d.pref_coupon === "") {
                        	weui.topTips('当前优惠券无法使用', 5000);
                            sessionStorage.removeItem("o_c");
                            this.o_c = "";
                        } else {
                            page.offer_amount = d.pref_coupon.prep_amount / 100;
                            page.offer_show = true;
                        }
                        
                    }
                });
            },
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
                let t_num = this.tnum;
                this.$router.push({name: "couponsForOrder", params: {order_no:num,tnum:t_num}});
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

                let unicode = this.CardData[0].card_code || "";
                let coupon_id = this.o_c.id || "";
                let url = '/general_api/api/change_price?tnum=' + this.tnum + "&order_no=" + this.on + "&ct=" + ct + "&unicode=" + unicode + "&coupons=" + coupon_id + '&ts='+Date.parse(new Date());
                
                fetch(url).then(r => r.json()).then(d => {
                    this.loading = false;
                    console.log(d);
                    if (d.code == 0) {
                        this.movieDetails = d.data;
                    }
                }).catch(e => {
                    this.loading = false;
                })
            },
        }
    }
</script>
<style>
/**************** 订单卡片 开始 ****************/

.clear::after {
    display: block;
    content: '';
    clear: both;
}

.order-card-box {
    width: 100%;
    height: 4.22rem;
    background: #7B8793;
}

.order-card-con-bg {
    width: 100%;
    height: 100%;
    padding: .37rem .3rem;
    box-sizing: border-box;
}

.order-card-con {
    position: relative;
    width: 100%;
    height: 100%;
    background: #fff;
    border-radius: .1rem;
    padding: .4rem;
    box-sizing: border-box;
    font-size: .26rem;
}

.order-card-con::before,
.order-card-con::after {
    position: absolute;
    bottom: .76rem;
    content: '';
    display: block;
    background: #7B8793;
    width: .3rem;
    height: .3rem;
    border-radius: 50%;
    z-index: 10;
}

.order-card-con::before {
    left: -0.15rem;
}

.order-card-con::after {
    right: -0.15rem;
}

.order-card-con .dashed {
    position: absolute;
    left: 0;
    bottom: .9rem;
    width: 100%;
    height: 0;
    border-bottom: 1px dashed #EAEAEA;
}

.order-card-title {
    margin-bottom: .1rem;
}

.card-title-name {
    font-size: .34rem;
}

.card-title-sore {
    margin-left: .3rem;
    font-size: .26rem;
    font-weight: 500 !important;
}

.card-title-right {
    font-size: .26rem;
    color: #a9a9a9;
}

.order-card-title p {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
}

.order-card-status {
    position: absolute;
    bottom: .2rem;
    display: flex;
    justify-content: space-between;
}

.t-cancel,
.t-change {
    color: #a9a9a9;
}

.t-cancel {
    margin-right: .3rem;
}

.icon-duigou {
    color: #00c203;
}

.card-title-right-time {
    position: absolute;
    top: .9rem;
    right: .4rem;
}

/**************** 订单卡片 结束 ****************/

.myweui-panel {
    margin-top: .3rem !important;
}

.select-goods-info-title {
    font-size: .34rem;
}

.select-goods-info-vol {
    width: 12em;
    font-size: .26rem;
    color: #a9a9a9;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.select-goods-info-price span:first-child {
    font-size: .34rem;
    margin-right: .2rem;
    color: #00c203;
}

.select-goods-info-price span:last-child {
    font-size: .26rem;
    color: #a9a9a9;
}

.select-goods-cal {
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.select-goods-cal p {
    color: #ed7d29;
    font-size: .22rem;
    background: #fff5e4;
    padding: 0 .1rem;
    border-radius: .06rem;
    margin-bottom: .3rem;
}

.select-goods-cal-item {
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 1.6rem;
    font-size: .34rem;
}

.cal-btn {
    font-size: .46rem;
    color: #00c203;
}

.cal-input {
    display: block;
    width: .4rem;
    height: .4rem;
    font-size: .34rem;
    text-align: center;
    line-height: .4rem;
}

.goods-info-title-logo {
    font-size: .26rem;
    color: #FF3958;
    padding: 0 .05rem;
    border: 1px solid #FF3958;
    border-radius: .06rem;
    margin-right: .15rem;
}

.myweui-media-box__desc {
    display: block;
    color: #a9a9a9;
    font-size: .22rem;
    line-height: 2em;
}
.weui-cells_checkbox .weui-icon-checked::before {
    font-size: .4rem;
}

/*********** 确认总价 ***********/

.confirm-btn-container {
    position: relative;
    left: 0;
    z-index: 100;
    margin-top: 3rem;
    background: #fff;
    width: 100%;
    padding: 0 .3rem;
    padding-bottom: .2rem;
    box-sizing: border-box;
}

.btn-container {
    position: relative;
    z-index: 99;
}

.confirm-btn-total-price {
    font-size: .34rem;
    text-align: center;
    padding-top: .4rem;
    padding-bottom: .26rem;
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
.vip-cell {
    padding-top: .4rem!important;
    padding-bottom: .4rem!important;
}
.vip-cell-title {
    font-size: .34rem;
    margin-bottom: .3rem;
}
.vip-cell-con {
    font-size: .26rem;
    color: #a9a9a9;
}
.vip-cell-con span {
    color: #00c203;
}
</style>