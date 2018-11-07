<template>
	<div><!-- 固定 容器-->
		<div class="login-top">
			<!-- <img class="login-top-logo" src="http://pe1s.static.pdr365.com/timg.jpeg" > -->
			<img class="login-top-logo" :src="logo" >
			<p class="login-top-title">欢迎来到腾讯好时光</p>	
		</div>
		<div class="login-mid">
			<form autocomplete="off" method="POST" class="form-wrapper" id="login-formWrapper">
				<div class="login-mid-form">
					<input type="tel" v-model="UserPhone" name="username" class="text-input" maxlength="60" placeholder="请输入手机号" value="" id="mobile">
				</div>
				<div class="login-mid-form login-mid-form-code">
					<input type="tel" v-model="ValidateCode" id="img_validate_code" name="verifycode" style="margin-left: 0;" class="text-input-code" value="" placeholder="请输入验证码" autocomplete="off" maxlength="6">
					<Button v-on:click="godo_code" style="float: right;display: inline-block;margin-top: .3rem;border: none;font-size: 0.28rem;">{{time}}</Button>
				</div>
				<div class="login-box">
					<div class="login" v-on:click="godo_login">登录</div>
				</div>
			</form>
		</div>
		<p class="login-hint">点击登录，即代表已阅读并同意<span>《好时光影院相关条例》</span></p>
	    <div id="loadingToast" v-if="loading">
		    <div class="weui-mask_transparent"></div>
		    <div class="weui-toast">
		        <i class="weui-loading weui-icon_toast"></i>
		        <p class="weui-toast__content">{{loading}}</p>
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
	</div>
</template>
<script>
	import Cookies from 'js-cookie'
	export default {
		data(){
			return {
				loading:'',
				login:false,
				tip_msg:"",
				UserPhone:'',
				ValidateCode:'',
				time:'获取验证码',
				num:'',
				apid:'',
				logo: this.logo_info.logo

			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="用户登录";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
			this.apid = this.$route.params.apid;
		},
		computed: {
			logo_info() {
				console.log(this.$store.state.logo);
				return this.$store.state.logo
			}
		},
		methods:{
			back:function(){
				//自定义返回上一页函数 可以copy到每一个组件中方便使用
				router.go(-1);
			},showTip:function(msg,ts){
				var page = this;
				page.tip_msg = msg;
				if(ts){
					clearTimeout(window.maxtm);
					window.maxtm = setTimeout(function(){page.tip_msg="";},ts)
				}
			},showlogin:function(msg,ts){
				var page = this;
				page.loading = msg;
				if(ts){
					clearTimeout(window.maxtm);
					window.maxtm = setTimeout(function(){page.loading="";},ts)
				}
			},godo_code:function(){
				var page = this;
				var UserPhone = page.UserPhone;
				var pattern = /^[1][3,4,5,7,8,9][0-9]{9}$/;
				if (!pattern.test(UserPhone)) {
					page.showTip("请输入正确的手机号");
					return
				}
				page.ValidateCode = '';	//重制字段
				page.showlogin("正在发送验证码",1000);
				var url = '/general_api/api/MessageCode?p='+UserPhone+'&ts='+Date.parse(new Date());
		        fetch(url).then(r => r.json()).then(d => {
		        	if (d.available) {
		        		page.loading = false;
		        		page.num = 1 * 60 * 1000;
        				page.daojishi(page.num);
		        	}else{
		        		page.loading = false;
		        		page.showTip(d.messages[0]);
		        	}
		        });
			},daojishi:function(num){
		        var page = this;
		    	if(page.num==0){
		        	var yzm = '发送验证码';
		        	page.time= yzm;
		        	return;
		        }else{
		        	var time = num/1000;
		        	page.time= time + ' S';
	            	page.num = num;
		        }
		        setTimeout(function(){
		        	num -= 1000;
		        	page.daojishi(num);
		        }, 1000)
		    },godo_login:function(){
				var page = this;
				var UserPhone = page.UserPhone;
				var UserCode = page.ValidateCode;
				let apid = Cookies.get('_lac_ap_');
				var url = '/general_api/api/Login?p='+UserPhone+'&code='+UserCode+'&apid='+apid+'&ts='+Date.parse(new Date());
				//page.showlogin("正在登录",5000);
				let loading = weui.loading('加载中');
		        fetch(url).then(r => r.json()).then(d => {
		        	loading.hide();
		        	if (d.available) {
		        		//document.cookie = "_lac_k_=" + d.data;
		        		Cookies.set('_lac_k_', d.data);
		        		this.$router.go(-1);
		        		page.showlogin("登录成功，正在跳转回之前页面",1000);
		        	}else{
		        		page.showTip(d.messages[0]);
		        		//重新走授权流程
		        		//location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();	//online should be
		        	}
		        });
		        page.login = false;
			}
		}
	}
</script>
<style>
.login-top{
	width: 100%;
	height: auto;
	text-align: center;
}
.login-top-logo{
	display: inline-block;
	width: 1.5rem;
	height: 1.4rem;
	margin: 1.95rem 0 .53rem;
}
.login-top-title{
	display: inline-block;
	width: 100%;
	font-size: .36rem;
}
.login-mid{
	width: auto;
	height: auto;
	padding: 0 0.75rem;
}
.form-wrapper{
	width: 100%;
	height: auto;
	margin-top: .4rem;
}
.login-mid-form{
	width: 100%;
	height: 1.2rem;
	border-bottom: 1px solid #a4acb6;
}
.login-mid-form-code{
	font-size: .3rem;
	color: #1AAD19;
}
.text-input{
	width: 90%;
	height: 1rem;
	line-height: 1rem;
	padding-left: .1rem;
	margin-top: .2rem;
	font-size: .3rem;
	color: #a4acb6;
	outline: none;
	border: none;
	background: transparent;
}
.text-input-code{
	float: left;
    margin-left: 5%;
	width: 55%;
	height: 1rem!important;
	line-height: 1rem!important;
	padding-left: .1rem;
	margin-top: .2rem;
	font-size: .3rem!important;
	color: #a4acb6!important;
	outline: none;
	border: none!important;
	display: inline-block!important;
	background: transparent;
}
.login-box{
	width: 100%;
	text-align: center;
	height: .88rem;
	margin: .4rem 0 .58rem 0;
}
.login{
	display: inline-block;
	width: 2.5rem;
	text-align: center;
	height: .88rem;
	line-height: .88rem;
	background: #09bb07;
	color: #FFFFFF;
	font-size: .36rem;
	border-radius: .88rem;
}
.login-hint{
	width: 100%;
	text-align: center;
	color: #9b9b9b;
	font-size: .25rem;
}
.login-hint span{
	color: #6a7fa6;
}

</style>
