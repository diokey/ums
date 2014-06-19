package org.gs.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.gs.dao.ClassAccessDAO;
import org.gs.dao.RoleDAO;
import org.gs.dao.UserDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.ClassAccess;
import org.gs.model.Role;
import org.gs.model.RoleClassAccess;
import org.gs.model.Structure;
import org.gs.model.User;
import org.gs.model.UserClassAccess;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.TreeNode;

@ManagedBean
@ViewScoped
public class ClassAccessBean {

	public void saveAccess(int roleIndex, int userIndex) {
		Set<Integer> data = new LinkedHashSet<Integer>();
		for(TreeNode node : this.selectedClasses) {
			
			if(node.isLeaf()) {
				this.getParents(node, data);
			}else {
				this.getParents(node, data);
				this.getChildren(node, data);
			}
		}
		
		Set<Integer> dataRemoved = new LinkedHashSet<Integer>();
		for(TreeNode node : this.tobeRemoved) {
			
			if(node.isLeaf()) {
				Structure s = (Structure) node.getData();
				dataRemoved.add(s.getId());
			}else {
				this.getChildren(node, dataRemoved);
			}
		}
		
		RoleClassAccess role = null;
		
		for(RoleClassAccess temp : this.roleAccessList) {
			if(temp.getRoleId()==roleIndex) {
				role = temp;
				break;
			}
		}
		
		if(role==null)
			return;
		
		UserClassAccess u = role.getUserAccess().get(userIndex);
		Set<Integer> access = u.getSelectedNodes();
		access.addAll(data);
		access.removeAll(dataRemoved);
		
		boolean success = true;
		ClassAccessDAO classAccessDao = new ClassAccessDAO();
		classAccessDao.deleteUserAccess(u.getUser().getUserId());
		for(Integer i : access) {
			ClassAccess classe = new ClassAccess();
			classe.setClassId(i);
			classe.setUserId(u.getUser().getUserId());
			
			if(!classAccessDao.create(classe)) {
				success = false;
			}
		}
		
		if(!success) {
			System.out.println("Error while saving");
		}else {
			System.out.println("Successfully saved");
		}
		
		
		System.out.println("log : "+access.size());
		System.out.println("size : "+this.selectedClasses.size());
		this.selectedClasses.clear();
		this.tobeRemoved.clear();
	}
	
	public void nodeSelected(NodeSelectEvent event) {
		
		this.selectedClasses.add(event.getTreeNode());
 	}
	
	public void nodeUnSelected(NodeUnselectEvent event) {

		this.selectedClasses.remove(event.getTreeNode());
		this.tobeRemoved.add(event.getTreeNode());
		
	}
	
	public void onRowExpansion(int roleIndex, int userIndex) {
		
		RoleClassAccess role = null;
		this.selectedClasses.clear();
		this.tobeRemoved.clear();
		
		for(RoleClassAccess temp : this.roleAccessList) {
			if(temp.getRoleId()==roleIndex) {
				role = temp;
				break;
			}
		}
		
		if(role==null)
			return;
		
		UserClassAccess u = role.getUserAccess().get(userIndex);
		System.out.println("nb nodes : "+u.getSelectedNodes().size());
	}
	
	private void getParents(TreeNode node, Set<Integer> result) {
		if(node==null || node.getData().equals("root"))
			return;
		
		Structure s = (Structure) node.getData();
		result.add(s.getId());
		
		getParents(node.getParent(),result);
	}
	
	private void getChildren(TreeNode node, Set<Integer> result) {
		Structure s = (Structure) node.getData();
		result.add(s.getId());
		List<TreeNode> nodes = node.getChildren();
		if(nodes==null)
			return;
		for(TreeNode n : nodes) {
			s = (Structure) n.getData();
			result.add(s.getId());
			if(!n.isLeaf()) {
				getChildren(n,result);
			}
		}
		
	}
	
	/*
	private Set<TreeNode> getSelectedClasses(TreeNode root, Set<TreeNode> result) {
		if(result==null) {
			result = new LinkedHashSet<TreeNode>();
		}
		if(root==null){
			return result;
		}
		if(!root.getData().equals("root")) {
			Structure s = (Structure) root.getData();
			result.add(s.getId());
			List<TreeNode> nodes = root.getChildren();
			for(TreeNode n : nodes) {
				this.getSelectedClasses(n, result);
			}
		}
		return result;
	}
	*/
	public List<UserClassAccess> users(int roleId) {
		
		List<User> userList = new ArrayList<User>();
		List<UserClassAccess> userClassAccess = new ArrayList<UserClassAccess>();
		UserDAO userDao = new UserDAO();
		userList = userDao.getUsersByRole(roleId);
		
		for(User u : userList) {
			 
			Object[] result = this.schoolTreeBean.userAccessTree(u.getUserId());
			TreeNode node = (TreeNode) result[0];
			Set<Integer> selected = (Set<Integer>) result[1];
			
			UserClassAccess access = new UserClassAccess();
			
			access.setUser(u);
			access.setTree(node);
			access.setSelectedNodes(selected);
			
			userClassAccess.add(access);
		}
		
		return userClassAccess;
	}
	
	@PostConstruct
	private void init() {
		roleDao = new RoleDAO();
		this.roleAccessList = new ArrayList<RoleClassAccess>();
		this.selectedClasses = new ArrayList<TreeNode>();
		this.tobeRemoved = new LinkedHashSet<TreeNode>();
		this.roles = roleDao.findAll();
				
		for(Role r : roles) {
			RoleClassAccess rca = new RoleClassAccess(r);
			List<UserClassAccess> users = this.users(r.getRoleId());
			
			rca.setUserAccess(users);
			
			this.roleAccessList.add(rca);
		}
	}
	
	public ClassAccessBean() {
		// TODO Auto-generated constructor stub
		System.out.println("Called");
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public SchoolTreeBean getSchoolTreeBean() {
		return schoolTreeBean;
	}

	public void setSchoolTreeBean(SchoolTreeBean schoolTreeBean) {
		this.schoolTreeBean = schoolTreeBean;
	}

	public List<RoleClassAccess> getRoleAccessList() {
		return roleAccessList;
	}

	public void setRoleAccessList(List<RoleClassAccess> roleAccessList) {
		this.roleAccessList = roleAccessList;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	public Set<TreeNode> getTobeRemoved() {
		return tobeRemoved;
	}

	public void setTobeRemoved(Set<TreeNode> tobeRemoved) {
		this.tobeRemoved = tobeRemoved;
	}




	private List<Role> roles;
	private Set<TreeNode> tobeRemoved;
	private List<TreeNode> selectedClasses;
	private TreeNode[] selectedNodes;
	private RoleDAO roleDao;
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;
	
	private List<RoleClassAccess> roleAccessList;
}
