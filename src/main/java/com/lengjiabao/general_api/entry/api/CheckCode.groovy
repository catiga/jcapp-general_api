package com.lengjiabao.general_api.entry.api

import javax.servlet.http.Cookie

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax



def domain = JC.request.get().getServerName();
def code = JC.request.param('code')?.trim();
def type = JC.request.param('type')?.trim();
if(!type) {
	type = 'account';
}

SimpleAjax ret = JC.internal.call(SimpleAjax, 'crm', '/h5/p/info_by_code', [domain:domain, code:code]);

if(!ret.available) {
	Result view = new Result();
	view.setData(ret.messages);
	return view;
}
def account_info = ret.data;

//_lac_ated_
Cookie _lac_ated_ = new Cookie('_lac_ated_', type);
_lac_ated_.setPath('/');
ResponseSource.getResponse().addCookie(new JCCookie(_lac_ated_))

Cookie _op_cookie_ = new Cookie('_lac_op_', account_info['part_id']);
_op_cookie_.setPath('/');
ResponseSource.getResponse().addCookie(new JCCookie(_op_cookie_))

Result result = new Result();

if(!account_info['token']) {
	if(type=='account') {
		//说明需要绑定手机号码
		result.setData(SimpleAjax.notAvailable('bind_mobile'));
	} else {
		result.setData(SimpleAjax.available());	//继续业务逻辑
	}
} else {
	Cookie cookie = new Cookie('_lac_k_', account_info['token']);
	cookie.setPath('/');
	ResponseSource.getResponse().addCookie(new JCCookie(cookie))
	result.setData(SimpleAjax.available());	//继续业务逻辑
}

return result;


