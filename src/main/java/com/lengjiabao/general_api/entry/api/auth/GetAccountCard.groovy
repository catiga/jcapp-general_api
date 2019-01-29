package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.GlobalHolder

//String point = "crm";
//String address = "/h5/user/get_account_mcs";
//List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//
//JCRequest reques = RequestSource.getRequest();
//
//def  accountInfo = reques.getAttribute("_user_");//获取用户信息
//def ap_id = accountInfo['ap_id'];//获取用户信息
//
//CommunicationParam param1 = new CommunicationParam("apid", ap_id);//获取用户信息
//
//params.add(param1);
//
//CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
//String ret = systemCaller.doworkAsString(address, params);
//return new Result().setData(ret);

String point = "crm";
String address = "/h5/user/get_account_mcs";
List<CommunicationParam> params = new ArrayList<CommunicationParam>();

def  accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
def ap_id = accountInfo['ap_id'];//获取用户信息
def mobile = accountInfo['mobile'];//获取用户信息

String ret = JC.internal.call('crm', '/h5/user/get_account_mcs', [apid:ap_id.toString(),pid:GlobalHolder.pid.toString(),mobile:mobile]);
return new Result().setData(ret);

