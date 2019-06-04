<template>
	<div><!-- 固定 容器-->
		<div class="details-bg"></div>
		<!-- <div>
			<div class="myIntegral-top">
				<p class="myIntegral-top-record">积分纪录</p>
				<p class="myIntegral-top-gain" v-on:click="godo_integral">积分获取</p>
			</div>
			<div class="myIntegral-list-box">
				<div class="myIntegral-list">
					<div class="myIntegral-list-top">
						<div class="myIntegral-list-top-cut-off"></div>
						<p class="myIntegral-list-top-time">本月</p>
						<p class="myIntegral-list-top-integral">获取：<span>55</span></p>
					</div>
					<div class="myIntegral-list-bot">
						<ul class="myIntegral-list-bot-ul">
							<li class="myIntegral-list-bot-li">
								<div class="myIntegral-list-bot-msg">
									<p class="myIntegral-list-bot-msg-nm">我不是药神-保利影城</p>
									<p class="myIntegral-list-bot-msg-time">2018-08-09</p>
								</div>
								<div class="myIntegral-list-bot-integral">+5</div>
							</li>
							<li class="myIntegral-list-bot-li">
								<div class="myIntegral-list-bot-msg">
									<p class="myIntegral-list-bot-msg-nm">我不是药神-保利影城</p>
									<p class="myIntegral-list-bot-msg-time">2018-08-09</p>
								</div>
								<div class="myIntegral-list-bot-integral">+5</div>
							</li>
							<li class="myIntegral-list-bot-li">
								<div class="myIntegral-list-bot-msg">
									<p class="myIntegral-list-bot-msg-nm">我不是药神-保利影城</p>
									<p class="myIntegral-list-bot-msg-time">2018-08-09</p>
								</div>
								<div class="myIntegral-list-bot-integral">+5</div>
							</li>
						</ul>
					</div>
				</div>
				
				<div class="myIntegral-list">
					<div class="myIntegral-list-top">
						<div class="myIntegral-list-top-cut-off"></div>
						<p class="myIntegral-list-top-time">本月</p>
						<p class="myIntegral-list-top-integral">获取：<span>55</span></p>
					</div>
					<div class="myIntegral-list-bot">
						<ul class="myIntegral-list-bot-ul">
							<li class="myIntegral-list-bot-li">
								<div class="myIntegral-list-bot-msg">
									<p class="myIntegral-list-bot-msg-nm">我不是药神-保利影城</p>
									<p class="myIntegral-list-bot-msg-time">2018-08-09</p>
								</div>
								<div class="myIntegral-list-bot-integral">+5</div>
							</li>
							<li class="myIntegral-list-bot-li">
								<div class="myIntegral-list-bot-msg">
									<p class="myIntegral-list-bot-msg-nm">我不是药神-保利影城</p>
									<p class="myIntegral-list-bot-msg-time">2018-08-09</p>
								</div>
								<div class="myIntegral-list-bot-integral">+5</div>
							</li>
							<li class="myIntegral-list-bot-li">
								<div class="myIntegral-list-bot-msg">
									<p class="myIntegral-list-bot-msg-nm">我不是药神-保利影城</p>
									<p class="myIntegral-list-bot-msg-time">2018-08-09</p>
								</div>
								<div class="myIntegral-list-bot-integral">+5</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div> -->
		<div class="my-coupons">
			<div class="NoCoupons">
				<img src="https://ex.piaodaren.com/img_server/_common/icon/Nointegrl.png">
				<p>您还没有积分呢～</p>
			</div>
		</div>
	</div>
</template>
<script>
	export default {
	data(){
		return {
			loading:true,
		};
	},beforeRouteEnter:function(to, from, next){
		//当组件加载时自动调用此函数 函数结尾必须next();
		document.title="我的积分";
		next();
	},created:function(){
		//组件加载完成会自动调用此方法
	},methods:{
		back:function(){
			//自定义返回上一页函数 可以copy到每一个组件中方便使用
			router.go(-1);
		},godo_integral:function(){
			this.$router.push({name: 'integrationRule'})
		}
	}
}
</script>
<style>
.myIntegral-top{
	width: 100%;
	height: 1rem;
}
.myIntegral-top-gain , .myIntegral-top-record{
	display: inline-block;
	height: 1rem;
	line-height: 1rem;
}
.myIntegral-top-record{
	float: left;
	margin-left: .3rem;
	font-size: .34rem;
}
.myIntegral-top-gain{
	float: right;
	margin-right: .3rem;
	font-size: .28rem;
	color: #888888;
}
.myIntegral-list-box{
	width: 100%;
}
.myIntegral-list{
	width: auto;
	padding-left: .3rem;
	background: #FFFFFF;
	margin-bottom: .3rem;
}
.myIntegral-list-top{
	width: 100%;
	height: .88rem;
	border-bottom: 1px solid #efeff4;
}
.myIntegral-list-top-cut-off{
	display: inline-block;
	width: .05rem;
	height: .4rem;
	background: #09BB07;
	vertical-align: middle;
}
.myIntegral-list-top-integral , .myIntegral-list-top-time{
	display: inline-block;
	line-height: .88rem;
	vertical-align: middle;
}
.myIntegral-list-top-time{
	font-size: .26rem;
	color: #010101;
	margin-left: .1rem;
}
.myIntegral-list-top-integral{
	float: right;
	margin-right: .3rem;
	font-size: .26rem;
	color: #000000;
}
.myIntegral-list-top-integral span{
	display: inline-block;
	color: #09BB07;
}
.myIntegral-list-bot , .myIntegral-list-bot-ul{
	width: 100%;
}
.myIntegral-list-bot-li{
	width: 100%;
	height: 1.7rem;
	border-bottom: 1px solid #efeff4;
}
.myIntegral-list-bot-msg{
	display: inline-block;
	width: auto;
	max-width: 90%;
	height: 1.7rem;
}
.myIntegral-list-bot-msg-nm{
	width: 100%;
	font-size: .34rem;
	margin-top: .4rem;
	overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.myIntegral-list-bot-msg-time{
	width: 100%;
	font-size: .26rem;
	margin-top: .1rem;
	color: #A9A9A9;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.myIntegral-list-bot-integral{
	display: inline-block;
	float: right;
	margin-right: .3rem;
	margin-top: .5rem;
	font-size: .26rem;
	color: #09BB07;
}

</style>