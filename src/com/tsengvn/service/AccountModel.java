package com.tsengvn.service;
/**
 * Creator: Hien Ngo
 * Date: Nov 20, 2012
 */
public class AccountModel {
	private String accNo;
	private String cusNo;
	private String cusName;
	private float rate;
	private long openDate;
	private long bookedBalance;
	private long closeDate;
	private long maturity;
	private boolean autoRenew;
	private String autoTransfer;
	
	public AccountModel(String aAccNo, String aCusNo){
		accNo = aAccNo;
		cusName = aCusNo;
	}
	
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getCusNo() {
		return cusNo;
	}
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public long getOpenDate() {
		return openDate;
	}
	public void setOpenDate(long openDate) {
		this.openDate = openDate;
	}
	public long getBookedBalance() {
		return bookedBalance;
	}
	public void setBookedBalance(long bookedBalance) {
		this.bookedBalance = bookedBalance;
	}
	public long getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(long closeDate) {
		this.closeDate = closeDate;
	}
	public long getMaturity() {
		return maturity;
	}
	public void setMaturity(long maturity) {
		this.maturity = maturity;
	}
	public boolean isAutoRenew() {
		return autoRenew;
	}
	public void setAutoRenew(boolean autoRenew) {
		this.autoRenew = autoRenew;
	}
	public String getAutoTransfer() {
		return autoTransfer;
	}
	public void setAutoTransfer(String autoTransfer) {
		this.autoTransfer = autoTransfer;
	}
	
	
}
