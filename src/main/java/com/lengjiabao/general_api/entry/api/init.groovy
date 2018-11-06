package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def proj_info = JC.request.get().getAttribute('__current_proj_info__');
if(proj_info) {
	return GeneralPub.success(proj_info);
}

return GeneralPub.fail('config_error', '项目初始化失败', null);