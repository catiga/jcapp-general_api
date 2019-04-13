package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result

def ret = JC.internal.call('ticketingsys', '/api/comming_list', null);
 
return new Result().setData(ret);


