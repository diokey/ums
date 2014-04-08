package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.Student;
import org.gs.util.CommonUtils;

public class StudentDAO extends DAO<Student>{

	public StudentDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}
	
	public StudentDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(Student object) {
		// TODO Auto-generated method stub
		String req = "INSERT INTO student (prefix,first_name,gender,middle_name,last_name,birth_date,country,city,address,nationality,tel1,tel2,email,skype,blood_group,marital_status,user_id,created_on,modified_on)"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),NOW())";
		
		PreparedStatement st = null;
		boolean rep = false;
		
		
		try {
			st = this.con.prepareStatement(req);
			st.setString(1, object.getPrefix());
			st.setString(2, object.getName());
			st.setString(3, object.getGender());
			st.setString(4, object.getMiddleName());
			st.setString(5, object.getLastName());
			st.setString(6, object.getBirthDate()==null?
					null:CommonUtils.date2String(object.getBirthDate()));
			st.setString(7, object.getCountry());
			st.setString(8, object.getCity());
			st.setString(9, object.getAdress());
			st.setString(10, object.getNationality());
			st.setString(11, object.getTel1());
			st.setString(12, object.getTel2());
			st.setString(13, object.getEmail());
			st.setString(14, object.getSkype());
			st.setString(15, object.getBloodGroup());
			st.setString(16, object.getMaritalStatus());
			st.setInt(17, object.getUserId());
			
			rep = st.executeUpdate()>0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rep;
	}

	@Override
	public boolean update(Student object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Student object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Student find(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM student where student_id=?";
		ResultSet res = null;
		PreparedStatement pst = null;
		Student student = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, id);
			
			res = pst.executeQuery();
			
			if(res.next()) {
				student = new Student();
				
				student.setStudentId(res.getInt("student_id"));
				student.setPrefix(res.getString("prefix"));
				student.setName(res.getString("first_name"));
				student.setGender(res.getString("gender"));
				student.setLastName(res.getString("last_name"));
				student.setMiddleName(res.getString("middle_name"));
				student.setBirthDate(res.getDate("birth_date"));
				student.setCountry(res.getString("country"));
				student.setCity(res.getString("city"));
				student.setAdress(res.getString("address"));
				student.setNationality(res.getString("nationality"));
				student.setTel1(res.getString("tel1"));
				student.setTel2(res.getString("tel2"));
				student.setEmail(res.getString("email"));
				student.setSkype(res.getString("skype"));
				student.setBloodGroup(res.getString("blood_group"));
				student.setMaritalStatus(res.getString("marital_status"));
				student.setUserId(res.getInt("user_id"));
				student.setCreatedOn(res.getDate("created_on"));
				student.setDeleted(res.getBoolean("deleted"));
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
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return student;
	}
	
	public List<Student> findAll() {
		List<Student> allStudents = new ArrayList<Student>();
		
		
		return allStudents;
	}

}
