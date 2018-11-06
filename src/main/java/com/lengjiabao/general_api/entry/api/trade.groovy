package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower

import com.jeancoder.core.result.Result

import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource



String point = "trade";  //访问的是哪个app的名称
//String address = "/incall/cashier/catalog";  //访问的是哪个app路径
String address = "/incall/uni_code_pay"
List<CommunicationParam> params = new ArrayList<CommunicationParam>();  // == def params = [];

JCRequest reques = RequestSource.getRequest();
 def unicode = reques.getParameter("unicode");
 def ct = reques.getParameter("ct");
 def tnum = reques.getParameter("tnum");

CommunicationParam param1 = new CommunicationParam("unicode", unicode);
CommunicationParam param2 = new CommunicationParam("ct", ct);
CommunicationParam param3 = new CommunicationParam("tnum", tnum);

params.add(param1);
params.add(param2);
params.add(param3);

CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
def ret = systemCaller.doworkAsString( address, params); 


// JCRequest reques = RequestSource.getRequest();
// def name = reques.getParameter("name");


Result result = new Result();
result.setData(ret);


return result;