<template>
	<div><!-- 固定 容器-->
		<!--<div class="details-bg"></div>-->
		<!--会员卡-->
		<div class="ed-myCards">
			<div class="ed-myCards-top-box">
				<div class="ed-myCards-top">
					<div class="ed-myCards-top-logo">
						<img :src="logo.domain + '/img_server/' + logo.logo"> 
						<p>{{CardDetails.mcname}}</p>
					</div>
					<p class="ed-myCards-top-num">{{CardDetails.mc_num}}</p> 
				</div>
			</div>
		</div>
		<!--会员卡介绍-->
		<div class="cardInfo-box">
			<div class="cardInfo" style="text-align: left;word-break: break-all;">
				{{CardDetails.content}}
				<!-- <p class="cardInfo-title">特权说明</p>
				<p class="cardInfo-info">1、结账前需出示影城电子会员卡，会员积分根据实际支付金额累计（每消费一元积一分）</p>
				<p class="cardInfo-info">2、会员优惠：活动详见</p>
				<p class="cardInfo-title">电话</p>
				<p class="cardInfo-info">*400-1717-917</p>
				<p class="cardInfo-title">使用须知</p>
				<p class="cardInfo-info">1、每人限领一张会员卡</p>
				<p class="cardInfo-info">2、每个微信用户限领一张</p> -->
				<p class="cardInfo-title">有效日期</p>
				<p class="cardInfo-info">*{{CardDetails.validate_period}}</p>
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
				CardDetails:'',
			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="会员卡详情";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
			this.GetAccountCard()
		},methods:{
			back:function(){
				//自定义返回上一页函数 可以copy到每一个组件中方便使用
				router.go(-1);
			},GetAccountCard:function(){
				var page = this;
				var token = Cookies.get('_lac_k_');
		        var url = '/general_api/api/auth/GetAccountCard?token='+token+'&ts='+Date.parse(new Date());;
		        fetch(url).then(r => r.json()).then(d => {
		        	if (d.available) {
		        		page.CardDetails = d.obj[0];
		        		page.loading = false;
		        	}else {
		        		let auth_type = Cookies.get('_lac_ap_');
		        		if(!auth_type) {
		        			location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();	//online should be
		        		} else {
		        			this.$router.push({name: 'login', params: {apid:auth_type}})
		        		}
	                }
		        });
		       
			},
		}
	}
</script>
<style>
.cardInfo-box{
	padding: 0 .4rem;
	width: auto;
}
.cardInfo{
	width: 100%;
}
.cardInfo-title{
	font-size: .34rem;
	color: #000000;
    line-height: 2;
}
.cardInfo-info{
	font-size: .28rem;
	color: #353535;
}

</style>