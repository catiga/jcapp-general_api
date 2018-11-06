package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource
import java.util.ArrayList;//list集合
import java.util.List;//list集合



//处理接口

//Result result = new Result();
//List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//JCRequest reques = RequestSource.getRequest();
//def p = reques.getParameter("p");
//def code = reques.getParameter("code");
//def apid = reques.getParameter("apid");
//
//CommunicationParam param1 = new CommunicationParam("p", p);
//CommunicationParam param2 = new CommunicationParam("code", code);
//CommunicationParam param3 = new CommunicationParam("apid", apid);
//
//params.add(param1);
//params.add(param2);
//params.add(param3);
////获取app调用句柄 本应用appcode为ticketingsys
//CommunicationPower caller = CommunicationSource.getCommunicator("crm");
//def aaaa = caller.doworkAsString("/h5/sys/sms_login", params);
//
//
//return result.setData(aaaa);

def p = JC.request.param('p');
def code = JC.request.param('code');
def apid = JC.request.param('apid');

def aaaa = JC.internal.call('crm', '/h5/sys/sms_login', [p:p,code:code,apid:apid]);

Result result = new Result();
return result.setData(aaaa);



