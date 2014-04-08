package org.gs.controller;

import org.gs.dao.SettingsDAO;
import org.gs.lang.Locale;
import org.gs.model.Settings;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.gs.util.RessourceBundleUtil;

public class Configuration {

	public Configuration() {
		//First of all check if the user has access to this page.
    	// Meaning if the user is connected.
    	// TODO Should possibly check some other user right
    	
    	
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		if(u==null)
			FacesUtil.redirect("/",RessourceBundleUtil.getMessage("notConnected"));
		
	}

	public static boolean isLoggingEnabled() {
		SettingsDAO settingsDao = new SettingsDAO();
				
		Settings enableLogs = settingsDao.find("system.logs.enable", Locale.getSelectedLanguageBis());
		
		return enableLogs==null?false : enableLogs.getValue().equals("1");
	}
	
	SettingsDAO settingsDao = new SettingsDAO();
	Settings setting = new Settings();
}
