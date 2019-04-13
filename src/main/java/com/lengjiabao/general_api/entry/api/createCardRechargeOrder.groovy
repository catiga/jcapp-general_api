package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.GlobalHolder//list集合

Result result = new Result();

def h_id = JC.request.param("h_id");
def mc_pwd = JC.request.param("mc_pwd");

/*
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
CommunicationParam param1 = new CommunicationParam("h_id", h_id);
CommunicationParam param2 = new CommunicationParam("mc_pwd", mc_pwd);
params.add(param1);
params.add(param2);
//获取app调用句柄 本应用appcode为ticketingsys
CommunicationPower caller = CommunicationSource.getCommunicator("crm");
def aaaa = caller.doworkAsString("/h5/user/create_card_recharge_order", params);
*/


def aaaa = JC.internal.call('crm', "/h5/user/create_card_recharge_order", ['h_id':h_id,'mc_pwd':mc_pwd, pid:GlobalHolder.pid]);

return result.setData(aaaa);
