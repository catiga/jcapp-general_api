<template>
  <div>
    <div class="details-bg"></div>
    <div class="bindCard-box">
      <form>
        <div class="bindCard-top">
          <label class="kahao">门店/影城</label>
          <select
            v-model="card_store"
            id="select"
            class="kahao_d"
            style="width: 60%;margin-left:.5rem;"
          >
            <option
              v-for="(item, index) in cinemaList"
              :key="index"
              :value="item.id"
            >{{item.store_name}}</option>
          </select>
        </div>
        <div class="bindCard-bot">
          <label class="kahao">卡号</label>
          <input
            class="bindCard-bot-input"
            v-model="card_num"
            id="Kpassword"
            type="text"
            name="Kpassword"
            placeholder="请填写会员卡号"
          >
        </div>
        <div class="bindCard-bot">
          <label class="kahao">密码</label>
          <input
            class="bindCard-bot-input"
            v-model="card_pwd"
            id="Kpassword"
            type="text"
            name="Kpassword"
            placeholder="请填写正确的密码"
          >
        </div>
      </form>
    </div>

    <div class="bindCard-bind">
      <div v-on:click="do_bind">
        <p class="bind-bd">绑定</p>
      </div>
    </div>
  </div>
</template>
<script>
import Cookies from "js-cookie";
import { mapState, mapActions } from "vuex";

export default {
  data() {
    return {
      loading: true,
      synopsis: "synopsis-none",
      showBtn: "showBtn",
      card_num: "",
      card_pwd: "",
      card_store: "",
      cinemaList: [] // 影城列表
    };
  },
  beforeRouteEnter: function(to, from, next) {
    //当组件加载时自动调用此函数 函数结尾必须next();
    document.title = "绑定会员卡";
    next();
  },
  created: function() {
    //组件加载完成会自动调用此方法
    this.getStoreList();
  },
  methods: {
    back: function() {
      //自定义返回上一页函数 可以copy到每一个组件中方便使用
      router.go(-1);
    },
    godo_paycard: function() {
      location.href = "/general_api/tcss/index#/purchasingCard/purchasing";
      // this.$router.push({name: 'purchasingCard'})
    },
    do_bind: function() {
      let page = this;
      let token = Cookies.get("_lac_k_");
      let card_num = this.card_num;
      let card_pwd = this.card_pwd;
      let card_store = this.card_store;
      if (!card_num || !card_pwd) {
        weui.dialog({
          content: "请输入会员卡号和密码",
          buttons: [
            {
              label: "确定",
              type: "primary",
              onClick: function() {}
            }
          ]
        });
        return;
      }

      let loading = weui.loading("加载中");

      var url =
        "/general_api/api/auth/bind_my_card?token=" +
        token +
        "&mc_num=" +
        card_num +
        "&mc_pwd=" +
        card_pwd +
        "&s_id=" +
        card_store +
        "&" +
        new Date().getTime();
      fetch(url)
        .then(r => r.json())
        .then(d => {
          loading.hide();
          if (d.available) {
            weui.dialog({
              content: "会员卡绑定成功",
              buttons: [
                {
                  label: "确定",
                  type: "primary",
                  onClick: function() {
                    page.$router.go(-1);
                  }
                }
              ]
            });
          } else {
          	let err_msg = d.messages[0];
            if(err_msg=='user_no_login') {
            	err_msg = '您还未登陆，请尝试从公众号进入个人中心绑卡操作';
            }
            weui.dialog({
              content: err_msg,
              buttons: [
                {
                  label: "去登陆",
                  type: "primary",
                  onClick: function() {
                  	//重新走一遍授权流程
                  	location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();
                  }
                }
              ]
            });
          }
        });
    },
    // 获取门店
    getStoreList: function() {
      let city_no = Cookies.get("city_no");
      if (city_no == undefined) {
        let url = "/general_api/api/cityList?ts=" + Date.parse(new Date());
        fetch(url)
          .then(r => r.json())
          .then(d => {
            if (d.code == "0" && d.data && d.data.length > 0) {
              let city_no = Number(d.data[0].city_no);
              let url =
                "/general_api/api/StoreList?city_no=" +
                city_no +
                "&ts=" +
                Date.parse(new Date());
              fetch(url)
                .then(r => r.json())
                .then(d => {
                  if (d.code == 0 && d.data && d.data.length > 0) {
                    this.cinemaList = d.data;
                  }
                });
            }
          });
      } else {
        let url =
          "/general_api/api/StoreList?city_no=" +
          city_no +
          "&ts=" +
          Date.parse(new Date());
        fetch(url)
          .then(r => r.json())
          .then(d => {
            if (d.code == 0 && d.data && d.data.length > 0) {
              this.cinemaList = d.data;
            }
          });
      }
    },
    back: function() {
      //自定义返回上一页函数 可以copy到每一个组件中方便使用
      this.$router.go(-1);
    }
  }
};
</script>
<style>
.bindCard-box {
  width: 100%;
  background: #ffffff;
  margin-top: 0.3rem;
}
.bindCard-top {
  font-size: 0.34rem;
  color: #000000;
  padding: 0.3rem;
}
.kahao_d {
  font-size: 0.34rem;
}
.bindCard-bot-input {
  font-size: 0.32rem;
  border: none !important;
  outline: none !important;
  display: inline-block !important;
}
.kahao {
  display: inline-block;
  width: 1.5rem;
}
.bindCard-bot {
  padding: 0.3rem;
  font-size: 0.34rem;
  color: #000000;
  border-top: 1px solid #efeff4;
}
.bindCard-bind {
  width: auto;
  padding: 0 0.3rem;
}
.bindCard-bind div {
  margin-top: 0.6rem;
  width: 100%;
  text-align: center;
  height: 0.88rem;
  line-height: 0.88rem;
  border: 1px solid #029400;
  border-radius: 0.1rem;
  background: #09bb07;
  color: #ffffff;
  font-size: 0.36rem;
}
.bind-bd {
  height: 0.88rem;
  line-height: 0.88rem;
  font-size: 0.36rem;
  color: #ffffff;
}
.bind-hint {
  margin-top: 0.2rem;
  font-size: 0.24rem;
  color: #999999;
  letter-spacing: 1px;
}
</style>

