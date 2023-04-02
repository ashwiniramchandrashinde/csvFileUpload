package com.example.csvfileapp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "csvdata")
public class csvBean {
	
	@Id
	@Column(name = "id")
	private long id;
	
	@Column(name = "userName")
	String userName;
	
	@Column(name = "emailAddress")
	String emailAddress;
	
	@Column(name = "phoneNumber")
	String phoneNumber;
	
	public csvBean(){}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public csvBean(long id, String userName, String emailAddress, String phoneNumber) {
		super();
		this.id = id;
		this.userName = userName;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "csvBean [id=" + id + ", userName=" + userName + ", emailAddress=" + emailAddress + ", phoneNumber="
				+ phoneNumber + "]";
	}
	
	
	
	

}
