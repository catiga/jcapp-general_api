package com.lengjiabao.general_api.entry.ex.admin.mc

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def pid = GlobalHolder.pid;

def token = JC.request.param('token');
SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token_with_pid', [token:token,pid:pid]);
if(!ret.available) {
	return GeneralPub.comfail('no_login');
}

//重新定义pid，取当前用户管理归属项目
def aim_pid = ret.data['user']['pid'];

def op_user_id = ret.data['user']['id'];
def op_user_name = ret.data['user']['name'];

def id = JC.request.param('id');
def mc_num = JC.request.param('mc_num');
def mch_id = JC.request.param('mch_id');
def new_pwd = JC.request.param('new_pwd');

if(!new_pwd) {
	return GeneralPub.comfail('请输入新密码');
}
if(new_pwd.trim().length()!=6) {
	return GeneralPub.comfail('请输入六位数字');
}

//SimpleAjax result = JC.internal.call(SimpleAjax, 'crm', '/h5/mc/createRechargeAmountOrder', [pid:aim_pid,mc_num:mc_num,h_id:mch_id,r_amount:r_amount,op_user_id:op_user_id,op_user_name:op_user_name]);
//
//if(!result.available) {
//	def err_code = result.messages[0];
//	def err_msg = result.messages[1];
//	return GeneralPub.fail(err_code, err_msg, null);
//}

return GeneralPub.success();
