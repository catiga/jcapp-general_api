package com.lengjiabao.general_api.entry.ex.call.auth

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息

if(accountInfo==null) {
	return GeneralPub.fail('user_not_login', '请登录', null);
}

def mobile = accountInfo['mobile'];//获取用户信息
def pid = accountInfo['pid'];//获取用户信息
def ap_id = accountInfo['ap_id'];

def coupon_code = JC.request.param('coupon_code');
if(!coupon_code) {
	return GeneralPub.fail('param_error', '请输入券码', null); 
}

SimpleAjax add_result = JC.internal.call(SimpleAjax, 'market', '/coupon/add_code', [pid:pid, coupon_code:coupon_code, mobile:mobile, ap_id:ap_id]);
if(add_result==null) {
	return GeneralPub.fail('app_call_error', '请检查微服务网络', null);
}

if(!add_result.available) {
	def err_code = add_result.messages[0];
	def err_msg = err_code;
	if(add_result.messages.length>1) {
		err_msg = add_result.messages[1];
	}
	
	return GeneralPub.fail(err_code, err_msg, null);
}

return GeneralPub.success();
