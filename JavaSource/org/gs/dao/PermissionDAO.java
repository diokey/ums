package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.Permission;
import org.gs.model.Role;

public class PermissionDAO extends DAO<Permission>{

	public PermissionDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}
	
	public PermissionDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(Permission object) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO permissions (name,description,status) VALUES (?,?,?)";
		boolean rep = false;
		
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setString(1, object.getName());
			pst.setString(2, object.getDescription());
			pst.setString(3, object.getStatus());
			
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
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return rep;
	
	}
	
	public boolean createRolePermission(Role r,Permission p) {
		boolean rep = false;
		String sql = "INSERT INTO role_permissions (role_id,permission_id) VALUES(?,?)";
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, r.getRoleId());
			pst.setInt(2, p.getPermissionId());
			
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
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return rep;
	}

	@Override
	public boolean update(Permission object) {
		// TODO Auto-generated method stub
		String sql = "UPDATE permissions SET name=?, description=?, status=? where permission_id=?";
		
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setString(1, object.getName());
			pst.setString(2, object.getDescription());
			pst.setString(3, object.getStatus());
			pst.setInt(4, object.getPermissionId());
			
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
	public boolean delete(Permission object) {
		// TODO Auto-generated method stub
		if(object==null)
			return false;
		
		String sql = "DELETE FROM permissions where permission_id=?";
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, object.getPermissionId());
			
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
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return rep;
	}
	
	/**
	 * Removes a permission to all roles in the table.
	 * @param permission Permission object. Basically, only the permission is needed
	 * @return returns true if operations successful, false otherwise
	 */
	public boolean revokePermissionToAllRoles(Permission permission) {
		boolean res = false;
		
		String sql = "DELETE FROM role_permissions where permission_id=?";
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			
			pst.setInt(1, permission.getPermissionId());
			
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
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	/**
	 * Removes a permission from a specified role.
	 * @param role . Basically, only the role id is needed
	 * @param permission Permission object. Basically, only the permission id is needed
	 * @return returns true if operations successful, false otherwise
	 */
	public boolean deleteRolePermission(Role role, Permission permission) {
		boolean res = false;
		
		String sql = "DELETE FROM role_permissions where role_id=? and permission_id=?";
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, role.getRoleId());
			pst.setInt(2, permission.getPermissionId());
			
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
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}

	@Override
	public Permission find(int id) {
		// TODO Auto-generated method stub
		Permission permission = null;
		String sql = "SELECT * FROM permissions where permission_id=?";
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, id);
			
			res = pst.executeQuery();
			
			if(res.next()) {
				permission = new Permission();
				
				permission.setPermissionId(res.getInt("permission_id"));
				permission.setName(res.getString("name"));
				permission.setDescription(res.getString("description"));
				permission.setStatus(res.getString("status"));
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
		
		return permission;
	}
	
	public boolean roleHasPermission(Role r, Permission p) {
		boolean rep = false;
		String sql = "SELECT * FROM role_permissions where role_id=? and permission_id=?";
		
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, r.getRoleId());
			pst.setInt(2, p.getPermissionId());
			
			res = pst.executeQuery();
			
			if(res.next()) {
				rep = true;
			}else{
				rep = false;
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
		
		return rep;
	}
	
	public Permission findByName(String permissionName) {
		Permission permission = null;
		String sql = "SELECT * FROM permissions where name=?";
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setString(1, permissionName);
			
			res = pst.executeQuery();
			
			if(res.next()) {
				permission = new Permission();
				
				permission.setPermissionId(res.getInt("permission_id"));
				permission.setName(res.getString("name"));
				permission.setDescription(res.getString("description"));
				permission.setStatus(res.getString("status"));
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
		
		return permission;
	}
	
	public List<Permission> findRolePermission(int roleId) {
		
		List<Permission> permissions = new ArrayList<Permission>();
		
		String sql = "SELECT permission_id FROM role_permissions WHERE role_id = ?";
		//System.out.println("SELECT permission_id FROM role_permissions WHERE role_id = "+roleId);
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, roleId);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				permissions.add(this.find(res.getInt("permission_id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		return permissions;
	}
	
	public List<Permission> findAll() {
		List<Permission> permissions = new ArrayList<Permission>();
		
		String sql = "Select * from permissions";
		PreparedStatement pst = null;
		ResultSet res = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				Permission p = new Permission();
				
				p.setPermissionId(res.getInt("permission_id"));
				p.setName(res.getString("name"));
				p.setDescription(res.getString("description"));
				p.setStatus(res.getString("status"));
				
				permissions.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return permissions;
	}

}
