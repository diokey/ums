package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.User;

public class UserDAO extends DAO<User>{

	public UserDAO() {
		super(SingletonConnection.getInstance());
	}

	public UserDAO(Connection con) {
		super(con);
	}
	
	
	@Override
	public boolean create(User object) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO users (role_id,email,username,password_hash,created_on,banned,display_name,language,active,force_password_reset) "
				+ "values (?,?,?,?,NOW(),?,?,?,?,?) ";
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			
			pst.setInt(1, object.getUserRoleId());
			pst.setString(2, object.getEmail());
			pst.setString(3, object.getUsername());
			pst.setString(4, object.getUserPassword());
			pst.setBoolean(5, object.isBanned());
			pst.setString(6, object.getDisplayName());
			pst.setString(7, object.getLanguage());
			pst.setBoolean(8, object.isActive());
			pst.setBoolean(9, object.isForcePasswordReset());
			
			rep = pst.executeUpdate()>0;
			
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
		
		return rep;
	}

	@Override
	public boolean update(User object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(User object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User find(int id) {
		// TODO Auto-generated method stub
		User selected = null;
		
		String req = "SELECT * FROM users where id = ?";
		
		PreparedStatement statement = null;
		ResultSet res = null;
		
		try {
			statement = this.con.prepareStatement(req);
			statement.setInt(1, id);
			
			res = statement.executeQuery();
			
			if(res.next()) {
				
				selected = new User();
				selected.setUserId(res.getInt("id"));
				selected.setUserRoleId(res.getInt("role_id"));
				selected.setEmail(res.getString("email"));
				selected.setUsername(res.getString("username"));
				selected.setUserPassword(res.getString("password_hash"));
				selected.setResetHash(res.getString("reset_hash"));
				selected.setLastLogin(res.getString("last_login"));
				selected.setLastIp(res.getString("last_ip"));
				selected.setDeleted(res.getBoolean("deleted"));
				selected.setResetBy(res.getInt("reset_by"));
				selected.setBanned(res.getBoolean("banned"));
				selected.setBannedMessage(res.getString("ban_message"));
				selected.setDisplayName(res.getString("display_name"));
				selected.setLanguage(res.getString("language"));
				selected.setActive(res.getBoolean("active"));
				selected.setPasswordIterations(res.getInt("password_iterations"));
				selected.setForcePasswordReset(res.getBoolean("force_password_reset"));
				selected.setUserRole(new RoleDAO().find(selected.getUserRoleId()));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return selected;
	}
	
	public User checkLogin(String username, String password) {
		User selected = null;
		
		String req = "SELECT * FROM users where username = ? and password_hash = ?";
		
		PreparedStatement statement = null;
		ResultSet res = null;
		
		try {
			statement = this.con.prepareStatement(req);
			statement.setString(1, username);
			statement.setString(2, password);
			
			res = statement.executeQuery();
			
			if(res.next()) {
				
				selected = new User();
				selected.setUserId(res.getInt("id"));
				selected.setUserRoleId(res.getInt("role_id"));
				selected.setEmail(res.getString("email"));
				selected.setUsername(res.getString("username"));
				selected.setUserPassword(res.getString("password_hash"));
				selected.setResetHash(res.getString("reset_hash"));
				selected.setLastLogin(res.getString("last_login"));
				selected.setLastIp(res.getString("last_ip"));
				selected.setDeleted(res.getBoolean("deleted"));
				selected.setResetBy(res.getInt("reset_by"));
				selected.setBanned(res.getBoolean("banned"));
				selected.setBannedMessage(res.getString("ban_message"));
				selected.setDisplayName(res.getString("display_name"));
				selected.setLanguage(res.getString("language"));
				selected.setActive(res.getBoolean("active"));
				selected.setPasswordIterations(res.getInt("password_iterations"));
				selected.setForcePasswordReset(res.getBoolean("force_password_reset"));
				selected.setUserRole(new RoleDAO().find(selected.getUserRoleId()));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return selected;
	}
	
	public User findBy(String key, String value) {
		User selected = null;
		
		String req = "SELECT * FROM users where "+key+" = ?";
		
		PreparedStatement statement = null;
		ResultSet res = null;
		
		try {
			statement = this.con.prepareStatement(req);
			statement.setString(1, value);
			
			res = statement.executeQuery();
			
			if(res.next()) {
				
				selected = new User();
				selected.setUserId(res.getInt("id"));
				selected.setUserRoleId(res.getInt("role_id"));
				selected.setEmail(res.getString("email"));
				selected.setUsername(res.getString("username"));
				selected.setUserPassword(res.getString("password_hash"));
				selected.setResetHash(res.getString("reset_hash"));
				selected.setLastLogin(res.getString("last_login"));
				selected.setLastIp(res.getString("last_ip"));
				selected.setDeleted(res.getBoolean("deleted"));
				selected.setResetBy(res.getInt("reset_by"));
				selected.setBanned(res.getBoolean("banned"));
				selected.setBannedMessage(res.getString("ban_message"));
				selected.setDisplayName(res.getString("display_name"));
				selected.setLanguage(res.getString("language"));
				selected.setActive(res.getBoolean("active"));
				selected.setPasswordIterations(res.getInt("password_iterations"));
				selected.setForcePasswordReset(res.getBoolean("force_password_reset"));
				selected.setUserRole(new RoleDAO().find(selected.getUserRoleId()));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				res.close();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return selected;
	}
	
	public List<User> findAll() {
		List<User> users = new ArrayList<User>(); 
		String req = "SELECT * FROM users";
		
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			pst = SingletonConnection.getInstance().prepareStatement(req);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				
				User u = new User();
				u.setUserId(res.getInt("id"));
				u.setUserRoleId(res.getInt("role_id"));
				u.setEmail(res.getString("email"));
				u.setUsername(res.getString("username"));
				u.setUserPassword(res.getString("password_hash"));
				u.setResetHash(res.getString("reset_hash"));
				u.setLastLogin(res.getString("last_login"));
				u.setLastIp(res.getString("last_ip"));
				u.setDeleted(res.getBoolean("deleted"));
				u.setResetBy(res.getInt("reset_by"));
				u.setBanned(res.getBoolean("banned"));
				u.setBannedMessage(res.getString("ban_message"));
				u.setDisplayName(res.getString("display_name"));
				u.setLanguage(res.getString("language"));
				u.setActive(res.getBoolean("active"));
				u.setPasswordIterations(res.getInt("password_iterations"));
				u.setForcePasswordReset(res.getBoolean("force_password_reset"));
				u.setUserRole(new RoleDAO().find(u.getUserRoleId()));
				
				users.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				res.close();
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return users;
	}

	public List<User> getUsersByRole(int roleId) {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>(); 
		String req = "SELECT * FROM users where role_id = ?";
		
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			pst = this.con.prepareStatement(req);
			pst.setInt(1, roleId);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				
				User u = new User();
				u.setUserId(res.getInt("id"));
				u.setUserRoleId(res.getInt("role_id"));
				u.setEmail(res.getString("email"));
				u.setUsername(res.getString("username"));
				u.setUserPassword(res.getString("password_hash"));
				u.setResetHash(res.getString("reset_hash"));
				u.setLastLogin(res.getString("last_login"));
				u.setLastIp(res.getString("last_ip"));
				u.setDeleted(res.getBoolean("deleted"));
				u.setResetBy(res.getInt("reset_by"));
				u.setBanned(res.getBoolean("banned"));
				u.setBannedMessage(res.getString("ban_message"));
				u.setDisplayName(res.getString("display_name"));
				u.setLanguage(res.getString("language"));
				u.setActive(res.getBoolean("active"));
				u.setPasswordIterations(res.getInt("password_iterations"));
				u.setForcePasswordReset(res.getBoolean("force_password_reset"));
				u.setUserRole(new RoleDAO().find(u.getUserRoleId()));
				
				users.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				res.close();
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return users;
	}
	
}
