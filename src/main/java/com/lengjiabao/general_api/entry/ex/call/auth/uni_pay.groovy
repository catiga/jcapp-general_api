package com.lengjiabao.general_api.entry.ex.call.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def domain = JC.request.get().getServerName();
def tnum = JC.request.param("tnum");
def ct = JC.request.param("ct");
def pwd = JC.request.param("pwd");
def o_rem = JC.request.param('o_rem');

def accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
if(accountInfo==null) {
	return GeneralPub.fail('user_not_login', '请登录', null);
}

def unicode;
if (ct.equals("101001")) {
	unicode = JC.request.param("unicode");
} else {
	unicode = accountInfo['part_id'];//获取用户信息
}
def ap_id = accountInfo['ap_id'];//获取用户信息

def coupon_id = JC.request.param('coupon_id');

def aaaa = JC.internal.call("trade", "/incall/ro_code_pay", [tnum:tnum,ct:ct,pwd:pwd,unicode:unicode,o_rem:o_rem,coupon_id:coupon_id,domain:domain,ap_id:ap_id])

def ret_pay_result = null;;
try {
	aaaa = JackSonBeanMapper.jsonToMap(aaaa);
	ret_pay_result = aaaa;
} catch(any) {
	ret_pay_result = [:];
}
return GeneralPub.success(ret_pay_result);

