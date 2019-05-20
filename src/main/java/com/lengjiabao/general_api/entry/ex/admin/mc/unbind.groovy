package com.lengjiabao.general_api.entry.ex.admin.mc

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def id = JC.request.param('id');
def pid = GlobalHolder.pid;

SimpleAjax avret = JC.internal.call(SimpleAjax, 'crm', '/mc/unbind_mc', [apmc_id:id, pid:pid]);
if(avret.available) {
	return GeneralPub.success();
}

def err_code = avret.messages[0];
def err_msg = null;
if(avret.messages.length>1) {
	err_msg = avret.messages[1];
}

return GeneralPub.fail(err_code, err_msg, null);
