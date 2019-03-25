package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower

import com.jeancoder.core.result.Result

import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource

String point = "scm";  //访问的是哪个app的名称
//String address = "/incall/cashier/catalog";  //访问的是哪个app路径
String address = "/incall/h5/create_order"
List<CommunicationParam> params = new ArrayList<CommunicationParam>();  // == def params = [];

 JCRequest reques = RequestSource.getRequest();
 def gs = reques.getParameter("gs");
 def token = reques.getParameter("token");
 def sid = reques.getParameter("sid");
 def tbn = reques.getParameter("tbn");

CommunicationParam param1 = new CommunicationParam("gs", gs);
CommunicationParam param2 = new CommunicationParam("sid", sid);
CommunicationParam param3 = new CommunicationParam("token", token);
CommunicationParam param4 = new CommunicationParam("tbn", tbn);

params.add(param1);
params.add(param2);
params.add(param3);
params.add(param4);


CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
def ret = systemCaller.doworkAsString( address, params); 

Result result = new Result();
result.setData(ret);

return result;
