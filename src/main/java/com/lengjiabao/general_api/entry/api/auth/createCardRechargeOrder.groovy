package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.GlobalHolder


//处理接口

Result result = new Result();
List<CommunicationParam> params = new ArrayList<CommunicationParam>();

def h_id = JC.request.param("h_id");
def mc_pwd = JC.request.param("mc_pwd");
def sid = JC.request.param("sid");
def  accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息

def ap_id = accountInfo['ap_id'];//获取用户信息
def mobile = accountInfo['mobile'];//获取用户手机号

//CommunicationParam param1 = new CommunicationParam("h_id", h_id);
//CommunicationParam param2 = new CommunicationParam("mc_pwd", mc_pwd);
//
//CommunicationParam param3 = new CommunicationParam("apid", ap_id);//获取用户信息
//CommunicationParam param4 = new CommunicationParam("mobile", mobile);//获取用户手机号
//CommunicationParam param5 = new CommunicationParam("sid", sid);//获取用户手机号
//
//params.add(param1);
//params.add(param2);
//
//params.add(param3);
//params.add(param4);
//params.add(param5);
//
////获取app调用句柄 本应用appcode为ticketingsys
//CommunicationPower caller = CommunicationSource.getCommunicator("crm");
//def aaaa = caller.doworkAsString("/h5/user/create_card_recharge_order", params);

def aaaa = JC.internal.call('crm', "/h5/user/create_card_recharge_order", [h_id:h_id, mc_pwd:mc_pwd, apid:apid, mobile:mobile, sid:sid, pid:GlobalHolder.pid]);

return result.setData(aaaa);
