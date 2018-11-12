package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder//获取用户信息
import com.lengjiabao.general_api.ready.util.RemoteUtil

def order_no = JC.request.param("order_no");
def tnum = JC.request.param('tnum');
def unicode = JC.request.param('unicode');
def ct = JC.request.param('ct');
def coupons = JC.request.param('coupons');
def token = JC.request.param("token");

def accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息

println JackSonBeanMapper.toJson(accountInfo);
def ap_id = accountInfo['ap_id'];//获取用户信息

def pid = GlobalHolder.pid;
def aaaa = JC.internal.call('scm', '/incall/h5/view_order', [on:order_no,apid:ap_id,token:token,pid:pid,coupon_id:coupons]);

aaaa = JackSonBeanMapper.jsonToMap(aaaa);

if(aaaa&&aaaa['data']) {
	def datas = aaaa['data'];
	
	def order_info = datas[0];
	def coupon_info = datas[1];
	
	aaaa['data'] = order_info;
	
	if(!coupons) {
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

List<CommunicationParam> params  = new ArrayList<CommunicationParam> ();
params.add(new CommunicationParam("tnum",tnum));
params.add(new CommunicationParam("unicode",unicode));
params.add(new CommunicationParam("ct",ct));
params.add(new CommunicationParam("coupons",coupons));
SimpleAjax pref_ajax = RemoteUtil.connect(SimpleAjax, 'trade', '/incall/ro_preferential', params);

if(pref_ajax.available) {
	aaaa['data']['pay_amount'] = pref_ajax.data[0];
}

Result result = new Result();

return result.setData(aaaa);


