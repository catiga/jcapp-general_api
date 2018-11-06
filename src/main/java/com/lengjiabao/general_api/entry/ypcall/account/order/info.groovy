package com.lengjiabao.general_api.entry.ypcall.account.order

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

//获取服务订单
def token = JC.request.param('token');
def order_id = JC.request.param('order_id');
def pid = GlobalHolder.pid;

SimpleAjax ret = JC.internal.call(SimpleAjax, 'reserve', '/sp/order_view', [pid:pid,token:token,order_id:order_id]);
if(!ret.available) {
	return GeneralPub.fail(ret.messages[0], ret.messages[1], null);
}

return GeneralPub.success(ret.data);