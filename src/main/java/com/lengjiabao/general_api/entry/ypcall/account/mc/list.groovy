package com.lengjiabao.general_api.entry.ypcall.account.mc

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');


SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'crm', '/token/mc_list', [pid:1,token:token]);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}

println ret_obj.data;

return GeneralPub.success(ret_obj.data);
