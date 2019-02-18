package com.lengjiabao.general_api.ready.common

import java.io.Serializable


class McAvailabilityStatus implements Serializable {
	
	 boolean available;
	
	 String[] messages;
	
	 Object obj ;
	 
	 public McAvailabilityStatus() {}
	 
	 
	 private McAvailabilityStatus(boolean result, String[] msgs, Object data) {
		 this.available = result;
		 this.messages = msgs;
		 this.data = data;
	 }
 
	 public boolean isAvailable() {
		 return available;
	 }
 
	 public void setAvailable(boolean available) {
		 this.available = available;
	 }
 
	 public String[] getMessages() {
		 return messages;
	 }
 
	 public void setMessages(String[] messages) {
		 this.messages = messages;
	 }
 
	 public Object getData() {
		 return data;
	 }
 
	 public void setData(Object data) {
		 this.data = data;
	 }
 
	 public static McAvailabilityStatus available() {
		 return new McAvailabilityStatus(true, [] as String[], []);
	 }
	 
	 public static McAvailabilityStatus available(String[] messages) {
		 return new McAvailabilityStatus(true, messages, null);
	 }
	 
	 public static McAvailabilityStatus available(String[] messages, Object data) {
		 return new McAvailabilityStatus(true, messages, data);
	 }
	 
	 public static McAvailabilityStatus notAvailable(String[] messages) {
		 return new McAvailabilityStatus(false, messages, null);
	 }
	 
	 public static McAvailabilityStatus notAvailable(String[] messages, Object data) {
		 return new McAvailabilityStatus(false, messages, data);
	 }
}
