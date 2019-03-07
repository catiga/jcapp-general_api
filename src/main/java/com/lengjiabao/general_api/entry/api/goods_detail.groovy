package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def gid = JC.request.param('gid');
def skuid = JC.request.param('skuid');

SimpleAjax goods_data = JC.internal.call(SimpleAjax, 'scm', '/goods/get_by_id', [id:gid,pid:GlobalHolder.pid]);
if(goods_data && goods_data.available) {
	return GeneralPub.success(goods_data.data);
}

return GeneralPub.fail('obj_error', '商品查询失败', null);