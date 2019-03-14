<template>
	<div><!-- 固定 容器-->
		<div class="details-bg"></div>
		<div class="my-ticket-box">
			<div class="my-cardWrap">
				<!--影票信息-->
				<div class="my-card my-cardtop my-module-1">
					<img class="my-module-1-img" src="http://pe1s.static.pdr365.com/default_movie_pic.jpg" />
					<div class="my-module-1-right">
						<p class="my-module-1-r-nm">{{movieDetails.film_name}}</p>
						<p class="my-module-1-r-cinema">
							<span>影城</span>
							{{movieDetails.store_name}}
						</p>
						<p class="my-module-1-r-time">
							<span>时间</span>
							{{movieDetails.plan_date}} {{movieDetails.plan_time}} 
						</p>
						<p class="my-module-1-r-seats">
							<span>座位号</span>
							<p class="my-module-1-r-seats-list">
								<span class="my-module-1-r-seats-list-hall">{{movieDetails.hall_name}}</span>
								<span class="my-module-1-r-seats-list-seat" v-for="item in movieDetails.seats">{{item.seat_sr}}排{{item.seat_sc}}座</span>
							</p>
						</p>
						
					</div>
			    </div>
			    <!--影票码-->
			    <div class="my-card my-cardbot my-module-2">
			    		<p class="my-module-2-num">{{ticket_num}}张电影票</p>
			    		<p class="my-module-2-hint">在取票机扫码或输入取票码取电影票</p>
			    		<img class="my-module-2-codeimg" :src="'https://tool.kd128.com/qrcode?text='+movieDetails.ticket.ticket_flag_1+movieDetails.ticket.ticket_flag_2" />
			    		<p class="my-module-2-code">
			    			序列号：
			    			<span>{{movieDetails.ticket.ticket_flag_1}}</span>
			    			验证码：
			    			<span>{{movieDetails.ticket.ticket_flag_2}}</span>
			    		</p>
			    </div>
			    <!--小食码-->
			    <!-- <div class="my-card my-cardbot my-module-2">
			    		<p class="my-module-2-num">1张小食券</p>
			    		<p class="my-module-2-hint">请到影院卖品柜台像员工出示以下信息取小食</p>
			    		<img class="my-module-2-codeimg" src="http://qr.liantu.com/api.php?text=1111" />
			    		<p class="my-module-2-code">
			    			序列号：
			    			<span>123456</span>
			    			验证码：
			    			<span>123456</span>
			    		</p>
			    </div> -->
			    <!--影院信息-->
			    <div class="my-card my-cardbot3 my-module-3">
			    		<p class="my-module-3-nm">{{movieDetails.store_name}}</p>
			    		<!-- <p class="my-module-3-info">北京市平谷区华联商厦5层</p> -->
			    </div>
			    <!--订单信息-->
			    <div class="my-card my-cardbot4 my-module-4">
			    		<p class="my-module-4-nm">实付金额<span>{{(movieDetails.pay_amount + movieDetails.handle_fee + movieDetails.service_fee)/100}}元</span></p>
			    		<p class="my-module-4-info">订单号<span>{{movieDetails.order_no}}</span></p>
			    </div>
			    
			     <!--观影须知-->
			    <div class="my-card my-cardbot5 my-module-5">
			    		<p class="my-module-5-title">观影须知</p>
			    		<p class="my-module-5-clause">1、请提前到达影院现场，找到自助取票机，打印纸质电影票，完成取票。</p>
			    		<p class="my-module-5-clause">2、如现场自助取票机无法打印电影票，请联系影院工作人员处理。</p>
			    		<p class="my-module-5-clause">3、凭打印好的纸质电影票，检票入场观影。</p>
			    </div>
			</div>
		</div>
	</div>
</template>
<script>
	export default {
		data : function(){
			return {
				loading:true,
				on:'',
				movieDetails:'',
				ticket_num: '',
			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="订单详情页";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
            this.on = this.$route.params.on;
            this.GetOrderDetails();
		},methods:{
			back:function(){
				//自定义返回上一页函数 可以copy到每一个组件中方便使用
				router.go(-1);
			},GetOrderDetails:function(){
                var page = this;
                var url = '/general_api/api/GetTicketOrderDetails?order_no='+page.on+'&ts='+Date.parse(new Date());
                fetch(url).then(r => r.json()).then(d => {
                    if (d.code == 0) {
                        page.movieDetails = d.data;
                        page.ticket_num = d.data.seats.length;
                        page.loading = false;
                    }
                });
            },
		}
	}
</script>
<style>
.my-ticket-box{
	width: auto;
	height: auto;
	padding: .2rem .3rem;
}
.my-cardWrap {
    float: left;
    width: 100%;
    height: auto;
    margin-bottom: .2rem;
}
.my-cardtop {
    float: left;
    border-radius: .18rem .18rem 0 0;
    width: 100%;
	min-height: 4.12rem;
    padding-bottom: .3rem;
}
.my-cardbot {
    float: left;
    width: 100%;
    height: auto;
    min-height: 4.9rem;
    border-top: .18em dashed #efeff4;
    border-radius:  0 0 .18rem .18rem;
}
.my-card {
    background: #FFFFFF;
    position: relative;
}
.my-cardbot:before {
    left: -.15rem;
}
.my-cardbot:before, .my-cardbot:after {
    content: "";
    position: absolute;
    display: block;
    width: .3rem;
    height: .3rem;
    background: #efeff4;
    border-radius: 50%;
    top: -.18rem;
}
.my-cardbot:after {
    right: -.15rem;
}

.my-cardbot-top{
	width: 100%;
    height: 4.42rem;
    float: left;
    padding-bottom: .3rem;
}
.my-cardbot-bot{
	width: 100%;
    height: 4.9rem;
    float: left;
    background: #fff;
    padding-bottom: .3rem;
    border-radius: 0 0 .18rem .18rem;
    border-top: 1px solid #efeff4;
}


.my-module-1-img{
	float: left;
	width: 2.46rem;
	height: 3.31rem;
	border-radius: .06rem;
	margin: .5rem .4rem 0 .4rem;
}
.my-module-1-right{
	float: left;
	width: 3.4rem;
	height: auto;
}
.my-module-1-r-nm{
	width: 100%;
	margin-top: .6rem;
	line-height: 1;
	font-size: .34rem;
	color: #000000;
}
.my-module-1-r-cinema{
	width: 100%;
	margin-top: .3rem;
	line-height: 1;
	font-size: .26rem;
	color: #000000;
}
.my-module-1-r-cinema span , .my-module-1-r-time span , .my-module-1-r-seats span{
	display: block;
	width: 100%;
	color: #A9A9A9;
	margin-bottom: .14rem;
}
.my-module-1-r-time , .my-module-1-r-seats , .my-module-1-r-seats-list{
	width: 100%;
	margin-top: .1rem;
	font-size: .26rem;
	color: #000000;
}
.my-module-1-r-seats-list-hall{
	display: inline-block;
	margin-right: .37rem;
}
.my-module-1-r-seats-list-seat{
	display: inline-block;
	margin-right: .2rem;
}

.my-module-2{
	text-align: center;
}
.my-module-2-num , .my-module-2-hint , .my-module-2-code{
	width: 100%;
	text-align: center;
	font-size: .26rem;
	line-height: 1;
}
.my-module-2-hint{
	color: #A9A9A9;
	margin-top: .22rem;
}
.my-module-2-num{
	color: #A9A9A9;
	margin-top: .36rem;
}
.my-module-2-codeimg{
	display: inline-block;
	width: 2.48rem;
	height: 2.48rem;
	margin-top: .28rem;
}
.my-module-2-code{
	margin-top: .37rem;	
}
.my-module-2-code span{
	color: #000000;
}


.my-cardbot3 {
    float: left;
    width: 100%;
    height: auto;
    min-height: 1.79rem;
    padding-bottom: .3rem;
    border-top: .18em dashed #efeff4;
    border-radius:  0 0 .18rem .18rem;
}
.my-cardbot3:before {
    left: -.15rem;
}
.my-cardbot3:before, .my-cardbot3:after {
    content: "";
    position: absolute;
    display: block;
    width: .3rem;
    height: .3rem;
    background: #efeff4;
    border-radius: 50%;
    top: -.18rem;
}
.my-cardbot3:after {
    right: -.15rem;
}
.my-module-3-nm{
	font-size: .34rem;
	color: #000000;
	margin: .53rem 0 .3rem .3rem;
}
.my-module-3-info{
	font-size: .26rem;
	color: #A9A9A9;
	margin-left: .3rem;
}



.my-cardbot4 {
    float: left;
    width: 100%;
    height: auto;
    min-height: 1.79rem;
    padding-bottom: .3rem;
    border-top: .18em dashed #efeff4;
    border-radius:  0 0 .18rem .18rem;
}
.my-cardbot4:before {
    left: -.15rem;
}
.my-cardbot4:before, .my-cardbot4:after {
    content: "";
    position: absolute;
    display: block;
    width: .3rem;
    height: .3rem;
    background: #efeff4;
    border-radius: 50%;
    top: -.18rem;
}
.my-cardbot4:after {
    right: -.15rem;
}
.my-module-4-nm{
	font-size: .36rem;
	color: #A9A9A9;
	margin: .5rem .3rem .3rem .3rem;
}
.my-module-4-nm span{
	display: inline-block;
	float: right;
	font-size: .34rem;
	color: #000000;
}
.my-module-4-info{
	font-size: .36rem;
	color: #A9A9A9;
	margin: .15rem .3rem 0rem .3rem;
}
.my-module-4-info span{
	display: inline-block;
	float: right;
	font-size: .34rem;
	color: #000000;
}




.my-cardbot5 {
    float: left;
    width: 100%;
    height: auto;
    min-height: 2.4rem;
    padding-bottom: .3rem;
    border-top: .18em dashed #efeff4;
    border-radius:  0 0 .18rem .18rem;
}
.my-cardbot5:before {
    left: -.15rem;
}
.my-cardbot5:before, .my-cardbot5:after {
    content: "";
    position: absolute;
    display: block;
    width: .3rem;
    height: .3rem;
    background: #efeff4;
    border-radius: 50%;
    top: -.18rem;
}
.my-cardbot5:after {
    right: -.15rem;
}
.my-module-5-title{
	font-size: .26rem;
	color: #000000;
	margin: .5rem .3rem .2rem .3rem;
}
.my-module-5-clause{
	font-size: .2rem;
	color: #888888;
	margin: 0rem .3rem .2rem .3rem;
}

</style>