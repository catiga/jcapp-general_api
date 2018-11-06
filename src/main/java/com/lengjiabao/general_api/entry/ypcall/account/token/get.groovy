package com.lengjiabao.general_api.entry.ypcall.account.token

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.MemSource
import com.jeancoder.core.power.MemPower
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def ap_id = JC.request.param('ap_id')?.trim();
def mobile = JC.request.param('mobile')?.trim();
def code = JC.request.param('code')?.trim();		//短信验证码
def pid = GlobalHolder.pid;
SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'crm', '/wechat/app/sms_login', [p:mobile,code:code,apid:ap_id,pid:pid]);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}
//返回token
return GeneralPub.success(ret_obj.data);