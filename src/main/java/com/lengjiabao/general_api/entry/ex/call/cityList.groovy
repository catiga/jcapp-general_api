package com.lengjiabao.general_api.entry.ex.call

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder

//def ret = JC.internal.call('ticketingsys', '/api/proj_citys', [pid:1]);

def ret = JC.internal.call(SimpleAjax, 'project', '/incall/proj_citys', [pid:GlobalHolder.pid]);

def ret_data = [:]
ret_data['code'] = 0;
ret_data['msg'] = 'success';
if(ret.available) {
	ret_data['data'] = ret.data;
} else {
	ret_data['code'] = -1;
	ret_data['msg'] = '获取城市失败';
}

return new Result().setData(ret_data);
