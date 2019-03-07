<template>
  <div>
    <div class="head-pic">
      <!-- swiper -->
      <swiper :options="swiperOption" style="height:100%;background:#ccc;">
        <swiper-slide>
          <img src="http://sc.jb51.net/uploads/allimg/150403/10-1504031H411E6.jpg" alt>
        </swiper-slide>
        <swiper-slide>
          <img
            src="http://img.bimg.126.net/photo/31kQlCGP44-34Q5yxvoqmw==/5770237022569104952.jpg"
            alt
          >
        </swiper-slide>
        <swiper-slide>
          <img src="http://image.biaobaiju.com/uploads/20181105/20/1541422392-CQziZYypNx.jpg" alt>
        </swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
      </swiper>
    </div>
    <div class="info">
      <p style="font-size:1.4em;margin-bottom: .1rem;">{{goods_info.goods_name}}</p>
      <p>
        <span style="color:#B11B0F;margin-right:1em;">
          ¥
          <b style="font-size:1.5em">760.00</b>
        </span>
        <span style="color:#A2A2A2">
          ¥
          <s>1520.00</s>
        </span>
      </p>
    </div>
    <div
      class="service"
      style="display:flex;margin-left:.3rem;border-top:1px solid #A2A2A2;padding-top:.3rem;"
    >
      <p style="width:6em;display:flex;align-items:center;">
        <span>服务</span>
      </p>
      <p>
        <span class="item">
          <i class="iconfont icon-duigou"></i>正品保障
        </span>
        <span class="item">
          <i class="iconfont icon-duigou"></i>无忧售后
        </span>
        <span class="item">
          <i class="iconfont icon-duigou"></i>极速退款
        </span>
        <span class="item">退换货特别提示</span>
        <span class="item">退换货特别提示</span>
      </p>
    </div>
    <div class="content-tab">
      <p style="display:flex;text-align:center;justify-content:center;font-size:1.2em;">
        <span :class="tab_name === 'details' ? 'active' : ''" @click="tab_name = 'details'">图文详情</span>
        <span :class="tab_name === 'params' ? 'active' : ''" @click="tab_name = 'params'">商品参数</span>
      </p>
    </div>
    <div class="content">
      <div class="content-html" v-show="tab_name === 'details'" v-html="content_html"></div>
      <div class="content-params" v-show="tab_name === 'params'">
        <p>
          <span>规格</span>
          <span>默认款式</span>
        </p>
        <p>
          <span>颜色</span>
          <span>默认款式</span>
        </p>
        <p>
          <span>功效</span>
          <span></span>
        </p>
        <p>
          <span>商品简介</span>
          <span>价值¥1520，优惠价¥760。套餐内涵：新精华肌底液30ml，新精华肌底液7ml*4，心精华肌底液1ml*2。</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import Cookies from 'js-cookie'

export default {
  beforeRouteEnter: function(to, from, next) {
    document.title = "商品详情";
    next();
  },
  data() {
    return {
      swiperOption: {
        pagination: {
          el: ".swiper-pagination"
          // type: "fraction"
        },
        autoplay: true,
        loop: true
      },
      tab_name: "details",
      content_html: "",
      gid: "", // 商品ID
      skuid: "", // 商品skuID
      goods_info: null,
      goods_imgs: null
    };
  },
  mounted() {
    this.gid = this.$route.params.gid;
    this.skuid = this.$route.params.skuid;
    this.wxinit();
    this.getGoodsInfo();
  },
  methods: {
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
    // 获取商品详情
    getGoodsInfo() {
      let page = this;
      let token = Cookies.get("_lac_k_");
      let url = "/general_api/api/goods_detail?gid=" + this.gid + "&skuid=" + this.skuid + "&token=" + token + "&ts=" + Date.parse(new Date());
      let loading = weui.loading("加载中");
      fetch(url)
        .then(r => r.json())
        .then(d => {
        	loading.hide();
        	if(d.ret_code=='0000') {
        		page.goods_info = d.data[0];
        		page.goods_imgs = d.data[1];
        	} else {
        		weui.dialog({
          			content: "内容",
          			buttons: [
            		{
              		label: "确定",
              		type: "primary",
              		onClick: function() {}
            		}
          		]
        		});
        	}
        });
    },
  }
};
</script>

<style lang="less" scoped>
.head-pic {
  height: 6rem;
}
.info {
  padding: 0.3rem;
}
.iconfont {
  color: #b11b0f;
  font-size: 1em;
}
.item {
  margin-right: 0.1rem;
}
.content {
  margin-bottom: 2rem;
  &-tab {
    margin-top: 1rem;
    margin-bottom: 0.5rem;
    p {
      span {
        margin: 0 2em;
        padding-bottom: 0.5em;
      }
      .active {
        color: #b11b0f;
        border-bottom: 2px solid #b11b0f;
      }
    }
  }
  &-params {
    padding: 0 0.3rem;
    p {
      padding: 0.1rem 0;
      border-top: 1px solid #a2a2a2;
      display: flex;
      span:first-of-type {
        margin-right: 4em;
      }
    }
  }
}
</style>
