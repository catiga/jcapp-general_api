package com.lengjiabao.general_api.entry.ypcall.account.pet

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.res.PetInfo
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

//def bearing = JC.request.param('bearing')?.trim();
//def bindNo = JC.request.param('bindNo')?.trim();
//def birth = JC.request.param('birth')?.trim();
//def cat = JC.request.param('cat')?.trim();
//def catcode = JC.request.param('catcode')?.trim();
//def character = JC.request.param('character')?.trim();
//def color = JC.request.param('color')?.trim();
//def food = JC.request.param('food')?.trim();
//def gender = JC.request.param('gender')?.trim();
//def headimg = JC.request.param('headimg')?.trim();
//def high = JC.request.param('high')?.trim();
//def name = JC.request.param('name')?.trim();
//def nursing = JC.request.param('nursing')?.trim();
//def remark = JC.request.param('remark')?.trim();
//def sicken = JC.request.param('sicken')?.trim();
//def sterilisation = JC.request.param('sterilisation')?.trim();

def token = JC.request.param('token');
def id = JC.request.param('id');

if(id) {
	SimpleAjax ret = JC.internal.call(SimpleAjax, 'crm', '/binddata/view_pet', [token:token,id:id,pid:1]);
	if(ret.available) {
		return GeneralPub.success(ret.data);
	}
	return GeneralPub.comfail(ret.messages[0]);
}

return GeneralPub.comfail('empty');