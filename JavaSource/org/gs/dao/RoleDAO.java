package org.gs.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.gs.db.SingletonConnection;
import org.gs.model.Role;

public class RoleDAO extends DAO<Role> {

	public RoleDAO() {
		super(SingletonConnection.getInstance());
	}

	@Override
	public boolean create(Role object) {
		// TODO Auto-generated method stub
		return false;
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
		String req = "SELECT * FROM Roles where role_id = ?";
		
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
}
