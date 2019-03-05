package com.lengjiabao.general_api.entry.h5.wx.card

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

//获取参数
def code_id = JC.request.param('code_id');

def accountInfo = JC.request.get().getAttribute("_user_");//获取用户信息
def ap_id = accountInfo['ap_id'];//获取用户信息
def mobile = accountInfo['mobile'];
def part_id = accountInfo['part_id'];

//验证code
SimpleAjax code_data = JC.internal.call(SimpleAjax, 'market', '/wx/create_cb', [pid:GlobalHolder.pid, code_id:code_id, part_id:part_id]);
if(!code_data.available) {
	return GeneralPub.fail(code_data.messages[0], code_data.messages[1], null);
}

return GeneralPub.success(code_data.data);


