package com.lengjiabao.general_api.entry.ypcall.index

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def ap_id = JC.request.param('ap_id');
def part_id = JC.request.param('part_id');

println 'index msgs:ap_id=' + ap_id + ', part_id=' + part_id;

def data = [:];
data['dtid'] = 0;
data['dtkey'] = '998102198584';
data['dtcat'] = '1000';
data['dttitle'] = '消息：';
data['dtinfo'] = '您的宝贝即将服务完成';
data['click_url'] = null;

def ret_list = [];
//这里需要判断消息类型
//ret_list.add(data);
return GeneralPub.success(ret_list);