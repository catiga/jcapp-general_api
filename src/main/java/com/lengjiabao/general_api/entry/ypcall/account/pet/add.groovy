package com.lengjiabao.general_api.entry.ypcall.account.pet

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.res.PetInfo
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

PetInfo pet = new PetInfo();

def pid = GlobalHolder.pid;

def token = JC.request.param('token');

def ret = JC.internal.call('crm', '/binddata/save_pet', [token:token,p:pet,pid:pid]);

return GeneralPub.success();