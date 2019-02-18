package com.lengjiabao.general_api.ready.common

import java.io.Serializable


class McAvailabilityStatus implements Serializable {
	 boolean available;
	
	 String[] messages;
	
	 Object obj ;
	 
	 private static final McAvailabilityStatus AVAILABLE_INSTANCE = new McAvailabilityStatus(true, new String[0]);
	 
	 
	 public static McAvailabilityStatus available(String[] messages) {
		 return new McAvailabilityStatus(true, messages);
	 }
	 
	 public static McAvailabilityStatus available(String[] messages, RetObj obj) {
		 return new McAvailabilityStatus(true, messages, obj);
	 }
	 
	 
	 public static McAvailabilityStatus available() {
		 return AVAILABLE_INSTANCE;
	 }
	 
	 public static McAvailabilityStatus available(RetObj obj) {
		 return new McAvailabilityStatus(true, [""] as String[], obj);
	 }
	 
	 public static McAvailabilityStatus available(Object obj) {
		 
		 return new McAvailabilityStatus(true, [""] as String[], new RetObj() {
			 @Override
			 public Object getObj() {
				 return obj;
			 }
		 });
	 }
	 
	 private McAvailabilityStatus(boolean available, String[] messages) {
		 this.available = available;
		 this.messages = messages;
	 }
	 
	 private McAvailabilityStatus(boolean available, String[] messages, RetObj obj) {
		 this.available = available;
		 this.messages = messages;
		 this.obj = obj;
	 }
}
