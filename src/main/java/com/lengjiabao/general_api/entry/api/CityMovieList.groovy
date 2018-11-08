package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource


def datas = '''
[{
	"date": "2018-11-09",
	"aliasName": "今天",
	"plans": [{
		"cinemaId": "1",
		"planId": "1111201811090037",
		"startTime": "2018-11-09 13:00:00",
		"endTime": "2018-11-09 14:50:00",
		"subtitle": null,
		"language": "国语",
		"format": null,
		"hallId": "0000000000000002",
		"hall": null,
		"dimensional": "普通",
		"size": null,
		"lastUpdateTime": null,
		"planDate": "2018-11-09",
		"buy_flag": 1,
		"standard_price": "4000",
		"current_price": "4000",
		"schemas": [],
		"startClock": "13:00",
		"endClock": "14:50"
	}, {
		"cinemaId": "1",
		"planId": "1111201811090036",
		"startTime": "2018-11-09 15:00:00",
		"endTime": "2018-11-09 16:50:00",
		"subtitle": null,
		"language": "国语",
		"format": null,
		"hallId": "0000000000000002",
		"hall": null,
		"dimensional": "普通",
		"size": null,
		"lastUpdateTime": null,
		"planDate": "2018-11-09",
		"buy_flag": 1,
		"standard_price": "4000",
		"current_price": "4000",
		"schemas": [],
		"startClock": "15:00",
		"endClock": "16:50"
	}]
},
{
	"date": "2018-11-08",
	"aliasName": "今天",
	"plans": [{
		"cinemaId": "1",
		"planId": "1111201811090037",
		"startTime": "2018-11-09 13:00:00",
		"endTime": "2018-11-09 14:50:00",
		"subtitle": null,
		"language": "国语",
		"format": null,
		"hallId": "0000000000000002",
		"hall": null,
		"dimensional": "普通",
		"size": null,
		"lastUpdateTime": null,
		"planDate": "2018-11-09",
		"buy_flag": 1,
		"standard_price": "4000",
		"current_price": "4000",
		"schemas": [],
		"startClock": "13:00",
		"endClock": "14:50"
	}, {
		"cinemaId": "1",
		"planId": "1111201811090036",
		"startTime": "2018-11-09 15:00:00",
		"endTime": "2018-11-09 16:50:00",
		"subtitle": null,
		"language": "国语",
		"format": null,
		"hallId": "0000000000000002",
		"hall": null,
		"dimensional": "普通",
		"size": null,
		"lastUpdateTime": null,
		"planDate": "2018-11-09",
		"buy_flag": 1,
		"standard_price": "4000",
		"current_price": "4000",
		"schemas": [],
		"startClock": "15:00",
		"endClock": "16:50"
	}, {
		"cinemaId": "1",
		"planId": "1111201811090035",
		"startTime": "2018-11-09 17:00:00",
		"endTime": "2018-11-09 18:50:00",
		"subtitle": null,
		"language": "国语",
		"format": null,
		"hallId": "0000000000000002",
		"hall": null,
		"dimensional": "普通",
		"size": null,
		"lastUpdateTime": null,
		"planDate": "2018-11-09",
		"buy_flag": 1,
		"standard_price": "4000",
		"current_price": "4000",
		"schemas": [],
		"startClock": "17:00",
		"endClock": "18:50"
	}, {
		"cinemaId": "1",
		"planId": "1111201811090032",
		"startTime": "2018-11-09 19:00:00",
		"endTime": "2018-11-09 20:50:00",
		"subtitle": null,
		"language": "国语",
		"format": null,
		"hallId": "0000000000000002",
		"hall": null,
		"dimensional": "普通",
		"size": null,
		"lastUpdateTime": null,
		"planDate": "2018-11-09",
		"buy_flag": 1,
		"standard_price": "4000",
		"current_price": "4000",
		"schemas": [],
		"startClock": "19:00",
		"endClock": "20:50"
	}, {
		"cinemaId": "1",
		"planId": "1111201811090031",
		"startTime": "2018-11-09 09:40:00",
		"endTime": "2018-11-09 11:30:00",
		"subtitle": null,
		"language": "国语",
		"format": null,
		"hallId": "0000000000000003",
		"hall": null,
		"dimensional": "普通",
		"size": null,
		"lastUpdateTime": null,
		"planDate": "2018-11-09",
		"buy_flag": 1,
		"standard_price": "4000",
		"current_price": "4000",
		"schemas": [],
		"startClock": "09:40",
		"endClock": "11:30"
	}]
}
]
''';

def sort_plans(def now_plans, def key) {
	def date_sort_result = new LinkedList();
	for(x in now_plans) {
		if(!date_sort_result) {
			println 'first_add=' + x[key] + ', and data=' + JackSonBeanMapper.toJson(x);
			date_sort_result.add(x);
			continue;
		}
		def last_one = date_sort_result.getLast();
		
		if(last_one[key]<=x[key]) {
			println 'first_add=' + x[key] + ', and data=' + JackSonBeanMapper.toJson(x);
			date_sort_result.add(x);
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

//datas = JackSonBeanMapper.jsonToList(datas);
//datas = sort_plans(datas, 'date');
//println JackSonBeanMapper.toJson(datas);
//return;

def cinema_id = JC.request.param('cinema_id');
def aaaa = JC.internal.call('ticketingsys', '/api/movies', [cinema_id:cinema_id]);

try {
	aaaa = JackSonBeanMapper.jsonToMap(aaaa);
	def aaaa_data = aaaa['data'];
	println aaaa_data;
	Iterator it = aaaa_data.iterator();
	//针对结果日期进行排序
	while(it.hasNext()) {
		//def it_name = it.next()['name'].toString();
		def it_next = it.next();
		def date_list = it_next['dates'];
		
//		for(x in date_list) {
//			x['plans'] = sort_plans(x['plans'], 'startTime');
//		}
		it_next['dates'] = sort_plans(date_list, 'date');
	}
	//aaaa['data'] = aaaa_data;
}catch(any) {
	any.printStackTrace();
}

Result result = new Result();
return result.setData(aaaa);
