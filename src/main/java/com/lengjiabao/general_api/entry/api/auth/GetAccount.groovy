package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.GlobalHolder

JCLogger LOGGER = LoggerSource.getLogger();

def token = GlobalHolder.token;
def pid = GlobalHolder.pid;

def  accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
def ap_id = accountInfo['ap_id'];//获取用户信息

LOGGER.info('token=' + token + ', pid=' + pid);
def ret = JC.internal.call('crm', '/h5/p/info_by_apid', [apid:ap_id,pid:pid]);

Result result = new Result();
result.setData(ret);


return result;



