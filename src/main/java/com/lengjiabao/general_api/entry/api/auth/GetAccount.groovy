package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower

import com.jeancoder.core.result.Result

import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.app.sdk.source.RequestSource


import com.lengjiabao.general_api.ready.dto.AccountInfo //获取用户信息
import com.lengjiabao.general_api.ready.util.GlobalHolder

JCLogger LOGGER = JCLoggerFactory.getLogger("auth/GetAccount");

def token = GlobalHolder.token;
def pid = GlobalHolder.pid;

LOGGER.info('token=' + token + ', pid=' + pid);
def ret = JC.internal.call('crm', '/h5/p/info', [token:token,pid:pid]);

Result result = new Result();
result.setData(ret);


return result;



