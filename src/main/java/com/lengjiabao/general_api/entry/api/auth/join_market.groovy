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

def tnum = JC.request.param('tnum');
def order_no = JC.request.param('order_no');
def mrid = JC.request.param('mrid');

JCLogger logger = JCLoggerFactory.getLogger('join_market');
logger.info('tnum=' + tnum);
logger.info('order_no=' + order_no);
logger.info('mrid=' + mrid);

SimpleAjax ret = JC.internal.call(SimpleAjax, 'market', '/market/judge_market', [mobile:mobile,pid:pid, ap_id:ap_id, market_rule_id:mrid, tnum:tnum, order_no:order_no]);
if(!ret.available) {
	def err_code = ret.messages[0];
	def err_msg = ret.messages[1];
	return GeneralPub.fail(err_code, err_msg, null);
}

return GeneralPub.success();
