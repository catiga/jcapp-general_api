package com.lengjiabao.general_api.entry.ypcall.cashier.pay

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.util.MD5Util
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def ct = JC.request.param('ct');
def tnum = JC.request.param('tnum');
def token = JC.request.param('token')?.trim();
def mc_num = JC.request.param('mc_num');
def mc_pwd = JC.request.param('mc_pwd');

def pid = GlobalHolder.pid;

SimpleAjax session = JC.internal.call(SimpleAjax, 'crm', '/token/validate', [token:token,pid:pid]);
if(session==null||!session.available) {
	return GeneralPub.fail('user_no_login', '请登陆', null);
}

def unicode = session.data['part_id'];
if(ct=='101001') {
	unicode = mc_num
}
CommunicationParam param1 = new CommunicationParam("tnum", tnum);
CommunicationParam param2 = new CommunicationParam("ct", ct);
CommunicationParam param3 = new CommunicationParam("pwd", mc_pwd);
CommunicationParam param4 = new CommunicationParam("unicode", unicode);
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
params.add(param1);
params.add(param2);
params.add(param3);
params.add(param4);
// params.add(param5);

//获取app调用句柄 本应用appcode为ticketingsys
CommunicationPower caller = CommunicationSource.getCommunicator("trade");
def aaaa = caller.doworkAsString("/incall/ro_code_pay", params);
aaaa = JackSonBeanMapper.jsonToMap(aaaa);

if(aaaa['code']!='0') {
	def err_code = aaaa['err_code'];
	def err_msg = aaaa['err_code_des'];
	return GeneralPub.fail(err_code, err_msg, null);
}
return GeneralPub.success(aaaa);


