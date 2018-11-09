package com.lengjiabao.general_api.entry.ypcall.account.order

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');
def oss = JC.request.param('oss');

def pid = GlobalHolder.pid;
SimpleAjax ret = JC.internal.call(SimpleAjax, 'reserve', '/sp/order_list', [pid:pid,token:token,oss:oss]);
if(!ret.available) {
	return GeneralPub.fail(ret.messages[0], ret.messages[1], null);
}

return GeneralPub.success(ret.data);