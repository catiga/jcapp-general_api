// 创建卖品订单
package com.lengjiabao.general_api.entry.ypcall.admin.cashier

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');
SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token', [token:token,pid:1]);
if(!ret.available) {
	return GeneralPub.comfail('no_login');
}
def gs = JC.request.param('gs');
def s_id = JC.request.param('s_id');
def tnum = '';
ret = JC.internal.call(SimpleAjax, 'scm', '/pay/create_order', [gs:gs,s_id:s_id,tnum:tnum,token:token]);

if(!ret.available) {
	def msg = ret.messages[0];
	if(ret.messages.length>1) {
		msg = ret.messages[1];
	}
	return GeneralPub.fail(ret.messages[0], msg, null);
}

return GeneralPub.success(ret.data);





