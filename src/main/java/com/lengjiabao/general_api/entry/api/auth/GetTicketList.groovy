package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource

import com.lengjiabao.general_api.ready.dto.AccountInfo //获取用户信息


//返回非list数据
Result result = new Result();
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
JCRequest reques = RequestSource.getRequest();

def token = reques.getParameter("token");

def  accountInfo = reques.getAttribute("_user_");//获取用户信息
def ap_id = accountInfo['ap_id'];//获取用户信息

CommunicationParam param1 = new CommunicationParam("token", token);
CommunicationParam param2 = new CommunicationParam("apid", ap_id);

params.add(param1);
params.add(param2);

CommunicationPower caller = CommunicationSource.getCommunicator("ticketingsys");
def aaaa = caller.doworkAsString("/api/orders", params);


return result.setData(aaaa);


