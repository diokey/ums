package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.ClassAccess;
import org.gs.model.Structure;

public class ClassAccessDAO extends DAO<ClassAccess> {

	public ClassAccessDAO() {
		super(SingletonConnection.getInstance());
	}
	
	public ClassAccessDAO(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	public List<Integer> getUserClassAccess(int userId) {
		String sql = "SELECT * FROM class_access where user_id = ?";
		List<Integer> userClassAccess = new ArrayList<Integer>();
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, userId);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				userClassAccess.add(res.getInt("school_structure_details_id"));
			}
		}catch(SQLException e){
			
		}finally{
			try {
				res.close();
				pst.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return userClassAccess;
	}
	
	public boolean deleteUserAccess(int userId) {
		// TODO Auto-generated method stub
				String sql = "DELETE FROM class_access WHERE user_id = ?";
				
				PreparedStatement pst = null;
				boolean result = false;
				
				try {
					pst = this.con.prepareStatement(sql);
					pst.setInt(1, userId);
					
					result = pst.execute();
						
					
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
				
				return result;
	}
	
	@Override
	public boolean create(ClassAccess object) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO class_access(school_structure_details_id,user_id)"
				+ " VALUES (?,?)";
		
		PreparedStatement pst = null;
		boolean result = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, object.getClassId());
			pst.setInt(2, object.getUserId());
			
			result = pst.execute();
				
			
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
		
		return result;
	}

	@Override
	public boolean update(ClassAccess object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ClassAccess object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ClassAccess find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
