package com.lengjiabao.general_api.entry.api

import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.AvailabilityStatus
import com.lengjiabao.general_api.ready.zhipeng.CheckMovieTicketsClass//list集合




//返回非list数据
Result result = new Result();
CheckMovieTicketsClass Sspi = new CheckMovieTicketsClass(); //new一个对象
Sspi.acmid = 100;
Sspi.price = 100;
Sspi.info = "40"; 
Sspi.grade = 40;
//String aaa = JackSonBeanMapper.toJson(Sspi); //工具类转化成字符串

final CheckMovieTicketsClass ss = Sspi;
AvailabilityStatus status =  AvailabilityStatus.available( null, ss);

return result.setData(status);



