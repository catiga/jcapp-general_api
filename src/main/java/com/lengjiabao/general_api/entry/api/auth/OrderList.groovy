package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower

import com.jeancoder.core.result.Result

import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource

import com.lengjiabao.general_api.ready.dto.AccountInfo //获取用户信息





String point = "scm";  //访问的是哪个app的名称
String address = "/incall/h5/list_order"

def ds = JC.request.param("ds");
def oss = JC.request.param("oss");
def token = JC.request.param("token");

def  accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
def ap_id = accountInfo['ap_id'];//获取用户信息

//List<CommunicationParam> params = new ArrayList<CommunicationParam>();  // == def params = [];
//CommunicationParam param1 = new CommunicationParam("apid", ap_id);
//CommunicationParam param2 = new CommunicationParam("ds", ds);
//CommunicationParam param3 = new CommunicationParam("oss", oss);
//CommunicationParam param4 = new CommunicationParam("token", token);
//params.add(param1);
//params.add(param2);
//params.add(param3);
//params.add(param4);
//CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
//def ret = systemCaller.doworkAsString( address, params); 

def ret = JC.internal.call('scm', '/incall/h5/list_order', [apid:ap_id,ds:ds,oss:oss,token:token]);

Result result = new Result();
result.setData(ret);

return result;
