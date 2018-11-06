package com.lengjiabao.general_api.entry.ypcall.index

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def ap_id = JC.request.param('ap_id');
def part_id = JC.request.param('part_id');
def phone = JC.request.param('phone');
def pid = GlobalHolder.pid;

SimpleAjax ajax = JC.internal.call(SimpleAjax, 'crm', '/wechat/app/phone_login', [p:phone,ap_id:ap_id,part_id:part_id,pid:pid]);
if(!ajax.available) {
	def err_code = ajax.messages[0];
	def err_msg = err_code;
	if(ajax.messages.length>1) {
		err_msg = ajax.messages[1];
	}
	return GeneralPub.fail(err_code, err_msg, null);
}

return GeneralPub.success(ajax.data);

