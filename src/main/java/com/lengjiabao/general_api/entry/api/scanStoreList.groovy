package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC

def all_stores = JC.internal.call('project', '/incall/stores', [jc_domain:JC.request.get().getServerName()]);