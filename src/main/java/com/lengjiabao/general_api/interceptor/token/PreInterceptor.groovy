package com.lengjiabao.general_api.interceptor.token

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResultSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.AvailabilityStatus
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder

@urlmapped(["/api/auth", '/ypcall/account', '/ex/call/auth'])
@urlpassed('/ypcall/account/token/get')

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
Logger.info("cookie :" + token)

if (!token) {
	token = JC.request.param('token');
	Logger.info("request :" + token)
	if(!token) {
		ResultSource.setResult(result.setData(AvailabilityStatus.notAvailable("user_no_login")));
		return result.setData(AvailabilityStatus.notAvailable("user_no_login"));
	}
}
Logger.info("cookieemd :" + token)
def pid = GlobalHolder.pid;

SimpleAjax simpleAjax = JC.internal.call(SimpleAjax, 'crm', '/h5/p/info', [token:token, pid:pid]);

if (!simpleAjax.available ) {
	result.setData(AvailabilityStatus.notAvailable("user_no_login"));
	ResultSource.setResult(result);
	return result;
}
if (simpleAjax.data == null ) {
	ResultSource.setResult(result.setData(AvailabilityStatus.notAvailable("user_no_login")));
	return result.setData(AvailabilityStatus.notAvailable("user_no_login"));
}
GlobalHolder.setToken(token);
request.setAttribute("_user_", simpleAjax.data);
return true;



