package com.lengjiabao.general_api.entry.ypcall.account.mc

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');

def pid = GlobalHolder.pid;

//SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'crm', '/token/mc_list', [pid:pid,token:token]);

def ap_id = JC.request.get().getAttribute('_user_')['ap_id'];

String ret = JC.internal.call('crm', '/h5/user/get_account_mcs', [apid:ap_id.toString(),pid:GlobalHolder.pid.toString()]);

def ret_obj = JackSonBeanMapper.jsonToMap(ret);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}

return GeneralPub.success(ret_obj.data);
