<template>
	<div>
		<!-- <div class="details-bg"></div> -->
		<div class="kaitong">
			<img src="http://pe1s.static.pdr365.com/1536575795599.jpg">
			<p>开通成功</p>
		</div>
		<div class="goOk" v-on:click="godo_index">完成</div>
	</div>
</template>
<script>
	export default {
		data(){
			return {
				loading:true,
				account:'',
			}
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="好时光会员卡";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
		},methods:{
			godo_index:function(){
				//this.$router.go(-1);
				this.$router.push({name: 'myCards'});
			}
		},
	}
</script>
<style>
.kaitong{
	position: relative;
	width: 100%;
	height: 5rem;
}
.kaitong img{
	position: absolute;
	top: 0;
	left: 0;
	bottom: 0;
	right: 0;
	margin: auto auto;
	width: 2rem;
	height: 2rem;
}
.kaitong p{
	position: absolute;
	top: 4rem;
	left: 0;
	bottom: 0;
	right: 0;
	margin: auto auto;
	width: 100%;
	text-align: center;
	font-size: .38rem;
}
.goOk{
	width: 90%;
	text-align: center;
	height: .9rem;
	line-height: .9rem;
	background: #09BB07;
	color: #fff;
	font-size: .36rem;
	border-radius: .1rem;
	margin-left: 5%;
    margin-top: 2rem;
}
</style>