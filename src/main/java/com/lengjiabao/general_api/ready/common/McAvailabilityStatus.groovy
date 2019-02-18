package com.lengjiabao.general_api.ready.common

import java.io.Serializable

class McAvailabilityStatus implements Serializable {
	 boolean available;
	
	 String[] messages;
	
	 Object obj ;
	 
	 private static final AvailabilityStatus AVAILABLE_INSTANCE = new AvailabilityStatus(true, new String[0]);
	 
	 
	 public static AvailabilityStatus available(String[] messages) {
		 return new AvailabilityStatus(true, messages);
	 }
	 
	 public static AvailabilityStatus available(String[] messages, RetObj obj) {
		 return new AvailabilityStatus(true, messages, obj);
	 }
	 
	 
	 public static AvailabilityStatus available() {
		 return AVAILABLE_INSTANCE;
	 }
	 
	 public static AvailabilityStatus available(RetObj obj) {
		 return new AvailabilityStatus(true, [""] as String[], obj);
	 }
	 
	 public static AvailabilityStatus available(Object obj) {
		 
		 return new AvailabilityStatus(true, [""] as String[], new RetObj() {
			 @Override
			 public Object getObj() {
				 return obj;
			 }
		 });
	 }
}
