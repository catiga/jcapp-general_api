package com.lengjiabao.general_api.entry.ex.call.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

JCLogger logger = LoggerSource.getLogger();

def token = JC.request.param("token");

def accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息

if(accountInfo==null) {
	return GeneralPub.fail('user_not_login', '请登录', null);
}

def ap_id = accountInfo['ap_id'];//获取用户信息

logger.info('query ticket op : ap_id=' + ap_id);

def pid = GlobalHolder.pid;
def aaaa = JC.internal.call('ticketingsys', '/api/orders', ['apid':ap_id,'token':token, 'pid':pid]);

def ret_data = [];
try {
	aaaa = JackSonBeanMapper.jsonToMap(aaaa);
	ret_data = aaaa['data'];
} catch(any) {}

return GeneralPub.success(ret_data);


