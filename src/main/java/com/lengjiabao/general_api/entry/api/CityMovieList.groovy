package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource

def sort_plans(def now_plans, def key) {
	def date_sort_result = new LinkedList();
	for(x in now_plans) {
		if(!date_sort_result) {
			date_sort_result += x;
			continue;
		}
		def last_one = date_sort_result.getLast();
		def first_one = date_sort_result.getFirst();
		
		if(last_one[key]<=x[key]) {
			date_sort_result += x;
		} else {
			def sort_index = 0;
			for(y in date_sort_result) {
				if(x[key]>=y[key]) {
					sort_index++;
				} else {
					date_sort_result.add(sort_index, x);
					break;
				}
			}
		}
	}
	return date_sort_result;
}

def cinema_id = JC.request.param('cinema_id');
def aaaa = JC.internal.call('ticketingsys', '/api/movies', [cinema_id:cinema_id]);
println 'aaaa=' + aaaa;
try {
	aaaa = JackSonBeanMapper.jsonToMap(aaaa);
	def aaaa_data = aaaa['data'];
	Iterator it = aaaa_data.iterator();
	//针对结果日期进行排序
	while(it.hasNext()) {
		//def it_name = it.next()['name'].toString();
		def it_next = it.next();
		def date_list = it_next['dates'];
		
		for(x in date_list) {
			x['plans'] = sort_plans(x['plans'], 'startTime');
		}
		it_next['dates'] = sort_plans(it_next['dates'], 'date');
	}
	aaaa['data'] = aaaa_data;
}catch(any) {
	any.printStackTrace();
}


Result result = new Result();
return result.setData(aaaa);







