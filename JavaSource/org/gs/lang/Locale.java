package org.gs.lang;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class Locale implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String selectedLanguage="en_US";
	private String newLanguage;
	private static String selectedLanguageBis="en_US";
	
	public Locale() {
		// TODO Auto-generated constructor stub
		
	}

	public String getNewLanguage() {
		return newLanguage;
	}

	public void setNewLanguage(String newLanguage) {
		this.newLanguage = newLanguage;
	}

	public String getSelectedLanguage() {
		return selectedLanguage;
	}

	public void setSelectedLanguage(String selectedLanguage) {
		this.selectedLanguage = selectedLanguage;
	}
	
	public void changeLanguage() {
		this.selectedLanguage = this.newLanguage;
		
		selectedLanguageBis=this.selectedLanguage;
	}

	public static String getSelectedLanguageBis() {
		return selectedLanguageBis;
	}

	public static void setSelectedLanguageBis(String selectedLanguageBis) {
		Locale.selectedLanguageBis = selectedLanguageBis;
	}
	

}
