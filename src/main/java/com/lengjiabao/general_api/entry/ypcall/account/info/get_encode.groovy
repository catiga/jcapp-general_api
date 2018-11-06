package com.lengjiabao.general_api.entry.ypcall.account.info

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');
def store_id = JC.request.param('store_id');

def pid = GlobalHolder.pid;

SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'crm', '/token/info', [pid:pid,token:token]);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}
def ret_obj_data = ret_obj.data;

def user_info = JC.request.get().getAttribute('_user_');
//同时获取会员卡信息
//SimpleAjax mc_list = JC.internal.call(SimpleAjax, 'crm', '/token/mc_list', [pid:pid,token:token]);
def mc_list = JC.internal.call('crm', '/h5/user/get_account_mcs', [pid:pid, apid:user_info['ap_id']]);
println mc_list;

mc_list = JackSonBeanMapper.jsonToMap(mc_list);

def mc_data = null;
if(mc_list.available&&mc_list.obj) {
	mc_data = mc_list.obj;
	ret_obj_data+=mc_data;
}

//同时判断是否为管理用户

return GeneralPub.success(ret_obj_data);
