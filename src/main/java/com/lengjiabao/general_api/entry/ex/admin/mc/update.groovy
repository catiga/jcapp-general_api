package com.lengjiabao.general_api.entry.ex.admin.mc

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');
SimpleAjax token_ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token_with_pid', [token:token]);
if(!token_ret.available) {
	return GeneralPub.comfail('no_login,登录状态已过期');
}

def id = JC.request.param('id');
def name = JC.request.param('name');
def phone = JC.request.param('phone');
def discount = JC.request.param('discount');
def goods_discount = JC.request.param('goods_discount');

def mul_100 = new BigDecimal(100);
try {
	discount = new BigDecimal(discount).multiply(mul_100).setScale(2);
} catch(any) {
}

try {
	goods_discount = new BigDecimal(goods_discount).multiply(mul_100).setScale(2);
} catch(any) {
}

//更新会员信息
def mc_info = [:];
mc_info['id'] = id;
mc_info['name'] = name;
mc_info['phone'] = phone;
mc_info['discount'] = discount;
mc_info['goods_discount'] = goods_discount;

SimpleAjax ret = JC.internal.call(SimpleAjax, 'crm', '/mc/update', [info:mc_info]);

if(!ret.available) {
	def code = ret.messages[0];
	def msg = code;
	if(ret.messages.length>1) {
		msg = ret.messages[1];
	}
	return GeneralPub.fail(code, msg, null);
}

return GeneralPub.success(ret.data);

