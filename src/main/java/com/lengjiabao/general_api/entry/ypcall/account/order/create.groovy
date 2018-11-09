package com.lengjiabao.general_api.entry.ypcall.account.order

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');

def sp_id = JC.request.param('sp_id');
def sp_items = JC.request.param('sp_items');
def res_id = JC.request.param('res_id');
def store_id = JC.request.param('store_id');
def ab_ids = JC.request.param('ab_ids');

def sp_date = JC.request.param('sp_date');
def sp_time = JC.request.param('sp_time');

def pid = GlobalHolder.pid;

SimpleDateFormat _sdf_yyMMdd_ = new SimpleDateFormat('yyyyMMdd HH:mm:ss');
//开始整理数据
try {
	sp_date = sp_date.replaceAll('-', '');
	
	def time = sp_date + ' ' + sp_time;
	time = _sdf_yyMMdd_.parse(time);
	if(time.before(Calendar.getInstance().getTime())) {
		return GeneralPub.comfail('请选择正确的时间');
	}
} catch(any) {
	return GeneralPub.comfail('请选择正确的时间');
}

SimpleAjax order_info = JC.internal.call(SimpleAjax, 'reserve', '/sp/create_order', [pid:pid,token:token,sp_id:sp_id,res_id:res_id,store_id:store_id,ab_ids:ab_ids,sp_date:sp_date,sp_time:sp_time,sp_items:sp_items]);
if(!order_info.available) {
	def error_code = order_info.messages[0];
	def error_msg = order_info.messages.length>1?order_info.messages[1]:error_code;
	return GeneralPub.fail(error_code, error_msg, null);
}
return GeneralPub.success(order_info.data);

