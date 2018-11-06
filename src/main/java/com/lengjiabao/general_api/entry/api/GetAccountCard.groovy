package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper;//返回家数据
import com.lengjiabao.general_api.ready.common.AvailabilityStatus; //返回
import java.util.ArrayList;//list集合
import java.util.List;//list集合

String point = "crm";
String address = "/h5/user/get_account_mcs";
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//CommunicationParam param = new CommunicationParam("key", "value");
//params.add(param);
CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
String ret = systemCaller.doworkAsString(address, params);
return new Result().setData(ret);
