package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource

import java.util.ArrayList;//list集合
import java.util.List;//list集合



//处理接口

Result result = new Result();
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
JCRequest reques = RequestSource.getRequest();
def tnum = reques.getParameter("tnum");
def ct = reques.getParameter("ct");
def pwd = reques.getParameter("pwd");
def unicode = reques.getParameter("unicode");

CommunicationParam param1 = new CommunicationParam("tnum", tnum);
CommunicationParam param2 = new CommunicationParam("ct", ct);
CommunicationParam param3 = new CommunicationParam("pwd", pwd);
CommunicationParam param4 = new CommunicationParam("unicode", unicode);

params.add(param1);
params.add(param2);
params.add(param3);
params.add(param4);

//获取app调用句柄 本应用appcode为ticketingsys
CommunicationPower caller = CommunicationSource.getCommunicator("trade");
def aaaa = caller.doworkAsString("/incall/ro_code_pay", params);


return result.setData(aaaa);