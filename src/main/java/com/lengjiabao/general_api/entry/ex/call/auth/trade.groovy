package com.lengjiabao.general_api.entry.ex.call.auth

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def tnum = JC.request.param('tnum');
if(!tnum) {
	return GeneralPub.comfail('交易编号参数错误');
}
def pid = GlobalHolder.pid;

//去查询交易信息
SimpleAjax ret_ajax = JC.internal.call(SimpleAjax, 'trade', '/query/trade_by_num', [pid:pid, tnum:tnum]);

if(ret_ajax && ret_ajax.available) {
	return GeneralPub.success(ret_ajax.data);
}

return GeneralPub.fail(ret_ajax.messages[0], ret_ajax.messages[1], null);

