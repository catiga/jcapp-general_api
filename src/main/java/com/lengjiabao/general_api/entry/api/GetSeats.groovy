package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.util.GlobalHolder

def cinema_id = JC.request.param("cinema_id");
def plan_id = JC.request.param("plan_id");
def last_update_time = JC.request.param("last_update_time");
def hall_id = JC.request.param("hall_id");
def plan_date = JC.request.param("plan_date");
def ap_id = JC.request.param('ap_id')?.trim();
if(!ap_id) {
	ap_id = '';
}
def pid = GlobalHolder.pid;

def aaaa = JC.internal.call('ticketingsys', '/api/seats', [cinema_id:cinema_id,plan_id:plan_id,last_update_time:last_update_time,hall_id:hall_id,plan_date:plan_date, ap_id:ap_id,pid:pid]);

Result result = new Result();
return result.setData(aaaa);



