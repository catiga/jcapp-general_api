package com.lengjiabao.general_api.entry.ypcall.sp.info

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def sp_id = JC.request.param('sp_id');
def s_id = JC.request.param('store_id');

SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'reserve', '/sp/res_list', [pid:1,sp_id:sp_id,s_id:s_id]);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}

def retobj = [];
for(x in ret_obj.data) {
	def ret_map = [id:x['id'],restype:x['restype'],resid:x['id'],title:x['title']];
	ret_map['info'] = x['info'];
	ret_map['imgurl'] = x['imgurl'];
	ret_map['name'] = x['name'];
	
	ret_map['context'] = x['context'];
	ret_map['level'] = x['level'];
	ret_map['resno'] = x['resno'];
	ret_map['restus'] = x['restus'];
	
	if(ret_map['imgurl']) {
		if(!ret_map['imgurl'].toString().startsWith('http')) {
			ret_map['imgurl'] = 'http://pe1s.static.pdr365.com/' + ret_map['imgurl'];
		}
	} else {
		ret_map['imgurl'] = 'http://pe1s.static.pdr365.com/huacai/51141.jpg';
	}
	retobj.add(ret_map);
}

return GeneralPub.success(retobj);
