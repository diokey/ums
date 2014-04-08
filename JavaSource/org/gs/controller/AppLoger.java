package org.gs.controller;

import org.gs.dao.LogDAO;
import org.gs.model.Log;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;

public class AppLoger {

	private AppLoger() {
		// TODO Auto-generated constructor stub
	}

	public static boolean log(String message) {
		boolean res = false;
		Log log = new Log();
		log.setDescription(message);
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		
		if(u!=null)
			log.setUserId(u.getUserId());
		
		log.setUserIp(FacesUtil.getRemodeAdrr());
		
		LogDAO lDao = new LogDAO();
		
		res = lDao.create(log);
		
		log = null;
		lDao = null;
		
		return res;
	}
	
	public static boolean log(String errorType, String message) {
		
		boolean res = true;
		
		if(Configuration.isLoggingEnabled()) {
			
			Log log = new Log();
			log.setDescription(message);
			log.setLogSeverity(errorType);
			
			User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
			
			if(u!=null)
				log.setUserId(u.getUserId());
			
			log.setUserIp(FacesUtil.getRemodeAdrr());
			
			LogDAO lDao = new LogDAO();
			
			res = lDao.create(log);
			
			log = null;
			lDao = null;
		}
		
		return res;
	}
}
