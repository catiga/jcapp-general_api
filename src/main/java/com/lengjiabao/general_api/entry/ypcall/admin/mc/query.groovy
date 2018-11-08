package com.lengjiabao.general_api.entry.ypcall.admin.mc

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def pid = GlobalHolder.pid;

def token = JC.request.param('token');
SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token', [token:token,pid:pid]);
if(!ret.available) {
	return GeneralPub.comfail('no_login');
}


def mck = JC.request.param('mck');
ret = JC.internal.call(SimpleAjax, 'crm', '/mc/query', [mck:mck,pid:pid]);
if(!ret.available) {
	return GeneralPub.comfail(ret.messages[0]);
}

return GeneralPub.success(ret.data);