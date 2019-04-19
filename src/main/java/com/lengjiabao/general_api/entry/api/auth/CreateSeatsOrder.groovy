package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder

def cinema_id = JC.request.param('cinema_id');
def plan_date = JC.request.param('plan_date');
def plan_id = JC.request.param('plan_id');
def last_update_time = JC.request.param('last_update_time');
def seat_ids = JC.request.param('seat_ids');
def token = JC.request.param('token');

def  accountInfo = JC.request.get().getAttribute("_user_");
def ap_id = accountInfo['ap_id'];
def mobile = accountInfo['mobile'];
Result result = new Result();

//获取pid
//def domain = JC.request.get().getServerName();
//SimpleAjax proj_obj = JC.internal.call(SimpleAjax, 'project', '/incall/project_stand', [domain:domain]);
//if(!proj_obj.available) {
//	def map = [code:1000, msg:'域名未配置'];
//	result.setData(map);
//	return result;
//}
//def pid = proj_obj.data['id'];

def pid = GlobalHolder.pid;

if(cinema_id=='5') {
	def errs = [:];
	errs.put('code', 99999);
	errs.put('msg', '售票系统维护中，请置影城前台购票');
	return result.setData(errs);
}

def param_dic = [cinema_id:cinema_id,plan_date:plan_date,plan_id:plan_id,last_update_time:last_update_time,seat_ids:seat_ids,token:token,apid:ap_id, pid:pid,mobile:mobile];
def aaaa = JC.internal.call('ticketingsys', '/api/genorder', param_dic);

return result.setData(aaaa);





