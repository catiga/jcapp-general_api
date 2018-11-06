package com.lengjiabao.general_api.entry.ypcall.admin.goods

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');
def goods_sn = JC.request.param('goods_sn');
def sid = JC.request.param('sid');
def wh_id = JC.request.param('wh_id');
if(!wh_id) {
	wh_id = '';
}

SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token', [token:token,pid:1]);
if(!ret.available) {
	return GeneralPub.comfail('no_login');
}

SimpleAjax goods_data = JC.internal.call(SimpleAjax, 'scm', '/goods/query_with_stock', [pid:1, g_no:goods_sn, sid:sid,wh_id:wh_id]);
if(!goods_data.available) {
	return GeneralPub.success();
}

return GeneralPub.success(goods_data.data);