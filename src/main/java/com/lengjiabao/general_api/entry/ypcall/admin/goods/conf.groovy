package com.lengjiabao.general_api.entry.ypcall.admin.goods

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

SimpleAjax conf = JC.internal.call(SimpleAjax, 'scm', '/goods/conf', null);

return GeneralPub.success(conf.data);