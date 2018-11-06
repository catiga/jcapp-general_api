<template>
	<div><!-- 固定 容器-->
		<div class="details-bg"></div>
		<!--城市-->
		<div	 class="cinema-city">
			<div class="c-city-box">
				<router-link to="/cityList">
				<div class="c-city-name">
				北京市
				</div>
				<div class="c-am-list-arrow">
					<span class="c-am-icon arrow vertical"></span>
				</div>
				</router-link>
			</div>
		</div>
		<!--影城列表-->
		<div class="cinema-box">
			<ul class="cinema-ul">
				<li class="cinema-li" v-on:click="godo_choose">
					<div class="cinema-li-top">
						<p class="cinema-li-nm">奥莱环球影城</p>
						<p class="cinema-li-price">29.8<span>元起</span></p>
					</div>
					<p class="cinema-li-info">北京市朝阳区望京soho北京市朝阳区望京soho</p>
					<div class="cinema-li-distance">200m</div>
				</li>
				<li class="cinema-li">
					<div class="cinema-li-top">
						<p class="cinema-li-nm">奥莱环球影城</p>
						<p class="cinema-li-price">29.8<span>元起</span></p>
					</div>
					<p class="cinema-li-info">北京市朝阳区望京soho北京市朝阳区望京soho</p>
					<div class="cinema-li-distance">200m</div>
				</li>
				<li class="cinema-li">
					<div class="cinema-li-top">
						<p class="cinema-li-nm">奥莱环球影城</p>
						<p class="cinema-li-price">29.8<span>元起</span></p>
					</div>
					<p class="cinema-li-info">北京市朝阳区望京soho北京市朝阳区望京soho</p>
					<div class="cinema-li-distance">200m</div>
				</li>
			</ul>
		</div>
        <div class="bottom-tab">    
			<a href="javascript:void(0)" class="movie"v-on:click="godo_index">        
				<div class="icon"></div>    
			</a>   
			<a href="javascript:void(0)" class="store" v-on:click="godo_goods">
				<div class="icon"></div>    
			</a>      
			<a href="javascript:void(0)" class="cinema" data-status="active">
				<div class="icon"></div>    
			</a>    
			<a href="javascript:void(0)" class="mine" v-on:click="godo_mine">        
				<div class="icon"></div>    
			</a>
		</div>
	</div>
</template>
<script>
	export default {
		data : function(){
			return {
				loading:true,
				synopsis:'synopsis-none',
				showBtn:'showBtn'
			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="影院";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
		},methods:{
			back:function(){
				//自定义返回上一页函数 可以copy到每一个组件中方便使用
				router.go(-1);
			},godo_choose:function(){
				this.$router.push({name: 'index'})
			},
			godo_goods:function(){
				location.href="/general_api/tcss/index#/storeList"
			},godo_index:function(){
				this.$router.push({name: 'index'})
			},godo_mine:function(){
				location.href="/general_api/tcss/index#/personalCenter"
			}
		}
	}
</script>
<style>
.details-bg{
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: -1;
	background: #efeff4;
	
}
.cinema-city{
	width: 100%;
	height: .88rem;
	background: #FFFFFF;
}
.c-city-box{
	color: #A9A9A9;
	z-index: 99;
	font-weight: bold;
}
.c-city-name {
    display: inline-block;
    font-size: .26rem;
    margin-left: .3rem;
    width: auto;
    max-width: 70px;
    height: .88rem;
    line-height: .88rem;
    vertical-align: middle;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.c-am-list-arrow {
    display: inline-block;
    height: .3rem;
    margin-right: -2px;
    width: 12px;
    vertical-align: middle;
}
.c-am-icon.arrow {
    display: block;
    width: 12px;
    height: 12px;
    overflow: hidden;
}
.c-am-icon.arrow.vertical:before {
    margin-left: 2px;
    -webkit-transform: rotate(45deg);
}
.c-am-icon.arrow:before {
    content: "";
    display: block;
    width: 7px;
    height: 7px;
    margin-left: 0;
    border: 2px solid #A9A9A9;
    border-top: 0 none;
    border-left: 0 none;
}

.cinema-box{
	width: 100%;
	height: auto;
	background: #FFFFFF;
}
.cinema-ul{
	width: auto;
	height: auto;
	padding-left: .3rem;
}
.cinema-li{
	position: relative;
	width: 100%;
	height: auto;
	min-height: 2rem;
	padding-bottom: .4rem;
	border-top: 1px solid #eeeeee;
}
.cinema-li-top{
	width: 100%;
	height: auto;
	padding: .39rem 0 .3rem 0;
}
.cinema-li-nm{
	display: inline-block;
	font-size: .34rem;
	color: #000000;
	margin-right: .35rem;
}
.cinema-li-price{
	display: inline-block;
	font-size: .32rem;
	color: #00c203;
}
.cinema-li-price span{
	display: inline-block;
	font-size: .24rem;
	color: #A9A9A9;
}
.cinema-li-info{
	width: auto;
	max-width: 55%;
	font-size: .26rem;
	color: #A9A9A9;
}
.cinema-li-distance{
	position: absolute;
	top: 1.1rem;
	right: .3rem;
	font-size: .24rem;
	color: #bfbfbf;
}

</style>