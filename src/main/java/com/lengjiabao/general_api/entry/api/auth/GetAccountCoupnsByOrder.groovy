package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper

JCRequest reques = RequestSource.getRequest();
def accountInfo = reques.getAttribute("_user_");//获取用户信息
def mobile = accountInfo['mobile'];//获取用户信息
def pid = accountInfo['pid'];//获取用户信息
def ap_id = accountInfo['ap_id'];

def order_id = JC.request.param('order_id');
def order_oc = JC.request.param('oc');

def ret = JC.internal.call('market', '/coupon/get_available_coupon_list', [mobile:mobile,pid:pid, ap_id:ap_id]);
ret = JackSonBeanMapper.jsonToMap(ret);

def ret_code = ret['code'];
def ret_msg = ret['msg'];
def ret_list = ret['list'];

def ret_list_new = [];
if(ret_code=='0') {
	//正常状态
	if(ret_list) {
		for(x in ret_list) {
			def crapp = x['crapp'];
			if(x['validate_end']==0) {
				x['validate_end'] = '2099-12-31'
			}
			if(crapp==order_oc) {
				ret_list_new.add(x);
			}
		}
	}
}
ret['list'] = ret_list_new;

return new Result().setData(ret);



