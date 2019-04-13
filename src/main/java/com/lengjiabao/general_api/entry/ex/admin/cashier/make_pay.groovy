// 收银台 >> 支付
package com.lengjiabao.general_api.entry.ex.admin.cashier

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.wx.WxMsg
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

String point = "trade";
String address = "/incall/uni_code_pay";

def unicode = JC.request.param("unicode");
def ct = JC.request.param("ct");
def tnum = JC.request.param("tnum");
def pwd = JC.request.param("pwd");
def mp = JC.request.param("mp");
def mpv = JC.request.param("mpv");
def mpc = JC.request.param("mpc");
def coupons = JC.request.param("coupons");

//List<CommunicationParam> params = new ArrayList<CommunicationParam>();  // == def params = [];
//CommunicationParam param1 = new CommunicationParam("unicode", unicode);
//CommunicationParam param2 = new CommunicationParam("ct", ct);
//CommunicationParam param3 = new CommunicationParam("tnum", tnum);
//CommunicationParam param4 = new CommunicationParam("pwd", pwd);
//CommunicationParam param5 = new CommunicationParam("mp", mp);
//CommunicationParam param6 = new CommunicationParam("mpv", mpv);
//CommunicationParam param7 = new CommunicationParam("mpc", mpc);
//CommunicationParam param8 = new CommunicationParam("coupons", coupons);
//params.add(param1);
//params.add(param2);
//params.add(param3);
//params.add(param4);
//params.add(param5);
//params.add(param6);
//params.add(param7);
//params.add(param8);
//CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
//def ret = systemCaller.doworkAsString( address, params); 


def ret = JC.internal.call('trade', '/incall/uni_code_pay', [unicode:unicode,ct:ct,tnum:tnum,pwd:pwd,mp:mp,mpv:mpv,mpc:mpc,coupons:coupons]);


def result = JackSonBeanMapper.jsonToMap(ret);
if(result['code']=='0') {
	
	try {
		//ret 为PayResult的格式
		def pay_result = JackSonBeanMapper.jsonToMap(ret);
		if(pay_result['code']=='0'&&ct=='201001') {
			//成功并且并且是微信支付扫码的情况
			//准备发通知
			def pid = GlobalHolder.pid;
			def supp_config = JC.internal.call('project', '/incall/frontconfig', [pid:pid,app_type:'20']);	//寻找微信公众号的配置信息
			supp_config = JackSonBeanMapper.jsonToMap(supp_config);
			if(supp_config!=null&&supp_config['app_id']!=null) {
				def app_id = supp_config['app_id'];
				def app_key = supp_config['app_key'];
				def openid = result['user_code'];
				def bzdata = result['bzdata'];
				
				WxMsg.push_consume_msg(app_id, app_key, openid, bzdata);
			}
		}
	}catch(any) {
	}
	
	return GeneralPub.success(result);
}
return GeneralPub.comfail(result);


