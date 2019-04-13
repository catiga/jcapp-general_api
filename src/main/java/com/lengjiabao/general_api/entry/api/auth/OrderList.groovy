package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result

String point = "scm";  //访问的是哪个app的名称
String address = "/incall/h5/list_order"

def ds = JC.request.param("ds");
def oss = JC.request.param("oss");
def token = JC.request.param("token");

def  accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
def ap_id = accountInfo['ap_id'];//获取用户信息

def ret = JC.internal.call('scm', '/incall/h5/list_order', [apid:ap_id,ds:ds,oss:oss,token:token]);

Result result = new Result();
result.setData(ret);

return result;
