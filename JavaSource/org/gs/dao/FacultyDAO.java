package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.gs.model.Faculty;

public class FacultyDAO extends DAO<Faculty> {

	public FacultyDAO(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Faculty object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Faculty object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Faculty object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Faculty find(int id) {
		// TODO Auto-generated method stub
		ResultSet res = null;
		try {
			PreparedStatement statement = this.con.prepareStatement("SELECT * FROM faculte where id=?");
			statement.setInt(1, id);
			
			res = statement.executeQuery();
			
			if(res.first()) {
				Faculty fac = new Faculty();
				fac.setFacultyId(res.getInt("faculty_id"));
				fac.setFacultyName("faculty_name");
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
