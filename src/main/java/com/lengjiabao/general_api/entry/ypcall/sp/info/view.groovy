package com.lengjiabao.general_api.entry.ypcall.sp.info

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def sp_id = JC.request.param('sp_id');

SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'reserve', '/sp/view', [pid:1,sp_id:sp_id]);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}

def retobj = [:];
def content = ret_obj.data[0];
def items = ret_obj.data[1];

def detail = [
	chargetype:content['chargetype'], 
	duratime:content['duratime'], 
	duraunit:content['duraunit'], 
	flag:content['flag'], 
	id:content['id'], 
	imgurl:content['imgurl'], 
	pid:content['pid'], 
	plrule:content['plrule'], 
	price:content['price'], 
	spcontent:content['spcontent'], 
	spin:content['spin'], 
	spinfo:content['spinfo'], 
	spname:content['spname'], 
	spno:content['spno'], 
	sptype:content['sptype'], 
	timeran:content['timeran']
];



retobj.put('detail',detail);

retobj.put('items',items);

return GeneralPub.success(retobj);
