package org.gs.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.Role;

public class RoleDAO extends DAO<Role> {

	public RoleDAO() {
		super(SingletonConnection.getInstance());
	}

	@Override
	public boolean create(Role object) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO roles (role_name,description,build_in,can_delete,login_destination)"
				+ " VALUES (?,?,?,?,?)";
		PreparedStatement pst = null;
		boolean res = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setString(1, object.getRoleName());
			pst.setString(2, object.getRoleDescription());
			pst.setBoolean(3, object.isBuildIn());
			pst.setBoolean(4, object.isCanDelete());
			pst.setString(5, object.getLoginDestination());
			
			res = pst.executeUpdate()>0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}

	@Override
	public boolean update(Role object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Role object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Role find(int id) {
		// TODO Auto-generated method stub
		String req = "SELECT * FROM roles where role_id = ?";
		
		ResultSet res = null;
		PreparedStatement st = null;
		Role r = null;
		try {
			
			st = this.con.prepareStatement(req);
			
			st.setInt(1, id);
			
			res = st.executeQuery();
			
			if(res.next()) {
				r = new Role();
				
				r.setRoleId(res.getInt("role_id"));
				r.setRoleDescription(res.getString("description"));
				r.setRoleName(res.getString("role_name"));
				r.setBuildIn(res.getBoolean("build_in"));
				r.setCanDelete(res.getBoolean("can_delete"));
				r.setLoginDestination(res.getString("login_destination"));
				r.setDeleted(res.getBoolean("deleted"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	
	public List<Role> findAll() {
		List<Role> roles = new ArrayList<Role>();
		
		String sql = "Select * from roles";
		
		ResultSet res = null;
		PreparedStatement st = null;
		Role r = null;
		try {
			
			st = this.con.prepareStatement(sql);
			
			res = st.executeQuery();
			
			while(res.next()) {
				r = new Role();				
				r.setRoleId(res.getInt("role_id"));
				r.setRoleDescription(res.getString("description"));
				r.setRoleName(res.getString("role_name"));
				r.setBuildIn(res.getBoolean("build_in"));
				r.setCanDelete(res.getBoolean("can_delete"));
				r.setLoginDestination(res.getString("login_destination"));
				r.setDeleted(res.getBoolean("deleted"));
				
				roles.add(r);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return roles;
	}
}
