package org.gs.util;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class FacesUtil {

	public FacesUtil() {
		// TODO Auto-generated constructor stub
	}
		
	public static void redirect(String path) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			context.redirect(context.getRequestContextPath() + path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void redirect(String path, String messageText) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			
			//keep flash messages, otherwise it will get lost after page redirect
			
			keepFlashMessages();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,messageText,messageText);	
			
			FacesUtil.addMessage(null, message);
			
			
			context.redirect(context.getRequestContextPath() + path);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void keepFlashMessages() {
		FacesContext context = FacesContext.getCurrentInstance();
		Flash flash = context.getExternalContext().getFlash();
		flash.setKeepMessages(true);
		flash.setRedirect(true);
	}
	
	public static void unsetFlashMessages() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(false);
	}
	
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public static String getRemodeAdrr() {
		
		HttpServletRequest request =  getRequest();
		String ip =request.getHeader( "X-FORWARDED-FOR" );
		
		if ( ip == null ) {
		    ip = request.getRemoteAddr();
		}
		
		return ip;
	}
	
	public static HttpSession getSession(boolean b) {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(b);
	}
	
	public static UIViewRoot getViewRoot() {
		return FacesContext.getCurrentInstance().getViewRoot();
	}
	
	public static UIComponent getComponent(String id) {
		return getViewRoot().findComponent(id);
	}
	
	public static boolean memberConnected() {
		if((getSessionAttribute(Constantes.CONNECTED_USER))!=null) {
			return true;
		}
		return false;
	}
	
	public static Object getSessionAttribute(String attrib) {
		HttpSession session=getSession(true);
		
		return session.getAttribute(attrib);		
	}
	public static void setSessionAttribute(String attrib, Object value) {
		HttpSession session=getSession(true);
		session.setAttribute(attrib, value);
	}
	
	public static boolean logOut() {
		if(memberConnected()){
			getSession(false).invalidate();
			return true;
		}
		return false;		
	}
	
	public static void addMessage(String componentId, FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(componentId, message);
	}
	
	public static String getRessourcePath(String resource) {
		
			ServletContext cont=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String rep=cont.getRealPath(resource);
			
		return rep;
	}
	
	public static void main(String args[]) {
		FacesUtil.getRessourcePath("");
	}
}
