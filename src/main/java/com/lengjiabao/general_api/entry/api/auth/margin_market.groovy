package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

JCRequest reques = RequestSource.getRequest();
def accountInfo = JC.request.attr("_user_");//获取用户信息
def mobile = accountInfo['mobile'];//获取用户信息
def pid = accountInfo['pid'];//获取用户信息
def ap_id = accountInfo['ap_id'];

def mrid = JC.request.param('mrid');
def market_id = JC.request.param('market_id');

JCLogger logger = JCLoggerFactory.getLogger('margin_market');
logger.info('mrid=' + mrid);

SimpleAjax ret = JC.internal.call(SimpleAjax, 'market', '/market/judge_market', [mobile:mobile,pid:pid, ap_id:ap_id, market_id:market_id, market_rule_id:mrid]);
if(!ret.available) {
	def err_code = ret.messages[0];
	def err_msg = ret.messages[1];
	if(err_code=='repeat_join_forbid') {
		//说明活动次数超限
		
	}
	return GeneralPub.fail(err_code, err_msg, null);
}

return GeneralPub.success('剩余可享优惠次数', ret.data);
