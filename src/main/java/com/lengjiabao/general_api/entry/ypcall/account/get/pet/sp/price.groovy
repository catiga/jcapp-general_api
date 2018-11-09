package com.lengjiabao.general_api.entry.ypcall.account.get.pet.sp

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def att_ids = JC.request.param('a_bind_ids');
def sp_id = JC.request.param('sp_id');
def res_id = JC.request.param('res_id');

def pid = GlobalHolder.pid;

SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'reserve', '/sp/get_price', [pid:pid,att_ids:att_ids,res_id:res_id,sp_id:sp_id]);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}

def content = ret_obj.data;
def retobj = [];
def detail = [
	ab_id:content['ab_id'], 
	canbook:content['canbook'], 
	lowprice:content['lowprice']
];


retobj.add(detail);

return GeneralPub.success(retobj);

