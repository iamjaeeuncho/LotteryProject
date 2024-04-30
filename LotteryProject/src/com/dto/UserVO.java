package com.dto;

import javax.xml.crypto.Data;

public class UserVO {
	
	public int UserNo;
	public String userId;
	public String userPw;
	public String email;
	public Data createDat;
	public Data modifieDat;
	public String status;
	public int getUserNo() {
		return UserNo;
	}
	
	public void setUserNo(int userNo) {
		UserNo = userNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Data getCreateDat() {
		return createDat;
	}
	public void setCreateDat(Data createDat) {
		this.createDat = createDat;
	}
	public Data getModifieDat() {
		return modifieDat;
	}
	public void setModifieDat(Data modifieDat) {
		this.modifieDat = modifieDat;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
