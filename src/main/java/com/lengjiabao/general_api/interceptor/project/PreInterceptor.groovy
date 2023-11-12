package com.lengjiabao.general_api.interceptor.project

import com.jeancoder.annotation.urlmapped
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper


@urlmapped("/")

JCLogger logger = LoggerSource.getLogger();

def domain = JC.request.get().getServerName();
SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/incall/project_stand', [domain:domain]);

logger.info('project_stand: domain=' + domain + ', and ret=' + JackSonBeanMapper.toJson(ret));
def pid = 1;
if(ret.available) {
	pid = ret.data['id'];
	JC.request.get().setAttribute('__current_proj_info__', ret.data);
}

GlobalHolder.setPid(pid);

if(ret==null || ret.data==null || ret.data['proj_status']!='10') {
	//说明已经停止服务
	return SimpleAjax.notAvailable('proj_stoped');
}
return true;
