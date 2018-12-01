<template>
	<div><!-- 固定 容器-->
		<!--<div class="details-bg"></div>-->
		<div v-if=" CardNm == 'purchasing'|| CardNm == 'Scanpurchasing' ">
			<div class="purchasingcard-top">
				<p class="purchasingcard-top-title">选择等级</p>
			</div>
			<div class="purchasingcard-mid">
				<ul class="purchasingcard-mid-ul">
					<li v-if="RechargeData != -1" v-for="(item,index) in RechargeData" :class=" HId == item.id ? 'purchasingcard-mid-li-true' : 'purchasingcard-mid-li' " v-on:click="godo_choose(item.id,index)">
						<p :class=" HId == item.id ? 'purchasingcard-mid-li-grade-true' : 'purchasingcard-mid-li-grade' " style="height: .35rem;line-height: .35rem;">{{item.h_name}}</p>
						<p  :class=" HId == item.id ? 'purchasingcard-mid-li-introduce-true' : 'purchasingcard-mid-li-introduce' ">{{item.cr_type_desc}}</p>
						<p style="margin-top: .1rem;" :class=" HId == item.id ? 'purchasingcard-mid-li-price-true' : 'purchasingcard-mid-li-price'">¥{{item.getpay/100}}</p>
						<img v-if=" HId == item.id " class="purchasingcard-mid-li-true-img"  src="http://pe1s.static.pdr365.com/tencent/icon/dot_icon.png" />
					</li>
				</ul>
			</div>
			<div class="PurchasingCard-box">
				<div class="PurchasingCard" v-on:click="SshowNum">
					<span class="PurchasingTitle">设置支付密码</span>
					<span class="PurchasingHint">密码用于消费操作时，验证您的身份</span>
					<span class="shezhi" v-if="pwd != ''">已设置</span>
					<div class="vertical2"></div>
				</div>
			</div>
			<div class="purchasingcard-bot">
				<div class="purchasingcard-bot-title">
					支付方式
				</div>
				<div class="purchasingcard-bot-pay">
					<div>
						<img src="http://pe1s.static.pdr365.com/tencent/icon/wechat_pay_icon.png" />
						<p>微信支付</p>
					</div>
					<i class="weui-icon-success weui-icon_msg" style="font-size: .4rem;margin-right: .3rem;"></i>
				</div>
			</div>
			<div class="purchase-box">
				<div class="purchase" v-on:click="godo_purchasing">立即开通</div>
			</div>
			<div class="purchasing-num-box" v-if="one" style="text-align: center;">
				<div class="purchasing-num-bgc" v-on:click="one=false"></div>
				<div class="purchasing-num">
					<div class="purchasing-num-top">
						<img v-on:click="one=false" style="float: left;display: inline-block;width: .4rem;margin-left: .2rem;margin-top: .15rem;" src="http://pe1s.static.pdr365.com/chahao.png">
						<p>设置会员卡支付密码</p>
					</div>
					<input class="purchasing-num-input" v-model="Ticket_one" type="num" placeholder="请输入6位数字密码" readonly="readonly" maxlength="6" clearable>
					<p class="purchasing-num-p">支付密码用于支付操作时，验证您的身份</p>
					<div class='keyboard' style="background: #fff;" @click.stop='TicketKeyPress'>
			            <div class='key-row'>
				            <div class='numInput key-cell borderR' data-num='1' style="border-left: none;">1</div>
				            <div class='numInput key-cell borderR' data-num='2'>2</div>
				            <div class='numInput key-cell borderR' data-num='3'>3</div>
			            </div>
			            <div class='key-row'>
				            <div class='numInput key-cell borderR' data-num='4' style="border-left: none;">4</div>
				            <div class='numInput key-cell borderR' data-num='5'>5</div>
				            <div class='numInput key-cell borderR' data-num='6'>6</div>
			            	<!-- <div class='numInput key-cell' data-num='C' style="border-bottom: none;">清空</div> -->
			            </div>
			            <div class='key-row'>
				            <div class='numInput key-cell borderR' data-num='7' style="border-left: none;border-left:none;">7</div>
				            <div class='numInput key-cell borderR' data-num='8'>8</div>
				            <div class='numInput key-cell borderR' data-num='9'>9</div>
				            <!-- <div class='numInput key-cell' data-num='C' style="border-bottom: none;">取消</div> -->
			            </div>
			            <div class='key-row'>
				            <div class='numInput key-cell' data-num='D' style="background: #C6CBD4;font-size: 0.3rem;">删除</div>
				            <div class='numInput key-cell' data-num='0'>0</div>
				            <div class='numInput key-confirm' data-num='S' style="background: #C6CBD4;font-size: 0.3rem;">确认</div>
			            </div>
			        </div>
				</div>
			</div>

			<div class="purchasing-num-box" v-if="two" style="text-align: center;">
				<div class="purchasing-num-bgc" v-on:click="two=false"></div>
				<div class="purchasing-num">
					<div class="purchasing-num-top">
						<img v-on:click="two=false" style="float: left;display: inline-block;width: .4rem;margin-left: .2rem;margin-top: .15rem;" src="http://pe1s.static.pdr365.com/chahao.png">
						<p>确认会员卡支付密码</p>
					</div>
					<input class="purchasing-num-input" v-model="Ticket_two" type="num" placeholder="请输入6位数字密码" readonly="readonly" maxlength="6" clearable>
					<p class="purchasing-num-p">请在此输入您的支付密码</p>
					<div class='keyboard' style="background: #fff;" @click.stop='TicketTwo'>
			            <div class='key-row'>
				            <div class='numInput key-cell borderR' data-num='1' style="border-left: none;">1</div>
				            <div class='numInput key-cell borderR' data-num='2'>2</div>
				            <div class='numInput key-cell borderR' data-num='3'>3</div>
			            </div>
			            <div class='key-row'>
				            <div class='numInput key-cell borderR' data-num='4' style="border-left: none;">4</div>
				            <div class='numInput key-cell borderR' data-num='5'>5</div>
				            <div class='numInput key-cell borderR' data-num='6'>6</div>
			            	<!-- <div class='numInput key-cell' data-num='C' style="border-bottom: none;">清空</div> -->
			            </div>
			            <div class='key-row'>
				            <div class='numInput key-cell borderR' data-num='7' style="border-left: none;border-left:none;">7</div>
				            <div class='numInput key-cell borderR' data-num='8'>8</div>
				            <div class='numInput key-cell borderR' data-num='9'>9</div>
				            <!-- <div class='numInput key-cell' data-num='C' style="border-bottom: none;">取消</div> -->
			            </div>
			            <div class='key-row'>
				            <div class='numInput key-cell' data-num='D' style="background: #C6CBD4;font-size: 0.3rem;">删除</div>
				            <div class='numInput key-cell' data-num='0'>0</div>
				            <div class='numInput key-confirm' data-num='S' style="background: #C6CBD4;font-size: 0.3rem;">确认</div>
			            </div>
			        </div>
				</div>
			</div>
		</div>
		<div v-if="CardNm == 'recharge' ">
			<div class="purchasingcard-top">
				<p class="purchasingcard-top-title">选择充值金额</p>
			</div>
			<div class="purchasingcard-mid">
				<ul class="purchasingcard-mid-ul"> 
					<li v-if="RechargeData != -1" v-for="(item,index) in RechargeData" :data-index="index" :class=" HId == item.id ? 'purchasingcard-mid-li-true' : 'purchasingcard-mid-li' " v-on:click="godo_choose(item.id,index)">
						<p :data-index="index" :class=" HId == item.id ? 'purchasingcard-mid-li-price-true' : 'purchasingcard-mid-li-price' ">¥{{item.getpay /100}}</p>
						<p :data-index="index" :class=" HId == item.id ? 'purchasingcard-mid-li-introduce-true' : 'purchasingcard-mid-li-introduce' ">{{item.cr_type_desc}}</p>
						<img :data-index="index" v-if=" HId == item.id " class="purchasingcard-mid-li-true-img" src="http://pe1s.static.pdr365.com/tencent/icon/dot_icon.png" />
					</li>
				</ul>
			</div>
			<div class="purchasingcard-bot">
				<div class="purchasingcard-bot-title">
					支付方式
				</div>
				<div class="purchasingcard-bot-pay">
					<div>
						<img src="http://pe1s.static.pdr365.com/tencent/icon/wechat_pay_icon.png" />
						<p>微信支付</p>
					</div>
					<i class="weui-icon-success weui-icon_msg" style="font-size: .4rem;margin-right: .3rem;"></i>
				</div>
			</div>
			<div class="purchase-box">
				<div class="purchase" v-on:click="godo_recharge">立即充值</div>
			</div>
		</div>
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
    //import '../styles/purchasingCard.css' 
	export default {
		data : function(){
			return {
				loading:true,
				tip_msg:"",
				synopsis:'synopsis-none',
				showBtn:'showBtn',
				RechargeData:'',
				id:'',//等级id
				HId:'',//选中id
				Num:'',//卡号
				Ticket_one:'',
				Ticket_two:'',
				pwd:'',
				S:'',//第一次输入的密码
				one: false,
				two: false,
				other:'',
				CardNm:'',//判断是充值还是开卡
				CardNum:'',
				sid: '', // 门店id
			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="腾讯好时光";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
			this.Num = Cookies.get('CardNum');
			this.CardNm = this.$route.params.CardNm;
			this.GradeList();
			this.sid = Cookies.get('s_id');
		},methods:{
			SshowNum:function(){
				this.one = true;
			},showTip:function(msg,ts){
				var page = this;
				page.tip_msg = msg;
				if(ts){
					clearTimeout(window.maxtm);
					window.maxtm = setTimeout(function(){page.tip_msg="";},ts)
				}
			},GradeList:function () {
				var page = this;
		        var url = '/general_api/api/CardGradeList?ts='+Date.parse(new Date());
		        fetch(url).then(r => r.json()).then(d => {
		        	page.loading = false;
		        	page.RechargeData = d.obj;
		        	// }	
		        	page.HId = d.obj[0].id;
		        	page.id = d.obj[0].id;
		        });
			},godo_choose:function(id,index){
				var page = this;
				var list = page.RechargeData;
				for (var i = 0; i < list.length; i++) {
	                if (i == index) {
	                	page.HId = list[index].id;
	                }
	            }
	            page.id = id;
			},godo_purchasing:function(){
				var page = this;
				var mc_pwd = page.pwd;
				var h_id = page.HId;
				if (mc_pwd == '') {
					this.showTip("请设置6位数的密码~",1000);
					return
				}
				page.loading = true;
				// page.showlogin("请稍等~",1300);
                var token = Cookies.get('_lac_k_');
				var url = '/general_api/api/auth/createCardRechargeOrder?token='+token+'&mc_pwd='+ mc_pwd + '&h_id='+h_id + '&sid=' + this.sid +'&ts='+Date.parse(new Date());
		        fetch(url).then(r => r.json()).then(d => {
		        	// console.log(d)
		        	if (d.available) {
		        		// console.log(d)
						page.loading = false;
		        		// page.showlogin("创建成功~",1300);
                		var token = Cookies.get('_lac_k_');
		        		var url = '/general_api/api/auth/GetWxObj?ct=201101&tnum='+ d.obj.data.tnum + '&token='+token+'&ts='+Date.parse(new Date());
				        fetch(url).then(r => r.json()).then(d => {
				        	// console.log(d)
				        	if (d.code == -1) {
				        		page.showTip(d.text);
				        	}else if (d.code == 0) {
				        		page.other = d.other;
				        		page.onBridgeReady();
				        	}else if (d.available == false && d.messages[0] == 'user_no_login') {
	                            page.showTip('用户未登录');
	                        }
				        });
		        	}else if (d.available == false && d.messages[0] == 'user_no_login') {
						page.loading = false;
	                    page.showTip('用户未登录');

	                }else{
						page.loading = false;
		        		page.showTip(d.messages[0]);
		        	}
		        });
			},onBridgeReady:function(){
				var page = this;
				var other = page.other;
				page.loading = false;
				var CardNm = page.CardNm;
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
						if (CardNm == 'Scanpurchasing') {
							page.$router.push({name: 'kaikaSuccess'});
						}else{
							page.$router.go(-1);
						}
					} else if (res.err_msg == "get_brand_wcpay_request:cancel"){
						alert('您取消了付款');
					} else if (res.err_msg == "get_brand_wcpay_request:fail") {
						alert('支付失败');
					}
				});
			},TicketKeyPress(e) {
		       var num = e.target.dataset.num;
		       //不同按键处理逻辑
		       // -1 代表无效按键，直接返回
		       if (num == -1) return false;
		       switch (String(num)) {
		       //小数点
		       case '.':
		        this.TicketDecimalPoint();
		        break;
		       //删除键
		       case 'D':
		        this.TicketDeleteKey();
		        break;
		       //清空键
		       case 'C':
		        this.TicketClearKey();
		        break;
		       //确认键
		       case 'S':
		        this.TicketConfirmKey();
		        break;
		       default:
		        this.TicketNumberKey(num);
		        break;
		       }
		    },
		    //处理小数点函数
		    TicketDecimalPoint() {
		        if (this.Ticket_one.indexOf('.') > -1) return false;
		        if (!this.Ticket_one.length)
		        this.Ticket_one = '0.';
		        else
		        this.Ticket_one = this.Ticket_one + '.';
		    },
		    //处理删除键
		    TicketDeleteKey() {
		        var S = this.Ticket_one;
		        if (!S.length) return false;
		        this.Ticket_one = S.substring(0, S.length - 1);
		    },
		      //处理清空键
		    TicketClearKey() {
		        this.Ticket_one = '';
		    },
		    TicketConfirmKey() {
		        var S = this.Ticket_one;
		        if (!S.length){
		        	this.showTip("您目前未输入~",1000);
			        return false;
		        }
		        if (S.length == 6) {
		        	this.pwd = S;
		        	this.one = false;
		        	this.two = true;
		        }else{
		        	this.showTip("请设置6位数的密码~",1000);
		        }
		        //输入的密码
		    },
		       //处理数字
		    TicketNumberKey(num) {
		    	// console.log("aaa")
		        var S = this.Ticket_one;
		        if (S.length < 6) {
			        if ( S.indexOf('.') > -1 && S.substring(S.indexOf('.') + 1).length < 2)
			        this.Ticket_one = S + num;
			        if (!(S.indexOf('.') > -1)) {
			        if (num == 0 && S.length == 0)
			         this.Ticket_one = '0.';
			        else {
			         if (S.length && Number(S.charAt(0)) === 0) return;
			         this.Ticket_one = S + num;
			        }
			        }
		        }else{
		        	this.showTip("最多只能设置6位数的密码~",1000);
		        }
		    },

		    TicketTwo(e) {
		       var num = e.target.dataset.num;
		       //不同按键处理逻辑
		       // -1 代表无效按键，直接返回
		       if (num == -1) return false;
		       switch (String(num)) {
		       //小数点
		       case '.':
		        this.aTicketDecimalPoint();
		        break;
		       //删除键
		       case 'D':
		        this.aTicketDeleteKey();
		        break;
		       //清空键
		       case 'C':
		        this.aTicketClearKey();
		        break;
		       //确认键
		       case 'S':
		        this.aTicketConfirmKey();
		        break;
		       default:
		        this.aTicketNumberKey(num);
		        break;
		       }
		    },
		    //处理小数点函数
		    aTicketDecimalPoint() {
		        if (this.Ticket_two.indexOf('.') > -1) return false;
		        if (!this.Ticket_two.length)
		        this.Ticket_two = '0.';
		        else
		        this.Ticket_two = this.Ticket_two + '.';
		    },
		    //处理删除键
		    aTicketDeleteKey() {
		        var Two = this.Ticket_two;
		        if (!Two.length) return false;
		        this.Ticket_two = Two.substring(0, Two.length - 1);
		    },
		      //处理清空键
		    aTicketClearKey() {
		        this.Ticket_two = '';
		    },
		    aTicketConfirmKey() {
		        var Two = this.Ticket_two;
		        if (!Two.length){
		        	this.showTip("您目前未输入~",1000);
			        return false;
		        }
		        if (Two.length == 6) {
		        	if ( this.pwd == Two) {
		        		this.pwd = Two;
		        	}else{
		        		this.showTip("两次输入的密码不一致~",1000);
		        		return;
		        	}
		        	// this.pwd = Two;
		        	this.two = false;
		        	this.one = false;
		        	
		        }else{
		        	this.showTip("请设置6位数的密码~",1000);
		        }
		        //输入的密码
		    },
		       //处理数字
		    aTicketNumberKey(num) {
		    	// console.log("aaa")
		        var Two = this.Ticket_two;
		        if (Two.length < 6) {
			        if ( Two.indexOf('.') > -1 && Two.substring(Two.indexOf('.') + 1).length < 2)
			        this.Ticket_two = Two + num;
			        if (!(Two.indexOf('.') > -1)) {
			        if (num == 0 && Two.length == 0)
			         this.Ticket_two = '0.';
			        else {
			         if (Two.length && Number(Two.charAt(0)) === 0) return;
			         this.Ticket_two = Two + num;
			        }
			        }
		        }else{
		        	this.showTip("最多只能设置6位数的密码~",1000);
		        }
		    },
		    //立即充值
		    godo_recharge:function(){
				var page = this;
				var mc_num = page.Num;
				var h_id = page.id;
				page.loading = true;
				var url = '/general_api/api/RechargeCard?mc_num='+ mc_num + '&h_id='+h_id + '&sid=' + this.sid+'&ts='+Date.parse(new Date());
		        fetch(url).then(r => r.json()).then(d => {
		        	if (d.available ) {
		        		page.loading = false;
                		var token = Cookies.get('_lac_k_');
		        		var url = '/general_api/api/auth/GetWxObj?ct=201101&tnum='+ d.obj.data.tnum + '&token='+token+'&ts='+Date.parse(new Date());;
				        fetch(url).then(r => r.json()).then(d => {
				        	console.log(d)
				        	if (d.code == -1) {
				        		page.showTip(d.text);
				        	}else if (d.code == 0) {
				        		page.other = d.other;
				        		page.onBridgeReady();
				        	}else if (d.available == false && d.messages[0] == 'user_no_login') {
	                            page.showTip('用户未登录');
	                        }
				        });
		        	}else{
		        		page.loading = false;
		        		page.showTip(d.messages[0]);
		        	}
		        });
			}
		}
	}
</script>
<style lang="less" scoped >
.purchasing-num-box{
	position: fixed;
	top: 0;
	left: 0;
	bottom: 0;
	right: 0;
	width: 100%;
	height: 100%;
}
.purchasing-num-bgc{
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.6);
}
.purchasing-num{
	position: absolute;
	bottom: 0;
	width: 100%;
	height: 6.4rem;
	background: #fff;
}
.purchasing-num-top{
	width: 100%;
	text-align: center;
	height: .8rem;
	line-height: .8rem;
	font-size: .26rem;
	font-weight: bold;
}
.purchasing-num-input{
	width: 60%;
	text-align: center;
	height: .6rem;
	border: 1px solid #C6CBD4;
}
.purchasing-num-p{
	color: #C6CBD4;
	margin: .2rem auto .6rem auto;
}
.keyboard{
	position: absolute;
	bottom: 0;
	width: 100%;
}
.numInput{
	display: inline-block;
	width: 33%;
	height: 1rem;
	line-height: 1rem;
    font-size: 0.35rem;
    font-weight: bold;
    border: 1px solid #C6CBD4;
    border-bottom: none;
    border-right: none;
}
.PurchasingCard-box{
	width: auto;
	height: 1.2rem;
	border-top: .2rem solid #f0eff5;
	border-bottom: .2rem solid #f0eff5;
	padding: 0 .3rem;
}
.PurchasingCard{
	width: 100%;
	height: 1rem;
	line-height: .8rem;
}
.PurchasingTitle{
	display: inline-block;
	float: left;
    font-size: 0.26rem;
    /*font-weight: bold;*/
}
.PurchasingHint{
	display: inline-block;
	float: left;
	margin-left: .25rem;
    font-size: 0.2rem;
    color: #999;
}
.shezhi{
	display: inline-block;
	float: right;
	margin-right: .3rem;
	color: #09BB07;
	font-size: .2rem;
}
.vertical2{
    position: absolute;
    right: .3rem;
    display: inline-block;
    /*float: right;*/
    margin-top: 1px;
}
.vertical2:before {
    content: "";
    display: inline-block;
    -webkit-transform: rotate(-45deg);
    width: 10px;
    height: 10px;
    margin-left: 0;
    border: 2px solid #A9A9A9;
    border-top: 0 none;
    border-left: 0 none;
}
.purchasingcard-top{
	width: auto;
	height: .88rem;
	padding-left: .3rem;
}
.purchasingcard-top-title{
	width: 100%;
	text-align: left;
	height: .88rem;
	line-height: .88rem;
	font-size: .26rem;
	border-bottom: 1px solid #efeff4;
}
.purchasingcard-mid{
	width: auto;
	padding-left: .3rem;
    padding-bottom: .3rem;	
}
.purchasingcard-mid-ul{
	width: 100%;
    text-align: left;
}
.purchasingcard-mid-li{
    position: relative;
    display: inline-block;
    margin-top: 0.3rem;
    margin-right: 0.3rem;
    margin-left: 0.08rem;
    width: 2rem;
    height: 1.87rem;
    border: 1px solid #efeff4;
    border-radius: 0.06rem;
}

.purchasingcard-mid-li-true{
    position: relative;
    display: inline-block;
    margin-top: 0.3rem;
    margin-right: 0.3rem;
    margin-left: 0.08rem;
    width: 2rem;
    height: 1.87rem;
    border: 1px solid #09bb07;
    border-radius: 0.06rem;
}
.purchasingcard-mid-li-grade{
	line-height: 1;
    text-align: center;
    font-size: 0.26rem;
    font-weight: bold;
    color: #B2BCC1;
    margin-top: 0.33rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.purchasingcard-mid-li-grade-true{
	line-height: 1;
    text-align: center;
    font-size: 0.26rem;
    font-weight: bold;
    color: #09bb07;
    margin-top: 0.33rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;	
}
.purchasingcard-mid-li-introduce{
	line-height: 1;
	text-align: center;
	margin-top: .24rem;
	font-size: .18rem;
	color: #A9A9A9;
}
.purchasingcard-mid-li-introduce-true{
	line-height: 1;
	text-align: center;
	margin-top: .24rem;
	font-size: .18rem;
	color: #09bb07;
}
.purchasingcard-mid-li-price{
	line-height: 1;
	text-align: center;
    font-size: 0.3rem;
    font-weight: bold;
    color: #B2BCC1;
    margin-top: .33rem;
    /*overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;*/
}
.purchasingcard-mid-li-price-true{
	line-height: 1;
	text-align: center;
    font-size: 0.3rem;
    font-weight: bold;
    margin-top: .33rem;
    color: #09bb07;
    /*overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;*/

}
.purchasingcard-bot{
	width: auto;
	padding-left: .3rem;
}
.purchasingcard-bot-title{
	width: 100%;
	text-align: left;
	height: .88rem;
	line-height: .88rem;
	margin-top: .25rem;
	font-size: .26rem;
	border-bottom: 1px solid #efeff4;
}
.purchasingcard-bot-pay{
	width: 100%;
	height: .46rem;
	margin-top: .3rem;
	display: flex;
	align-items: center;
	justify-content: space-between;
}
.purchasingcard-bot-pay div{
	height: .46rem;
	display: flex;
	align-items: center;
	justify-content: space-between;
	
}
.purchasingcard-bot-pay div img{
	display: inline-block;
	width: .4rem;
	height: .4rem;
}
.purchasingcard-bot-pay div p{
	display: inline-block;
	margin-left: .25rem;
}
.purchasingcard-bot-pay input{
	margin-right: .3rem;
	height: .46rem;
	width: .46rem;
}
.purchase-box{
	position: fixed;
	bottom: .6rem;
	left: 0;
	right: 0;
	padding: 0 .3rem;
}
.purchase{
    width: 100%;
    text-align: center;
    height: .88rem;
    line-height: .88rem;
    border: 1px solid #029400;
    border-radius: .1rem;
    background: #09BB07;
    color: #FFFFFF;
    font-size: .36rem;
}

.purchasingcard-mid-li-true-img{
    position: absolute;
    top: -.03rem;
    right: -.01rem;
    width:  .4rem;
    height:  .37rem;
}
.purchasingcard-top{
	width: auto;
	height: .88rem;
	padding-left: .3rem;
}
.purchasingcard-top-title{
    text-align: left;
	width: 100%;
	height: .88rem;
	line-height: .88rem;
	font-size: .26rem;
	border-bottom: 1px solid #efeff4;
}
.purchasingcard-mid{
	width: auto;
	padding-left: .3rem;	
}
.purchasingcard-mid-ul{
	width: 100%;
	text-align: left;
}
.purchasingcard-mid-li{
	position: relative;
	display: inline-block;
	margin-top: .3rem;
	margin-right: .3rem;
	margin-left: .08rem;
	width: 2rem;
	height: 1.87rem;
	border: 1px solid #efeff4;
	border-radius: .06rem;
}

.purchasingcard-mid-li-true{
    border: 1px solid #09bb07;	
    position: relative;
	display: inline-block;
	margin-top: .3rem;
	margin-right: .3rem;
	margin-left: .08rem;
	width: 2rem;
	height: 1.87rem;
	border: 1px solid #efeff4;
	border-radius: .06rem;
}
.purchasingcard-mid-li-grade{
	line-height: 1;
	text-align: center;
}
.purchasingcard-mid-li-grade{
	margin-top: .33rem;
	font-size: .26rem;
	font-weight: bold;
}
.purchasingcard-mid-li-introduce{
	line-height: 1;
	text-align: center;
	margin-top: .24rem;
	font-size: .18rem;
	color: #A9A9A9;
}
.purchasingcard-mid-li-introduce-true{
	line-height: 1;
	text-align: center;
	margin-top: .24rem;
	font-size: .18rem;
	color: #09bb07;
}
.purchasingcard-mid-li-price{
	line-height: 1;
	text-align: center;
    font-size: 0.5rem;
    font-weight: bold;
    color: #A9A9A9;
    margin-top: .5rem;
}
.purchasingcard-mid-li-price-true{
	line-height: 1;
	text-align: center;
    font-size: 0.5rem;
    font-weight: bold;
    color: #09bb07;
    margin-top: .5rem;

}
.purchasingcard-bot{
	width: auto;
	padding-left: .3rem;
}
.purchasingcard-bot-title{
	text-align: left;
	width: 100%;
	height: .88rem;
	line-height: .88rem;
	margin-top: .25rem;
	font-size: .26rem;
	border-bottom: 1px solid #efeff4;
}
.purchasingcard-bot-pay{
	width: 100%;
	height: .46rem;
	margin-top: .3rem;
	display: flex;
	align-items: center;
	justify-content: space-between;
}
.purchasingcard-bot-pay div{
	height: .46rem;
	display: flex;
	align-items: center;
	justify-content: space-between;
	
}
.purchasingcard-bot-pay div img{
	display: inline-block;
	width: .4rem;
	height: .4rem;
}
.purchasingcard-bot-pay div p{
	display: inline-block;
	margin-left: .25rem;
}
.purchasingcard-bot-pay input{
	margin-right: .3rem;
	height: .46rem;
	width: .46rem;
}
.purchase-box{
	position: fixed;
	bottom: .6rem;
	left: 0;
	right: 0;
	padding: 0 .3rem;
}
.purchase{
    width: 100%;
    text-align: center;
    height: .88rem;
    line-height: .88rem;
    border: 1px solid #029400;
    border-radius: .1rem;
    background: #09BB07;
    color: #FFFFFF;
    font-size: .36rem;
}


.purchasingcard-mid-li-true{
    border: 1px solid #09bb07;	
}
.purchasingcard-mid-li-true-img{
    position: absolute;
    top: -.03rem;
    right: -.01rem;
    width:  .4rem;
    height:  .37rem;
}
</style>