package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.lengjiabao.general_api.ready.common.SimpleAjax

JCLogger logger = JCLoggerFactory.getLogger('q_markets');

def  accountInfo = JC.request.attr("_user_");//获取用户信息
def mobile = accountInfo['mobile'];//获取用户信息
def pid = accountInfo['pid'];//获取用户信息
def ap_id = accountInfo['ap_id'];

def order_id = JC.request.param('order_id');
def order_oc = JC.request.param('oc');

SimpleAjax ret = JC.internal.call(SimpleAjax, 'market', '/market/get_tcss_by_order', [mobile:mobile,pid:pid, ap_id:ap_id, order_no:order_id, order_oc:order_oc]);

//增加购票规则描述
if(ret && ret.available) {
	for(x in ret.data) {
		def number_policy = x['number_policy'];			//2,m,
		def price_policy = x['price_policy'];			//20/2d:15;20/3d:25
		
		if(number_policy) {
			def policy_str = '';
			try {
				def arr_num_pol = number_policy.split(',');
				def total_num = arr_num_pol[0];
				def total_pin = '活动内';
				if(arr_num_pol[1]=='m') {
					total_pin = '每月';
				} else if(arr_num_pol[1]=='w') {
					total_pin = '每周';
				} else if(arr_num_pol[1]=='d') {
					total_pin = '每天';
				}
				policy_str = total_pin + '限购' + arr_num_pol;
			} catch(any) {
			}
			x['policy_str'] = policy_str;
		}
	}
}
return ret;
