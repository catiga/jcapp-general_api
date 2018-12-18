package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.jeancoder.core.http.JCRequest
import com.jeancoder.app.sdk.source.RequestSource

import java.util.ArrayList;//list集合
import java.util.List;//list集合



Result result = new Result();
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
JCRequest reques = RequestSource.getRequest();
def cinema_id = reques.getParameter("cinema_id");
def plan_id = reques.getParameter("plan_id");
def last_update_time = reques.getParameter("last_update_time");
def hall_id = reques.getParameter("hall_id");

CommunicationParam param1 = new CommunicationParam("cinema_id", cinema_id);
CommunicationParam param2 = new CommunicationParam("plan_id", plan_id);
CommunicationParam param3 = new CommunicationParam("last_update_time", last_update_time);
CommunicationParam param4 = new CommunicationParam("hall_id", hall_id);

println "cinema_id_" + cinema_id;
println "cinema_id_" + plan_id;
println "cinema_id_" + last_update_time;
println "cinema_id_" + hall_id;




params.add(param1);
params.add(param2);
params.add(param3);
params.add(param4);
// params.add(new CommunicationParam("cinema_id",1));
// params.add(new CommunicationParam("plan_id",314672));
// params.add(new CommunicationParam("last_update_time","2018-07-30 11:37:51"));
// params.add(new CommunicationParam("hall_id",437));
//获取app调用句柄 本应用appcode为ticketingsys
CommunicationPower caller = CommunicationSource.getCommunicator("ticketingsys");
def aaaa = caller.doworkAsString("/plan/seats", params);
	
return result.setData(aaaa);

//处理接口
// Result result = new Result()
// AvailabilityStatus status = new AvailabilityStatus();
// try {
// 	  String point = "project";
// 	String address = "/incall/city";

// 	JCRequest reques = RequestSource.getRequest();
// 	def store_id = reques.getParameter("store_id");
// 	def cat = reques.getParameter("cat");

// 	List<CommunicationParam> params = new ArrayList<CommunicationParam>();
// 	//CommunicationParam param = new CommunicationParam("key", "value");
// 	//params.add(param);
// 	CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
// 	def ret = systemCaller.doworkAsString(address, params);
	
// 	status.available = true;
// 	status.obj = ret;

// } catch(Exception e) {
//     String[] messages = ["123","1233"] as String[];
// 	status.available = false;
// 	status.messages = e.getMessage();
// } 
// result.setData(status);
// return result;


//返回非list数据
// Result result = new Result();
// MoviePlanSeatsClass Sspi = new MoviePlanSeatsClass(); //new一个对象
// Sspi.acmid = 100;
// Sspi.price = 100;
// Sspi.info = "40"; 
// Sspi.grade = 40;
// //String aaa = JackSonBeanMapper.toJson(Sspi); //工具类转化成字符串

// final MoviePlanSeatsClass ss = Sspi;
// AvailabilityStatus status =  AvailabilityStatus.available( null, ss);

// return result.setData(status);



