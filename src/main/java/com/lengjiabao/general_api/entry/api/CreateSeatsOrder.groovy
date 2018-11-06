package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource

import com.lengjiabao.general_api.ready.dto.AccountInfo //获取用户信息




Result result = new Result();
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
JCRequest reques = RequestSource.getRequest();

AccountInfo  accountInfo = reques.getAttribute("_user_");//获取用户信息
def ap_id = accountInfo.ap_id;//获取用户信息

def cinema_id = reques.getParameter("cinema_id");
def plan_date = reques.getParameter("plan_date");
def plan_id = reques.getParameter("plan_id");
def last_update_time = reques.getParameter("last_update_time");
def seat_ids = reques.getParameter("seat_ids");
def token = reques.getParameter("token");

CommunicationParam param1 = new CommunicationParam("cinema_id", cinema_id);
CommunicationParam param2 = new CommunicationParam("plan_date", plan_date);
CommunicationParam param3 = new CommunicationParam("plan_id", plan_id);
CommunicationParam param4 = new CommunicationParam("last_update_time", last_update_time);
CommunicationParam param5 = new CommunicationParam("seat_ids", seat_ids);
CommunicationParam param6 = new CommunicationParam("token", token);

CommunicationParam param7 = new CommunicationParam("apid", ap_id);//获取用户信息

params.add(param1);
params.add(param2);
params.add(param3);
params.add(param4);
params.add(param5);
params.add(param6);

params.add(param7);//获取用户信息

// params.add(new CommunicationParam("cinema_id",cinema_id));
// params.add(new CommunicationParam("day",day));
//获取app调用句柄 本应用appcode为ticketingsys
CommunicationPower caller = CommunicationSource.getCommunicator("ticketingsys");
def aaaa = caller.doworkAsString("/api/genorder", params);


return result.setData(aaaa);




