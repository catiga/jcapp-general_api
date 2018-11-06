import axios from 'axios'
import {
  Message
} from 'iview'

// 获取卖品分组
export const groupListData = routeName => {
  let url = '/general_api/api/goods_cat?ts='+Date.parse(new Date());
  if(routeName==='storeList') {
	url = url + '&c=GENERAL_EC';
  } else if(routeName==='ScavengingPointMea') {
	url = url + '&c=GENERAL_SOC';
  }
  let arr = [];
  let loading = weui.loading('加载中');
  return axios.get(url).then(res => {
	  loading.hide();
    if (res.data) {
    	res.data.forEach(item => {
    		arr.push(item);
    	});
    	return arr;
      /*
      if(routeName === 'storeList') {
        res.data.forEach(item => {
        if (item.sons && item.cat_name_cn == '非堂食') {
          item.sons.forEach(item => {
            arr.push(item);
          });
        } else {
          return;
        }
      });
      return arr;
      } else if(routeName === 'ScavengingPointMea') {
        res.data.forEach(item => {
        if (item.sons) {
          item.sons.forEach(item => {
            arr.push(item);
          });
        } else {
          return;
        }
      });
      return arr;
      }
      */
    } else {
      Message.error({
        content: "暂无可用分组",
        duration: 3
      });
      return arr;
    }
  }).catch(e => {
    console.log(e);
    loading.hide();
    Message.error({
      content: "获取分组失败,请检查网络" + e,
      duration: 3
    });
    return arr;
  });
};

//获取商品列表
export const goodsListData = (cat_id,sid) => {
	
  let loading = weui.loading('加载中');

  let url = '/general_api/api/goods?ts='+Date.parse(new Date());
  let arr = [];
  let data = {
    cat_id,
    sid
  };
  return axios.get(url, {
    params: data
  }).then(res => {
    if (res.data.length > 0) {
      arr = res.data;
      arr.forEach((item, index) => {
        item[0].skus = JSON.parse(item[0].skus);
        let brr = [];
        for (var key in item[0].skus) {
          brr.push(key);
        }
        item[0].length = brr.length;
        item[0].count = 0;
      });

      loading.hide();

      return arr;
    } else {
      loading.hide();
      return arr;
    }
  })

}