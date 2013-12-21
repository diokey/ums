package org.gs.controller;

import org.gs.dao.SettingsDAO;
import org.gs.lang.Locale;
import org.gs.model.Settings;

public class Configuration {

	public Configuration() {
		// TODO Auto-generated constructor stub
	}

	public static boolean isLoggingEnabled() {
		SettingsDAO settingsDao = new SettingsDAO();
				
		Settings enableLogs = settingsDao.find("system.logs.enable", Locale.getSelectedLanguageBis());
		
		return enableLogs==null?false : enableLogs.getValue().equals("1");
	}
	
	SettingsDAO settingsDao = new SettingsDAO();
	Settings setting = new Settings();
}
