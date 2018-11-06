package com.lengjiabao.general_api.entry.ypcall.admin.sp.order

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');
def order_id = JC.request.param('order_id');

def pid = GlobalHolder.pid;

SimpleAjax ret = JC.internal.call(SimpleAjax, 'reserve', '/admin/sp_order_accept', [pid:pid,token:token,order_id:order_id]);

if(!ret.available) {
	def err_code = ret.messages[0];
	def err_msg = ret.messages[0];
	if(ret.messages.length>1) {
		err_msg = ret.messages[1];
	}
	return GeneralPub.fail(err_code, err_msg, null);
}

return GeneralPub.success(ret.data);