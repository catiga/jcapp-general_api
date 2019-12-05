package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result

//处理接口
Result result = new Result();
def domain = JC.request.get().getServerName();
def tnum = JC.request.param("tnum");
def ct = JC.request.param("ct");
def pwd = JC.request.param("pwd");
def o_rem = JC.request.param('o_rem');

def accountInfo = JC.request.attr("_user_");//获取用户信息
def mobile = accountInfo['mobile'];//获取用户信息
def pid = accountInfo['pid'];//获取用户信息
def ap_id = accountInfo['ap_id'];

def unicode;
if (ct.equals("101001")) {
	unicode = JC.request.param("unicode");
} else {
	unicode = accountInfo['part_id'];//获取用户信息
}

def coupon_id = JC.request.param('coupon_id');
def market_id = JC.request.param('market_id');

def aaaa = JC.internal.call("trade", "/incall/ro_code_pay", [tnum:tnum,ct:ct,pwd:pwd,unicode:unicode,o_rem:o_rem,coupon_id:coupon_id,market_id:market_id,domain:domain,ap_id:ap_id, mobile:mobile])
return result.setData(aaaa);

