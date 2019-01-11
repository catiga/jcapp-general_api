
package com.lengjiabao.general_api.entry.ypcall.admin.tcss.scan

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.util.MoneyUtil
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');
def tcode = JC.request.param('tcode');
def vcode = JC.request.param('vcode');
def store_id = JC.request.param('store_id');
def partner = '11110001';

println 'tcode=' + tcode;
println 'vcode=' + vcode;
println 'store_id=' + store_id;

def pid = GlobalHolder.pid;

DataObj doo = new DataObj();
doo.code = "0";
doo.msg = "success";

SimpleAjax ret_data = JC.internal.call(SimpleAjax, 'ticketingsys', '/ticketing/take_by_codes', [get_code:tcode,validate_code:vcode,pid:pid]);
if(!ret_data.available) {
//	doo.code = ret_data.messages[0];
//	doo.msg = ret_data.messages[1];
//	return doo;
	return GeneralPub.comfail(ret_data.messages[0], ret_data.messages[1]);
}
def order = ret_data.data[0];
def seats = ret_data.data[1];

def ticket_data = [:];
ticket_data['store_name'] = order['store_name'];
ticket_data['hall_name'] = order['hall_name'];
//ticket_data['hall_name'] = '测试影厅';

ticket_data['plan_date'] = order['plan_date'];
ticket_data['plan_time'] = order['plan_time'];
ticket_data['film_name'] = order['film_name'];
ticket_data['handle_fee'] = '0';
ticket_data['order_no'] = order['order_no'];
ticket_data['total_amount'] = order['total_amount'];
ticket_data['pay_amount'] = order['pay_amount'];
ticket_data['ticket_sum'] = order['ticket_sum'];
ticket_data['a_time'] = order['a_time'];

def each = MoneyUtil.divide(order['pay_amount'].toString(), order['ticket_sum'].toString());
if (new BigDecimal(each).compareTo(new BigDecimal(seats[0]['pub_fee'].toString())) >= 0 ) {
	// 价格正常
	ticket_data['total_amount'] = new BigDecimal(each).setScale(0);
	ticket_data['pay_amount'] = new BigDecimal(each).setScale(0);
} else {
	// 价格除以100
	ticket_data['total_amount'] = new BigDecimal(seats[0]['pub_fee']).setScale(0);
	ticket_data['pay_amount'] = new BigDecimal(seats[0]['pub_fee']).setScale(0);
}

def o_seats = [];
for(x in seats) {
	def os = [:];
	os['seat_no'] = x['seat_no'];
	os['seat_gr'] = x['seat_sr'];
	os['seat_gc'] = x['seat_sc'];
	os['seat_sr'] = x['seat_sr'];
	os['seat_sc'] = x['seat_sc'];
	os['handle_fee'] = '0';
	
	if (new BigDecimal(each).compareTo(new BigDecimal(x['pub_fee'].toString())) >= 0 ) {
		os['sale_fee'] = new BigDecimal(each).setScale(0);
	} else {
		// 价格除以100
		os['sale_fee'] = new BigDecimal(x['pub_fee']).setScale(0);
	}
	o_seats.add(os);
}
ticket_data['o_seats'] = o_seats;

def orderRemote = [:];
orderRemote['ticket_flag1'] = tcode;
orderRemote['ticket_flag2'] = vcode;

ticket_data['orderRemote'] = orderRemote;

//增加二维码
ticket_data['qrcode'] = 'Vm0wd2QyUXlWa2hWV0doVllteEtWMVl3WkRSWFJteFZVMnhPV0Zac2JETlhhMUpUVmpBeFdHVkliRmhoTWsweFZqQmFTMlJIVmtkWGJGcE9ZV3RGZUZkV1pEUlRNazE0V2toV2FGSnRVbFJVVkVwdlZWWmtWMWRzV214U2JHdzBWa2MxVDJGV1NuTlhia0pXWWxob00xWldXbXRXTVdSelYyMTRVMkpXU2twV2JURXdZekpHVjFOdVZsSmlSMmhZV1d4U1YxSkdXbGRYYlhSWFRWWndNRlZ0ZUZOaFZscHlWMVJHVjJFeVVYZFhWbVJIVmpGT2RWWnNTbWhsYlhoWVYxWmtNR1F5VW5OWGExcFlZa1p3YzFacVJtRlRSbHBJWlVaa1ZXSkdjRnBWVjNoVFZtc3hkV0ZHVWxkU1JWcE1WbTB4VDFJeFduTldiV2hPVm14YWVsWkdVa05WYTNCVlZHdFZKVE5F';
//增加票号
ticket_data['tknum'] = order.order_no;
//增加票类
ticket_data['tkcat'] = "标准票";

doo.data = ticket_data;

return GeneralPub.success(doo);

class DataObj {
	def code;
	def msg;
	def data;
}

class BindDto{
	def user_id;
	def user_name;
	def seat;
	def plan_date;
	def plan_time;
	def movie_name;
	def hall_name;
	def plan_id;
	def store_name;
	def store_id;
}
