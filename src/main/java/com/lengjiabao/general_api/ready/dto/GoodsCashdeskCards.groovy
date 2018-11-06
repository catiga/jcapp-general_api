package com.lengjiabao.general_api.ready.dto;

import java.io.Serializable;

public class GoodsCashdeskCards implements Serializable{
	
	private String acmid;

	private String balance;

	private String canPayWithOther;

	private String cn;

	private String discount;

	private String level;

	private String min_recharge_money;


	private List<String> list;

	public List<String> getList(){
	 	return list;
	}

	// private List<goods>;

	public String getAcmid() {
		return acmid;
	}
	public void setAcmid(String acmid) {
		this.acmid = acmid;
	}

	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCanPayWithOther() {
		return canPayWithOther;
	}
	public void setCanPayWithOther(String canPayWithOther) {
		this.canPayWithOther = canPayWithOther;
	}

	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

	public String getMin_recharge_money() {
		return min_recharge_money;
	}
	public void setMin_recharge_money(String min_recharge_money) {
		this.min_recharge_money = min_recharge_money;
	}
}
