<template>
	<div style="width:100%;height:100%;"><!-- 固定 容器-->
		<!-- <div class="ChooseCity">
			 <div class="city-box" style="top: .2rem;">
				<router-link to="/cityList">
				<div class="city-name">
				{{city_name == undefined ? '北京市' : city_name}}
				</div>
				<div class="am-list-arrow">
					<span class="am-icon arrow vertical"></span>
				</div>
				</router-link>
			</div>
		</div> -->
		<div class="swiper-gb" style="height:1rem">
            <swiper class="banner-box" :options="swiperOption" ref="indexSwiper">
                <swiper-slide class="banner-slide" style="transform:scale(1);padding:0;">
                	<!-- <img class="banner-img" src="https://cdn.iplaysky.com/static/img/wechat_banner.png"> -->
					<div></div>
                </swiper-slide>
            </swiper>
            <div class="city-box">
				<!-- <router-link to="/cityList"> -->
				<div class="city-name" v-on:click="godo_cinemas">
				{{cinemanm == '' ? '...' : cinemanm}}
				</div>
				<div class="am-list-arrow">
					<span class="am-icon arrow vertical"></span>
				</div>
				<!-- </router-link> -->
			</div>
        </div>
        <div class="movie-box">
    		<div class="hotsShowing">
    			<div class="hotsShowing-top">
    				<div class="hotsShowing-top-l">
    					<div class="hotsShowing-top-l-main"></div>
    					<div class="hotsShowing-top-l-title">热映</div>
    				</div>
    				<div class="hotsShowing-top-r">
    					<div class="hotsShowing-top-r-num">共{{MovieNum}}部</div>
    					<span class="show-icon show"></span>
    				</div>
    			</div>
    			<div class="hotsShowing-bottom" style="position: relative;">
					<div class="wrapper" style="height:auto;position:absolute;left:0;top:0;height:100%;" ref="movieWrapper">
						<ul :style="movieListWidth">
							<li v-for="(item,index) in MovieList">
								<img :src="item.img == null ? 'http://pe1s.static.pdr365.com/default_movie_pic.jpg' : item.img " @click="videoDetails(item.properties.id,index)" />
								<p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">{{item.name}}</p>
								<div @click="godo_buy(item.properties.id,index)">购票</div>
							</li>
							
						</ul>
					</div>
    			</div>
    		</div>
    		<div class="hotsShowing" style="padding-bottom: 50px;">
    			<div class="hotsShowing-top">
    				<div class="hotsShowing-top-l">
    					<div class="hotsShowing-top-l-main"></div>
    					<div class="hotsShowing-top-l-title">即将上映</div>
    				</div>
    				<div class="hotsShowing-top-r">
    					<!-- <div class="hotsShowing-top-r-num">更多</div> -->
    					<span class="show-icon show"></span>
    				</div>
    			</div>
    			<div class="hotsShowing-bottom" style="position:relative;">
					<div class="wrapper" style="height:auto;position:absolute;left:0;top:0;height:100%;" ref="comingWrapper">
						<ul :style="comingListWidth">
							<li v-for="(item,index) in ComeList">
								<img :src="item.img == null ? 'http://pe1s.static.pdr365.com/default_movie_pic.jpg' : item.img " v-on:click="GoIngvideoDetails(item.id,index)" />
								<!-- <img :src="item.img == null ? 'http://pe1s.static.pdr365.com/default_movie_pic.jpg' : item.img " /> -->
								<p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">{{item.nm}}</p>
								<span>{{item.rt}}</span>
								<!-- <div v-on:click="godo_buy(item.properties.id,index)">购票</div> -->
							</li>	
						</ul>
					</div>
    			</div>
    		</div>
        </div>
        <!-- <div class="bottom-tab">
        	<a href="javascript:void(0)" class="store" v-on:click="godo_goods">
				<div class="icon"></div>    
			</a> 
			<a href="javascript:void(0)" class="movie" data-status="active">        
				<div class="icon"></div>    
			</a>
			<a href="javascript:void(0)" class="cinema" v-on:click="godo_cinemas">
				<div class="icon"></div>    
			</a>
			<a href="javascript:void(0)" class="mine" v-on:click="godo_mine">        
				<div class="icon"></div>    
			</a>
		</div> -->
		
		<tabBottom :active="'movie'"></tabBottom>
		
		<!--弹出对话框-->
		<div class="js_dialog" id="iosDialog2" v-if="forbid">
	        <div class="weui-mask"></div>
	        <div class="weui-dialog">
	            <div class="weui-dialog__bd" style="font-size: .3rem;padding: .6rem 20px .28rem;min-height: 24px;color: #333333;">监测到离您最近的影城</div>
	            <div class="input-box" style="font-size: .3rem;margin-bottom: .3rem;">
	            好时光影城
	            </div>
	            <div class="weui-dialog__ft">
	                <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default" v-on:click="godo_cinema">切换</a>
	                <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" v-on:click="forbid=false">确定</a>
	            </div>
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
	import BScroll from 'better-scroll'
	import tabBottom from '_c/tabBottom/tabBottom.vue'
	export default {
		data(){
			return {
				loading:true,
				forbid:false,
				tip_msg:"",
				MovieList:'',
				MovieNum:'',
				ComeList:'',
				cinemaId: 0,
				city_list: '',
				city_name: '',
				city_no:'',
				cinemanm:'',
				comingListWidth: "",
				movieListWidth: "",
				swiperOption: {
					// some swiper options/callbacks
					// 所有的参数同 swiper 官方 api 参数
					// ...
					autoplay: true,//可选选项，自动滑动
				}
			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="正在热映";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
			// this.GetStore();//获取影城列表
			this.wxinit();
			this.wxlocate();
			this.CheckLogin();
			this.ComeingMovie();//即将上映
			this.cityList();//获取城市列表
			this.GetMovie();//获取影片列表
            var cinemanm = this.getCookie("storeName");
            this.decodeUnicode(cinemanm);
		},
		computed: {
			swiper() {
				return this.$refs.indexSwiper.swiper
			}
		},
		methods:{
			wxinit:function(){
				let page = this;
	
				let param = "cu=/general_api/tcss/index";
	            let url = "/general_api/h5/wx/jsticket/?"+ param;
	            
		        fetch(url).then(r => r.json()).then(d => {
		        	console.log(d);
		        	if (d.ret_code == '0000') {
		        		wx.config({
						    debug: false,
						    appId: d.data.appid, // 必填，公众号的唯一标识
						    timestamp: d.data.timestamp, // 必填，生成签名的时间戳
						    nonceStr: d.data.noncestr, // 必填，生成签名的随机串
						    signature: d.data.sign_str,// 必填，签名，见附录1
						    jsApiList: ['startRecord','translateVoice', 'stopRecord', 'onVoiceRecordEnd', 'playVoice', 
						                'pauseVoice', 'stopVoice', 'onVoicePlayEnd', 'uploadVoice', 'downloadVoice', 
						                'chooseImage', 'previewImage', 'uploadImage', 'downloadImage', 
						                'translateVoice', 'getNetworkType', 'onMenuShareTimeline', 'onMenuShareAppMessage','getLocation','openLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
						});
		        	}
		        });
			},
			wxlocate:function(){
				let page = this;
	    		wx.ready(function(){
		    		wx.getLocation({
						type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
						success: function (res) {
						    var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
					        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
					        var speed = res.speed; // 速度，以米/每秒计
					        var accuracy = res.accuracy; // 位置精度
	
			    			// navigator.geolocation.getCurrentPosition(function(msg){
	
			    			// var longitude = msg.coords.longitude;
			    			// var latitude = msg.coords.latitude;
			    			AMap.service('AMap.Geocoder',function(){
			    			    let geocoder = new AMap.Geocoder({});
			    			    geocoder.getAddress([longitude,latitude], function(status, result) {
			    			    	console.log(result);
			    			        if (status === 'complete' && result.info === 'OK') {
			    			           window.location_msg = result;
									//    page.reqLocate(longitude,latitude,result.regeocode.addressComponent.province,result.regeocode.addressComponent.city);
										let loc_city = result.regeocode.addressComponent.city?result.regeocode.addressComponent.city:result.regeocode.addressComponent.province;
										let str = '当前定位城市:' + loc_city;
										let cookie_city = Cookies.get('city_name');
										
										alert(cookie_city + '===' + loc_city);
										if(cookie_city) {
											//已经存储过城市
											if(cookie_city.indexOf(loc_city.substring(0, loc_city.length - 1))>-1) {
												//说明当前城市与之前选择的城市一致，不用做处理
												
											} else {
												//不一致
												/*
												let is_supp_city = false;
												if(page.city_list) {
													page.city_list.forEach(item => {
														if(item.city_name.indexOf(loc_city.substring(0, loc_city.length - 1))>-1) {
															is_supp_city = true;
														}
													})
												}
												if(is_supp_city) {
													//是系统支持的城市
												} else {
												}
												*/
												page.$router.push({name: 'cinemaList', params: { mid: '2' }})
											}
										} else {
											//没有存储过城市，需要弹出提示当前城市，并支持选择城市框
											page.confirmLocate(str, true);
										}
			    			        }else{
										//page.reqLocate("1","1","","");
										page.confirmLocate('定位失败，请重新选择城市', false);
			    			        }
			    			    });  
			    			});
			    		},fail:function(msg){
		 					page.city_forbid = true;
							//page.reqLocate("1","1","","");
							page.confirmLocate('定位失败，请重新选择城市', false);
			    		},cancel:function(msg){
							page.city_fail = true;
							page.confirmLocate('定位失败，请重新选择城市', false);
			    			//page.reqLocate("1","1","","");
					        // enableHighAcuracy: true,
					        // timeout: 10000,
					        // maximumAge: 3000
					    }
		    		});
				});
			},

			// 定位确认弹窗
			confirmLocate(msg, status) {
				weui.dialog({
					content: msg,
					buttons: [{
						label: '确定',
						type: 'primary',
						onClick: function () { 
							if(!status) {
								this.$router.push({name: 'cinemaList', params: { mid: '2' }})
							}
						}
					} 
					]
				});
			},
			
			
			//门店名称转码
            decodeUnicode:function (str) {
                str = str.replace(/\\/g, "%");
                let inner_cinemaname = unescape(str);
                inner_cinemaname = eval("'" + inner_cinemaname + "'");
                this.cinemanm = unescape(inner_cinemaname.replace(/\u/g, "%u"));
                //this.cinemanm = unescape(str);
            },
			CheckLogin:function(){
                let auth_type = Cookies.get('_lac_ap_');
                if(!auth_type) {
                	location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();	//online should be
                }
			},
			//取cookie
            getCookie:function(name){
                var strcookie = document.cookie;//获取cookie字符串
                var arrcookie = strcookie.split("; ");//分割
                //遍历匹配
                for ( var i = 0; i < arrcookie.length; i++) {
                	var arr = arrcookie[i].split("=");
	                if (arr[0] == name){
	                	return arr[1];
	                }
                }
                return "";
            },
            //获取城市列表
            cityList:function(){
            	let city_no = Cookies.get('city_no');
            	if(!city_no) {
            		var page = this;
	            	var url = '/general_api/api/cityList?ts='+Date.parse(new Date());
			        fetch(url).then(r => r.json()).then(d => {
			        	console.log(d);
			        	if (d.code == '0' && d.data && d.data.length>0) {
			        		page.city_list = d.data;
			        		Cookies.set('city_no', d.data[0].city_no);
			        		// this.city = d.data;
			        		page.city_name = d.data[0].city_name;
	        				page.city_no = Number(d.data[0].city_no);
			        		this.GetStore(Number(d.data[0].city_no));
			        		Cookies.set('city_name', page.city_name);
			        	}
			        	page.loading = false;
			        });
            	} else {
            		//判断有没有加载过门店
            		let cinema_id = Cookies.get('cinemaId');
            		if(!cinema_id) {
						//需要加载一次门店列表
						this.GetStore(city_no);
					} else {
						this.cinemaId = cinema_id;
					}
            	}
			},
			ComeingMovie(){
		        var url = '/general_api/api/UpcomingMovies';
		        fetch(url).then(r => r.json()).then(d => {
		        	if (d.code == 0) {
						this.ComeList = d.data.coming;
						this.comingListWidth = `width: ${1.78 * d.data.coming.length}rem`;
						this.$nextTick(() => {
							this.scroll = new BScroll(this.$refs.comingWrapper, {click: true, eventPassthrough: true, scrollX: true, scrollY: false, preventDefault: false})
						})
		        	}
		        });
			},GetStore:function(city_no){
				var page = this;
            	var url = '/general_api/api/StoreList?city_no='+city_no+'&ts='+Date.parse(new Date());
		        fetch(url).then(r => r.json()).then(d => {
		        	console.log(d);
		        	if (d.code == 0 && d.data && d.data.length>0) {
		        		let cinema_id = 0;
		        		if(d.data[0].cinema_id) {
		        			cinema_id = d.data[0].cinema_id;
		        		}
		        		page.cinemaId = cinema_id;
		        		page.cinemanm = d.data[0].store_name;
		        		page.s_id = d.data[0].s_id;
		        		//默认设置cookie
		        		console.log('set cinemaId default cookie======' + page.cinemaId);
		        		Cookies.set('cinemaId', page.cinemaId);
		        		Cookies.set('s_id', d.data[0].store_basic);	//一定有
		        		//var add = d.data[0].city + d.data[0].zone + d.data[0].address
		        		let add = d.data[0].city;
						if(d.data[0].zone) {
							add = add + d.data[0].zone;
						}
						if(d.data[0].address) {
							add = add + d.data[0].address;
						}
						page.encodeUnicodeAdd(add);
						page.encodeUnicodeName(d.data[0].physics_name);
						this.GetMovie();
		        	}
		        	page.loading = false;
		        });
			},encodeUnicodeAdd: function(str) {
			    var res = [];
			    for ( var i=0; i<str.length; i++ ) {
				res[i] = ( "00" + str.charCodeAt(i).toString(16) ).slice(-4);
			    }
			    //document.cookie = "storeAdd="+"\\u" + res.join("\\u")
			    Cookies.set('storeAdd', "\\u" + res.join("\\u"));
			},
			encodeUnicodeName: function(str) {
			    var res = [];
			    for ( var i=0; i<str.length; i++ ) {
				res[i] = ( "00" + str.charCodeAt(i).toString(16) ).slice(-4);
			    }
			    //document.cookie = "storeName="+"\\u" + res.join("\\u")
			    Cookies.set('storeName', "\\u" + res.join("\\u"));
			},GetMovie(){
				let cinema_id = Cookies.get('cinemaId');
				if(!cinema_id) {
					//需要加载一次门店列表
					let city_no = Cookies.get('city_no');
					this.GetStore(city_no);
				} else {
					var url = '/general_api/api/CityMovieList?cinema_id='+cinema_id+'&ts='+Date.parse(new Date());
			        fetch(url).then(r => r.json()).then(d => {
			        	if (d.code == 0) {
			        		this.MovieList = d.data;
							this.MovieNum = d.data.length;
							this.movieListWidth = `width: ${1.78 * d.data.length}rem`;
							this.$nextTick(() => {
								this.scroll = new BScroll(this.$refs.movieWrapper, {click: true, eventPassthrough: true, scrollX: true, scrollY: false, preventDefault: false})
							})
			        	}
			        	this.loading = false;
			        });
				}
			},showTip:function(msg,ts){
				var page = this;
				page.tip_msg = msg;
				if(ts){
					clearTimeout(window.maxtm);
					window.maxtm = setTimeout(function(){page.tip_msg="";},ts)
				}
			},back:function(){
				//自定义返回上一页函数 可以copy到每一个组件中方便使用
				router.go(-1);
			},godo_cinema:function(){
				this.$router.push({name: 'moviecinemaList', params: { mid: '2' }})
			},GoIngvideoDetails:function(mid,index){
				this.$router.push({name: 'GoIngvideoDetails', params: { mid: mid , index : index }})
			},
			videoDetails:function(mid,index){
				this.$router.push({name: 'videoDetails', params: { mid: mid , index : index }})
			},
			godo_buy:function(mid,index){
				var page = this;
				this.$router.push({name: 'schedule',params: { cinemaId: page.cinemaId , mid : mid , index : index  }})
			},
			godo_mine:function(){
				location.href="/general_api/tcss/index#/personalCenter"
				// this.$router.push({name: 'personalCenter'})
			},
			godo_cinemas:function(){
				this.$router.push({name: 'cinemaList', params: { mid: '2' }})
			},
			godo_goods:function(){
				location.href="/general_api/tcss/index#/storeList"
				// this.$router.push({name: 'storeList'})
			},
		},
		components: {
            tabBottom
        }
	}
</script>
<style>
.ChooseCity{
	height: .6rem;
}
.swiper-gb{
	position: relative;
	width: 100%;
	height: 3.25rem;
}
.banner-box , .banner-slide , .banner-img{
	width: 100%;
	height: 100%;
}
.city-box{
    position: absolute;
    top: .3rem;
    left: .3rem;
    line-height: 1.3;
    z-index: 99;
    font-weight: bold;
    background: rgba(0,0,0,.4);
    height: auto;
    padding: .1rem;
    border-radius: .3rem;
}
.city-name {
    font-size: .26rem;
    vertical-align: middle;
    display: inline-block;
    max-width: 170px;
    width: auto;
    height: .3rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
	color: #fff;
}
.am-list-arrow {
    display: inline-block;
    height: .3rem;
    margin-right: -2px;
    width: 12px;
    vertical-align: middle;
}
.am-icon.arrow {
    display: block;
    width: 12px;
    height: 12px;
    overflow: hidden;
}
.am-icon.arrow.vertical:before {
    margin-left: 2px;
    -webkit-transform: rotate(45deg);
    margin-top: .04rem;
}
.am-icon.arrow:before {
    content: "";
    display: block;
    width: 6px;
    height: 6px;
    margin-left: 0;
    border: 2px solid #fff;
    border-top: 0 none;
    border-left: 0 none;
}
.movie-box{
	width: 100%;
	height: auto;
	border-radius: 10px 10px 0 0;
	background: #fff;
    float: left;
    margin-top: -10px;
    z-index: 9;
    position: relative;
}
.hotsShowing{
	width: 100%;
	height: auto;
}
.hotsShowing-top{
	display: flex;
	width: auto;
	padding: 0 .3rem;
	height: .9rem;
	/*align-items: center;*/
	line-height: .9rem;
	justify-content: space-between;
}
.hotsShowing-top-l , .hotsShowing-top-r{
	/*display: inline-block;*/
	height: .9rem;
	width: auto;
}
.hotsShowing-top-l-main{
	display: inline-block;
	width: .07rem;
	height: .4rem;
	border-radius: .4rem;
	background: #1aad19;
	vertical-align: middle;
}
.hotsShowing-top-l-title{
	display: inline-block;
	font-size: .3rem;
	vertical-align: middle;
	margin-left: .2rem;
}
.hotsShowing-top-r-num{
	display: inline-block;
	font-size: .3rem;
	color: #999;
	vertical-align: middle;
}

.show-icon.show {
    display: inline-block;
    width: 12px;
    height: 12px;
    overflow: hidden;
    vertical-align: middle;
}
.show-icon.show:before {
	margin-top: 2px;
    -webkit-transform: rotate(-45deg);
}
.show-icon.show:before {
    content: "";
    display: inline-block;
    width: 6px;
    height: 6px;
    margin-left: 0;
    border: 2px solid #999;
    border-top: 0 none;
    border-left: 0 none;
}
.hotsShowing-bottom{
	width: auto;
	margin: 0 .3rem;
	height: 4rem;
	border-bottom: 1px solid #ccc;
	
}
.hotsShowing-bottom ul{
	/* width: 100%; */
	/* height: auto; */
	/* overflow:auto; */
	white-space: nowrap;
}
.hotsShowing-bottom ul li{
	/*float: left;*/
	display: inline-block;
	width: 1.7rem;
	height: 3.8rem;
	margin-right: .08rem;
}
.hotsShowing-bottom ul li img{
	width: 1.7rem;
	height: 2.35rem;
	border-radius: 5px;
}
.hotsShowing-bottom ul li p{
	font-size: .28rem;
	line-height: .6rem;
}
.hotsShowing-bottom ul li div{
	width: 1.05rem;
	text-align: center;
	height: .5rem;
	line-height: .5rem;
	border: 1px solid #1aad19;
	font-size: .26rem;
	color: #1aad19;
	border-radius: 4px;
}
.hotsShowing-bottom ul li span{
	display: inline-block;
	font-size: .25rem;
	color: #999;
}

.bottom-tab {
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    height: 50px;
    z-index: 10;
    box-sizing: border-box;
    width: 100%;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-orient: horizontal;
    -webkit-box-direction: normal;
    -webkit-flex-direction: row;
    -ms-flex-direction: row;
    flex-direction: row;
    -webkit-flex-wrap: nowrap;
    -ms-flex-wrap: nowrap;
    flex-wrap: nowrap;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center;
    padding-top: 6px;
    background: #f7f7fa;
    border-top: 1px solid #f7f7f8;
}
.bottom-tab a {
    display: block;
    text-align: center;
    -webkit-box-flex: 1;
    -webkit-flex: 1 1 33%;
    -ms-flex: 1 1 33%;
    flex: 1 1 33%;
    color: #8a869e;
}
.bottom-tab a[data-status=active] {
    color: #1aad19;
}
.bottom-tab a .icon {
    width: 22px;
    height: 22px;
    margin: 0 auto;
}
.bottom-tab .movie[data-status=active] .icon {
    background-image: url();
    background-position: center center;
    background-size: cover;
}
.bottom-tab .movie:after {
    content: '电影';
    display: block;
    font-size: 10px;
    padding-top: .125rem;
    padding-top: .6vmin;
}
.bottom-tab .store .icon {
    background-image: url(http://pe1s.static.pdr365.com/tencent/icon/goods_f.png);
    background-position: center center;
    background-size: cover;
}

.bottom-tab .store[data-status=active] .icon {
    background-image: url(http://pe1s.static.pdr365.com/tencent/icon/goods_b.png);
    background-position: center center;
    background-size: cover;
}
.bottom-tab .store:after {
    content: '商品';
    display: block;
    font-size: 10px;
    padding-top: .125rem;
    padding-top: .6vmin;
}
.bottom-tab .movie[data-status=active] .icon{
	background-image: url(http://pe1s.static.pdr365.com/tencent/icon/movie_b.png);
    background-position: center center;
    background-size: cover;
}
.bottom-tab .movie .icon{
	background-image: url(http://pe1s.static.pdr365.com/tencent/icon/movie_f.png);
    background-position: center center;
    background-size: cover;
}
.bottom-tab .cinema .icon {
    background-image: url(http://pe1s.static.pdr365.com/tencent/icon/cinema_f.png);
    background-position: center center;
    background-size: cover;
}
.bottom-tab .cinema[data-status=active] .icon {
    background-image: url(http://pe1s.static.pdr365.com/tencent/icon/cinema_b.png);
    background-position: center center;
    background-size: cover;
}
.bottom-tab .cinema:after {
    content: '门店';
    display: block;
    font-size: 10px;
    padding-top: .125rem;
    padding-top: .6vmin;
}
.bottom-tab .mine .icon {
    background-image: url(http://pe1s.static.pdr365.com/tencent/icon/user_f.png);
    background-position: center center;
    background-size: cover;
}
.bottom-tab .mine[data-status=active] .icon {
    background-image: url(http://pe1s.static.pdr365.com/tencent/icon/user_b.png);
    background-position: center center;
    background-size: cover;
}
.bottom-tab .mine:after {
    content: '我的';
    display: block;
    font-size: 10px;
    padding-top: .125rem;
    padding-top: .6vmin;
}
</style>