<template>
	<div>
		<div class="details-bg"></div>
		<div class="mine-top-box">
			<div class="mine-top">
				<div class="mine-top-img">
					<img :src="account.head == '' || account.head == null ? 'https://ex.piaodaren.com/img_server/_common/icon/touxiang.png' : account.head" style="border:.04rem solid #f2f2f2;" />
				</div>
				<div class="mine-top-nm">
					<p>{{account.nickname == '' || account.nickname == null ? '默认昵称' : account.nickname}}</p>
					<!-- <img src="https://ex.piaodaren.com/img_server/_common/icon/level_1.png" /> -->
				</div>
				<div class="mine-top-material" v-on:click="godo_modified">资料</div>
			</div>
			<div class="mine-top-menu">
				<ul class="mine-top-menu-ul">
					<li class="mine-top-menu-list" v-on:click="godo_myCards" style="width:50%">
						<p class="mine-top-menu-list-num">{{balance}}</p>
						<p class="mine-top-menu-list-nm">余额</p>
					</li>
					<li class="mine-top-menu-list" v-on:click="godo_myIntegral" style="display:none">
						<p class="mine-top-menu-list-num">{{Intergral}}</p>
						<p class="mine-top-menu-list-nm">积分</p>
					</li>
					<li class="mine-top-menu-list" v-on:click="godo_coupons" style="width:50%">
						<p class="mine-top-menu-list-num">{{Coupons}}</p>
						<p class="mine-top-menu-list-nm">优惠券</p>
					</li>
				</ul>
			</div>
		</div>
		<div class="mine-mid-menu">
			<div class="mine-mid-menu-top">
				<div class="mine-mid-menu-top-list" v-on:click="godo_myCards">
					<img src="https://ex.piaodaren.com/img_server/_common/icon/card_icon.png" style="height: .4rem;margin-top: .25rem;" />
					<p>会员卡</p>
					<div class="bm-list-arrow">
						<span class="bm-icon arrow vertical"></span>
					</div>
				</div>
				<div class="mine-mid-menu-top-list" v-on:click="godo_myTicket">
					<img src="https://ex.piaodaren.com/img_server/_common/icon/order_icon.png" style="width: 0.37rem;height: 0.4rem;margin-top: .25rem;" />
					<p>订单中心</p>
					<div class="bm-list-arrow">
						<span class="bm-icon arrow vertical"></span>
					</div>
				</div>
				<div class="mine-mid-menu-top-list" v-on:click="godo_myMarkets">
					<img src="https://ex.piaodaren.com/img_server/_common/icon/102.png" style="width: 0.37rem;height: 0.4rem;margin-top: .25rem;" />
					<p>活动信息</p>
					<div class="bm-list-arrow">
						<span class="bm-icon arrow vertical"></span>
					</div>
				</div>
			</div>
			
			<!-- <div class="mine-mid-menu-bot">
				<div class="mine-mid-menu-bot-list">
					<img src="https://ex.piaodaren.com/img_server/_common/icon/set_icon.png" style="width: .4rem;height: .4rem;" />
					<p>设置</p>
					<div class="bm-list-arrow">
						<span class="bm-icon arrow vertical"></span>
					</div>
				</div>
			</div> -->
		</div>
		<div id="loadingToast" v-if="loading">
            <div class="weui-mask_transparent"></div>
            <div class="weui-toast">
                <i class="weui-loading weui-icon_toast"></i>
                <p class="weui-toast__content">加载中</p>
            </div>
        </div>
        <!-- <div class="bottom-tab">
        	<a href="javascript:void(0)" class="store" v-on:click="godo_goods">
				<div class="icon"></div>    
			</a>
			<a href="javascript:void(0)" class="movie" v-on:click="godo_index">        
				<div class="icon"></div>    
			</a>
			<a href="javascript:void(0)" class="cinema" v-on:click="godo_cinemas">
				<div class="icon"></div>    
			</a>
			<a href="javascript:void(0)" class="mine" data-status="active">        
				<div class="icon"></div> 
			</a>
		</div> -->
		
		<tabBottom v-if="tabShow !== 'scan'" :active="'mine'"></tabBottom>
		
	</div>
</template>
<script>
    import Cookies from 'js-cookie'
    import tabBottom from '_c/tabBottom/tabBottom.vue'
	export default {
		data(){
			return {
				loading: true,
				account: '',
				balance: 0,
				Coupons: 0,
				Intergral: 0,
				tabShow: ""
			}
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="个人中心";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
			
			this.GetAccount();
		},
		mounted() {
			this.tabShow = this.$route.params.st;
		},
		methods:{
			GetAccount:function(){
	        	let page = this;
	        	let token = Cookies.get('_lac_k_');
	        	if(!token) {
	        		location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();
	        		return;
	        	}
	            let url = '/general_api/api/auth/GetAccount?token=' + token + '&' + new Date().getTime();
	            fetch(url).then(r => r.json()).then(d => {
	            	console.log(d);
	            	console.log('token=' + token);
	                if (d.available) {
	                    page.account = d.data;
	                    this.GetAccountCard();
	                    this.GetCoupons();
	                }else {
	                    let apid = Cookies.get('_lac_ap_');
	                    if(!apid) {
	                    	location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();	//online should be
	                    } else {
	                    	if(!token) {
	                    		this.$router.push({name: 'login', params: {apid:apid}})
	                    	}
	                    }
	                }
	                page.loading = false;
	            })
	        },
	        //获取会员卡信息接口
			GetAccountCard:function(){
				var page = this;
	            var token = Cookies.get('_lac_k_');
		        var url = '/general_api/api/auth/GetAccountCard?token='+token+'&ts='+Date.parse(new Date());
		        fetch(url).then(r => r.json()).then(d => {
		        	if(d.available) {
		        		if (d.obj[0].balance) {
		        			page.balance = d.obj[0].balance/100;
		        			page.Intergral = d.obj[0].point;
		        		}
		        	}
		        	page.loading = false;
		        });
		       
			},
			GetCoupons:function(){
				var page = this;
	        	var token = Cookies.get('_lac_k_');
	            var url = '/general_api/api/auth/GetAccountCoupns?token=' + token + '&' + new Date().getTime();
	            fetch(url).then(r => r.json()).then(d => {
	                console.log(d)
	                if (d.code == '0') {
                    	page.loading = false;
                    	if (d.list != null ) {
                    		page.Coupons = d.list.length;
                    	}
	                }
	            })
			},
			back:function(){
				//自定义返回上一页函数 可以copy到每一个组件中方便使用
				router.go(-1);
			},godo_coupons:function(){
				location.href="/general_api/tcss/index#/myCoupons"
				// this.$router.push({name: 'myCoupons'})
			},godo_myTicket:function(){
				location.href="/general_api/tcss/index#/myTicket"
				// this.$router.push({name: 'myTicket'})
			},godo_myCards:function(){
				location.href="/general_api/tcss/index#/myCards"
				// this.$router.push({name: 'myCards'})
			},godo_myIntegral:function(){
				this.$router.push({name: 'myIntegral'})
			},godo_cinemas:function(){
				this.$router.push({name: 'cinemaList', params: { mid: '2' }})
			},
			godo_myMarkets() {
                this.$router.push({name: "myMarkets", params: {}});
            },
			godo_goods:function(){
				location.href="/general_api/tcss/index#/storeList"
			},godo_index:function(){
				this.$router.push({name: 'index'})
			},godo_modified:function(){
				this.$router.push({name: 'modifiedData'})
			}
		},
		components: {
            tabBottom
        }
	}
</script>
<style>
.mine-top-box{
	width: 100%;
	height: 5rem;
}
.mine-top{
	width: 100%;
	text-align: center;
	height: 3.5rem;
	position: relative;
	background: url(https://ex.piaodaren.com/img_server/_common/icon/user_bg_new.png) 0% 0% / cover no-repeat;
}
.mine-top-img{
	display: inline-block;
	width: 100%;
	height: auto;
}
.mine-top-img img{
	display:inline-block;
	width: 1.28rem;
	height: 1.28rem;
	margin-top: .5rem;
	border-radius: 50%;
	border:.04rem solid #f2f2f2;
}
.mine-top-nm{
	width: 100%;
	text-align: center;
	height: auto;
	margin-top: .23rem;
	display: flex;
    justify-content: center;
}
.mine-top-nm p{
    font-size: .34rem;
    color: #fff;
}
.mine-top-nm img{
    width: .6rem;
    height: .5rem;
    margin-left: .2rem;
}
.mine-top-material{
	position: absolute;
	right: 0;
	top: .63rem;
	width: .85rem;
	text-align: center;
	height: .36rem;
	line-height: .36rem;
	color: #09bb07;
	font-size: .22rem;
	background: #e0e0e0;
	border-radius: .9rem 0 0 .9rem;
}
.mine-top-menu{
	width: 100%;
	height: 1.5rem;
	background: #FFFFFF;
}
.mine-top-menu-ul{
	width: 100%;
	height: 100%;
}
.mine-top-menu-list{
	float: left;
	width: 33.3%;
	text-align: center;
	height: 1.5rem;
}
.mine-top-menu-list-num{
	display: inline-block;
	width: 100%;
	font-size: .36rem;
	line-height: 1;
	margin-top: .46rem;
	color: #191622;
}
.mine-top-menu-list-nm{
	display: inline-block;
	width: 100%;
	font-size: .24rem;
	line-height: 1;
	margin-top: .2rem;
	color: #949c9f;
}

.mine-mid-menu{
	width: 100%;
	height: auto;
	background: #FFFFFF;
	border-top: .3rem solid #efeff4;
}
.mine-mid-menu-top{
	width: 100%;
	height: auto;
}
.mine-mid-menu-top-list{
	width: auto;
	height: .86rem;
	line-height: .86rem;
	margin-left: .3rem;
	border-bottom: 1px solid #efeff4;
}
.mine-mid-menu-top-list img{
    float: left;
    margin-top: .3rem;
	display: inline-block;
	width: .4rem;
	height: .3rem;
    vertical-align: middle;
}
.mine-mid-menu-top-list p{
    float: left;
    margin-top: .3rem;
	display: inline-block;
	width: auto;
	height: .4rem;
	font-size: .34rem;
	line-height: 1;
	color: #000000;
    margin-left: .28rem;
    vertical-align: middle;
}

.mine-mid-menu-bot{
	width: 100%;
	height: auto;
	background: #FFFFFF;
	border-top: .3rem solid #efeff4;
}
.mine-mid-menu-bot-list{
	width: auto;
	height: .86rem;
	line-height: .86rem;
	margin-left: .3rem;
}
.mine-mid-menu-bot-list img{
	float: left;
    margin-top: .25rem;
	display: inline-block;
	width: .4rem;
	height: .3rem;
    vertical-align: middle;
}
.mine-mid-menu-bot-list p{
	float: left;
	margin-top: .3rem;
	display: inline-block;
	width: auto;
	height: .4rem;
	font-size: .34rem;
	line-height: 1;
	color: #000000;
    margin-left: .28rem;
    vertical-align: middle;
}

.bm-list-arrow {
    float: right;
    margin-right: .3rem;
    display: inline-block;
    height: .86rem;
    width: 15px;
}
.bm-icon.arrow {
	margin-top: .3rem;
    display: block;
    width: 15px;
    height: 25px;
    overflow: hidden;
}
.bm-icon.arrow.vertical:before {
    -webkit-transform: rotate(-45deg);
}
.bm-icon.arrow:before {
    content: "";
    display: block;
    width: 8px;
    height: 10px;
    margin-left: 0;
    border: 2px solid #A9A9A9;
    border-top: 0 none;
    border-left: 0 none;
}
</style>