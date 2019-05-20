package com.lengjiabao.general_api.entry.ex.admin.mc

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def pid = GlobalHolder.pid;

def token = JC.request.param('token');
SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token_with_pid', [token:token,pid:pid]);
if(!ret.available) {
	return GeneralPub.comfail('no_login');
}

//重新定义pid，取当前用户管理归属项目
def aim_pid = ret.data['user']['pid'];

def id = JC.request.param('id');

SimpleAjax avret = JC.internal.call(SimpleAjax, 'crm', '/mc/unbind_mc', [apmc_id:id, pid:aim_pid]);
if(avret.available) {
	return GeneralPub.success();
}

def err_code = avret.messages[0];
def err_msg = null;
if(avret.messages.length>1) {
	err_msg = avret.messages[1];
}

return GeneralPub.fail(err_code, err_msg, null);
