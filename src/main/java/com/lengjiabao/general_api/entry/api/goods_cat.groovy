package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder

def pid = GlobalHolder.pid;

//定义要取的渠道，这个要统一返回H5电商和扫码点餐数据，这里是默认
def dscs = 'GENERAL_EC,GENERAL_SOC';
//从前端接收
def input_dscs = JC.request.param('c');
if(input_dscs) {
	dscs = input_dscs;		//如果前端传入，则取前端传入的
}
SimpleAjax ret = JC.internal.call(SimpleAjax, 'scm', '/incall/h5/catalog', [pid:pid, dscs:dscs]);

Result result = new Result();
result.setData(ret.data);

return result;