package com.lengjiabao.general_api.entry.ypcall.admin.inventory.check

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def icid = JC.request.param('icid');
def goods_id = JC.request.param('goods_id');
def goods_sku_id = JC.request.param('goods_sku_id');

def real_stock = JC.request.param('real_stock');
def rec_stock = JC.request.param('rec_stock');
def remark = JC.request.param('remark');

def pid = GlobalHolder.pid;


def param_dic = [icid:icid,goods_id:goods_id,goods_sku_id:goods_sku_id,real_stock:real_stock,rec_stock:rec_stock,remark:remark,pid:pid];

SimpleAjax ret = JC.internal.call(SimpleAjax, 'scm', '/inventory/save_item', param_dic);

if(!ret.available) {
	def er_code = ret.messages[0];
	def er_msg = er_code;
	if(ret.messages.length>1) {
		er_msg = ret.messages[1];
	}
	return GeneralPub.fail(er_code, er_msg, null);
}

return GeneralPub.success();