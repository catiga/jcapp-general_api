package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource



//处理接口

//Result result = new Result();
//List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//JCRequest reques = RequestSource.getRequest();
//def cinema_id = reques.getParameter("cinema_id");
//
//CommunicationParam param1 = new CommunicationParam("cinema_id", cinema_id);
//
//params.add(param1);
//CommunicationPower caller = CommunicationSource.getCommunicator("ticketingsys");
//def aaaa = caller.doworkAsString("/api/movies", params);

def cinema_id = JC.request.param('cinema_id');

def aaaa = JC.internal.call('ticketingsys', '/api/movies', [cinema_id:cinema_id]);



try {
	aaaa = JackSonBeanMapper.jsonToMap(aaaa);
	def aaaa_data = aaaa['data'];
	Iterator it = aaaa_data.iterator();
	
	while(it.hasNext()) {
		def it_name = it.next()['name'].toString();
		/*
		if(it_name.indexOf('盲道')<0&&it_name.indexOf('拯救大明星')<0&&it_name.indexOf('十月阳春')<0&&it_name.indexOf('夏洛特烦恼')<0) {
			it.remove();
		}
		*/
	}
	aaaa['data'] = aaaa_data;
}catch(any) {
	any.printStackTrace();
}





Result result = new Result();
return result.setData(aaaa);


