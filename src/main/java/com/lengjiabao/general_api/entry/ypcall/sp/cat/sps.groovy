package com.lengjiabao.general_api.entry.ypcall.sp.cat

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def cat_id = JC.request.param('cat_id');
def ss = 0;	//取启用状态的
SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'reserve', '/sp/cat_sps', [pid:1,cat_id:cat_id,ss:ss]);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}

def retobj = [];
for(x in ret_obj.data) {
	def id = x['id'];
	def spno = x['spno'];
	def spname = x['spname'];
	def spinfo = x['spinfo'];
	def spcontent = x['spcontent'];
	def imgurl = x['imgurl'];
	def price = x['price'];
	def duratime = x['duratime'];
	def duraunit = x['duraunit'];
	def flag = x['flag'];
	
	def data_model = [id:id,spno:spno,spname:spname,spinfo:spinfo,spcontent:spcontent,imgurl:imgurl,price:price,duratime:duratime,duraunit:duraunit,flag:flag];
	retobj.add(data_model);
}

return GeneralPub.success(retobj);

