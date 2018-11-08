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

def id = JC.request.param('id');
def mc_num = JC.request.param('mc_num');
def mch_id = JC.request.param('mch_id');
def recharge_amount = JC.request.param('recharge_amount');

def r_amount = null;
try {
	r_amount = new BigDecimal(recharge_amount);
	r_amount = r_amount.multiply(new BigDecimal(100));
} catch(any) {
}
if(r_amount==null) {
	return GeneralPub.comfail('充值金额参数错误');
}

print 'aim_pid===' + aim_pid;

SimpleAjax result = JC.internal.call(SimpleAjax, 'crm', '/h5/mc/createRechargeAmountOrder', [pid:aim_pid,mc_num:mc_num,h_id:mch_id,r_amount:r_amount]);

if(!result.available) {
	def err_code = result.messages[0];
	def err_msg = result.messages[1];
	return GeneralPub.fail(err_code, err_msg, null);
}

return GeneralPub.success(result.data);
