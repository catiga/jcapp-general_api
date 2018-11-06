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



//String point = "scm";  //访问的是哪个app的名称
////String address = "/incall/cashier/catalog";  //访问的是哪个app路径
//String address = "/incall/h5/catalog_goods"
//List<CommunicationParam> params = new ArrayList<CommunicationParam>();  // == def params = [];
//
//JCRequest reques = RequestSource.getRequest();
// def cat_id = reques.getParameter("cat_id");
//
//CommunicationParam param = new CommunicationParam("cat_id", cat_id);
//
//params.add(param);
//
//CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
//def ret = systemCaller.doworkAsString( address, params); 
//
//
//// JCRequest reques = RequestSource.getRequest();
//// def name = reques.getParameter("name");
//
//
//Result result = new Result();
//result.setData(ret);
//
//
//return result;

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


