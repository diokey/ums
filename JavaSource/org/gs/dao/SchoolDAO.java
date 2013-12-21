package org.gs.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.School;

public class SchoolDAO extends DAO<School> {

	public SchoolDAO() {
		super(SingletonConnection.getInstance());
	}
	
	
	public SchoolDAO(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public boolean create(School object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(School object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(School object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public School find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<School> findAll() {
		List<School> schools = new ArrayList<School>();
		
		String req = "SELECT * FROM school";
		
		ResultSet res = null;
		Statement statement = null;
		
		try {
			statement = this.con.createStatement();
			
			res = statement.executeQuery(req);
			
			while(res.next()) {
				School s = new School();
				s.setSchoolId(res.getInt("school_id"));
				s.setTitle(res.getString("title"));
				s.setAdress(res.getString("adress"));
				s.setPhone(res.getString("phone"));
				s.setPrincipal(res.getString("principal"));
				s.setEmail(res.getString("email"));
				s.setSchoolYears(new SchoolYearDAO().findBySchool(s.getSchoolId()));
				schools.add(s);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return schools;
	}

}
