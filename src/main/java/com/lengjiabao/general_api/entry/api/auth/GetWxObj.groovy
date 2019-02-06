package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
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
def domain = reques.getServerName();
def tnum = reques.getParameter("tnum");
def ct = reques.getParameter("ct");
def pwd = reques.getParameter("pwd");
def o_rem = JC.request.param('o_rem');

def  accountInfo = reques.getAttribute("_user_");//获取用户信息
def unicode;
if (ct.equals("101001")) {
	unicode = reques.getParameter("unicode");
} else {
	unicode = accountInfo['part_id'];//获取用户信息
}

def coupon_id = JC.request.param('coupon_id');

println 'need_transfer_coupon_id=' + coupon_id;

//CommunicationParam param1 = new CommunicationParam("tnum", tnum);
//CommunicationParam param2 = new CommunicationParam("ct", ct);
//CommunicationParam param3 = new CommunicationParam("pwd", pwd);
//CommunicationParam param4 = new CommunicationParam("unicode", unicode);
//CommunicationParam param5 = new CommunicationParam("coupon_id", coupon_id);
//CommunicationParam param6 = new CommunicationParam('o_rem', o_rem);
//
//params.add(param1);
//params.add(param2);
//params.add(param3);
//params.add(param4);
//params.add(param5);
//params.add(param6);
//domain
////获取app调用句柄 本应用appcode为ticketingsys
//CommunicationPower caller = CommunicationSource.getCommunicator("trade");
//def aaaa = caller.doworkAsString("/incall/ro_code_pay", params);
def aaaa = JC.internal.call("trade", "/incall/ro_code_pay", [tnum:tnum,ct:ct,pwd:pwd,unicode:unicode,o_rem:o_rem,coupon_id:coupon_id,domain:domain])
return result.setData(aaaa);

