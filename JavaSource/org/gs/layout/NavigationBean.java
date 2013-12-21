package org.gs.layout;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class NavigationBean {

	//methods
	public void chooseMenu(String path) {
		System.out.println("path"+path);
		this.selectedMenu = path;
	}
	
	//constructor
	public NavigationBean() {
		// TODO Auto-generated constructor stub
	}
	
	//getters and setters
	
	public String getSelectedMenu() {
		return selectedMenu;
	}

	public void setSelectedMenu(String selectedMenu) {
		this.selectedMenu = selectedMenu;
	}
	
	//private properties	
	private String selectedMenu;
}
