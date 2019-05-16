package com.lengjiabao.general_api.entry.ex.admin.store

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def pid = GlobalHolder.pid;
def store_basic_id = JC.request.param('store_id');
def end_date = JC.request.param('end_date');
def token = JC.request.param('token');

SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token_with_pid', [token:token,pid:pid]);
if(!ret.available) {
	return GeneralPub.fail('user_not_login', '请登录后操作', null);
}

//重置pid
pid = ret.data.user.pid;

SimpleAjax update_cache_result = JC.internal.call(SimpleAjax, 'ticketingsys', '/store/refresh_cache', [store_basic:store_basic_id, end_date:end_date, pid:pid]);
if(update_cache_result.available) {
	return GeneralPub.success();
}

def err_code = update_cache_result.messages[0];
def err_msg = err_code;
if(update_cache_result.messages.length>1) {
	err_msg = update_cache_result.messages[1];
}

return GeneralPub.fail(err_code, err_msg, update_cache_result);

