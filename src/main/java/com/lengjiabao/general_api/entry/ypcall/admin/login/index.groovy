package com.lengjiabao.general_api.entry.ypcall.admin.login

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def username = JC.request.param('username');
def password = JC.request.param('password');

def pid = GlobalHolder.pid;

SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_login', [jc_name:username,jc_pass:password,pid:pid]);

if(!ret.available) {
	def err_code = ret.messages[0];
	def err_msg = err_code;
	if(ret.messages.length>1) {
		err_msg = ret.messages[1];
	}
	return GeneralPub.fail(err_code, err_msg, null);
}

return GeneralPub.success(ret.data);

