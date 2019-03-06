<template>
  <div style="background:#EFEFF4;">
    <!-- 确认订单 开始 -->
    <div class="confirm-btn-container">
      <div class="btn-container">
        <p style="font-size:1.5em;text-align:center;margin-bottom:2em;">金额：1000元</p>
        <div class="confirm-btn">
          <a
            href="javascript:;"
            class="weui-btn weui-btn_primary"
            style="width:40%;font-size:0.34rem;color: #fff;"
            @click="goPay"
          >点击支付</a>
        </div>
      </div>
    </div>
    <!-- 确认订单 结束 -->
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },
  beforeRouteEnter(to, from, next) {
    document.title = "确认金额";
    next();
  },
  methods: {
    goPay() {
      var url = "/general_api/api/auth/GetWxObj?ts=" + Date.parse(new Date());
      fetch(url)
        .then(r => r.json())
        .then(d => {
          if (d.code == -1) {
          } else if (d.code == 0) {
          } else if (d.available == false && d.messages[0] == "user_no_login") {
          }
        });
    }
  }
};
</script>