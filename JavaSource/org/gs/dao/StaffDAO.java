package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.Staff;
import org.gs.model.Teacher;
import org.gs.util.CommonUtils;

public class StaffDAO extends DAO<Staff>{

	public StaffDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}
	
	public StaffDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Staff object) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO staff(first_name,middle_name,last_name,gender,birth_date,staff_position_id,"
				+ "highest_diploma,country,city,adress,nationality,tel1,tel2,email,skype,user_id,created_by,created_on,modified_by,modified_on,deleted)"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,NOW(),?)";
		PreparedStatement pst = null;
		boolean res = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			
			pst.setString(1, object.getFirstName());
			pst.setString(2, object.getMiddleName());
			pst.setString(3, object.getLastName());
			pst.setString(4, object.getGender());
			pst.setString(5, object.getBirthDate()==null?
					null:CommonUtils.date2String(object.getBirthDate()));
			pst.setInt(6, object.getStaffPosition());
			pst.setString(7, object.getHighestDiploma());
			pst.setString(8, object.getCountry());
			pst.setString(9, object.getCity());
			pst.setString(10, object.getAdress());
			pst.setString(11, object.getNationality());
			pst.setString(12, object.getTel1());
			pst.setString(13, object.getTel2());
			pst.setString(14, object.getSkype());
			pst.setString(15, object.getEmail());
			pst.setInt(16, object.getUserId());
			pst.setInt(17, object.getCreatedBy());
			pst.setInt(18, object.getModifiedBy());
			pst.setBoolean(19, object.isDeleted());
			
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
	public boolean update(Staff object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Staff object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Staff find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Staff findByUserId(int id) {
		
		String sql = "SELECT * FROM staff where user_id = ?";
		PreparedStatement st = null;
		ResultSet res = null;
		Teacher t = null;
		try {
			st = this.con.prepareStatement(sql);
			st.setInt(1, id);
			
			res = st.executeQuery();
			
			while(res.next()) {
				t = new Teacher();
				t.setStaffId(res.getInt("staff_id"));
				t.setFirstName(res.getString("first_name"));
				t.setMiddleName(res.getString("middle_name"));
				t.setLastName(res.getString("last_name"));
				t.setGender(res.getString("gender"));
				t.setBirthDate(res.getDate("birth_date"));
				t.setStaffPosition(res.getInt("staff_position_id"));
				t.setHighestDiploma(res.getString("highest_diploma"));
				t.setCountry(res.getString("country"));
				t.setCity(res.getString("city"));
				t.setAdress(res.getString("adress"));
				t.setNationality(res.getString("nationality"));
				t.setTel1(res.getString("tel1"));
				t.setTel2(res.getString("tel2"));
				t.setEmail(res.getString("email"));
				t.setSkype(res.getString("skype"));
				t.setUserId(res.getInt("user_id"));
				t.setCreatedBy(res.getInt("created_by"));
				t.setCreatedOn(res.getDate("created_on"));
				t.setModifiedBy(res.getInt("modified_by"));
				t.setModifiedOn(res.getDate("modified_on"));
				t.setDeleted(res.getBoolean("deleted"));
								
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return t;
	}
	
	public List<Teacher> findAllTeachers() {
		List<Teacher> teachers = new ArrayList<Teacher>();
		
		String sql = "SELECT * FROM staff where staff_position_id = 1";
		Statement st = null;
		ResultSet res = null;
		
		try {
			st = this.con.createStatement();
			
			res = st.executeQuery(sql);
			
			while(res.next()) {
				Teacher t = new Teacher();
				t.setStaffId(res.getInt("staff_id"));
				t.setFirstName(res.getString("first_name"));
				t.setMiddleName(res.getString("middle_name"));
				t.setLastName(res.getString("last_name"));
				t.setGender(res.getString("gender"));
				t.setBirthDate(res.getDate("birth_date"));
				t.setStaffPosition(res.getInt("staff_position_id"));
				t.setHighestDiploma(res.getString("highest_diploma"));
				t.setCountry(res.getString("country"));
				t.setCity(res.getString("city"));
				t.setAdress(res.getString("adress"));
				t.setNationality(res.getString("nationality"));
				t.setTel1(res.getString("tel1"));
				t.setTel2(res.getString("tel2"));
				t.setEmail(res.getString("email"));
				t.setSkype(res.getString("skype"));
				t.setUserId(res.getInt("user_id"));
				t.setCreatedBy(res.getInt("created_by"));
				t.setCreatedOn(res.getDate("created_on"));
				t.setModifiedBy(res.getInt("modified_by"));
				t.setModifiedOn(res.getDate("modified_on"));
				t.setDeleted(res.getBoolean("deleted"));
				
				teachers.add(t);				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return teachers;
	}
	
	public List<Staff> findAll() {
		List<Staff> teachers = new ArrayList<Staff>();
		
		String sql = "SELECT * FROM staff";
		Statement st = null;
		ResultSet res = null;
		
		try {
			st = this.con.createStatement();
			
			res = st.executeQuery(sql);
			
			while(res.next()) {
				Staff t = new Staff();
				t.setStaffId(res.getInt("staff_id"));
				t.setFirstName(res.getString("first_name"));
				t.setMiddleName(res.getString("middle_name"));
				t.setLastName(res.getString("last_name"));
				t.setGender(res.getString("gender"));
				t.setBirthDate(res.getDate("birth_date"));
				t.setStaffPosition(res.getInt("staff_position_id"));
				t.setHighestDiploma(res.getString("highest_diploma"));
				t.setCountry(res.getString("country"));
				t.setCity(res.getString("city"));
				t.setAdress(res.getString("adress"));
				t.setNationality(res.getString("nationality"));
				t.setTel1(res.getString("tel1"));
				t.setTel2(res.getString("tel2"));
				t.setEmail(res.getString("email"));
				t.setSkype(res.getString("skype"));
				t.setUserId(res.getInt("user_id"));
				t.setCreatedBy(res.getInt("created_by"));
				t.setCreatedOn(res.getDate("created_on"));
				t.setModifiedBy(res.getInt("modified_by"));
				t.setModifiedOn(res.getDate("modified_on"));
				t.setDeleted(res.getBoolean("deleted"));
				
				teachers.add(t);				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return teachers;
	}

}
