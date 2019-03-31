package com.lengjiabao.general_api.entry.ex.call

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def cinema_id = JC.request.param('cinema_id');
def plan_date = JC.request.param('plan_date');
def plan_id = JC.request.param('plan_id');
def last_update_time = JC.request.param('last_update_time');
def seat_ids = JC.request.param('seat_ids');
def token = JC.request.param('token');

def  accountInfo = JC.request.get().getAttribute("_user_");
if(!accountInfo) {
	return GeneralPub.fail('user_not_login', '用户未登陆', null);
}

def ap_id = accountInfo['ap_id'];
def mobile = accountInfo['mobile'];
//获取pid
def pid = GlobalHolder.pid;

def param_dic = [cinema_id:cinema_id,plan_date:plan_date,plan_id:plan_id,last_update_time:last_update_time,seat_ids:seat_ids,token:token,apid:ap_id, pid:pid,mobile:mobile];
def aaaa = JC.internal.call('ticketingsys', '/api/genorder', param_dic);

aaaa = JackSonBeanMapper.jsonToMap(aaaa);
if(aaaa['code']==0) {
	return GeneralPub.success(aaaa['data']);
}
return GeneralPub.fail(aaaa['code'], aaaa['msg'], null);





