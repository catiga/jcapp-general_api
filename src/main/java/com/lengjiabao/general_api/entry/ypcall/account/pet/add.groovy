package com.lengjiabao.general_api.entry.ypcall.account.pet

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.res.PetInfo
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

//def bearing = JC.request.param('bearing')?.trim();
//def bindNo = JC.request.param('bindNo')?.trim();
//def birth = JC.request.param('birth')?.trim();
//def cat = JC.request.param('cat')?.trim();
//def catcode = JC.request.param('catcode')?.trim();
//def character = JC.request.param('character')?.trim();
//def color = JC.request.param('color')?.trim();
//def food = JC.request.param('food')?.trim();
//def gender = JC.request.param('gender')?.trim();
//def headimg = JC.request.param('headimg')?.trim();
//def high = JC.request.param('high')?.trim();
//def name = JC.request.param('name')?.trim();
//def nursing = JC.request.param('nursing')?.trim();
//def remark = JC.request.param('remark')?.trim();
//def sicken = JC.request.param('sicken')?.trim();
//def sterilisation = JC.request.param('sterilisation')?.trim();

PetInfo pet = new PetInfo();

println JackSonBeanMapper.toJson(pet);

def token = JC.request.param('token');

def ret = JC.internal.call('crm', '/binddata/save_pet', [token:token,p:pet,pid:1]);

return GeneralPub.success();