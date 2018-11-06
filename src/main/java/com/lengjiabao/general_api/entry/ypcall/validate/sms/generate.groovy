package com.lengjiabao.general_api.entry.ypcall.validate.sms

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.MemSource
import com.jeancoder.core.power.MemPower
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def mobile = JC.request.param('mobile')?.trim();
def verify_code = JC.request.param('verify_code')?.trim();	//图片验证码

MemPower mem = MemSource.getMemPower();
def store_code = mem.get('_img_code_key_' + verify_code);
if(!store_code) {
	return GeneralPub.comfail('图片验证码错误');
}
SimpleAjax sms_code = JC.internal.call(SimpleAjax, 'crm', '/wechat/app/sms_code', [p:mobile]);

if(!sms_code.available) {
	return GeneralPub.comfail('短信服务繁忙，请稍后重试');
}

return GeneralPub.success();