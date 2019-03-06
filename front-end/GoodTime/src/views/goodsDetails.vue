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
    <div class="service" style="display:flex;padding-left">
      <p>服务</p>
      <p>
        <span>正品保障</span>
        <span>无忧售后</span>
        <span>极速退款</span>
        <span>退换货特别提示</span>
      </p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      swiperOption: {
        pagination: {
          el: ".swiper-pagination"
          // type: "fraction"
        },
        autoplay: true,
        loop: true
      }
    };
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
</style>
