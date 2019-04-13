package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.result.Result
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource


import com.lengjiabao.general_api.ready.dto.AccountInfo //获取用户信息
import com.lengjiabao.general_api.ready.util.GlobalHolder

String point = "scm";  //访问的是哪个app的名称
String address = "/incall/h5/create_order"

def gs = JC.request.param("gs");
def token = JC.request.param("token");
def sid = JC.request.param("sid");
def tbn = JC.request.param("tbn");
def dss = JC.request.param("dss");

def  accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
def ap_id = accountInfo['ap_id'];//获取用户信息

def pid = GlobalHolder.pid;

def ret = JC.internal.call('scm', '/incall/h5/create_order', [apid:ap_id,gs:gs,sid:sid,tbn:tbn,dss:dss,pid:pid]);
Result result = new Result();
result.setData(ret);
return result;


