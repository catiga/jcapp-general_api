package com.lengjiabao.general_api.entry.h5.wx

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax

//授权类型
/**
 * base:
 * account:
 */
JCLogger  Logger = LoggerSource.getLogger();
def type = JC.request.param('type');
def bu = JC.request.param('bu'); 
if(!type) {
	type = 'account';	//默认授权类型
}
def domain = JC.request.get().getServerName();
// 取 微信公众号 类型 配置
SimpleAjax supp_config = JC.internal.call(SimpleAjax, 'project', '/incall/frontconfig_by_domain', [domain:domain, app_type:'20']);
if(!supp_config.available) {
	return supp_config;
}
def app_id = supp_config.data['app_id'];
def app_secret = supp_config.data['app_key'];
def schema = JC.request.get().getSchema();
def back_url = schema + domain + '/general_api/h5/wx/check_code?type=' + type;
Logger.info("back_url_" +back_url)
if(bu) {
	back_url = back_url + '&bu=' + bu;
}

println 'auth get back url===' + back_url;

println app_id + '======' + app_secret;

back_url = URLEncoder.encode(back_url, "UTF-8");

def scope = 'snsapi_userinfo';
def redirect_url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + app_id + '&redirect_uri=' + back_url + '&response_type=code&scope=' + scope + '&state=STATE#wechat_redirect';
Logger.info("redirect_url_" +redirect_url)
//ResponseSource.getResponse().sendRedirect(redirect_url);
return new Result().setRedirectResource(redirect_url);




