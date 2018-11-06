package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource
// println '1211'
String point = "crm";
String address = "/h5/mc/get_hierarchy_list";
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//CommunicationParam param = new CommunicationParam("key", "value");
//params.add(param);
CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
String ret = systemCaller.doworkAsString(address, params);

// println ret //输入ret到日志
return new Result().setData(ret);

