package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper

def ret_data = JC.internal.call('project', '/incall/project_by_id', [pid:GlobalHolder.getPid().toString()]);

ret_data = JackSonBeanMapper.jsonToMap(ret_data);

def schema = JC.request.get().getSchema();
ret_data['domain'] = schema + ret_data['domain'];

return ret_data;