package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder

JCLogger logger = LoggerSource.getLogger();

Result result = new Result();

def token = JC.request.param("token");

def accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息

if(accountInfo==null) {
	result.setData(SimpleAjax.notAvailable('token_invalid,请登录'));
	return result;
}

def ap_id = accountInfo['ap_id'];//获取用户信息

logger.info('query ticket op : ap_id=' + ap_id);

def pid = GlobalHolder.pid;
def aaaa = JC.internal.call('ticketingsys', '/api/orders', ['apid':ap_id,'token':token, 'pid':pid]);

return result.setData(aaaa);


