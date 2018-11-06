package com.lengjiabao.general_api.entry.ypcall.admin.sp.order

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');
def qk = JC.request.param('qk');

def pid = GlobalHolder.pid;

SimpleAjax ret = JC.internal.call(SimpleAjax, 'reserve', '/admin/sp_order_list', [pid:pid,token:token,qk:qk]);

if(!ret.available) {
	def err_code = ret.messages[0];
	def err_msg = ret.messages[0];
	if(ret.messages.length>1) {
		err_msg = ret.messages[1];
	}
	return GeneralPub.fail(err_code, err_msg, null);
}

return GeneralPub.success(ret.data);