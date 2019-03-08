package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower

import com.jeancoder.core.result.Result

import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource

 JCRequest reques = RequestSource.getRequest();
 def apid = reques.getParameter("apid");
 def ds = reques.getParameter("ds");
 def oss = reques.getParameter("oss");
 def token = reques.getParameter("token");

def ret = JC.internal.call("scm", "/incall/h5/list_order", ['apid':apid,'ds':ds,'oss':oss,'token':token])

Result result = new Result();
result.setData(ret);


return result;