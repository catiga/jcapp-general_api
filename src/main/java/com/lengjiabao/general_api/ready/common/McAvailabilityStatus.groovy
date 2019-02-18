package com.lengjiabao.general_api.ready.common

import java.io.Serializable



class McAvailabilityStatus implements Serializable {
	
	 boolean available;
	
	 String[] messages;
	
	 Object obj ;
	 
	 
	 private McAvailabilityStatus(boolean available, String[] messages) {
		 this.available = available;
		 this.messages = messages;
	 }
	 
	 private McAvailabilityStatus(boolean available, String[] messages, RetObj obj) {
		 this.available = available;
		 this.messages = messages;
		 this.obj = obj;
	 }
	 
	 public void setAvailable(boolean available) {
		this.available = available;
	 }

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	public void setObj(RetObj obj) {
		this.obj = obj;
	}

	public boolean isAvailable() {
		 return available;
	 }
	 
	 public String[] getMessages() {
		 return messages;
	 }
	 
	 public Object getObj() {
		 return obj;
	 }
 
	 public static McAvailabilityStatus notAvailable(String name) {
		 String[] suggestions = new String[1];
		 for (int i = 0; i < suggestions.length; i++) {
			 suggestions[i] = name;
		 }
		 return new McAvailabilityStatus(false, suggestions);
	 }
	 
	 
	 public static McAvailabilityStatus notAvailable(Object obj) {
		 
		 return new McAvailabilityStatus(false, ["",""] as String[], new RetObj() {
			 @Override
			 public Object getObj() {
				 return obj;
			 }
		 });
	 }
	 
	 
	 public static McAvailabilityStatus notAvailable(String[] messages) {
		 return new McAvailabilityStatus(false, messages);
	 }
	 
	 public static McAvailabilityStatus notAvailable(String[] messages, RetObj obj) {
		 return new McAvailabilityStatus(false, messages, obj);
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
	 
	 public static McAvailabilityStatus available(String[] messages) {
		 return new McAvailabilityStatus(true, messages);
	 }
	 
	 public static McAvailabilityStatus available(String[] messages, RetObj obj) {
		 return new McAvailabilityStatus(true, messages, obj);
	 }
	 
	 private static final McAvailabilityStatus AVAILABLE_INSTANCE = new McAvailabilityStatus(true, new String[0]);
	 
}
