package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource



//String point = "ticketingsys";
//String address = "/api/comming_list";

//List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//
//CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
//def ret = systemCaller.doworkAsString(address, params);

def ret = JC.internal.call('ticketingsys', '/api/comming_list', null);

 
return new Result().setData(ret);


