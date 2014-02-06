package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.ClassCourse;

public class ClassCourseDAO extends DAO<ClassCourse>{

	public ClassCourseDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}
	
	public ClassCourseDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(ClassCourse object) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO class_course (class_id, course_id, period_id, hours, credits, course_description, created_on, modified_on) "
				+ "values (?,?,?,?,?,?,NOW(),NOW())";
		
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, object.getClassId());
			pst.setInt(2, object.getCourseId());
			pst.setInt(3, object.getPeriodId());
			pst.setInt(4, object.getHours());
			pst.setInt(5, object.getCredits());
			pst.setString(6, object.getCourseDescription());
			
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
	public boolean update(ClassCourse object) {
		// TODO Auto-generated method stub
		String sql = "UPDATE class_course SET class_id=?, course_id=?, period_id=?, hours=?, credits=?, course_description=?, modified_on = NOW() "
				+ "WHERE class_course_id = ?) ";
				
		
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, object.getClassId());
			pst.setInt(2, object.getCourseId());
			pst.setInt(3, object.getPeriodId());
			pst.setInt(4, object.getHours());
			pst.setInt(5, object.getCredits());
			pst.setString(6, object.getCourseDescription());
			pst.setInt(7, object.getClassCourseId());
			
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
	public boolean delete(ClassCourse object) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM class_course where class_course_id = ?";
		
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, object.getClassCourseId());
						
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
	
	public boolean softDelete(ClassCourse object) {
		String sql = "UPDATE class_course SET deleted = ?"
				+ " WHERE class_course_id = ? ";				
		
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
	
	public boolean restore(ClassCourse object) {
		String sql = "UPDATE class_course SET hours=?, credits=?, course_description=?, created_on = NOW(), modified_on = NOW(), deleted = ?"
				+ " WHERE class_id = ? and period_id = ? and course_id = ?) ";				
		
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
	
			
			pst.setInt(1, object.getHours());
			pst.setInt(2, object.getCredits());
			pst.setString(3, object.getCourseDescription());
			pst.setBoolean(4, false);
			pst.setInt(5, object.getClassId());
			pst.setInt(6, object.getPeriodId());
			pst.setInt(7, object.getCourseId());
						
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
	
	public boolean isSoftDeleted(ClassCourse object) {
		String sql = "SELECT class_course_id FROM class_course where class_id = ? and period_id = ? and course_id = ? and deleted = ?";
		ResultSet res = null;
		PreparedStatement pst = null;
		boolean result = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, object.getClassId());
			pst.setInt(2, object.getPeriodId());
			pst.setInt(3, object.getCourseId());
			pst.setBoolean(4, true);
			
			res = pst.executeQuery();
			
			result = res.next() ;
			
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
		
		return result;
	}

	@Override
	public ClassCourse find(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM class_course where class_course_id = ?";
		ResultSet res = null;
		PreparedStatement pst = null;
		ClassCourse classCourse = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, id);
			
			res = pst.executeQuery();
			
			if(res.next()) {
				classCourse = new ClassCourse();
				classCourse.setClassCourseId(res.getInt("class_course_id"));
				classCourse.setClassId(res.getInt("class_id"));
				classCourse.setCourseId(res.getInt("course_id"));
				classCourse.setPeriodId(res.getInt("period_id"));
				classCourse.setHours(res.getInt("hours"));
				classCourse.setCredits(res.getInt("credits"));
				classCourse.setCourseDescription(res.getString("course_description"));
				classCourse.setCreatedOn(res.getDate("created_on"));
				classCourse.setModified(res.getDate("modified_on"));
				classCourse.setDeleted(res.getBoolean("deleted"));
				classCourse.setCourse(new CourseDAO().find(classCourse.getCourseId()));
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
		
		return classCourse;
	}
	
	
	public List<ClassCourse> findTeacherCourses(int teacherId) {
		List<ClassCourse> classCourses = new ArrayList<ClassCourse>();
		
		String sql = "SELECT * FROM teacher_course where teacher_id=?";
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, teacherId);
			
			
			res = pst.executeQuery();
			
			while(res.next()) {
				System.out.println(" Class course id : "+res.getInt("classe_course_id"));
				ClassCourse c = this.find(res.getInt("classe_course_id"));
				
				classCourses.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return classCourses;
	}
	
	public List<ClassCourse> findClassCourses(int classId, int periodId) {
		List<ClassCourse> courses = new ArrayList<ClassCourse>();
		
		String sql = "SELECT class_course_id FROM class_course where class_id = ? and period_id = ? and deleted=?";
		
		PreparedStatement pst = null;
		ResultSet res = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, classId);
			pst.setInt(2, periodId);
			pst.setBoolean(3, false);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				courses.add(this.find(res.getInt("class_course_id")));
			}
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
		
		return courses;
	}

	public List<ClassCourse> findClassCourses(int classId, int periodId, int teacherId) {
		List<ClassCourse> courses = new ArrayList<ClassCourse>();
		//TODO Takes into consideration periodId to know which period 
		String sql = "SELECT classe_course_id FROM teacher_course where teacher_id = ? and deleted=?";
		System.out.println("SELECT classe_course_id FROM teacher_course where teacher_id = "+teacherId+" and deleted=?");
		PreparedStatement pst = null;
		ResultSet res = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, teacherId);
			//pst.setInt(2, teacherId);
			pst.setBoolean(2, false);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				courses.add(this.find(res.getInt("classe_course_id")));
			}
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
		
		return courses;
	} 
}
