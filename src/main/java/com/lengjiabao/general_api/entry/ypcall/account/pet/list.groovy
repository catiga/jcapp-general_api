package com.lengjiabao.general_api.entry.ypcall.account.pet

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def pid = GlobalHolder.pid;
def token = JC.request.param('token');
SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'crm', '/binddata/my_pets', [pid:pid,token:token]);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}

def pet_list = ret_obj.data;
for(x in pet_list) {
	if(!x['headpic']) {
		x['headpic'] = '12111.jpg';
	}
	x['headpic'] = 'http://pe1s.static.pdr365.com/' + x['headpic'];
}

return GeneralPub.success(ret_obj.data);
