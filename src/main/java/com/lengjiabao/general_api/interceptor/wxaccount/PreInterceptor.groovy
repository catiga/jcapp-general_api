package com.lengjiabao.general_api.interceptor.wxaccount

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResultSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub


@urlmapped(['/ypcall/account'])
@urlpassed('/ypcall/account/token/get')
JCRequest request = RequestSource.getRequest();
JCLogger Logger =  LoggerSource.getLogger(this.getClass().getName())
Result result = new Result();
try {
	def token = request.getParameter("token");
	if (token == null || token.equals("")) {
		ResultSource.setResult(result.setData(GeneralPub.comfail("user_no_login")));
		return false;
	} 
	
	String point = "crm"; 
	String address = "/h5/p/info"
	List<CommunicationParam> params = new ArrayList<CommunicationParam>();
	params.add(new CommunicationParam("token", token));
	def ret = CommunicationSource.getCommunicator(point).doworkAsString( address, params);
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
	request.setAttribute("_user_", simpleAjax.data);
//	AccountInfo  accountInfo = request.getAttribute("_user_")
	return true;
} catch (Exception e) {
	Logger.error("",e);
	ResultSource.setResult(result.setData(GeneralPub.comfail("user_no_login")));
	return result.setData(GeneralPub.comfail("user_no_login"));
}
