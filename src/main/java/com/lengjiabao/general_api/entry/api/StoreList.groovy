package com.lengjiabao.general_api.entry.api

import java.util.Date

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource

def pid = GlobalHolder.getPid();

def city_no = JC.request.param('city_no');
def aaaa = JC.internal.call('ticketingsys', '/api/city_cinemas', [city_no:city_no,pid:pid])
println aaaa;
def aaaa_array = null;
try {
	aaaa_array = JackSonBeanMapper.jsonToMap(aaaa);
	if(aaaa_array['code']==0) {
		aaaa_array = aaaa_array['data'];
	}
} catch(any) {
	any.printStackTrace();
}


//开始获取系统真实门店
def sys_stores_string = JC.internal.call('project', '/incall/stores', [city_no:city_no]);
def sys_stores = null;
try {
	sys_stores = JackSonBeanMapper.jsonToList(sys_stores_string);
} catch(any) {
}
def ret_stores = [];
if(sys_stores) {
	for(x in sys_stores) {
		def store_model = [:];
		store_model['id'] = x['id'];
		store_model['store_basic'] = x['id'];
		store_model['store_name'] = x['store_name'];
		store_model['store_no'] = x['store_no'];
		store_model['province'] = x['province'];
		store_model['city'] = x['city'];
		store_model['zone'] = x['zone'];
		store_model['address'] = x['address'];
		store_model['province_no'] = x['province_no'];
		store_model['city_no'] = x['city_no'];
		store_model['zone_no'] = x['zone_no'];
		store_model['physics_name'] = x['store_name'];
		store_model['c_time'] = x['c_time'];
		store_model['proj_id'] = x['proj_id'];
		store_model['latitude'] = x['latitude'];
		store_model['longitude'] = x['longitude'];
		
		if(aaaa_array) {
			for(y in aaaa_array) {
				if(x['id']==y['store_basic']) {
					store_model['cinema_id'] = y['id'];
					store_model['config_id'] = y['config_id'];
					store_model['systemInfo'] = y['systemInfo'];
					break;
				}
			}
		}
		if(!store_model['cinema_id']) {
			continue;
		}
		ret_stores.add(store_model);
	}
}

def ret_data = [:];
ret_data['code'] = 0;
ret_data['data'] = ret_stores;
Result result = new Result();
return result.setData(ret_data);




