package com.lengjiabao.general_api.interceptor.wxaccount

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResultSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub


@urlmapped(['/ypcall/account', '/h5/wx/card/create_cb'])
@urlpassed('/ypcall/account/token/get')

JCLogger Logger =  LoggerSource.getLogger(this.getClass().getName())
Result result = new Result();

Logger.info(JC.request.get().getRequestURI() + ':entry interceptor');
try {
	def token = JC.request.param("token");
	if (token == null || token.equals("")) {
		ResultSource.setResult(result.setData(GeneralPub.comfail("user_no_login")));
		return false;
	} 

	def ret = JC.internal.call("crm", "/h5/p/info", ['token':token,'pid':GlobalHolder.pid]);
	SimpleAjax simpleAjax = JackSonBeanMapper.fromJson(ret, SimpleAjax.class);
	if (!simpleAjax.available ) {
		result.setData(GeneralPub.comfail("user_no_login"));
		ResultSource.setResult(result);
		return result;
	}
	if (simpleAjax.data == null ) {
		ResultSource.setResult(result.setData(GeneralPub.comfail("user_no_login")));
		return result;
	}
	JC.request.get().setAttribute("_user_", simpleAjax.data);
//	AccountInfo  accountInfo = request.getAttribute("_user_")
	return true;
} catch (Exception e) {
	Logger.error("",e);
	ResultSource.setResult(result.setData(GeneralPub.comfail("user_no_login")));
	return result.setData(GeneralPub.comfail("user_no_login"));
}
