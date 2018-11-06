package com.lengjiabao.general_api.entry.ypcall.admin.goods

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def token = JC.request.param('token');
def pid = GlobalHolder.pid;

def id = JC.request.param('id');	//支持商品编辑
def goods_sn = JC.request.param('goods_sn');
def goods_code = JC.request.param('goods_code');
def goods_material = JC.request.param('goods_material');
def goods_name = JC.request.param('goods_name');
def sale_price = JC.request.param('sale_price');
def original_price = JC.request.param('original_price');
def cost_price = JC.request.param('cost_price');
def goods_unit = JC.request.param('goods_unit');
def imgs = JC.request.param('imgs');

SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token', [token:token,pid:pid]);
if(!ret.available) {
	return GeneralPub.comfail('no_login');
}

//开始保存商品数据
def goods = [:];
goods.put('id', id);
goods.put('goods_sn', goods_sn);
goods.put('goods_material', goods_material);
goods.put('goods_name', goods_name);
goods.put('sale_price', sale_price);
goods.put('original_price', original_price);
goods.put('cost_price', cost_price);
goods.put('goods_unit', goods_unit);
goods.put('goods_code', goods_code);
goods.put('imgs', imgs);

SimpleAjax update_result = JC.internal.call(SimpleAjax, 'scm', '/goods/update', [g:goods, pid:pid]);

return GeneralPub.success(update_result.data);

