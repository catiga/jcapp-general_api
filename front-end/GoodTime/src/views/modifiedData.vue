<template>
	<div><!-- 固定 容器-->
		<div class="details-bg"></div>
		<div class="b_MD">
			<div class="MD_top">
				<div class="MD_top_n">
					<div class="MD_top_img">
						<span>头像</span>
						<img :src=" img == '' ? account.head == '' || account.head == null ? 'http://pe1s.static.pdr365.com/touxiang.png' : account.head : img ">
						<input @change="upPIC" type="file" class="form-control" id="HeadImg">
					</div>

					<div class="MD_top_ck">
						<span>昵称</span>
						<input type="text" v-model="name" :placeholder="account.nickname == '' || account.nickname == null ? '请输入新的昵称' : account.nickname " name="">
					</div>
				</div>
			</div>
			<div class="MD_top_save" v-on:click="PreviewImage">保 存</div>
		</div>
		<div id="loadingToast" v-if="loading">
		    <div class="weui-mask_transparent"></div>
		    <div class="weui-toast">
		        <i class="weui-loading weui-icon_toast"></i>
		        <p class="weui-toast__content">加载中</p>
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
		data : function(){
			return {
				tip_msg:'',
				loading:true,
				name:'',
				img:'',
				account:'',
			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="修改资料";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
			this.GetAccount();
		},methods:{
			showTip:function(msg,ts){
                var page = this;
                page.tip_msg = msg;
                if(ts){
                    clearTimeout(window.maxtm);
                    window.maxtm = setTimeout(function(){page.tip_msg="";},ts)
                }
            },
			GetCode:function(){
	        	var page = this;
                var test = window.location.href;
	            var url = encodeURIComponent(test);
	            var token = Cookies.get('_lac_k_');
	            if ( test.indexOf("code") > 0) {
	                var str = test.split("=");
	                var strs = str[1].toString();
	                var code = strs.substr(0, 32);
	                var url = '/general_api/api/CheckCode?code='+code+'&ts='+Date.parse(new Date());
	                fetch(url).then(r => r.json()).then(d => {
	                    if (!d.available) {
	                        //需要登录
	                        if (d.messages[0]=='bind_mobile') {
	                            this.$router.push({name: 'login', params: { apid: d.data }})
	                            return;
	                        }else{
	                            this.showTip(d.messages[0]);
	                            return;
	                        }
	                		page.loading = false;
	                        return;
	                    }else{
	                        //存token
	                		page.loading = false;
	                        document.cookie = "_lac_k_=" + d.data;
	                        this.GetAccount();
	                    }
	                });
	               	page.loading = false;
	            // }else if( token == undefined && test.indexOf("code") == -1 ){
	            }else if( test.indexOf("code") == -1 ){
	            	location.href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8c675ee4c365e1cd&redirect_uri='+url+'&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect'
	            }
            },
			GetAccount:function(){
	        	var page = this;
	        	var token = Cookies.get('_lac_k_');
	            var url = '/general_api/api/auth/GetAccount?token='+token+'&ts='+Date.parse(new Date());
	            fetch(url).then(r => r.json()).then(d => {
	                if (d.available) {
	                	page.loading = false;
	                    page.account = d.data;
	                }else if ( d.available == false && d.messages[0] == 'user_no_login' ) {
	                    this.GetCode();//获取code
	                }
	                page.loading = false;
	            })
	        },upPIC:function(){
				var page = this;
				page.loading = true;
				var aaaa = new FormData();
		        aaaa.append("HeadImg", document.getElementById("HeadImg").files[0]);
		        // formData.append('token', token);
		        $.ajax({
		            url: '/general_api/api/upload_file',
		            type: "POST",
		            data: aaaa,
		            datatype:"json",
		            contentType: false,
		            processData: false,
		            traditional: true,
		            success: function (data) {
		             if (data.available) {
		             	page.loading = false;
		             	page.img = data.data;
		             }else{
		             	page.loading = false;
		             	page.showTip(data.messages[0]);
		             }
		            },
		            error: function (e) {
		             	page.loading = false;
		             
		            }
		        });
			},PreviewImage:function(){
				var page = this;
				page.loading = true;
				if (img == '') {
					var img = page.account.head;
				}else{
					var img = page.img;
				}
				if (page.name == '') {
					var nickName = page.account.nickname;
				}else{
					var nickName = page.name;
				}
                var token = Cookies.get('_lac_k_');
		        $.ajax({
		            url: '/general_api/api/auth/update_account',
		            type: "POST",
		            data: {
		            	'token':token,
		            	'head':img,
		            	'name':nickName,
		            },
		            datatype:"json",
		            success: d =>{
		            	page.loading = false;
		            	var data = JSON.parse(d);
		             	if (data.available) {
							page.showTip('修改资料成功');
							page.$router.go(-1);
		             	}else{
							page.showTip('修改资料失败');
		             	}
		            },
		            error:  e => {
		             	page.loading = false;
		             	page.showTip('改修资料失败');
		            }
		        });
			}
		}
	}
</script>
<style>
.b_MD{
	width: 100%;
}
.MD_top{
	width: auto;
	height: 2.33rem;
	padding: 0 0 0 .3rem;
	background: #fff;
}
.MD_top_n{
	width: 100%;
}
.MD_top_img{
	display: flex;
	width: 100%;
	height: 1.53rem;
	border-bottom: 1px solid #efeff4;
	justify-content: space-between;
}
.MD_top_img span{
	display: inline-block;
	line-height: 1.53rem;
	font-size: .3rem;
}
.MD_top_img img{
    position: absolute;
    right: 0;
	width: 1.1rem;
	height: 1.1rem;
	border-radius: 50%;
	margin: .21rem .3rem 0 0;
}
.form-control{
    width: 1.1rem;
    height: 1.1rem;
    margin-top: .2rem;
    margin-right: .2rem;
    opacity: 0;
    z-index: 99;	
}
.MD_top_ck{
	display: flex;
	width: 100%;
	height: .85rem;
	line-height: .85rem;
	justify-content: space-between;
}
.MD_top_ck span{
	display: inline-block;
	line-height: .85rem;
	font-size: .3rem;
}
.MD_top_ck input{
	text-align: right;
	margin-right: .3rem;
	width: 50%;
	height: .8rem;
    /*margin-top: .225rem;*/
	border: none;
	outline: none;
}
.MD_top_save{
	position: fixed;
	bottom: .4rem;
	width: 96%;
	margin-left: 2%;
	text-align: center;
	height: .88rem;
	line-height: .88rem;
	background: #0ABB07;
	border: 1px solid #019400;
	color: #fff;
	font-size: .34rem;
	border-radius: .08rem;
}
</style>