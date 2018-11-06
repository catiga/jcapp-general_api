package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource

import com.lengjiabao.general_api.ready.dto.AccountInfo //获取用户信息


//处理接口

Result result = new Result();
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
JCRequest reques = RequestSource.getRequest();
def h_id = reques.getParameter("h_id");
def mc_pwd = reques.getParameter("mc_pwd");
def  accountInfo = reques.getAttribute("_user_");//获取用户信息
def ap_id = accountInfo['ap_id'];//获取用户信息
def mobile = accountInfo['mobile'];//获取用户手机号

CommunicationParam param1 = new CommunicationParam("h_id", h_id);
CommunicationParam param2 = new CommunicationParam("mc_pwd", mc_pwd);

CommunicationParam param3 = new CommunicationParam("apid", ap_id);//获取用户信息
CommunicationParam param4 = new CommunicationParam("mobile", mobile);//获取用户手机号

params.add(param1);
params.add(param2);

params.add(param3);
params.add(param4);

//获取app调用句柄 本应用appcode为ticketingsys
CommunicationPower caller = CommunicationSource.getCommunicator("crm");
def aaaa = caller.doworkAsString("/h5/user/create_card_recharge_order", params);


return result.setData(aaaa);