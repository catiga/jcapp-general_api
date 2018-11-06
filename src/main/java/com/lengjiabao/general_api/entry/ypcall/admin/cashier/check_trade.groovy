package com.lengjiabao.general_api.entry.ypcall.admin.cashier

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def tnum = JC.request.param('tnum');
def tid = JC.request.param('tid');

SimpleAjax trade_data = JC.internal.call(SimpleAjax, 'trade', '/incall/check_trade', [tnum:tnum,pid:1]);

if(!trade_data.available) {
	def code = trade_data.messages[0];
	def msg = code;
	if(trade_data.messages.length>1) {
		msg = trade_data.messages[1];
	}
	return GeneralPub.fail(code, msg, null);
}

//处理，并保留可用交易
def trade = trade_data.data[0];
def pays = trade_data.data[1];

def ap_codes = ['001001', '101001', '201001', '202001'];
def available_pays = [];
for(x in pays) {
	for(y in ap_codes) {
		if(x['code']==y) {
			available_pays.add(x);
			break;
		}
	}
}

return GeneralPub.success([trade, available_pays]);