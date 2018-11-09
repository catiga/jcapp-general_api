package com.lengjiabao.general_api.entry.ypcall.account.pet

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def pid = GlobalHolder.pid;
def token = JC.request.param('token');
def id = JC.request.param('id');

if(id) {
	SimpleAjax ret = JC.internal.call(SimpleAjax, 'crm', '/binddata/view_pet', [token:token,id:id,pid:pid]);
	if(ret.available) {
		return GeneralPub.success(ret.data);
	}
	return GeneralPub.comfail(ret.messages[0]);
}

return GeneralPub.comfail('empty');