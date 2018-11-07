package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.util.GlobalHolder

return JC.internal.call('project', '/incall/project_by_id', [pid:GlobalHolder.getPid().toString()]);