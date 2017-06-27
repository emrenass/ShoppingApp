package com.shop.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import com.shopping.controller.ManagePerson;
import com.shopping.ejb.ManagePersonEJB;

public class RegisterBean {

	String userName;
	String password;
	String phoneNumber;
	String fname;
	String lname;
	
	@EJB
	ManagePersonEJB personEJB;
	
	
	
	public void register(){
		personEJB.addPerson(userName, password, fname, lname, phoneNumber);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

}
