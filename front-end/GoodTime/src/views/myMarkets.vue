<template>
	<div>
		<div class="details-bg"></div>
		<div class="my-coupons-box">
			<div class="my-coupons" v-if="Coupons.length > 0">
				<div class="my-coupons-list stamp02" v-for="(item,j) in Coupons">
					<div class="my-coupons-list-left">
						<p class="my-coupons-list-left-num" style="margin:auto;line-height:2.2rem;"> <span style="font-size:.4rem;"> 选座 </span></p>
						<p class="my-coupons-list-left-type"> </p>
					</div>
					<div class="my-coupons-list-mid">
						<p class="my-coupons-list-mid-title">{{item.title}}</p>
						<p class="my-coupons-list-mid-info">{{item.info}}</p>
						<p class="my-coupons-list-mid-info">购买规则：<span>{{item.policy_str}}</span></p>
					</div>
					<div class="my-coupons-list-pay" @click="choose(item)">查看余量</div>
				</div>
			</div>
			<div class="my-coupons" v-else>
				<div class="NoCoupons">
					<img src="https://ex.piaodaren.com/img_server/_common/icon/Nocoupons.png">
					<p>暂时没有活动～</p>
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
        
        <div class="myCards" v-if="CardData == null " >
			<div>
				<div class="myCards-top-box">
					<div class="myCards-top">
						<p class="myCards-top-hint-top">咨询电话 <a href="tel:08123306789">0812-3306789</a></p>
						<p class="myCards-top-hint">请在工作时间拨打</p>
					</div>
				</div>
			</div>
		</div>
		
		
        <div style="position:fixed;bottom:0;width:100%;padding:20px .3rem;background:#efeff4">
        	<a href="javascript:;" class="weui-btn weui-btn_primary" style="font-size:0.34rem;color: #fff;" @click="goBack">返回</a>
        </div>
	</div>
</template>
<script>
import Cookies from "js-cookie";
export default {
  data() {
    return {
      loading: true,
      Coupons: "",
      timelist: [],
    };
  },
  beforeRouteEnter: function(to, from, next) {
    //当组件加载时自动调用此函数 函数结尾必须next();
    document.title = "活动专区";
    next();
  },
  created: function() {
    //组件加载完成会自动调用此方法
  },
  mounted() {
    this.get_markets();
  },
  methods: {
    back: function() {
      //自定义返回上一页函数 可以copy到每一个组件中方便使用
      router.go(-1);
    },
    get_markets: function() {
      /**
       * oc: 商品 1000， 
       *     影票 2000
       */
      let page = this;
      let order_id = this.order_no;
      let token = Cookies.get("_lac_k_");
      let url =
        "/general_api/api/auth/q_markets?token=" +
        token +
        "&order_id=" +
        order_id +
        "&oc=2000" +
        "&ts=" +
        new Date().getTime();
      fetch(url).then(r => r.json()).then(d => {
      	  console.log(d);
          if (d.available) {
            page.loading = false;
            page.Coupons = d.data;
            var timelist = [];
            for (var i = 0; i < d.data.length; i++) {
            /*
              var TimeE = d.data[i].validate_end;
              var time = new Date(TimeE);
              var Y = time.getFullYear();
              var M = time.getMonth() + 1;
              var D = time.getDate();
              var times = Y + "-" + M + "-" + D;
            */
              timelist.push(d.data[i]);
            }
            page.timelist = timelist;
          } else {
            page.loading = false;
            let auth_type = Cookies.get("_lac_ap_");
          }
        });
    },
    //查看活动余量
    choose(item) {
    	let market_id = item.market_id;
    	let item_id = item.id;
    	//这里需要判断一下是否可以使用，当前用户
    	let token = Cookies.get("_lac_k_");
    	let url = '/general_api/api/auth/margin_market?mrid=' + item_id + '&market_id=' + market_id + '&token=' + token;
    	let loading = weui.loading('加载中');
    	fetch(url).then(r => r.json()).then(d => {
    		console.log(d);
    		loading.hide();
    		if(d.ret_code=='0000') {
    			//用户可以使用，初步检查通过，现在开始将参数返回
    			weui.topTips(d.ret_msg + d.data, 5000);
    		} else {
    			weui.topTips(d.ret_msg + d.ret_code, 5000);
    		}
    	})
    },
    goBack() {
        sessionStorage.removeItem("market_rule");
        this.$router.push({name: "confirmOrder",params: {tnum:this.tnum}});
    }
  }
};
</script>
<style>
.NoCoupons {
  width: 100%;
  height: 5rem;
  text-align: center;
}
.NoCoupons img {
  display: inline-block;
  width: 4rem;
  margin-top: 2rem;
}
.NoCoupons p {
  width: 100%;
  text-align: center;
  color: #999;
  font-size: 0.35rem;
}
.details-bg {
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
.my-coupons-box {
  width: auto;
  padding: 0 0.3rem;
  height: auto;
  padding-bottom:80px;
}
.my-coupons {
  width: 100%;
  height: auto;
}
.my-coupons-list {
  position: relative;
  width: 100%;
  height: 2.2rem;
  background: #ffffff;
  margin-top: 0.3rem;
  border-radius: 0.08rem;
}
.stamp02 {
  background: radial-gradient(transparent 0, transparent 5px, #fff 5px);
  background-size: 15px 15px;
  background-position: 8px 2px;
}
.stamp02:before {
  content: "";
  display: block;
  background-color: #fff;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 7px;
  right: 7px;
}
.my-coupons-list-left {
  float: left;
  position: relative;
  width: 1.9rem;
  height: 2.2rem;
  border-right: 1px dashed #efeff4;
  padding-left: 0.3rem;
  text-align: center;
}
.my-coupons-list-left-num {
  font-size: 0.3rem;
  color: #09bb07;
  margin-top: 0.45rem;
}
.my-coupons-list-left-num span {
  font-size: 0.56rem;
}
.my-coupons-list-left-type {
  font-size: 0.25rem;
  color: #09bb07;
  text-align: center;
}
.my-coupons-list-mid {
  float: left;
  position: relative;
  width: auto;
  max-width: 4.7rem;
  padding-left: 0.35rem;
  height: 2.2rem;
}
.my-coupons-list-mid-title {
  font-size: 0.34rem;
  color: #000000;
  margin-top: 0.35rem;
}
.my-coupons-list-mid-info {
  font-size: 0.24rem;
  color: #a9a9a9;
}
.my-coupons-list-mid-info span {
  font-size: 0.24rem;
  color: #000;
}
.my-coupons-list-pay {
  position: absolute;
  top: 0;
  bottom: 0;
  margin: auto 0;
  right: 0.6rem;
  width: 1.12rem;
  text-align: center;
  height: 0.56rem;
  line-height: 0.56rem;
  border-radius: 0.08rem;
  border: 1px solid #1aad19;
  color: #1aad19;
}
.my-coupons-list-code {
  position: absolute;
  top: 0.16rem;
  right: 0.3rem;
  width: 0.9rem;
}

.my-coupons-list-left-num-ed,
.my-coupons-list-left-num-ed span,
.my-coupons-list-left-type-ed,
.my-coupons-list-mid-title-ed,
.my-coupons-list-mid-info-ed span {
  color: #a9a9a9;
}



/*无会员卡，盗用提示信息*/
.myCards{
	width: 100%;
	height: auto;
}
.myCards-top-box{
	width: auto;
	height: auto;
	padding: .7rem .3rem 0rem .3rem;
}
.myCards-top{
	width: 100%;
	text-align: center;
	height: 3rem;
	background: #f4f4f4;
	border-radius: .1rem;
}
.myCards-top-title{
	width: 100%;
	font-size: .4rem;
	color: #000000;
	padding-top: .82rem;
}
.myCards-top-hint{
	width: 100%;
	font-size: .28rem;
	color: #888888;
	padding-top: .35rem;
}
.myCards-bot{
	width: auto;
	text-align: center;
	height: auto;
	padding: 1.6rem .3rem 0rem .3rem;
}
.myCards-bot-go{
	margin-bottom: .32rem;
}
.myCards-bot-go , .myCards-top-bind{
	width: 100%;
	height: .88rem;
	line-height: .88rem;
	border: 1px solid #029400;
	border-radius: .1rem;
	background: #09BB07;
	color: #FFFFFF;
	font-size: .36rem;
}
</style>