<template>
	<div style="overflow: hidden;width: 100%;height: 100%;"><!-- 固定 容器-->
		<div class="details-bg" style="background: #fff"></div>
		<!--有会员卡-->
		<div class="ed-myCards-box" v-if="CardData != null " v-for="(item,j) in CardData">
			<div v-if="j == 0">
				<div class="ed-myCards" style="background: #efeff4;" v-on:click="godo_cardDetails">
					<div class="ed-myCards-top-box">
						<div class="ed-myCards-top">
							<div class="ed-myCards-top-logo">
								<img src="http://pe1s.static.pdr365.com/timg.jpeg" />
								<p>{{item.rule.title}}</p>
							</div>
							<p class="ed-myCards-top-num">{{item.mc_num}}</p>
							<div class="ed-myCards-top-hint">
								<div>
									<img class="ed-myCards-top-hint-img" src="http://pe1s.static.pdr365.com/tencent/icon/info_icon.png" />
								<p>不可退换</p>
								</div>
								<img @click.stop="godo_cardCode(item.card_code)" class="ed-myCards-top-hint-code" src="http://pe1s.static.pdr365.com/tencent/icon/qrcode_icon.png" />
							</div>
						</div>
					</div>
				</div>
				<div class="ed-myCards-bot-box">
					<div class="ed-myCards-bot">
						<p class="ed-myCards-bot-balance">余额</p>
						<p class="ed-myCards-bot-balance-num">{{item.balance/100}}元</p>
						<div class="ed-myCards-integral-box">
							<p class="ed-myCards-bot-integral-grade">会员等级<span>{{item.levelname}}</span></p>
							<p class="ed-myCards-bot-integral" style="display:none">积分<span>{{item.point}}</span></p>
						</div>
						<div class="ed-myCards-but">
							<div class="ed-myCards-recharge" v-on:click="godo_recharge(item.mc_num)">充值</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--无会员卡-->
		<div class="myCards" v-if="CardData == null " >
			<div>
				<div class="myCards-top-box">
					<div class="myCards-top">
						<p class="myCards-top-hint-top">还未开通会员卡</p>
						<p class="myCards-top-hint">开通后购票可享受优惠价</p>
					</div>
				</div>
				<div class="myCards-bot">
					<div class="myCards-bot-go" v-on:click="godo_paycard">立即开通</div>
					<!-- <div class="myCards-top-bind" v-on:click="godo_bindcard">立即绑定</div> -->
				</div>
			</div>
		</div>
		
		<!--弹出充值对话框-->
		<div class="js_dialog" id="iosDialog2" v-if="forbid">
	        <div class="weui-mask"></div>
	        <div class="weui-dialog">
	            <div class="weui-dialog__bd" style="font-size: .36rem;padding: .6rem 20px .28rem;min-height: 24px;">充值会员</div>
	            <div class="input-box">
	            		<input class="moeny-input" type="number" name="aa" id="a" placeholder="请输入充值金额" value="请输入充值金额" />
	            </div>
	            <div class="weui-dialog__ft">
	                <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default" v-on:click="forbid=false">取消</a>
	                <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary">确认</a>
	            </div>
	        </div>
	    </div>
	    <div class="js_dialog" id="iosDialog1" v-if="img_tip">
	        <div class="weui-mask" v-on:click="img_tip=false"></div>
	        <div class="weui-dialog" style="border-radius: 0.2rem;">
				<div class="code-bot">
					<div class="code-img-box">
						<img class="weui-img" style="margin-top: .5rem;width: 70%;display: inline-block;" :src="'http://qr.liantu.com/api.php?text='+ Codesrc " alt="">
					</div>
                  <p class="code-p">扫描二维码进行会员卡支付</p>
                  <div class="code-true" v-on:click="img_tip=false">确定</div>
              </div>
	        </div>
	    </div>
	    <div id="loadingToast" v-if="loading">
	        <div class="weui-mask_transparent"></div>
	        <div class="weui-toast">
	            <i class="weui-loading weui-icon_toast"></i>
	            <p class="weui-toast__content">正在加载</p>
	        </div>
	    </div>
	</div>
</template>
<script>
	import Cookies from 'js-cookie'
	export default {
		data(){
			return {
				loading:true,
				synopsis:'synopsis-none',
				showBtn:'showBtn',
				forbid: false,
				img_tip: false,
				CardData:'',
				Codesrc:'',
			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="会员卡";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
			this.GetAccountCard();
		},methods:{
			godo_cardCode:function(code){
				var page = this;
				page.Codesrc = code;
				page.img_tip = true;
			},
			//获取会员卡信息接口
			GetAccountCard:function(){
				var page = this;
	            var token = Cookies.get('_lac_k_');
		        var url = '/general_api/api/auth/GetAccountCard?token='+token+'&ts='+Date.parse(new Date());
		        fetch(url).then(r => r.json()).then(d => {
		        	console.log(d);
		        	if (!d.available) {
	                    let auth_type = Cookies.get('_lac_ap_');
	                    let auth_key = Cookies.get('_lac_k_');
		        		if(!auth_type) {
		        			location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();	//online should be
		        		} else {
		        			if(!auth_key) {
		        				this.$router.push({name: 'login', params: {apid:auth_type}})
		        			}
		        		}
	                }
	                page.CardData = d.obj;
		        	page.loading = false;
		        });
		       
			},back:function(){
				//自定义返回上一页函数 可以copy到每一个组件中方便使用
				router.go(-1);
			},godo_paycard:function(){
				// this.$router.push({name: 'purchasingCard'})
				location.href="/general_api/tcss/index/?#/purchasingCard/purchasing"
			},godo_bindcard:function(){
				this.$router.push({name: 'bindCard'})
			},godo_cardDetails:function(){
				this.$router.push({name: 'cardDetails'})
			},godo_recharge:function(Num){
				document.cookie = "CardNum="+Num;
				location.href="/general_api/tcss/index/?#/purchasingCard/recharge";
			}
		}
	}
</script>
<style>
.code-bot{
	height: 6.5rem;
    border-radius: 0.3rem;	
}
.code-img-box{
	width: 100%;
}
.code-p{
    font-size: 15px;
    color: #999;
    height: .75rem;	
}
.code-true{
	width: 100%;
    height: 1rem;
    line-height: 1rem;
    font-size: 18px;
    border-top: 1px solid #d2d3d5;
    color: #00c200;	
}
/*无会员卡*/
.myCards{
	width: 100%;
	height: auto;
}
.myCards-top-box{
	width: auto;
	height: auto;
	padding: .7rem .3rem 0rem .3rem;
}
.myCards-top{
	width: 100%;
	text-align: center;
	height: 3rem;
	background: #f4f4f4;
	border-radius: .1rem;
}
.myCards-top-title{
	width: 100%;
	font-size: .4rem;
	color: #000000;
	padding-top: .82rem;
}
.myCards-top-hint{
	width: 100%;
	font-size: .28rem;
	color: #888888;
	padding-top: .35rem;
}
.myCards-bot{
	width: auto;
	text-align: center;
	height: auto;
	padding: 1.6rem .3rem 0rem .3rem;
}
.myCards-bot-go{
	margin-bottom: .32rem;
}
.myCards-bot-go , .myCards-top-bind{
	width: 100%;
	height: .88rem;
	line-height: .88rem;
	border: 1px solid #029400;
	border-radius: .1rem;
	background: #09BB07;
	color: #FFFFFF;
	font-size: .36rem;
}


/*已有会员卡*/
.ed-myCards-box{
	width: 100%;
	height: auto;
}
.ed-myCards{
	width: 100%;
	height: auto;
}
.ed-myCards-top-box{
	width: auto;
	height: 3.4rem;
	padding: .15rem .4rem .3rem .4rem;
}
.ed-myCards-top{
	width: 100%;
	height: 3rem;
	border-radius: .1rem;
    background-size: 100%;
	background: url(http://pe1s.static.pdr365.com/cardBg.png);
}
.ed-myCards-top-logo{
	width: 100%;
	height: .88rem;
	padding-top: .38rem;
    vertical-align: middle;
}
.ed-myCards-top-logo img{
    float: left;
	display: inline-block;
	width: .88rem;
	height: .88rem;
	margin: 0 .26rem;
	border-radius: 50%;
    vertical-align: middle;
}
.ed-myCards-top-logo p{
    float: left;
    margin-top: .25rem;
	display: inline-block;
	color: #FFFFFF;
	font-size: .34rem;
	line-height: 1;
    vertical-align: middle;
}
.ed-myCards-top-num{
	display: inline-block;
	width: 100%;
	text-align: center;
	font-size: .36rem;
	color: #FFFFFF;
	margin-top: .2rem;
}
.ed-myCards-top-hint{
	display: flex;
	align-items:center;
	justify-content:space-between;
	width: 100%;
	height: .42rem;
	margin-top: .4rem;
}
.ed-myCards-top-hint-img{
	display: inline-block;
	width: .22rem;
	height: .22rem;
	margin: 0 .05rem 0 .25rem;
}
.ed-myCards-top-hint p{
	display: inline-block;
	font-size: .22rem;
	color: #FFFFFF;
	line-height: 1;
}
.ed-myCards-top-hint-code{
	display: inline-block;
	width: .42rem;
	height: .42rem;
	margin-right: .25rem;
}
.ed-myCards-bot{
	width: auto;
	height: 100%;
	background: #FFFFFF;
	padding: 0 .87rem;
}
.ed-myCards-bot-balance{
	width: 100%;
	text-align: center;
	height: auto;
	line-height: 1;
	color: #949c9f;
	font-size: .26rem;
	padding-top: .86rem;
	margin-bottom: .25rem;
}
.ed-myCards-bot-balance-num{
	width: 100%;
	text-align: center;
	height: auto;
	line-height: 1;
	color: #09BB07;
	font-size: .36rem;
	margin-bottom: .44rem;
}
.ed-myCards-integral-box{
	width: 100%;
	height: auto;
	border-top: 1px solid #efeff4;
	padding-top: .45rem;
}
.ed-myCards-bot-integral-grade , .ed-myCards-bot-integral{
    text-align: left;
	padding: 0 .65rem;
	font-size: .26rem;
	color: #949c9f;
	margin: .46rem 0 .08rem 0;
}
.ed-myCards-bot-integral-grade span , .ed-myCards-bot-integral span{
	float: right;
	color: #000000;
	font-size: .36rem;
}
.ed-myCards-but{
	width: 100%;
}
.ed-myCards-recharge , .ed-myCards-bind{
	width: 3.6rem;
	text-align: center;
	height: .7rem;
	line-height: .7rem;
	border: 1px solid #029400;
	background: #09BB07;
	border-radius: .1rem;
	color: #FFFFFF;
	font-size: .32rem;
	margin-top: 1rem;
	margin-left: 1.1rem;
}
.ed-myCards-bind{
	border: 1px solid #d6d5d6;
	background: #FFFFFF;
	color: #000000;
	margin-top: .3rem;
}
.myCards-top-hint-top{
	padding-top: .9rem;
	font-size:  .4rem;
	color: #000;
}


.input-box{
    width: auto;
    padding: 0 .26rem;	
}
.moeny-input{
	width: 100%;
    height: .75rem;
    border: 1px solid #ccc;
    border-radius: .1rem;
    margin-bottom: .32rem;
    outline: none;
    padding-left: .2rem;
    font-size: .34rem;	
}

</style>