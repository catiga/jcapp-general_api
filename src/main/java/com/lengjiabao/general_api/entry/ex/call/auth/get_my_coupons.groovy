package com.lengjiabao.general_api.entry.ex.call.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

JCLogger logger = LoggerSource.getLogger();
String point = "market";
String address = "/coupon/get_available_coupon_list";

def accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
if(accountInfo==null) {
	return GeneralPub.fail('user_not_login', '请登录', null);
}

def mobile = accountInfo['mobile'];//获取用户信息
def pid = accountInfo['pid'];//获取用户信息
def ap_id = accountInfo['ap_id'];

def ret = JC.internal.call(point, address, [mobile:mobile, pid:pid, ap_id:ap_id]);

logger.info('get account coupons:' + ret);

ret = JackSonBeanMapper.jsonToMap(ret);

def ret_code = ret['code'];
def ret_msg = ret['msg'];
def ret_list = ret['list'];

def ret_list_new = [];
if(ret_code=='0') {
	//正常状态
	if(ret_list) {
		for(x in ret_list) {
			if(x['validate_end']==0) {
				x['validate_end'] = '2099-12-31'
			}
			ret_list_new.add(x);
		}
	}
}
ret['list'] = ret_list_new;

return GeneralPub.success(ret);
