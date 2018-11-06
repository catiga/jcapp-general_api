package com.lengjiabao.general_api.entry.ypcall.admin.goods

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');
def goods_sn = JC.request.param('goods_sn');

def pid = GlobalHolder.pid;

SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token', [token:token,pid:pid]);
if(!ret.available) {
	return GeneralPub.comfail('no_login');
}

SimpleAjax goods_data = JC.internal.call(SimpleAjax, 'scm', '/goods/query', [pid:pid, g_no:goods_sn]);
if(!goods_data.available) {
	return GeneralPub.success();
}

return GeneralPub.success(goods_data.data);