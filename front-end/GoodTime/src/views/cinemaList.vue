<template>
	<div><!-- 固定 容器-->
		<div class="details-bg"></div>
		<!--城市-->
		<div	 class="cinema-city">
			<div class="c-city-box">
				<router-link to="/cityList" class="dy-list-content">
				<div class="c-city-name" style="color: #a9a9a9;">
				{{city_name}}
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
				<li class="cinema-li" v-for="item in cinemaList" v-on:click="godo_choose(item.cinema_id,item.id, item.store_name,item.city,item.zone,item.address,)">
					<div class="cinema-li-top">
						<p class="cinema-li-nm">{{item.store_name}}</p>
						<p class="cinema-li-price">29.8<span>元起</span></p>
					</div>
					<p class="cinema-li-info">{{item.city}}{{item.zone}}{{item.address}}</p>
					<div class="cinema-li-distance">200m</div>
				</li>
			</ul>
		</div>
        <!-- <div class="bottom-tab">
        	<a href="javascript:void(0)" class="store" v-on:click="godo_goods">
				<div class="icon"></div>    
			</a>
			<a href="javascript:void(0)" class="movie" v-on:click="godo_index">        
				<div class="icon"></div>    
			</a>
			<a href="javascript:void(0)" class="cinema" data-status="active">
				<div class="icon"></div>    
			</a>
			<a href="javascript:void(0)" class="mine" v-on:click="godo_mine">        
				<div class="icon"></div>     
			</a>
		</div> -->
		
		<tabBottom :active="'cinema'"></tabBottom>
		
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
	import tabBottom from '_c/tabBottom/tabBottom.vue'
	export default {
		data(){
			return {
				loading:true,
				cinemaList:'',
				city_name:''
			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="门店";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
			this.GetStore();//获取影城列表
		},methods:{
			GetStore:function(){
				var page = this;
            	var city_no = Cookies.get('city_no');
            	if (city_no == undefined ) {
            		var url = '/general_api/api/cityList?ts='+Date.parse(new Date());
			        fetch(url).then(r => r.json()).then(d => {
			        	if (d.code == '0' && d.data && d.data.length>0) {
            				var city_no = Number(d.data[0].city_no);
            				var url = '/general_api/api/StoreList?city_no='+city_no+'&ts='+Date.parse(new Date());
					        fetch(url).then(r => r.json()).then(d => {
					        	page.loading = false;
					        	if (d.code == 0 && d.data && d.data.length>0) {
					        		page.cinemaList = d.data;
					        	}
					        });
					        var city_name = Cookies.get('city_name');
					        this.decodeUnicodeNm(city_name)
			        	} else {
			        		page.loading = false;
			        	}
			        });
            	}else{
            		var url = '/general_api/api/StoreList?city_no='+city_no+'&ts='+Date.parse(new Date());
			        fetch(url).then(r => r.json()).then(d => {
			        	page.loading = false;
			        	if (d.code == 0 && d.data && d.data.length>0) {
			        		page.cinemaList = d.data;
			        	}
			        });
			        var city_name = Cookies.get('city_name');
			        this.decodeUnicodeNm(city_name)
            	}  
			},
			//解码
			decodeUnicodeNm:function (str) {
				var page = this;
                str = str.replace(/\\/g, "%");
                var city_name = unescape(str);
                if (city_name == undefined) {
		        	page.city_name = ''
		        }else{
		        	page.city_name = city_name;
		        }
                // return 
            },back:function(){
				//自定义返回上一页函数 可以copy到每一个组件中方便使用
				this.$router.go(-1);
			},godo_choose:function(cinema_id, id, store_name, city, zone, address){
				//document.cookie = "cinemaId=" + id;
				document.cookie = "cinemaId=" + cinema_id + ";path=/";
				document.cookie = "s_id=" + id + ";path=/";
				// document.cookie = "storeName=" + store_name;
				// document.cookie = "storeAdd=" + city+zone+address;
				let add = city;
				if(zone) {
					add = add + zone;
				}
				if(address) {
					add = add + address;
				}
				this.encodeUnicodeAdd(add);
				this.encodeUnicodeName(store_name);
				this.$router.push({name: 'index' ,params: {}})
				//this.$router.go(-1);	//不指定，为返回
			},
			encodeUnicodeAdd: function(str) {
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
			},godo_index:function(){
				this.$router.push({name: 'index'})
			},godo_mine:function(){
				location.href="/general_api/tcss/index#/personalCenter"
				// this.$router.push({name: 'personalCenter'})
			},godo_goods:function(){
				location.href="/general_api/tcss/index#/storeList"
				// this.$router.push({name: 'storeList'})
			}
		},
		components: {
            tabBottom
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
