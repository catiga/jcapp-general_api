package com.lengjiabao.general_api.entry.api.auth


import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder

JCLogger logger = LoggerSource.getLogger();
// 绑定会员卡
def   s_id = JC.request.param("s_id");
def   mc_num = JC.request.param("mc_num");
def   mc_pwd = JC.request.param("mc_pwd");
def   pid = GlobalHolder.pid.toString();

def accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
def ap_id = accountInfo['ap_id'];//获取用户信息
def mobile = accountInfo['mobile'];

logger.info("bind_my_card_" + s_id + "_" +mc_num+ "_" +mc_pwd);

SimpleAjax ajax =  JC.internal.call(SimpleAjax,"crm", "h5/user/bind_my_card", [s_id:s_id,mc_num:mc_num,mc_pwd:mc_pwd,ap_id:ap_id,mobile:mobile]);
return ajax;