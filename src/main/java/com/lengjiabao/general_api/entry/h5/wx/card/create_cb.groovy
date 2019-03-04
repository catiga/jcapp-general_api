package com.lengjiabao.general_api.entry.h5.wx.card

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.dto.ProjectFrontConfig
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

import groovy.json.JsonSlurper

def _create_ticket_ = "https://api.weixin.qq.com/card/create?access_token={ACCESS_TOKEN}";
def _access_token_ = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APP_ID}&secret={APP_KEY}";

//获取微信公众号
ProjectFrontConfig supp_config = JC.internal.call(ProjectFrontConfig, 'project', '/incall/frontconfig', [app_type:'20',pid:GlobalHolder.pid]);
if(supp_config==null||supp_config.app_id==null) {
	//以后统一的返回格式
	return GeneralPub.fail('pay_config_error', '系统配置错误，请稍后再试', null);
}

def app_id = supp_config.app_id;
def app_key = supp_config.app_key;
//获取access_token
_access_token_ = _access_token_.replace("{APP_ID}", app_id);
_access_token_ = _access_token_.replace("{APP_KEY}", app_key);
def json = JC.remote.http_call(_access_token_, null);

def jsonSlurper = new JsonSlurper();

//获取到的是Map对象
def map = jsonSlurper.parseText(json)
if(!map['access_token']) {
	return GeneralPub.fail('wx_remote_error', '微信获取access_token失败', null);
}