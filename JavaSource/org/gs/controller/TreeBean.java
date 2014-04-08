package org.gs.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.gs.util.RessourceBundleUtil;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean
@ViewScoped
public class TreeBean {

	public TreeBean() {
		//First of all check if the user has access to this page.
    	// Meaning if the user is connected.
    	// TODO Should possibly check some other user right
    	
    	
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		if(u==null)
			FacesUtil.redirect("/",RessourceBundleUtil.getMessage("notConnected"));
		
    	//-------------------- end checking connection checkings--------------------------------
		
		root = new DefaultTreeNode("Root",null);
		
		//List<Faculte> faculties = 
	}
	
	
	public TreeNode getRoot() {
		return root;
	}


	public void setRoot(TreeNode root) {
		this.root = root;
	}


	private TreeNode root;

}
