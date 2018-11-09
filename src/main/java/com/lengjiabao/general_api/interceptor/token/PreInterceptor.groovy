package com.lengjiabao.general_api.interceptor.token

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.app.sdk.source.ResultSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.AvailabilityStatus
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper

//@urlmapped("/api/auth")
@urlmapped(["/api/auth", '/ypcall/account'])

JCRequest request = RequestSource.getRequest();

JCLogger Logger =  LoggerSource.getLogger(this.getClass().getName())

Result result = new Result();

def token = null;
JCCookie[] cookies = JC.request.get().getCookies();
if(cookies!=null&&cookies.length>0) {
	for(JCCookie c : cookies) {
		if(c.getName().equals("_lac_k_")) {
			try {
				token = URLDecoder.decode(c.getValue(), "utf-8");
			}catch(Exception e){
			}
			break;
		}
	}
}

if (!token) {
	token = JC.request.param('token');
	if(!token) {
		ResultSource.setResult(result.setData(AvailabilityStatus.notAvailable("user_no_login")));
		return false;
	}
}
def pid = GlobalHolder.pid;

SimpleAjax simpleAjax = JC.internal.call(SimpleAjax, 'crm', '/h5/p/info', [token:token, pid:pid]);

if (!simpleAjax.available ) {
	result.setData(AvailabilityStatus.notAvailable("user_no_login"));
	ResultSource.setResult(result);
	return false;
}
if (simpleAjax.data == null ) {
	ResultSource.setResult(result.setData(AvailabilityStatus.notAvailable("user_no_login")));
	return false;
}
GlobalHolder.setToken(token);
request.setAttribute("_user_", simpleAjax.data);
return true;



