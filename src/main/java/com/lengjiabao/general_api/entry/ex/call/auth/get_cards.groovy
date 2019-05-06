package com.lengjiabao.general_api.entry.ex.call.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

String point = "crm";
String address = "/h5/user/get_account_mcs";

def accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息

if(accountInfo==null) {
	return GeneralPub.comfail('请登录');
}

def ap_id = accountInfo['ap_id'];//获取用户信息
def mobile = accountInfo['mobile'];//获取用户信息

//判断一下当前手机号有没有会员卡
def mobile_with_card = JC.internal.call('crm', '/api/order/mc/get_account_mcs_by_mobile', [mobile:mobile,pid:GlobalHolder.pid.toString()]);
try {
	mobile_with_card = JackSonBeanMapper.jsonToMap(mobile_with_card);
	if(mobile_with_card['available']&&mobile_with_card['obj']) {
		//return new Result().setData(mobile_with_card);
		return GeneralPub.success(mobile_with_card);
	}
} catch(any) {}

def cards = [];
def ret = JC.internal.call('crm', '/h5/user/get_account_mcs', [apid:ap_id.toString(),pid:GlobalHolder.pid.toString()]);
if(ret!=null) {
	try {
		ret = JackSonBeanMapper.jsonToMap(ret);
		if(ret['available']) {
			cards = ret['obj'];
		}
	} catch(any) {
	}
}
return GeneralPub.success(cards);


