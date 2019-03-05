package com.lengjiabao.general_api.entry.ypcall.wx.app.account

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

class DataObj {
	def code = "0";
	def msg = "success";
	def data;
}

DataObj doo = new DataObj();

def code = JC.request.param('code')?.trim();
SimpleAjax ret_result = JC.internal.call(SimpleAjax, 'crm', '/wechat/app/check_code', [pid:GlobalHolder.pid,code:code]);

if(!ret_result.available) {
	//失败的情况
	def err_code = ret_result.messages[0];
	def err_msg = err_code;
	if(ret_result.messages.length>1) {
		err_msg = ret_result.messages[1];
	}
	return GeneralPub.fail(err_code, err_msg, null);
}
//if(!ret_result.available) {
//	//判断消息
//	def err_msg = ret_result.messages[0];
//	if(err_msg!='bind_mobile') {
//		return GeneralPub.fail('-3', "openid got error", null);
//	}
//	def apid = ret_result.data;
//	//说明没有绑定手机号
//	return GeneralPub.fail('-2', "need mobile", apid);
//}

def ap_id = ret_result.data['ap_id'];
def part_id = ret_result.data['part_id'];
def token = ret_result.data['token'];

//完成正常授权，返回token
return GeneralPub.success(ret_result.data);


