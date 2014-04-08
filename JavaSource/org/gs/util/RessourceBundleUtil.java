package org.gs.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.model.SelectItem;

import org.gs.lang.Locale;


public class RessourceBundleUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String lang = Locale.getSelectedLanguageBis();
	//private static ResourceBundle uiMessages = ResourceBundle.getBundle( "ui_messages_"+getLang() );
	
	public RessourceBundleUtil() {
		// TODO Auto-generated constructor stub
		
	}

	public static String getLang() {
		lang = Locale.getSelectedLanguageBis();
		return lang;
	}

	public static void setLang(String lang) {
		RessourceBundleUtil.lang = lang;
	}
	
	public static ResourceBundle getUIMessages() {
		return ResourceBundle.getBundle( "ui_messages_"+getLang() );
	}
	
	public static String getMessage(String key) {
		return getUIMessages().getString(key);
	}
	
	
	public static List<SelectItem> getLanguages() {
		//TODO improve this to list languages depending on the languages property files
		// existing in the class path
		List<SelectItem> languages = new ArrayList<SelectItem>();
		languages.add(new SelectItem("en","English"));
		languages.add(new SelectItem("fr","French"));
		
		return languages;
	}
	
}
