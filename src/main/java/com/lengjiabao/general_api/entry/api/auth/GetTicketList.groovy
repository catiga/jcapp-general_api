package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder

Result result = new Result();

def token = JC.request.param("token");

def  accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
def ap_id = accountInfo['ap_id'];//获取用户信息
if(accountInfo==null || ap_id==null) {
	result.setData(SimpleAjax.notAvailable('token_invalid,请登录'));
	return result;
}

//List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//CommunicationParam param1 = new CommunicationParam("token", token);
//CommunicationParam param2 = new CommunicationParam("apid", ap_id);
//
//params.add(param1);
//params.add(param2);
//
//CommunicationPower caller = CommunicationSource.getCommunicator("ticketingsys");
//def aaaa = caller.doworkAsString("/api/orders", params);

def pid = GlobalHolder.pid;
def aaaa = JC.internal.call('ticketingsys', '/api/orders', ['apid':ap_id,'token':token, 'pid':pid]);

return result.setData(aaaa);


