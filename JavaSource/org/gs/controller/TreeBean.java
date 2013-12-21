package org.gs.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean
@ViewScoped
public class TreeBean {

	public TreeBean() {
		// TODO Auto-generated constructor stub
		
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
