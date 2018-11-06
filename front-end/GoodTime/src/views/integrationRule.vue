
<template>
	<div>
		<p class="integrationRule-title">积分规则详细介绍</p>
	</div>
</template>
<script>
	export default {
		data(){
			return {
				loading:true,
			}
		},
		beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="积分规则";
			next();
		},created:function(){
		//组件加载完成会自动调用此方法
		},methods:{
			back:function(){
				//自定义返回上一页函数 可以copy到每一个组件中方便使用
				router.go(-1);
			}
		}
	}
</script>
<style>
.integrationRule-title{
	width: 100%;
	text-align: center;
	font-size: .34rem;
	margin-top: .3rem;
}

</style>