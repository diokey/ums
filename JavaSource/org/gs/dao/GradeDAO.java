package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.Grade;

public class GradeDAO extends DAO<Grade>{

	public GradeDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}
	
	public GradeDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(Grade object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Grade object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Grade object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Grade find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Grade> findAll(int schoolType) {
		List<Grade> grades = new ArrayList<Grade>();
		Grade g = null;
		ResultSet res = null;
		PreparedStatement pst = null;
		
		String sql = "SELECT * FROM grade where school_type = ?";
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, schoolType);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				g = new Grade();
				
				g.setGradeId(res.getInt("gradeId"));
				g.setMinMarks(res.getFloat("marks_min"));
				g.setMaxMarks(res.getFloat("marks_max"));
				g.setGradePoint(res.getFloat("grade_point"));
				g.setLevelAchievement(res.getString("level_achievement"));
				g.setSchoolType(res.getInt("school_type"));
				g.setDeleted(res.getBoolean("deleted"));
				g.setLetterCode(res.getString("letter_code"));
				
				grades.add(g);
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
		
		return grades;
	}

}
