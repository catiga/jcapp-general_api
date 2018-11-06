package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
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

//List<CommunicationParam> params = new ArrayList<CommunicationParam>();  // == def params = [];
//CommunicationParam param1 = new CommunicationParam("gs", gs);
//CommunicationParam param2 = new CommunicationParam("sid", sid);
//CommunicationParam param3 = new CommunicationParam("token", token);
//CommunicationParam param4 = new CommunicationParam("tbn", tbn);
//CommunicationParam param5 = new CommunicationParam("apid", ap_id);//获取用户信息
//CommunicationParam param6 = new CommunicationParam("dss", dss);
//
//params.add(param1);
//params.add(param2);
//params.add(param3);
//params.add(param4);
//params.add(param5);
//params.add(param6);
//
//CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
//def ret = systemCaller.doworkAsString( address, params); 
//
//Result result = new Result();
//result.setData(ret);
//return result;




