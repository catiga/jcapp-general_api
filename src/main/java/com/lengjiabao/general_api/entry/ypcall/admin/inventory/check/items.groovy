package com.lengjiabao.general_api.entry.ypcall.admin.inventory.check

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');
def icid = JC.request.param('icid');

def pid = GlobalHolder.pid;

//获取特定门店下所有进行中的盘点
SimpleAjax ret = JC.internal.call(SimpleAjax, 'scm', '/inventory/take_items', [icid:icid,pid:pid]);

if(!ret.available) {
	def err_code = ret.messages[0];
	def err_msg = ret.messages[0];
	if(ret.messages.length>1) {
		err_msg = ret.messages[1];
	}
	return GeneralPub.fail(err_code, err_msg, null);
}

return GeneralPub.success(ret.data);
