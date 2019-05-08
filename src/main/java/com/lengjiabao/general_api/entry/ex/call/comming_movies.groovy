package com.lengjiabao.general_api.entry.ex.call

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def ret = JC.internal.call('ticketingsys', '/api/comming_list', null);

def ret_data = [];
try {
	ret = JackSonBeanMapper.jsonToMap(ret);
	if(ret['code']!=0) {
		return GeneralPub.fail(ret['code'], ret['msg'], null);
	}
	ret_data = ret['data'];
} catch(any) {
	
}

return GeneralPub.success(ret_data);

