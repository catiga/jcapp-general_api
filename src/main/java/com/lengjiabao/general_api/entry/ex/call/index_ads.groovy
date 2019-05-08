package com.lengjiabao.general_api.entry.ex.call

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def retobj = [];

JCLogger logger = LoggerSource.getLogger();

def pid = GlobalHolder.pid;
logger.info('h5 pid=' + pid);

SimpleAjax result = JC.internal.call(SimpleAjax, 'market', '/figure/get_h5_figure', [pid:pid]);

if(result&&result.available&&result.data) {
	for(x in result.data) {
		def ad_map = [:];
		//ad_map['disType'] = x['figure_type'];
		ad_map['disType'] = '100001';
		ad_map['title'] = x['info'];
		//前缀
		def domain = JC.request.get().getSchema();
		domain += JC.request.get().getServerName() + '/img_server/';
		domain += x['picture'];
		
		ad_map['imgUrl'] = domain;
		ad_map['clickUrl'] = x['url'];
		ad_map['id'] = x['id'];
		retobj.add(ad_map);
	}
}

return GeneralPub.success(retobj);

