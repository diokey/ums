package org.gs.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.Role;
import org.gs.model.RolePermission;

public class RolePermissionDAO extends DAO<RolePermission>{

	public RolePermissionDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}
	
	public RolePermissionDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(RolePermission object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(RolePermission object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(RolePermission object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RolePermission find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<RolePermission> findAll() {
		List<RolePermission> rolePermissions = new ArrayList<RolePermission>();
		List<Role> roles = null;
		RoleDAO rdao = new RoleDAO();
		
		roles = rdao.findAll();
		
		for(Role r : roles) {
			RolePermission p = new RolePermission();
			p.setRole(r);
			
			rolePermissions.add(p);
		}
		
		return rolePermissions;
	}

}
