<template>
	<div><!-- 固定 容器-->
		<div class="details-bg"></div>
		<!--电影信息-->
		<div class="video-details-content-box">
			<div class="video-details-content-bj">
				<img :src=" MovieD.pic_entry == null ? 'https://ex.piaodaren.com/img_server/_common/icon/default_movie_pic.jpg' : MovieD.pic_entry ">
			</div>
			<div class="video-details-content">
				<div class="video-details-content-img">
					<img :src=" MovieD.pic_entry == null ? 'https://ex.piaodaren.com/img_server/_common/icon/default_movie_pic.jpg' : MovieD.pic_entry ">
				</div>
				<div class="video-details-content-right">
					<div class="video-details-content-right-title">
						<p>{{MovieD.film_name}}</p>
						<div>{{MovieD.film_dimensional}}</div>
					</div>
					<p class="video-details-content-right-title-en">{{MovieD.film_name}}</p>
					<p class="video-details-content-right-type">{{MovieD.film_type}}</p>
					<p class="video-details-content-right-country">{{MovieD.film_region}}|{{MovieD.time_diff}}分钟</p>
					<p class="video-details-content-right-title-time">{{MovieD.release_date}} 上映</p>
					<div class="video-details-content-right-collect" style="text-align:right;padding-right:1rem;">{{MovieD.film_score}}
						<!--<div>
							<img src="">
							<span>收藏</span>
						</div>
						<p>8.8</p>-->
					</div>
				</div>
			</div>	
		</div>
		
		<!--简介-->
		<div class="video-synopsis">
			<div class="video-synopsis-top">
				<p class="video-synopsis-title">简介</p>
				<div class="video-synopsis-character">
					<span :class="synopsis" v-on:click="go_none">{{MovieD.film_content}}</span>
					<!--<div></div>-->
					<a :class="showBtn" v-on:click="go_block">展开</a>
				</div>
			</div>
			<div class="video-synopsis-bottom">
				<p class="video-synopsis-actor">演员</p>
				<div class="video-synopsis-actor-img" style="height: 2rem;position:relative;">
					<div class="wrapper" style="position:absolute;left:0;top:0;height:100%;" ref="actorWrapper">
						<ul :style="actorListWidth">
							<li v-for="item in MovieD.celebritys">
								<img :src="item.title_img" />
								<span>{{item.cele_name}}</span>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		
		<!--剧照-->
		<div class="stage-photo-box">
			<div class="stage-photo">
				<p class="stage-photo-title">剧照</p>
				<div class="stage-photo-list" style="position:relative;">
					<div class="wrapper" style="position:absolute;left:0;top:0;height:100%;" ref="stageScroll">
						<ul :style="stagePhotoWidth">
							<li v-for="j in MovieD.pictures">
								<img :src="j.pic_url">
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="gopay-box">
			<div class="gopay" v-on:click="go_cinema">立即购买</div>
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
	import BScroll from 'better-scroll'
	
	export default {
		data : function(){
			return {
				loading:true,
				synopsis:'synopsis-none',
				showBtn:'showBtn',
				mid:'',
				MovieD:'',
				index:'',
				actorListWidth: "",
				stagePhotoWidth: "",
			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="影片详情";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
			this.mid = this.$route.params.mid;
			this.index = this.$route.params.index;
			this.MovieDeatil();
		},methods:{
			MovieDeatil:function(){
				var page = this;
		        var url = '/general_api/api/MovieDetails?movie_id='+page.mid+'&ts='+Date.parse(new Date());
		        fetch(url).then(r => r.json()).then(d => {
		        	if (d.code == 0) {
						page.MovieD = d.data;
						page.actorListWidth = `width: ${1.34 * d.data.celebritys.length}rem`;
						page.stagePhotoWidth = `width: ${1.88 * d.data.pictures.length}rem`;
		        		page.$nextTick(() => {
							page.actorScroll = new BScroll(page.$refs.actorWrapper, {click: true,eventPassthrough: true, scrollX: true, scrollY: false, preventDefault: false});
							page.stageScroll = new BScroll(page.$refs.stageScroll, {click: true, eventPassthrough: true, scrollX: true, scrollY: false, preventDefault: false})
						});
						
		        	}
		        	page.loading = false;
		        	console.log(d)
		        });
			},back:function(){
				//自定义返回上一页函数 可以copy到每一个组件中方便使用
				router.go(-1);
			},go_block:function(){
				var page = this;
				page.synopsis = 'synopsis-block';
				page.showBtn = 'noneBtn'
			},go_none:function(){
				var page = this;
				page.synopsis = 'synopsis-none';
				page.showBtn = 'showBtn'
			},go_cinema:function(){
				let cinemaId = Cookies.get('cinemaId');
				this.$router.push({name: 'schedule',params: { cinemaId: cinemaId , mid : this.mid , index : this.index}})
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
.video-details-content-box{
	float: left;
	width: 100%;
	height: auto;
	border-bottom: .2rem solid #efeff4;
}
.video-details-content-bj{
    float: left;
	width: 100%;
	height: 2rem;
	overflow: hidden;
}
.video-details-content-bj img{
	width: 100%;
	height: auto;
}
.video-details-content{
    float: left;
	position: relative;
	width: 100%;
	padding-bottom: .1rem;
	background: #FFFFFF;
}
.video-details-content-img{
	position: absolute;
	top: -.5rem;
	left: .3rem;
	width: 2.36rem;
	height: 3.33rem;
}
.video-details-content-img img{
	width: 100%;
	height: 100%;
	border-radius: 6px;
}
.video-details-content-right{
	width: auto;
	height: auto;
	margin-left: 3.05rem;
}
.video-details-content-right-title{
	margin-top: .15rem;
}
.video-details-content-right-title p{
	display: inline-block;
	font-size: .34rem;	
	vertical-align: middle;
}
.video-details-content-right-title div{
	margin-left: .1rem;
	display: inline-block;
	padding: 0 .08rem;
	font-size: .28rem;
	color: #1AAD19;
	line-height: 1;
	border: 1px solid #1aad19;
	border-radius: 4px;
    vertical-align: middle;
}
.video-details-content-right-title-en{
	margin-top: .11rem;
	font-size: .26rem;
	color: #a9a9a9;
}
.video-details-content-right-type , 
.video-details-content-right-country , 
.video-details-content-right-title-time{
	margin-top: .1rem;
	font-size: .26rem;
	color: #333333;
}
.video-details-content-right-collect{
	font-size: .38rem;
    line-height: 1;
    margin-top: .08rem;
    color: #09bb07;
}
.video-details-content-right-collect div{
	display: inline-block;
	width: auto;
	border: 1px solid #CCCCCC;
	border-radius: 4px;
	padding: 3px 3px;
    vertical-align: middle;
}
.video-details-content-right-collect div img{
	width: .18rem;
	height: .18rem;
    vertical-align: middle;
}
.video-details-content-right-collect p{
	display: inline-block;
	margin-left: 1.2rem;
	font-size: .36rem;
	color: #00c203;
    vertical-align: middle;
}
.video-synopsis{
	float: left;
	width: auto;
	height: auto;
	padding: .4rem .3rem;
	background: #FFFFFF;
	border-bottom: .2rem solid #efeff4;
}
.video-synopsis-top{
	width: 100%;
	height: auto;
	padding-bottom: .3rem;
	border-bottom: 1px solid #efeff4;
}
.video-synopsis-title{
	font-size: .34rem;
	color: #000;
}
.video-synopsis-character{
	position: relative;
	width: 100%;
	height: auto;
	margin-top: .15rem;
}
.synopsis-none{
	display: inline-block;
	font-size: .26rem;
	color: #A9A9A9;
	text-overflow: -o-ellipsis-lastline;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
}
.synopsis-block{
	display: inline-block;
	font-size: .26rem;
	color: #A9A9A9;
	/*text-overflow: -o-ellipsis-lastline;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;*/
}

.video-synopsis-character div{
	display: inline-block;
	width: 10px;
	height: 10px;
	background: red;
}
.video-synopsis-botto{
	width: auto;
	height: auto;
	padding: .38rem .3rem;
}
.video-synopsis-actor{
	font-size: .34rem;
	color: #000;
	margin-top: .4rem;
}
.video-synopsis-actor-img{
	width: 100%;
	height: 2rem;
	margin-top: .3rem;
}
.video-synopsis-actor-img ul{
	width: 100%;
	height: 100%;
    white-space: nowrap;
}
.video-synopsis-actor-img ul li{
	/*float: left;*/
	display: inline-block;
	width: 1.06rem;
	height: 100%;
	margin-right: .28rem;
}
.video-synopsis-actor-img ul li img{
	/* width: 1.06rem;
	height: 1.26rem;
	border-radius: 4px; */
	width: 1.06rem;
	height: 1.06rem;
	border-radius: 50%;
}
.video-synopsis-actor-img ul li span{
	display: block;
	width: 100%;
	text-align: center;
	margin-top: .19rem;
	color: #A9A9A9;
	font-size: .28rem;
	overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}



.stage-photo-box{
	width: auto;
	height: auto;
	    background: #fff;
	padding: .4rem .3rem 1.4rem .3rem;
}
.stage-photo-title{
	font-size: .34rem;
	color: #000;
}
.stage-photo{
	font-size: .34rem;
	color: #000;
}
.stage-photo-list{
	width: 100%;
	height: 1.3rem;
	margin-top: .3rem;
}
.stage-photo-list ul{
	width: 100%;
	height: 1.3rem;
    white-space: nowrap;
}
.stage-photo-list ul li{
	display: inline-block;
	margin-right: .2rem;
	width: auto;
	height: auto;
}
.stage-photo-list ul li img{
	width: 100%;
	height: 1.18rem;
	border-radius: 4px;
}

.gopay-box{
	position: fixed;
	bottom: 0;
	width: 100%;
	height: 1.28rem;
	background: #fff;
}
.gopay{
	width: 90%;
    margin-left: 5%;
    text-align: center;
    height: .88rem;
    line-height: .88rem;
    color: #FFFFFF;
    font-size: .34rem;
    background: #09bb07;
    margin-bottom: 20px;
    border-radius: 4px;
    margin-top: 10px;
	border: 1px solid #029400;
}



.showBtn {
    position: absolute;
    bottom: 0;
    right:0;
    right: 7;
    display: inline-block;
    color: #349cec;
    background: -webkit-linear-gradient(left,hsla(0,0%,100%,0),#fff,#fff,#fff);
    background: linear-gradient(90deg,hsla(0,0%,100%,0),#fff,#fff,#fff);
    width: 2.8125rem;
    width: 14.1vw;
    text-align: right;
    line-height: 1.375rem;
    line-height: 3.9vw;
}
.noneBtn{
	display: none;
}

</style>