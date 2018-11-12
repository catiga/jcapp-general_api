package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower

import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource

Result result = new Result();

def cat_id = JC.request.param('cat_id');
def sid = JC.request.param('sid');		//用户选择的门店

if(!sid) {
	result.setData(SimpleAjax.notAvailable('param_error,请选择门店'));
	return result;
}

def token = GlobalHolder.token;
def pid = GlobalHolder.pid;
if(!token) {
	token = '';
}

def ret = JC.internal.call('scm', '/incall/h5/catalog_goods', [cat_id:cat_id,token:token,pid:pid,sid:sid]);


result.setData(ret);

return result;


