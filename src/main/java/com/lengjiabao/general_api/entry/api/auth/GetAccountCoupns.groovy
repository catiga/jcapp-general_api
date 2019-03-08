package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper

JCLogger logger = LoggerSource.getLogger();
String point = "market";
String address = "/coupon/get_available_coupon_list";
List<CommunicationParam> params = new ArrayList<CommunicationParam>();

JCRequest reques = RequestSource.getRequest();
def  accountInfo = reques.getAttribute("_user_");//获取用户信息
def mobile = accountInfo['mobile'];//获取用户信息
def pid = accountInfo['pid'];//获取用户信息

CommunicationParam param1 = new CommunicationParam("mobile", mobile);//获取用户信息
CommunicationParam param2 = new CommunicationParam("pid", pid);//获取用户信息

params.add(param1);
params.add(param2);

CommunicationPower systemCaller = CommunicationSource.getCommunicatorNative(point);
def ret = systemCaller.doworkAsString(address, params);
logger.info('get account coupons:' + ret);
ret = JackSonBeanMapper.jsonToMap(ret);

def ret_code = ret['code'];
def ret_msg = ret['msg'];
def ret_list = ret['list'];

def ret_list_new = [];
if(ret_code=='0') {
	//正常状态
	if(ret_list) {
		for(x in ret_list) {
			if(x['validate_end']==0) {
				x['validate_end'] = '2099-12-31'
			}
			ret_list_new.add(x);
		}
	}
}
ret['list'] = ret_list_new;

return new Result().setData(ret);
