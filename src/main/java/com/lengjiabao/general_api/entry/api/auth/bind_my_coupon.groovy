package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder

JCLogger logger = JCLoggerFactory.getLogger("");
// 绑定优惠券
def coupon_code = JC.request.param("coupon_code");
def pid = GlobalHolder.pid.toString();

def accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
if(accountInfo==null) {
	return SimpleAjax.notAvailable('user_not_login,请先登录');
}
def ap_id = accountInfo['ap_id'];//获取用户信息
def mobile = accountInfo['mobile'] == null ? "" : accountInfo['mobile'];

logger.info("bind_my_coupon: {}", coupon_code);

SimpleAjax ajax =  JC.internal.call(SimpleAjax,"market", "/coupon/add_code", [pid:pid,ap_id:ap_id,mobile:mobile, coupon_code:coupon_code]);
return ajax;