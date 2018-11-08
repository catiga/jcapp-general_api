package com.lengjiabao.general_api.entry.ex.admin.wechat.app

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

class FunConf {
def code;
def icon;
def name;
def info;
def method;
}

class IndexFull {
	def user;
	def funcs;
	def stores;
}

//def f1 = new FunConf(['code':'101', 'icon':'bespoke.png', 'name':'我的预约','info':'查看我的预约信息']);
//def f2 = new FunConf(['code':'102', 'icon':'pingjia.png', 'name':'我的评价','info':'查看我的评价信息']);
//def f3 = new FunConf(['code':'103', 'icon':'heyan.png', 'name':'预约订单','info':'验证预约订单', method:'order/reserve/check/index']);
def f4 = new FunConf(['code':'104', 'icon':'card.png', 'name':'会员查询','info':'查询会员信息', method:'mc/index']);
//def f5 = new FunConf(['code':'105', 'icon':'gd.png', 'name':'商品管理','info':'商品管理', method:'goods/manage/index']);
//def f6 = new FunConf(['code':'106', 'icon':'pandian.png', 'name':'库存盘点','info':'商品库存盘点', method:'goods/inventory/index']);
//def f7 = new FunConf(['code':'107', 'icon':'20171110145038.png', 'name':'员工管理','info':'门店员工管理']);
//def f8 = new FunConf(['code':'108', 'icon':'20171110144917.png', 'name':'收银台','info':'收银台', method:'cashier/index']);

//def f9 = new FunConf(['code':'109', 'icon':'heyan.png', 'name':'券验证','info':'券验证']);
//def f10 = new FunConf(['code':'110', 'icon':'heyan.png', 'name':'业绩查询','info':'业绩查询']);
//def f11 = new FunConf(['code':'111', 'icon':'card.png', 'name':'修改会员卡信息','info':'修改会员卡信息']);

//def collect_admin=[f1, f2, f3, f4, f5, f6, f7, f8, f9, f10];
def collect_admin=[f4];

IndexFull index = new IndexFull();

def pid = GlobalHolder.pid;
def token = JC.request.param('token');
SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token_with_pid', [token:token,pid:pid]);
if(!ret.available) {
	return index;
}

index.user = ret.data['user']
index.funcs = collect_admin;

//获取系统门店列表
//重新定义pid，取当前用户管理的门店
def aim_pid = ret.data['user']['pid'];
SimpleAjax store_list = JC.internal.call(SimpleAjax, 'project', '/incall/mystore_stand', [pid:aim_pid]);
if(store_list.available) {
	index.stores = store_list.data;
}

return GeneralPub.success(index);


