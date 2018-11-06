<template>
	<div><!-- 固定 容器-->
		<div class="details-bg"></div>
		<div class="cityList-top">
			<p>当前城市：<span>{{city_name}}</span></p>
		</div>
		<div class="cityList-bot">
			<ul>
				<li v-for="item in city" :data-no="item.city_no" v-on:click="go_index(item.city_name,item.city_no)">
					{{item.city_name}}
					<!-- <div class="pitch-up"></div> -->
				</li>
			</ul>
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
		data(){
			return {
				loading:true,
				city:'',
				city_name:'',
			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="切换城市";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
			this.city_nm = Cookies.get('goodsOrderNo');
			this.cityList()
		},methods:{
			back:function(){
				//自定义返回上一页函数 可以copy到每一个组件中方便使用
				router.go(-1);
			},cityList:function(){
				console.log("11")
				var url = '/general_api/api/cityList?ts='+Date.parse(new Date());
		        fetch(url).then(r => r.json()).then(d => {
		        	if (d.code == '0') {
		        		this.city = d.data;
		        		this.loading = false;
		        	}
		        });
		        var city_name = Cookies.get('city_name');
		        this.city_name = city_name;
		        
		        //this.decodeUnicodeNm(city_name)
			},go_index:function(city_name,city_no){
                //this.encodeUnicodeCityNM(city_name)
                //document.cookie = "city_no=" + Number(city_no);
                Cookies.set('city_no', city_no);
                Cookies.set('city_name', city_name);
				this.$router.push({name: 'cinemaList', params: { mid: '2' }})
			},
			//解码
			decodeUnicodeNm:function (str) {
                str = str.replace(/\\/g, "%");
                var city_name = unescape(str);
                if (city_name == undefined) {
		        	this.city_name = ''
		        }else{
		        	this.city_name = city_name;
		        }
                // return 
            },
            //转码
            encodeUnicodeCityNM: function(str) {
			    var res = [];
			    for ( var i=0; i<str.length; i++ ) {
				res[i] = ( "00" + str.charCodeAt(i).toString(16) ).slice(-4);
			    }
                //document.cookie = "city_name=" + "\\u" + res.join("\\u");
                Cookies.set('city_name', res.join("\\u"));
			},
		}
	}
</script>
<style>
.cityList-top{
	width: 100%;
	height: .88rem;
	background: #FFFFFF;
	margin-bottom: .3rem;
}
.cityList-top p{
	display: inline-block;
	padding-left: .3rem;
	font-size: .34rem;
	line-height: .88rem;	
}
.cityList-bot{
	width: auto;
	padding-left: .3rem;
	background: #FFFFFF;
}
.cityList-bot ul{
	width: 100%;
}
.cityList-bot ul li{
	width: 100%;
	height: .88rem;
	line-height: .88rem;
	font-size: .28rem;
	border-bottom: 1px solid #efeff4;
}
.pitch-up{
    display: inline-block;
    float: right;
    margin: .3rem;
    width: 11px;
    height: 4px;
    border: 3px solid #09BB07;
    border-top-color: transparent;
    border-right-color: transparent;
    -ms-transform: rotate(-60deg);
    -moz-transform: rotate(-60deg);
    -webkit-transform: rotate(-60deg);
    transform: rotate(-45deg);	
}

</style>