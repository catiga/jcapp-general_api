package com.lengjiabao.general_api.entry.ypcall.sp.info

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def sp_id = JC.request.param('sp_id');
def s_id = JC.request.param('store_id');

SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'reserve', '/sp/book_times', [pid:1,sp_id:sp_id,s_id:s_id]);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}
if(!ret_obj.data) {
	return GeneralPub.fail('obj_not_found', '未设置可预约时间', null);
}

//根据接口需求，合并数据
def ret_datas = ret_obj.data;

String[] weekDays = ["日", "一", "二", "三", "四", "五", "六"];
def week_map = [:];
def need_pass_time_keys = [];
def real_ret_data = [];
for(x in ret_datas) {
	def time_key = x['time_res_key']+x['time'];
	
	if(need_pass_time_keys.contains(time_key)) {
		continue;
	}
	
	//开始循环重置res_ids参数
	def res_ids = x['res_ids'];
	def can_books = x['canbook'];
	for(y in ret_datas) {
		if(x['date']==y['date']&&x['time']==y['time']) {
			if(x['res_ids']!=y['res_ids']) {
				res_ids = res_ids + ',' + y['res_ids'];
				//下面这行暂时关闭，等到微信小程序审核通过后需要打开
				can_books = can_books + ',' + y['canbook'];
			}
		}
	}
	x['res_ids'] = res_ids;
	//设置一个新变量
	x['res_books'] = can_books;
	if(can_books.toString().indexOf('0')>-1) {
		x['canbook'] = '0';
	} else {
		x['canbook'] = '1';
	}
	
	def date = x['date'].toString();
	def week = x['week'];
	date = date.substring(0,4) + '-' + date.substring(4,6) + '-' + date.substring(6);
	x['date'] = date;
	
	need_pass_time_keys.add(time_key);
	real_ret_data.add(x);
	
	if(week_map.get(date)) {
		continue;
	}
	week_map.put(date, [date:date.substring(5,7)+'-'+date.substring(8,10), week:weekDays[week-1]]);
}

return GeneralPub.success([data:real_ret_data, week:week_map.values()]);

