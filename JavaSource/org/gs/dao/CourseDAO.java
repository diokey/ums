package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.Course;

public class CourseDAO extends DAO<Course>{

	public CourseDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}
	
	public CourseDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(Course object) {
		// TODO Auto-generated method stub
		String req = "insert into course (course_name,course_description,school_id,created_by,created_on) "
				+ "values (?,?,?,?,NOW()) ";
		PreparedStatement pst = null;
		boolean res = false;
		try {
			pst = this.con.prepareStatement(req);
			pst.setString(1, object.getCourseName());
			pst.setString(2, object.getCourseDescription());
			pst.setInt(3, object.getSchoolId());
			pst.setInt(4, object.getCreatedBy());
			
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
			}
		}
		
		return res;
	}

	@Override
	public boolean update(Course object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Course object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Course find(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM course where course_id = ?";
		
		ResultSet res = null;
		PreparedStatement pst = null;
		Course c = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, id);
			
			res = pst.executeQuery();
			
			if(res.next()) {
				c = new Course();
				c.setCourseId(res.getInt("course_id"));
				c.setCourseName(res.getString("course_name"));
				c.setCourseDescription(res.getString("course_description"));
				c.setCreatedBy(res.getInt("created_by"));
				c.setCreatedOn(res.getDate("created_on"));
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
		
		return c;
	}
	
	public List<Course> findAll() {
		List<Course> courseList = new ArrayList<Course>();
		
		String req = "SELECT * FROM course";
		ResultSet res = null;
		
		try {
			res = this.con.createStatement().executeQuery(req);
			
			while(res.next()) {
				Course c = new Course();
				c.setCourseId(res.getInt("course_id"));
				c.setCourseName(res.getString("course_name"));
				c.setCourseDescription(res.getString("course_description"));
				c.setCreatedBy(res.getInt("created_by"));
				c.setCreatedOn(res.getDate("created_on"));
				
				courseList.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return courseList;
	}

}
