package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower

import com.jeancoder.core.result.Result

import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource



String point = "trade";  //访问的是哪个app的名称
//String address = "/incall/cashier/catalog";  //访问的是哪个app路径
String address = "/incall/allow_pt"
List<CommunicationParam> params = new ArrayList<CommunicationParam>(); 

JCRequest reques = RequestSource.getRequest();

CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
def ret = systemCaller.doworkAsString( address, params); 

Result result = new Result();
result.setData(ret);


return result;