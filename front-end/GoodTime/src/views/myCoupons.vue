<template>
	<div><!-- 固定 容器-->
		<div class="details-bg"></div>
		<!--无优惠券-->
		<div>
			
		</div>
		<!--有优惠券-->
		<div class="my-coupons-box">
			<div class="my-coupons" v-if="Coupons.length > 0">
				<div class="my-coupons-list stamp02" v-for="(item,j) in Coupons">
					<div class="my-coupons-list-left">
						<!-- <p class="my-coupons-list-left-num" v-if="item.coupon_type == '1000' ">¥ <span> {{item.rule_value/100}}</span></p> -->
						<p class="my-coupons-list-left-num" v-if="item.coupon_type == '1000' ">¥ <span> {{item.rule_value/100}}</span></p>
						<p class="my-coupons-list-left-num" v-else-if="item.coupon_type == '3000' "> <span> {{item.rule_value | toSlice}}折</span></p>
						<p class="my-coupons-list-left-num" style="margin:auto;line-height:2.2rem;" v-else-if="item.coupon_type == '2000' "> <span style="font-size:.4rem;"> 兑换券 </span></p>
						<p class="my-coupons-list-left-type">{{item.coupon_type == '1000' ? '代金券' : item.coupon_type == '3000' ? '折扣券' : '' }}</p>
					</div>
					<div class="my-coupons-list-mid">
						<p class="my-coupons-list-mid-title">{{item.batch_title}}</p>
						<p class="my-coupons-list-mid-info" v-if="item.coupon_type == '2000'"> <span>仅限到店使用</span> </p>
						<p class="my-coupons-list-mid-info" v-else> <span v-for="info in item.rule"> {{info}} </span> </p>
						<p class="my-coupons-list-mid-info">有效期：{{timelist[j]}}</p>
						<p class="my-coupons-list-mid-info">券码：<span>{{item.code}}</span></p>
					</div>
					<!--<div class="my-coupons-list-pay" v-if="item.coupon_type == 2000">购票</div>-->
          <div style="width: 2.12rem;height: 100%; position: absolute;right: 0;display: flex; flex-direction: column; justify-content: center;">
            <div class="my-coupons-list-pay-button"  v-if="item.coupon_type != 2000" v-on:click="go_use">使用</div>
            <div class="my-coupons-list-pay-button" @click="addToWeChatCard">加入卡包</div>

          </div>
				</div>
			</div>
			<div class="my-coupons" v-else>
				<div class="NoCoupons">
					<img src="http://pe1s.static.pdr365.com/Nocoupons.png">
					<p>您还没有优惠券呢～</p>
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
import Cookies from "js-cookie";
export default {
  data() {
    return {
      loading: true,
      Coupons: "",
      timelist: []
    };
  },
  beforeRouteEnter: function(to, from, next) {
    //当组件加载时自动调用此函数 函数结尾必须next();
    document.title = "我的优惠券";
    next();
  },
  created: function() {
    //组件加载完成会自动调用此方法
    this.GetCoupons();
  },
  methods: {
    go_use: function() {
    	this.$router.push({name: 'index', params: {}})
    },
    back: function() {
      //自定义返回上一页函数 可以copy到每一个组件中方便使用
      router.go(-1);
    },
    GetCoupons: function() {
      var page = this;
      var token = Cookies.get("_lac_k_");
      var url =
        "/general_api/api/auth/GetAccountCoupns?token=" +
        token +
        "&ts=" +
        Date.parse(new Date());
      fetch(url)
        .then(r => r.json())
        .then(d => {
          // console.log(d)
          if (d.code == "0") {
            page.loading = false;
            page.Coupons = d.list;
            var timelist = [];
            for (var i = 0; i < d.list.length; i++) {
              var TimeE = d.list[i].validate_end;
              var time = new Date(TimeE);
              var Y = time.getFullYear();
              var M = time.getMonth() + 1;
              var D = time.getDate();
              var times = Y + "-" + M + "-" + D;
              timelist.push(times);
            }
            page.timelist = timelist;
          } else {
            page.loading = false;
            let auth_type = Cookies.get("_lac_ap_");
            let auth_key = Cookies.get('_lac_k_');
            if (!auth_type) {
              	location.href = "/general_api/h5/wx/auth?type=base&" + new Date().getTime(); //online should be
            } else {
            	if(!auth_key) {
            		this.$router.push({name: 'login', params: {apid:auth_type}});
            	}
            }
          }
        });
    },
    // 加入微信卡包
    addToWeChatCard() {
      console.log('加入卡包');
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
/* .my-coupons-list-pay {
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
} */
.my-coupons-list-pay-button {
  margin: auto 0;
  width: 1.12rem;
  text-align: center;
  height: 0.56rem;
  line-height: 0.56rem;
  border-radius: 0.08rem;
  border: 1px solid #1aad19;
  color: #1aad19;
  margin-bottom: .18rem;
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
</style>