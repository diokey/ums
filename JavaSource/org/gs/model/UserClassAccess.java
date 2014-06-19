package org.gs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.primefaces.model.TreeNode;

public class UserClassAccess implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserClassAccess() {
		// TODO Auto-generated constructor stub
		this.selectedNodes = new LinkedHashSet<Integer>();
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public TreeNode getTree() {
		return tree;
	}
	public void setTree(TreeNode tree) {
		this.tree = tree;
	}
	
	public Set<Integer> getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(Set<Integer> selectedNodes) {
		this.selectedNodes = selectedNodes;
	}







	private User user;
	private TreeNode tree;
	private Set<Integer> selectedNodes;
}
