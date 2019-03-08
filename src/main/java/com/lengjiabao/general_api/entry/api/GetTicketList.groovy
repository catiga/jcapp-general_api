package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource


//返回非list数据
Result result = new Result();
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
JCRequest reques = RequestSource.getRequest();

def token = reques.getParameter("token");

CommunicationParam param1 = new CommunicationParam("token", token);

params.add(param1);

CommunicationPower caller = CommunicationSource.getCommunicator("ticketingsys");
//def aaaa = caller.doworkAsString("/api/orders", params);
def aaaa =  JC.internal.call("ticketingsys",'/api/orders' , ['token':token,pid:GlobalHolder.pid]);
return result.setData(aaaa);


