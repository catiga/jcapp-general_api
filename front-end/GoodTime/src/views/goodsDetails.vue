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
      <p style="font-size:1.4em;margin-bottom: .1rem;">兰蔻明星小黑瓶礼盒一人限购一个超出自动取消</p>
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
    };
  },
  mounted() {
    this.gid = this.$route.params.gid;
    this.skuid = this.$route.params.skuid;
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
