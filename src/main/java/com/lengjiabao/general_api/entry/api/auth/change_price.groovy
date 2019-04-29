package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.common.SimpleAjax

JCLogger logger = LoggerSource.getLogger(this.class);
def order_no = JC.request.param("order_no");
def tnum = JC.request.param('tnum');
def unicode = JC.request.param('unicode');
def ct = JC.request.param('ct');
def coupons = JC.request.param('coupons');

def aaaa = JC.internal.call("ticketingsys", "/api/order_detail", [order_no:order_no,coupon_id:'']);

aaaa = JackSonBeanMapper.jsonToMap(aaaa);

def datas = aaaa['data'];

def order_info = datas[0];
def coupon_info = datas[1];

aaaa['data'] = order_info;

SimpleAjax pref_ajax = JC.internal.call(SimpleAjax, 'trade', '/incall/ro_preferential', [tnum:tnum,unicode:unicode,ct:ct,coupons:coupons]);

if(pref_ajax.available) {
	aaaa['data']['pay_amount'] = pref_ajax.data[0];
} else {
	aaaa['code'] = '0001';	//通用错误
	def msg = '';
	if (pref_ajax.messages.length == 2) {
		msg = pref_ajax.messages[1];
	} else {
		msg = pref_ajax.messages[0];
	}
	def ret_obj = pref_ajax.data;
	if(ret_obj) {
		//ret_obj 为 trade 对象
		if(ret_obj.tss.startsWith('9')) {
			msg = '该交易已经超时关闭，请重新下单';
			aaaa['code'] = '9000';
		} else {
			if(!ret_obj.tss.startsWith('0')) {
				msg = '该交易已经付款成功，请到个人中心查看';
				aaaa['code'] = '1000';
			}
		}
	}
	aaaa['msg'] = msg;
}

Result result = new Result();

return result.setData(aaaa);


