package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper

JCLogger logger = JCLoggerFactory.getLogger('get_tcss_order_detail');

def accountInfo = JC.request.attr("_user_");//获取用户信息
def mobile = accountInfo['mobile'];//获取用户信息
def pid = accountInfo['pid'];//获取用户信息
def ap_id = accountInfo['ap_id'];

def order_no = JC.request.param("order_no");
def coupon_id = JC.request.param('coupon_id');
def market_id = JC.request.param('market_id');

def aaaa = JC.internal.call("ticketingsys", "/api/order_detail", [order_no:order_no,coupon_id:coupon_id, market_id:market_id, mobile:mobile, ap_id:ap_id]);
logger.info('market_id=' + market_id + ', mobile=' + mobile);
logger.info('market_result=' + aaaa);
aaaa = JackSonBeanMapper.jsonToMap(aaaa);

def datas = aaaa['data'];

def order_info = datas[0];
def coupon_info = datas[1];

aaaa['data'] = order_info;

/*
if(!coupon_id) {
	aaaa['pref_coupon'] = null;
} else {
	if(coupon_info&&coupon_info['available']) {
		def coupon_data = coupon_info['data']['other'];
		aaaa['pref_coupon'] = coupon_data;
		//开始改数据
		BigDecimal pay_amount = new BigDecimal(order_info['pay_amount'] + '');
		BigDecimal pref_amount = new BigDecimal(coupon_data['prep_amount'] + '');
		def real_pay_amount = pay_amount.subtract(pref_amount);
		if(real_pay_amount<0) {
			real_pay_amount = new BigDecimal(0);
		}
		aaaa['data']['pay_amount'] = real_pay_amount.setScale(2);
	} else {
		aaaa['pref_coupon'] = '';
	}
}
//代码替换成下面部分做下测试，还得测试劵是否正常
*/
if(coupon_info&&coupon_info['available']) {
	def coupon_data = coupon_info['data']['other'];
	aaaa['pref_coupon'] = coupon_data;
	//开始改数据
	BigDecimal pay_amount = new BigDecimal(order_info['pay_amount'] + '');
	BigDecimal pref_amount = new BigDecimal(coupon_data['prep_amount'] + '');
	def real_pay_amount = pay_amount.subtract(pref_amount);
	if(real_pay_amount<0) {
		real_pay_amount = new BigDecimal(0);
	}
	aaaa['data']['pay_amount'] = real_pay_amount.setScale(2);
} else {
	aaaa['pref_coupon'] = '';
}

if(aaaa['handle_fee']==null) {
	aaaa['handle_fee'] = new BigDecimal(0);
}
if(aaaa['service_fee']==null) {
	aaaa['service_fee'] = new BigDecimal(0);
}
Result result = new Result();

return result.setData(aaaa);


