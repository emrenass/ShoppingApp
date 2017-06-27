package com.shop.bean;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.shopping.ejb.ManagePersonEJB;

public class LoginBean
{
	private String name;
	private String password;
	private boolean loginSuccessful;

	@EJB
	ManagePersonEJB personEJB;
	
	public LoginBean() {}

	public String checkUserLogin(){
		
		boolean result = personEJB.listEnteredUser(getName(), getPassword());
		return (result ? "valid" : "");
	}



	public String getName ()
	{
		return name;
	}


	public void setName (final String name)
	{
		this.name = name;
	}


	public String getPassword ()
	{
		return password;
	}


	public void setPassword (final String password)
	{
		this.password = password;
	}

	public boolean isLoginSuccessful() {
		return loginSuccessful;
	}


	public void setLoginSuccessful(boolean loginSuccessful) {
		this.loginSuccessful = loginSuccessful;
	}

	public void logout() throws IOException {
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.invalidateSession();
	    ec.redirect(ec.getRequestContextPath() + "/Login.xhtml");
	}

}
