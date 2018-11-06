package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.JackSonBeanMapper


//返回非list数据
//Result result = new Result();
//List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//JCRequest reques = RequestSource.getRequest();
//def order_no = reques.getParameter("order_no");
//
//CommunicationParam param1 = new CommunicationParam("order_no", order_no);
//
//params.add(param1);
//
//CommunicationPower caller = CommunicationSource.getCommunicator("ticketingsys");
//def aaaa = caller.doworkAsString("/api/order_detail", params);
//
//
//return result.setData(aaaa);


def order_no = JC.request.param("order_no");
def coupon_id = JC.request.param('coupon_id');
def aaaa = JC.internal.call("ticketingsys", "/api/order_detail", [order_no:order_no,coupon_id:coupon_id]);

println 'aaaa=' + aaaa;
aaaa = JackSonBeanMapper.jsonToMap(aaaa);

def datas = aaaa['data'];

def order_info = datas[0];
def coupon_info = datas[1];

aaaa['data'] = order_info;



//构造测试数据
//if(coupon_info) {
//	coupon_info['available'] = true;
//	coupon_info['data'] = [:];
//	coupon_info['data']['offerAmount'] = '500';
//	coupon_info['data']['totalAmount'] = '2000';
//}


if(!coupon_id) {
	aaaa['pref_coupon'] = null;
} else {
	if(coupon_info&&coupon_info['available']) {
		def coupon_data = coupon_info['data']['other'];
		aaaa['pref_coupon'] = coupon_data;
		//开始改数据
		BigDecimal pay_amount = new BigDecimal(order_info['pay_amount'] + '');
		BigDecimal pref_amount = new BigDecimal(coupon_data['prep_amount'] + '');
		def real_pay_amount = pay_amount.subtract(pref_amount);
		if(real_pay_amount<0) {
			real_pay_amount = new BigDecimal(0);
		}
		aaaa['data']['pay_amount'] = real_pay_amount.setScale(2);
	} else {
		aaaa['pref_coupon'] = '';
	}
}

Result result = new Result();

return result.setData(aaaa);


