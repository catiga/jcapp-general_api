package com.lengjiabao.general_api.ready.dto;

import com.lengjiabao.general_api.ready.dto.UtilDto;

import java.io.Serializable;

public class GoodsCashdesk implements Serializable{
	
	private String basicId;

	private String buyNum;

	private String deliverType;

	private String id;

	private String mobile;

	private String on;

	private String os;

	private String payAmount;

	private String picUrl;

	private String storeName;


	private List<UtilDto> list;

	public List<UtilDto> getList(){
	 	return list;
	}

	// private List<goods>;

	public String getBasicId() {
		return basicId;
	}
	public void setBasicId(String basicId) {
		this.basicId = basicId;
	}

	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}

	public String getDeliverType() {
		return deliverType;
	}
	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOn() {
		return on;
	}
	public void setOn(String on) {
		this.on = on;
	}

	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}



	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
}
