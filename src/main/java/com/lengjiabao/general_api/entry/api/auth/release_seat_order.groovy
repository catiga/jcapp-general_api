package com.lengjiabao.general_api.entry.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder

def order_no = JC.request.param('order_no');
def pid = GlobalHolder.pid;

def  accountInfo = JC.request.get().getAttribute("_user_");
def ap_id = accountInfo['ap_id'];

Result result = new Result();

def param_dic = [order_no:order_no,ap_id:ap_id, pid:pid];
def aaaa = JC.internal.call('ticketingsys', '/api/releaseorder', param_dic);

return result.setData(aaaa);