package org.gs.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.gs.db.SingletonConnection;
import org.gs.model.TeacherCourse;

public class TeacherCourseDAO extends DAO<TeacherCourse>{

	public TeacherCourseDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}

	@Override
	public boolean create(TeacherCourse object) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO teacher_course(teacher_id,classe_course_id,created_by,modified_by,deleted,created_on,modified_on) "
				+ "values(?,?,?,?,?,NOW(),NOW())";
		
		boolean rep = false;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, object.getTeacherId());
			pst.setInt(2, object.getClassCourseId());
			pst.setInt(3, object.getCreatedBy());
			pst.setInt(4, object.getModifiedBy());
			pst.setBoolean(5, object.isDeleted());
			
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
				
			}
		}
		return rep;
	}

	@Override
	public boolean update(TeacherCourse object) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public TeacherCourse find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean delete(TeacherCourse object) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM teacher_course where teacher_course_id = ?";
		
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, object.getTeacherCourseId());
						
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
	
	public boolean deleteTeacherCourse(TeacherCourse object) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM teacher_course where teacher_id = ? and course_id = ?";
		
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, object.getTeacherCourseId());
			pst.setInt(2, object.getClassCourseId());
						
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
	
	public boolean softDelete(TeacherCourse object) {
		String sql = "UPDATE teacher_course SET deleted = ?"
				+ " WHERE teacher_course_id = ? ";				
		
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setBoolean(1, true);
			pst.setInt(2, object.getClassCourseId());
			
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
	
	
}
