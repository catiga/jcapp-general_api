package com.lengjiabao.general_api.entry.ypcall.res.detail

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def res_id = JC.request.param('res_id');

SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'reserve', '/sp/res_by_id', [pid:1,res_id:res_id]);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}

def ret_map = ret_obj.data;

if(ret_map['imgurl']) {
	if(!ret_map['imgurl'].toString().startsWith('http')) {
		ret_map['imgurl'] = 'http://pe1s.static.pdr365.com/' + ret_map['imgurl'];
	}
} else {
	ret_map['imgurl'] = 'http://pe1s.static.pdr365.com/huacai/51141.jpg';
}

return GeneralPub.success(ret_map);