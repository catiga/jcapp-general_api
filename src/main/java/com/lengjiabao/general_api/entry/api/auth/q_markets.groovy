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

return ret;
