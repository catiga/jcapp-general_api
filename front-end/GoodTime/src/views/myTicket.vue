<template>
	<div><!-- 固定 容器-->
		<div class="details-bg"></div>
		<div class="weui-tab">
			<div class="weui-navbar">
				<div class="weui-navbar__item" v-for="(item, index) in tabList" :key="index" :class="active === index ? 'weui-bar__item_on': ''" @click="active=index">{{item}}</div>
			</div>
			<div v-show="active === 0" class="weui-tab__panel" style="padding-top:50px;padding-bottom:50px;">
				<div class="my-ticket-box">
					<!--电影票+小吃-->
					<!-- <div class="cardWrap">
						<div class="card cardtop">
							<p>华联国际影城</p>
							<img class="cardtop-flag" src="http://pe1s.static.pdr365.com/tencent/icon/t_no_get.png" />
						</div>
						<div class="card cardbot">
								<div class="cardbot-top" v-on:click="godo_code">
									<div class="cardbot-img" style="background: url(http://pe1d.static.pdr365.com/FhodjILxWCZtSVkdtxJuEnhpCDk6) 0% 0% / cover no-repeat;"></div>
									<div class="cardbot-mid">
										<p class="cardbot-mid-title">邪不压正<span>2张</span></p>
										<p class="cardbot-mid-time">购票日期：2018-06-27 14:30</p>
										<p class="cardbot-mid-time">2号厅 <span> 8排8座 </span></p>
										<div class="cardbot-mid-snack">含观影小食</div>
									</div>
								</div>
								<div class="cardbot-bot">
									<div class="cardbot-img" style="background: url(http://pe1d.static.pdr365.com/FhodjILxWCZtSVkdtxJuEnhpCDk6) 0% 0% / cover no-repeat;"></div>
									<div class="cardbot-mid">
										<p class="cardbot-mid-title" style="margin-top: .4rem;">双人套餐</p>
										<p class="cardbot-mid-time" style="margin-top: .25rem;">大爆+机打可乐</p>
										<p class="cardbot-mid-time" style="margin-top: .25rem;">数量：1</p>
									</div>
								</div>
						</div>
					</div> -->
					<!--影票-->
					<div class="cardWrap" v-for="item in TicketList" v-on:click="godo_code(item.order_no)">
						<div class="card cardtop">
							<p>{{logo.proj_name}}</p>
							<img v-if="item.order_status=='2000' || item.order_status=='2900' || item.order_status=='2901'" class="cardtop-flag" src="http://pe1s.static.pdr365.com/tencent/icon/t_no_get.png" />
							<img v-else class="cardtop-flag" src="http://pe1s.static.pdr365.com/tencent/icon/t_done.png" />
						</div>
						<div class="card cardbot">
							<div class="cardbot-top">
								<div class="cardbot-img">
									<img style="width: 100%;height: 100%;" src="http://pe1d.static.pdr365.com/FhodjILxWCZtSVkdtxJuEnhpCDk6">
								</div>
								<div class="cardbot-mid">
									<p class="cardbot-mid-title">{{item.film_name}}<span>{{item.seats.length}}张</span></p>
									<p class="cardbot-mid-time">开场时间：{{item.plan_date}} {{item.plan_time}}</p>
									<p class="cardbot-mid-time">{{item.hall_name}} <span v-for="j in item.seats"> {{j.seat_sr}}排{{j.seat_sc}}座 </span></p>
									<!-- <div class="cardbot-mid-snack">含观影小食</div> -->
								</div>
							</div>
						</div>
					</div>
					<!--小吃-->
					
				</div>
			</div>
			<div v-show="active === 1" class="weui-tab__panel" style="padding-top:50px;padding-bottom:50px;">
				<div class="my-ticket-box">
					<!--小吃-->
					<div class="cardWrap" v-for="(item,index) in OrderList" v-if="item.oss!='0000'" v-on:click="godo_goodscode(item.order_no,index)">
						<div class="card cardtop">
							<p>{{logo.proj_name}}</p>
							<img v-if="item.oss=='1000'" class="cardtop-flag" src="http://pe1s.static.pdr365.com/dailingqu.png" />
							<img v-if="item.oss=='1010'" class="cardtop-flag" src="http://pe1s.static.pdr365.com/dailingqu.png" />
							<img v-if="item.oss=='1020'" class="cardtop-flag" src="http://pe1s.static.pdr365.com/dailingqu.png" />
							<img v-if="item.oss=='2000'" class="cardtop-flag" src="http://pe1s.static.pdr365.com/dailingqu.png" />
							<img v-if="item.oss=='3000'" class="cardtop-flag" src="http://pe1s.static.pdr365.com/tencent/icon/t_done.png" />
						</div>
						<div class="card cardbot">
							<div class="cardbot-bot" v-for="(goods,j) in item.items">
								<div class="cardbot-img" :data-img="goods.pic_url">
									<img style="width: 100%;height: 100%;" :src="'http://pe1s.static.pdr365.com/'+goods.pic_url">
								</div>
								<div class="cardbot-mid">
									<p class="cardbot-mid-title" style="margin-top: .4rem;">{{goods.goods_name}}</p>
									<p class="cardbot-mid-time" style="margin-top: .25rem;">{{goods.remark}}</p>
									<p class="cardbot-mid-time" style="margin-top: .25rem;">数量：{{goods.buy_num}}</p>
									<p class="cardbot-mid-time" style="margin-top: .25rem;">下单时间：{{timelist[index]}}</p>
								</div>
							</div>
						</div>
					</div>
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
	import {mapState,mapActions} from 'vuex'
	
	export default {
	data : function(){
		return {
			loading:true,
			OrderList:'',
			tip_msg:'',
			account:'',
			TicketList:'',
            apid:'',
			timelist:[],
			active: 0,
			tabList: ['影票', '商品'],
		};
	},beforeRouteEnter:function(to, from, next){
		//当组件加载时自动调用此函数 函数结尾必须next();
		document.title="我的订单";
		next();
	},created:function(){
		//组件加载完成会自动调用此方法
		// this.GetCode();//获取code
		this.GetAccount();
	},
	computed: {
		...mapState(['logo']),
	},
	methods:{
		GetCode:function(){
            var token = Cookies.get('_lac_ated_');
            if(!token) {
            	location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();	//online should be
            }
        },
		showTip:function(msg,ts){
            var page = this;
            page.tip_msg = msg;
            if(ts){
                clearTimeout(window.maxtm);
                window.maxtm = setTimeout(function(){page.tip_msg="";},ts)
            }
        },
        GetAccount:function(){
        	var page = this;
        	var token = Cookies.get('_lac_k_');
            var url = '/general_api/api/auth/GetAccount?token='+token+'&ts='+Date.parse(new Date());
            fetch(url).then(r => r.json()).then(d => {
                // console.log(d)
                if (d.available) {
                    page.account = d.data;
                    page.MyOrder(d.data.ap_id);
                    page.apid = d.data.ap_id;
					page.MyTiketOrder(d.data.ap_id)//获取我的影票订单
                }else if ( d.available == false && d.messages[0] == 'user_no_login' ) {
                    this.GetCode();//获取code
                }
                
            })
        },
		MyOrder:function(apid){
			var page = this;
			var token = Cookies.get('_lac_k_');	
            var url = '/general_api/api/OrderList?apid='+apid+'&ds=&token='+token+'&oss=1&ts='+Date.parse(new Date());
            fetch(url)
            .then(r => r.json())
            .then(d => {
                console.log(d)
                if (d) {
                    page.loading = false;
                    page.OrderList = d;
                    var timelist = [];
                    for(var i=0; i<d.length; i++) {
                        var TimeE = d[i].a_time;
                        var time = new Date(TimeE);
                        var Y = time.getFullYear();
                        var M = time.getMonth()+1;
                        var D = time.getDate();
                        var times =  Y+"-"+M+"-"+D;
                        timelist.push(times);
                    }
                    console.log(timelist);
                    page.timelist = timelist;
                }else{
                    page.loading = false;
                    // page.showTip('暂无订单');
                }
            })
            .catch(e => {
                page.loading = false;
            	page.OrderList = null;
                // page.showTip('暂无订单');
            });
		},

		MyTiketOrder:function(apid){
			var page = this;
			var token = Cookies.get('_lac_k_');
            var url = '/general_api/api/GetTicketList?apid='+apid+'&token='+token+'&ts='+Date.parse(new Date());
            fetch(url)
            .then(r => r.json())
            .then(d => {
            	page.loading = false;
                if (d.code == 0) {
                    page.TicketList = d.data;
                    // var timelist = [];
                    // for(var i=0; i<d.length; i++) {
                    //     var TimeE = d[i].a_time;
                    //     var Time = new Date(TimeE);
                    //     var Y = Time.getFullYear();
                    //     var M = (Time.getMonth()+1 < 10 ? '0'+(Time.getMonth()+1) : Time.getMonth()+1);
                    //     var D = (Time.getDate() < 10 ? '0'+(Time.getDate()) : Time.getDate());
                    //     var times =  Y+"-"+M+"-"+D;
                    //     timelist.push(times);
                    // }
                    // page.timelist = timelist;
                }else{
                    // page.showTip('暂无订单');
                }
            })
            .catch(e => {
                page.loading = false;
                // page.showTip('暂无订单');
            });
		},godo_code:function(on){
			this.$router.push({name: 'votingCode', params: { on: on }})
		},godo_goodscode:function(on,j){
            console.log('11')
            this.$router.push({name: 'GoodsCode', params: { on: on , index : j , apid : this.apid}})
        }
	}
}
</script>
<style>
.my-ticket-box{
	width: auto;
	height: auto;
	padding: .2rem .3rem;
}
.cardWrap {
    float: left;
    width: 100%;
    height: auto;
    margin-bottom: .2rem;
}
.cardtop {
    float: left;
    border-radius: .18rem .18rem 0 0;
    width: 100%;
    height: 1.03rem;
}
.cardbot {
    float: left;
    width: 100%;
    height: auto;
    min-height: 2.3rem;
    border-top: .18em dashed #efeff4;
    border-radius:  0 0 .18rem .18rem;
}
.cardtop-flag{
    position: absolute;
    top: .55rem;
    right: .65rem;
    width: 1.1rem;
    z-index: 99;
}   
.card {
    background: #FFFFFF;
    position: relative;
}
.cardbot:before {
    left: -.15rem;
}
.cardbot:before, .cardbot:after {
    content: "";
    position: absolute;
    display: block;
    width: .3rem;
    height: .3rem;
    background: #efeff4;
    border-radius: 50%;
    top: -.18rem;
}
.cardbot:after {
    right: -.15rem;
}
.cardtop p{
	width: auto;
	height: 1.03rem;
	line-height: 1.03rem;
	margin-left: .35rem;
	font-size: .26rem;
	color: #A9A9A9;
}
.cardbot-img{
	float: left;
	width: 1.65rem;
	height: 1.65rem;
	margin: .3rem .37rem 0 .3rem;
	border-radius: .06rem;
}
.cardbot-mid{
	float: left;
	width: auto;
	max-width: 4.2rem;
	height: auto;
}
.cardbot-mid-title{
	display: inline-block;
	width: auto;
	margin-top: .35rem;
	line-height: 1;
	color: #000000;
	font-size: .34rem;
}
.cardbot-mid-title span{
	display: inline-flex;
	margin-left: .3rem;
	font-size: .26rem;
}
.cardbot-mid-time , .cardbot-mid-time span{
	width: 100%;
	font-size: .26rem;
	color: #767676;
	line-height: 1;
	margin-top: .15rem;
}
.cardbot-mid-snack{
	display: inline-block;
	width: auto;
	padding: 0 .08rem;
	text-align: center;
	height: .3rem;
	line-height: .3rem;
	margin-top: .13rem;
	background: #fff5f4;
	color: #ed7d29;
	font-size: .2rem;
	border-radius: .02rem;
}

.cardbot-top{
	width: 100%;
    height: auto;
    float: left;
    padding-bottom: .3rem;
}
.cardbot-bot{
	width: 100%;
    height: auto;
    float: left;
    background: #fff;
    padding-bottom: .3rem;
    border-radius: 0 0 .18rem .18rem;
}


</style>