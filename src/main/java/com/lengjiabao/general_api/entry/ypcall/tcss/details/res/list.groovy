package com.lengjiabao.general_api.entry.ypcall.tcss.details.res

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'reserve', '/sp/cat_list', [pid:1]);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}

println ret_obj.data;

// def retobj = [];
// for(x in ret_obj.data) {
// 	def id = x['id'];
// 	def spno = x['spno'];
// 	def spname = x['spname'];
// 	def spinfo = x['spinfo'];
// 	def spcontent = x['spcontent'];
// 	def imgurl = x['imgurl'];
// 	def price = x['price'];
// 	def duratime = x['duratime'];
// 	def duraunit = x['duraunit'];
	
// 	def data_model = [id:id,spno:spno,spname:spname,spinfo:spinfo,spcontent:spcontent,imgurl:imgurl,price:price,duratime:duratime,duraunit:duraunit];
// 	retobj.add(data_model);
// }

// return GeneralPub.success(retobj);

