package com.lengjiabao.general_api.entry.front

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result

def schema = JC.request.get().getSchema();
def domain = JC.request.get().getServerName();

if(domain=='lzglxz.piaodaren.com') {
	domain = 'lzadmin.mt.ex.piaodaren.com';
}

def redirect_url = schema + domain + '/general_api/tcss/index';

JCLogger logger = LoggerSource.getLogger();
logger.info(redirect_url);

return new Result().setRedirectResource(redirect_url);
