package com.lengjiabao.general_api.entry.h5.wx

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.lengjiabao.general_api.ready.Sha1
import com.lengjiabao.general_api.ready.dto.ProjectFrontConfig
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.util.MD5Util
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

import groovy.json.JsonSlurper

JCLogger logger = LoggerSource.getLogger();

def cu = JC.request.param('cu');

def ret_data = JC.internal.call('project', '/incall/project_by_id', [pid:GlobalHolder.pid]);

ret_data = JackSonBeanMapper.jsonToMap(ret_data);

def schema = JC.request.get().getSchema();
def domain = schema + ret_data['domain'];

cu = domain + cu;

//获取微信公众号
ProjectFrontConfig supp_config = JC.internal.call(ProjectFrontConfig, 'project', '/incall/frontconfig', [app_type:'20',pid:GlobalHolder.pid]);
if(supp_config==null||supp_config.app_id==null) {
	//以后统一的返回格式
	return GeneralPub.fail('pay_config_error', '系统配置错误，请稍后再试', null);
}

def app_id = supp_config.app_id;
def app_key = supp_config.app_key;
//获取access_token
def _access_token_ = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APP_ID}&secret={APP_KEY}";
_access_token_ = _access_token_.replace("{APP_ID}", app_id);
_access_token_ = _access_token_.replace("{APP_KEY}", app_key);
def json = JC.remote.http_call(_access_token_, null);

def jsonSlurper = new JsonSlurper();

//获取到的是Map对象
def map = jsonSlurper.parseText(json)
if(!map['access_token']) {
	return GeneralPub.fail('wx_remote_error', '微信获取access_token失败', null);
}

def _jsapi_ticket_ = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={ACCESS_TOKEN}&type=jsapi";
_jsapi_ticket_ = _jsapi_ticket_.replace('{ACCESS_TOKEN}', map['access_token']);
json = JC.remote.http_call(_jsapi_ticket_, null);

map = jsonSlurper.parseText(json);
logger.info('test======' + JackSonBeanMapper.mapToJson(map));
if(map['errcode']!='0'&&map['errcode']!=0) {
	return GeneralPub.fail('wx_remote_error:' + map['errcode'], '微信获取jsticket失败', null);
}

def jsapi_ticket = map['ticket'];
def noncestr = MD5Util.getMD5(new Random().nextFloat() + "");
def timestamp = System.currentTimeMillis()/1000 + "";
def url = cu;

def original_str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
sign_str = Sha1.getSHA1(original_str);

ret_data = [:];
ret_data['appid'] = app_id;
ret_data['timestamp'] = timestamp;
ret_data['noncestr'] = noncestr;
ret_data['sign_str'] = sign_str;

return GeneralPub.success(ret_data);
