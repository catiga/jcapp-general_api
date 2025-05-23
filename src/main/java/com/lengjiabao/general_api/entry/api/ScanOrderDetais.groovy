package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower

import com.jeancoder.core.result.Result

import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource

import com.lengjiabao.general_api.ready.dto.AccountInfo //获取用户信息





String point = "scm";  //访问的是哪个app的名称
//String address = "/incall/cashier/catalog";  //访问的是哪个app路径
String address = "/incall/h5/view_order"
List<CommunicationParam> params = new ArrayList<CommunicationParam>();  // == def params = [];

 JCRequest reques = RequestSource.getRequest();
 def on = reques.getParameter("on");
 def token = reques.getParameter("token");

 AccountInfo  accountInfo = reques.getAttribute("_user_");//获取用户信息
def ap_id = accountInfo.ap_id;//获取用户信息

CommunicationParam param1 = new CommunicationParam("on", on);
CommunicationParam param2 = new CommunicationParam("token", token);
CommunicationParam param3 = new CommunicationParam("apid", ap_id);//获取用户信息

params.add(param1);
params.add(param2);
params.add(param3);


CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
def ret = systemCaller.doworkAsString( address, params); 

// println ret  //输入ret到日志

// JCRequest reques = RequestSource.getRequest();
// def name = reques.getParameter("name");


Result result = new Result();
result.setData(ret);


return result;