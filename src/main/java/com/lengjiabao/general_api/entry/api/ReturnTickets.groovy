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
def order_no = reques.getParameter("order_no");

CommunicationParam param1 = new CommunicationParam("order_no", order_no);

params.add(param1);
// params.add(new CommunicationParam("cinema_id",cinema_id));
// params.add(new CommunicationParam("day",day));
//获取app调用句柄 本应用appcode为ticketingsys
CommunicationPower caller = CommunicationSource.getCommunicator("ticketingsys");
def aaaa = caller.doworkAsString("/plan/reforder", params);


return result.setData(aaaa);