package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper

String point = "crm";
String address = "/h5/user/get_account_mcs";
List<CommunicationParam> params = new ArrayList<CommunicationParam>();

def  accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
def ap_id = accountInfo['ap_id'];//获取用户信息
def mobile = accountInfo['mobile'];//获取用户信息

//String ret = JC.internal.call('crm', '/h5/user/get_account_mcs', [apid:ap_id.toString(),pid:GlobalHolder.pid.toString(),mobile:mobile]);
//return new Result().setData(ret);


//判断一下当前手机号有没有会员卡
def mobile_with_card = JC.internal.call('crm', '/api/order/mc/get_account_mcs_by_mobile', [mobile:mobile,pid:GlobalHolder.pid.toString()]);
try {
	mobile_with_card = JackSonBeanMapper.jsonToMap(mobile_with_card);
	if(mobile_with_card['available']&&mobile_with_card['obj']) {
		return new Result().setData(mobile_with_card);
	}
} catch(any) {}

String ret = JC.internal.call('crm', '/h5/user/get_account_mcs', [apid:ap_id.toString(),pid:GlobalHolder.pid.toString()]);
return new Result().setData(ret);

