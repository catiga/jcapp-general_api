<template>
	<div><!-- 固定 容器-->
		<!--<div class="details-bg"></div>-->
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
	export default {
		data : function(){
			return {
				loading: false,
				tip_msg:"",
				RechargeData:'',
				id:'',//等级id
				HId:'',//选中id
				Num:'',//卡号
				other:'',//支付信息
				sid: ''
			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="充值会员";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
			this.GradeList();
			this.Num = this.$route.params.CardNm;
			this.sid = Cookies.get('s_id');
		},methods:{
			back:function(){
				//自定义返回上一页函数
				router.go(-1);
			},showTip:function(msg,ts){
				var page = this;
				page.tip_msg = msg;
				if(ts){
					clearTimeout(window.maxtm);
					window.maxtm = setTimeout(function(){page.tip_msg="";},ts)
				}
			},GradeList:function () {
				var page = this;
				page.loading = true;
				var token = Cookies.get('_lac_k_');
		        var url = '/general_api/api/CardGradeList?ts='+Date.parse(new Date()) + '&token=' + token;
		        fetch(url).then(r => r.json()).then(d => {
		        	// if (d.available) {
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
			},godo_recharge:function(){
				var page = this;
				var mc_num = page.Num;
				var h_id = page.id;
				page.loading = true;
				var url = '/general_api/api/RechargeCard?mc_num='+ mc_num + '&h_id='+h_id + '&sid=' + this.sid +'&ts='+Date.parse(new Date());
		        fetch(url).then(r => r.json()).then(d => {
		        	if (d.available ) {
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
			},onBridgeReady:function(){
				this.loading = false;
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
						// alert('您已支付成功');

						location.href="/general_api/tcss/index#/myCards"

						// this.$router.go(-1);
					} else if (res.err_msg == "get_brand_wcpay_request:cancel"){
						this.showTip('您取消了付款');
						// alert('您取消了付款');
					} else if (res.err_msg == "get_brand_wcpay_request:fail") {
						// alert('支付失败');
						this.showTip('支付失败');
					}
				});
			}
		}
	}
</script>

<style lang="less" scoped >
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