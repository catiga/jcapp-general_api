package com.lengjiabao.general_api.entry.ypcall.admin.tcss.scan

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
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
	doo.code = ret_data.messages[0];
	doo.msg = ret_data.messages[1];
	return doo;
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
ticket_data['total_amount'] = new BigDecimal(order['total_amount']).divide(new BigDecimal(100)).setScale(2).toString();
ticket_data['pay_amount'] = new BigDecimal(order['pay_amount']).divide(new BigDecimal(100)).setScale(2).toString();
ticket_data['ticket_sum'] = order['ticket_sum'];
ticket_data['a_time'] = order['a_time'];

def o_seats = [];
for(x in seats) {
	def os = [:];
	os['seat_no'] = x['seat_no'];
	os['seat_gr'] = x['seat_sr'];
	os['seat_gc'] = x['seat_sc'];
	os['seat_sr'] = x['seat_sr'];
	os['seat_sc'] = x['seat_sc'];
	os['handle_fee'] = '0';
	os['sale_fee'] = x['sale_fee'];
	
	o_seats.add(os);
}
ticket_data['o_seats'] = o_seats;

def orderRemote = [:];
orderRemote['ticket_flag1'] = tcode;
orderRemote['ticket_flag2'] = vcode;

ticket_data['orderRemote'] = orderRemote;

doo.data = ticket_data;

return GeneralPub.success(doo);

//try{
//	HpOrderDto order = ypchandler_hp.tcss_my_order_detail_by_tvcode(tcode,vcode);
//	if(order == null){
//		doo.code = "order_not_found";
//		doo.msg = "订单不存在";
//		return doo;
//	}
//
//
//	if(order.order_status=='2900' || order.order_status=='2901') {
//		doo.data = order;
//		return doo
//	}
//
//
//	ypc_log.info(order.getStore_id());
//	YpcSscOp ssc_op = ypchandler_hp.generate_ssc_op_by_store_id(order.getStore_id());
//	if(ssc_op == null){
//		doo.code = "connect_error";
//		doo.msg = "链接影城失败";
//		return doo;
//	}
//	SscOp remote_ssc_op = ssc_op.getSsc_op();
//	AppPrintTicketResult ticket_info = remote_ssc_op.fetchPringInfo(ssc_op.getCinema_no(),tcode,vcode);
//	if(!"0".equals(ticket_info.getCode())){
//		doo.code = ticket_info.getRmCode();
//		doo.msg = ticket_info.getRmMsg();
//		return doo;
//	}
//	if("1".equals(ticket_info.getPrinted())){
//		doo.code = "printed";
//		doo.msg = "已取票,请出示纸质票";
//		return doo;
//	}
//	AppSwitchPrintedResult ticket_print = remote_ssc_op.ticketPrint(ssc_op.getCinema_no(),tcode,vcode);
//	if(!"0".equals(ticket_print.getCode())){
//		doo.code = ticket_info.getRmCode();
//		doo.msg = ticket_info.getRmMsg();
//		return doo;
//	}
//	try{
//		ypchandler_hp.tcss_scan_entrance(order.getOrder_no());
//		InprmObj inprm_obj = new InprmObj();
//		inprm_obj.setPk("admin.tcss.scan.entrance");
//		inprm_obj.setSn("1513394646060");
//		TcssOrder torder = new TcssOrder();
//		torder.setId(order.getId());
//
//		for(HpOrderSeatDto seatdto : order.getO_seats()){
//			BindDto bindDto = new BindDto();
//			bindDto.user_id = ypchandler_hp.current_admin_user.getId();
//			bindDto.user_name = ypchandler_hp.current_admin_user.getUsername();
//			bindDto.seat = seatdto.getSeat_sr()+"排"+seatdto.getSeat_sc()+"坐";
//			bindDto.plan_date = order.getPlan_date();
//			bindDto.plan_time = order.getPlan_time();
//			bindDto.movie_name = order.getFilm_name();
//			bindDto.hall_name = order.getHall_name();
//			bindDto.plan_id = order.getPlan_id();
//			bindDto.store_name = order.getStore_name();
//			bindDto.store_id = order.getStore_id();
//
//			ypchandler_hp.save_ypc_dis_data(ypc_call_project.getId(), inprm_obj, torder, JackSonBeanMapper.toJson(bindDto));
//		}
//	}catch(Exception tsex){
//		tsex.printStackTrace();
//	}
//	doo.data = order;
//}catch(Exception e) {
//	doo.code = e.getMessage();
//	doo.msg = e.getCause();
//}
//return doo;


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
