package com.lengjiabao.general_api.entry.ypcall.validate.img

import javax.servlet.http.Cookie

import com.jeancoder.app.sdk.source.MemSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.power.MemPower
import com.lengjiabao.general_api.ready.util.ImgVerifyCodeUtil

final String sources = "23456789";
int verifySize = 4;
int codesLen = sources.length();
java.util.Random rand = new java.util.Random(System.currentTimeMillis());
StringBuilder verifyCode = new StringBuilder(verifySize);
for(int i = 0; i < verifySize; i++){
	verifyCode.append(sources.charAt(rand.nextInt(codesLen-1)));
}

JCResponse ret_response = ResponseSource.getResponse();
Cookie c = new Cookie('_img_code_key_', verifyCode.toString());
c.path = '/';
ret_response.addCookie(new JCCookie(c));

MemPower mem = MemSource.getMemPower();
mem.setUntil('_img_code_key_' + verifyCode, verifyCode.toString(), 5*60*1000L);		//验证码缓存存储，五分钟过期

ret_response.setHeader("Pragma", "No-cache");
ret_response.setHeader("Cache-Control", "no-cache");
ret_response.setDateHeader("Expires", 0);
ret_response.setContentType("image/jpeg");
int w = 100, h = 50;
ImgVerifyCodeUtil.outputImage(w, h, ret_response.getOutputStream(), verifyCode.toString());



