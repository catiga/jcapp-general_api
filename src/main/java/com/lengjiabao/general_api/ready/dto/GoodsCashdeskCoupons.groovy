package com.lengjiabao.general_api.ready.dto;

import java.io.Serializable;

public class GoodsCashdeskCoupons implements Serializable{
	
	private String code;

	private String id;

	private String takeby;

	private String status;

	private List<String> list;

	public List<String> getList(){
	 	return list;
	}

	// private List<goods>;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTakeby() {
		return takeby;
	}
	public void setTakeby(String takeby) {
		this.takeby = takeby;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
