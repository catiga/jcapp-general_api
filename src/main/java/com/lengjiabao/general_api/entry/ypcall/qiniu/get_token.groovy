package com.lengjiabao.general_api.entry.ypcall.qiniu

import com.jeancoder.app.sdk.source.QiniuSource
import com.jeancoder.core.power.QiniuPower
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

QiniuPower qiniu = QiniuSource.getQiniuPower();
def up_token = qiniu.token(null);

return GeneralPub.success(up_token);