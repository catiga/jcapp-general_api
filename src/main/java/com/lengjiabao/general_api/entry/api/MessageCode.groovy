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
//
//CommunicationParam param1 = new CommunicationParam("p", p);
//
//params.add(param1);
////获取app调用句柄 本应用appcode为ticketingsys
//CommunicationPower caller = CommunicationSource.getCommunicator("crm");
//def aaaa = caller.doworkAsString("/h5/sys/sms_code", params);
//
//
//return result.setData(aaaa);

def p = JC.request.param('p');
def aaaa = JC.internal.call('crm', "/h5/sys/sms_code", [p:p]);

Result result = new Result();
return result.setData(aaaa);