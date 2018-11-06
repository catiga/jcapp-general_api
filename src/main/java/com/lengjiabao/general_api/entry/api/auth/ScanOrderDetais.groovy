package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.util.GlobalHolder//获取用户信息

String point = "scm";  //访问的是哪个app的名称
String address = "/incall/h5/view_order"

 def on = JC.request.param("on");
 def token = JC.request.param("token");
 def coupon_id = JC.request.param('coupon_id');

 def  accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
def ap_id = accountInfo['ap_id'];//获取用户信息

def pid = GlobalHolder.pid;
def aaaa = JC.internal.call('scm', '/incall/h5/view_order', [on:on,apid:ap_id,token:token,pid:pid,coupon_id:coupon_id]);

//Result result = new Result();
//result.setData(aaaa);
//
//return result;


println 'aaaa=' + aaaa;
aaaa = JackSonBeanMapper.jsonToMap(aaaa);

if(aaaa&&aaaa['data']) {
	def datas = aaaa['data'];
	
	def order_info = datas[0];
	def coupon_info = datas[1];
	
	aaaa['data'] = order_info;
	
	if(!coupon_id) {
		aaaa['pref_coupon'] = null;
	} else {
		if(coupon_info&&coupon_info['available']) {
			def coupon_data = coupon_info['data']['other'];
			BigDecimal pay_amount = new BigDecimal(order_info['pay_amount'] + '');
			BigDecimal pref_amount = new BigDecimal(coupon_data['prep_amount'] + '');
			if(pref_amount.compareTo(new BigDecimal(0))<=0) {
				aaaa['pref_coupon'] = '';
			} else {
				aaaa['pref_coupon'] = coupon_data;
				
				def real_pay_amount = pay_amount.subtract(pref_amount);
				if(real_pay_amount<0) {
					real_pay_amount = new BigDecimal(0);
				}
				aaaa['data']['pay_amount'] = real_pay_amount.setScale(2);
			}
		} else {
			aaaa['pref_coupon'] = '';
		}
	}
}

Result result = new Result();
return result.setData(aaaa);

