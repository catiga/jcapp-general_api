package com.lengjiabao.general_api.entry.ypcall.admin.user

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.util.MD5Util
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def username = JC.request.param('username');
def password = JC.request.param('password');

def token = username + password + System.currentTimeMillis();
token = MD5Util.getMD5(token);

return GeneralPub.success(token);