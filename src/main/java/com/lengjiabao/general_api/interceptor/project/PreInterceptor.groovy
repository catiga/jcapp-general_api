package com.lengjiabao.general_api.interceptor.project

import com.jeancoder.annotation.urlmapped
import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder


@urlmapped("/")

def domain = JC.request.get().getServerName();
SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/incall/project_stand', [domain:domain]);

def pid = 1;
if(ret.available) {
	pid = ret.data['id'];
	JC.request.get().setAttribute('__current_proj_info__', ret.data);
}

GlobalHolder.setPid(pid);

return true;
