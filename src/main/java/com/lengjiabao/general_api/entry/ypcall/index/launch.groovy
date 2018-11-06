package com.lengjiabao.general_api.entry.ypcall.index

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def code = JC.request.param('code');
def pid = GlobalHolder.pid;
SimpleAjax ret_result = JC.internal.call(SimpleAjax, 'crm', '/wechat/app/check_code', [pid:pid,code:code]);

if(!ret_result.available) {
	//失败的情况
	def err_code = ret_result.messages[0];
	def err_msg = err_code;
	if(ret_result.messages.length>1) {
		err_msg = ret_result.messages[1];
	}
	//这里就已经创建了基本账户，及apid
	return GeneralPub.fail(err_code, err_msg, null);
}

def ap_id = ret_result.data['ap_id'];
def part_id = ret_result.data['part_id'];
def token = ret_result.data['token'];

//返回默认消息
def data = [:];
data['dtid'] = 0;
data['dtkey'] = '998102198584';
data['dtcat'] = '1000';
data['dttitle'] = '消息：';
data['dtinfo'] = '您的宝贝即将服务完成';
data['click_url'] = null;

if(ret_result.data['mobile']) {
	def mobile = ret_result.data['mobile'];
	//寻找是否为当前系统的管理用户手机号
	SimpleAjax admin_user_result = JC.internal.call(SimpleAjax, 'project', '/auth/user_by_phone', [phone:mobile,pid:pid]);
	if(admin_user_result.available) {
		ret_result.data['mt'] = admin_user_result.data;
	}
}

return GeneralPub.success(ret_result.data);

