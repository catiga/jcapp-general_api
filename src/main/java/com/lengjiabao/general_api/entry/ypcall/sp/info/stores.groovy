package com.lengjiabao.general_api.entry.ypcall.sp.info

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def sp_id = JC.request.param('sp_id');

SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'reserve', '/sp/stores', [pid:1,sp_id:sp_id]);

if(!ret_obj.available){
	return GeneralPub.comfail(ret_obj.messages[0]);
}

def retobj = [];
for(x in ret_obj.data) {
	def ret_map = [address:x['address'],city:x['city'],cityNo:x['cityNo'],id:x['id']];
	ret_map['introduction'] = x['introduction'];
	ret_map['logo'] = x['store_logo'];
	ret_map['name'] = x['store_name'];
	
	ret_map['no'] = x['store_no'];
	ret_map['phone'] = x['phone'];
	ret_map['province'] = x['province'];
	ret_map['provinceNo'] = x['province_no'];
	ret_map['zone'] = x['zone'];
	ret_map['zoneNo'] = x['zone_no'];
	retobj.add(ret_map);
}

return GeneralPub.success(retobj);
