package com.lengjiabao.general_api.entry.ex.admin.mc

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def pid = GlobalHolder.pid;

def token = JC.request.param('token');
def mck = JC.request.param('mck');

SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token_with_pid', [token:token,pid:pid]);
if(!ret.available) {
	return GeneralPub.fail('user_not_login', '请登录后操作', null);
}

//重新定义pid，取当前用户管理归属项目
def aim_pid = ret.data['user']['pid'];

ret = JC.internal.call(SimpleAjax, 'crm', '/mc/query', [mck:mck,pid:aim_pid]);
if(!ret.available) {
	return GeneralPub.comfail(ret.messages[0]);
}

if(!ret.data) {
	return GeneralPub.fail('data_empty', '未找到会员信息数据', null);
}

return GeneralPub.success(ret.data);