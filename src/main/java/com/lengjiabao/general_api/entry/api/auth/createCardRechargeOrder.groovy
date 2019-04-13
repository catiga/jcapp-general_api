package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.GlobalHolder

//处理接口
Result result = new Result();

def h_id = JC.request.param("h_id");
def mc_pwd = JC.request.param("mc_pwd");
def sid = JC.request.param("sid");
def  accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息

def ap_id = accountInfo['ap_id'];//获取用户信息
def mobile = accountInfo['mobile'];//获取用户手机号

def aaaa = JC.internal.call('crm', "/h5/user/create_card_recharge_order", [h_id:h_id, mc_pwd:mc_pwd, apid:ap_id, mobile:mobile, sid:sid, pid:GlobalHolder.pid]);

return result.setData(aaaa);
