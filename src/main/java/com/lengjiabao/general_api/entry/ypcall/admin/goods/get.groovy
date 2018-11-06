package com.lengjiabao.general_api.entry.ypcall.admin.goods

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');

def id = JC.request.param('id');	//支持商品编辑

SimpleAjax ret = JC.internal.call(SimpleAjax, 'scm', '/goods/get_by_id', [id:id, pid:1]);

if(!ret.available) {
	def code = ret.messages[0];
	def msg = ret.messages.length>1?ret.messages[1]:code;
	return GeneralPub.fail(ret.messages[0], msg, null);
}

return GeneralPub.success(ret.data);

