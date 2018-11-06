package com.lengjiabao.general_api.ready.util

import java.util.List

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower

class NativeUtil {

	public static <T> T connect(Class<T> mapclass, def point, def address, def param_dic) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		if(param_dic) {
			for(kv in param_dic) {
				CommunicationParam param = new CommunicationParam(kv.key, kv.value);
				params.add(param);
			}
		}
		CommunicationPower systemCaller = CommunicationSource.getCommunicatorNative(point);
		def ret = systemCaller.doworkAsString(address, params);
		return JackSonBeanMapper.fromJson(ret, mapclass);;
	}
	
	public static String connect( def point, def address, def param_dic) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		if(param_dic) {
			for(kv in param_dic) {
				CommunicationParam param = new CommunicationParam(kv.key, kv.value);
				params.add(param);
			}
		}
		CommunicationPower systemCaller = CommunicationSource.getCommunicatorNative(point);
		return systemCaller.doworkAsString(address, params);
	}
	
	public static <T> T connectAsArray(Class<T> mapclass, def point, def address, def param_dic) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		if(param_dic) {
			for(kv in param_dic) {
				CommunicationParam param = new CommunicationParam(kv.key, kv.value);
				params.add(param);
			}
		}
		CommunicationPower systemCaller = CommunicationSource.getCommunicatorNative(point);
		def ret = systemCaller.doworkAsString(address, params);
		T obj = JackSonBeanMapper.jsonToList(ret,mapclass);
		return obj;
	}
}
