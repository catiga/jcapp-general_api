package com.lengjiabao.general_api.entry.api.auth

import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.RemoteUtil

def order_no = JC.request.param("order_no");
def tnum = JC.request.param('tnum');
def unicode = JC.request.param('unicode');
def ct = JC.request.param('ct');
def coupons = JC.request.param('coupons');

def aaaa = JC.internal.call("ticketingsys", "/api/order_detail", [order_no:order_no,coupon_id:'']);

aaaa = JackSonBeanMapper.jsonToMap(aaaa);

def datas = aaaa['data'];

def order_info = datas[0];
def coupon_info = datas[1];

aaaa['data'] = order_info;

List<CommunicationParam> params  = new ArrayList<CommunicationParam> ();
params.add(new CommunicationParam("tnum",tnum));
params.add(new CommunicationParam("unicode",unicode));
params.add(new CommunicationParam("ct",ct));
params.add(new CommunicationParam("coupons",coupons));
//SimpleAjax pref_ajax = RemoteUtil.connect(SimpleAjax, 'trade', '/incall/ro_preferential', params);
SimpleAjax pref_ajax = JC.internal.call(SimpleAjax, 'trade', '/incall/ro_preferential', [tnum:tnum,unicode:unicode,ct:ct,coupons:coupons]);

if(pref_ajax.available) {
	aaaa['data']['pay_amount'] = pref_ajax.data[0];
}

Result result = new Result();

return result.setData(aaaa);


