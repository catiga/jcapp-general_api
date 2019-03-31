package com.lengjiabao.general_api.entry.ex.call.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def cinema_id = JC.request.param("cinema_id");
def plan_id = JC.request.param("plan_id");
def pid = GlobalHolder.pid;

def  accountInfo = JC.request.get().getAttribute("_user_");
if(!accountInfo) {
	return GeneralPub.fail('user_not_login', '用户未登陆', null);
}

def ap_id = accountInfo['ap_id'];

def aaaa = JC.internal.call('ticketingsys', '/api/my_plan_order', [cinema_id:cinema_id,plan_id:plan_id,ap_id:ap_id,pid:pid]);

if(aaaa) {
	aaaa = JackSonBeanMapper.jsonToMap(aaaa);
	if(aaaa['code']==0) {
		def order = aaaa['data'];
		return GeneralPub.success(order);
	}
}

return GeneralPub.success();



