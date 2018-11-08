package com.lengjiabao.general_api.entry.ex.admin.user

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.MD5Util
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def jc_name = JC.request.param('username');
def jc_pass = JC.request.param('password');

println jc_name + ":" + jc_pass;

def pid = GlobalHolder.pid;

SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_login', [jc_name:jc_name,jc_pass:jc_pass,pid:pid]);
if(!ret.available) {
	def err_code = ret.messages[0];
	def err_msg = ret.messages[0];
	if(ret.messages.length>1) {
		err_msg = ret.messages[1];
	}
	return GeneralPub.fail(err_code, err_msg, null);
}

return GeneralPub.success(ret.data);
