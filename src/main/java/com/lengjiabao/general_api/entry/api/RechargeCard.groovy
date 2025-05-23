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
def mc_num = reques.getParameter("mc_num");
def h_id = reques.getParameter("h_id");
def sid = reques.getParameter("sid");
def getpay = reques.getParameter("getpay");

CommunicationParam param1 = new CommunicationParam("mc_num", mc_num);
CommunicationParam param2 = new CommunicationParam("h_id", h_id);
CommunicationParam param3 = new CommunicationParam("sid", sid);
CommunicationParam param4 = new CommunicationParam("getpay", getpay);

params.add(param1);
params.add(param2);
params.add(param3);
params.add(param4);

//获取app调用句柄 本应用appcode为ticketingsys
CommunicationPower caller = CommunicationSource.getCommunicator("crm");
def aaaa = caller.doworkAsString("/h5/mc/createRechargeOrder", params);


return result.setData(aaaa);