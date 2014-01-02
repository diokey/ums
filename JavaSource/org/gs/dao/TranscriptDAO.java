package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.Transcript;

public class TranscriptDAO extends DAO<Transcript>{

	public TranscriptDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}
	
	public TranscriptDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(Transcript object) {
		// TODO Auto-generated method stub
		String req = "INSERT INTO transcript_detail (registration_id,class_course_id, hours_taken, grad, graded, grade, gp, gpa, created_on, created_by, modified_on, modified_by) "
				+ "values (?,?,?,?,?,?,?,?,NOW(),?,NOW(),?)";
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(req);
			pst.setInt(1, object.getRegistrationId());
			pst.setInt(2, object.getClassCourseId());
			pst.setInt(3, object.getHoursTaken());
			pst.setInt(4, object.getGrad());
			pst.setInt(5, object.getGraded());
			pst.setString(6, object.getGrade());
			pst.setFloat(7, object.getGp());
			pst.setFloat(8, object.getGpa());
			pst.setInt(9, object.getCreatedBy());
			pst.setInt(10, object.getModifiedBy());
			
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
	public boolean update(Transcript object) {
		// TODO Auto-generated method stub
		String req = "UPDATE transcript_detail set registration_id=?,class_course_id=?, hours_taken=?, grad=?, graded=?, grade=?, gp=?, gpa=?, modified_on=NOW(), modified_by=? "
				+ "WHERE transcript_detail_id=? ";
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(req);
			pst.setInt(1, object.getRegistrationId());
			pst.setInt(2, object.getClassCourseId());
			pst.setInt(3, object.getHoursTaken());
			pst.setInt(4, object.getGrad());
			pst.setInt(5, object.getGraded());
			pst.setString(6, object.getGrade());
			pst.setFloat(7, object.getGp());
			pst.setFloat(8, object.getGpa());
			pst.setInt(9, object.getModifiedBy());
			pst.setInt(10, object.getTranscriptId());
			
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
	public boolean delete(Transcript object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Transcript find(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM transcript_detail where transcript_detail_id=?";
		Transcript tr = null;
		
		PreparedStatement pst = null;
		ResultSet res = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, id);
			
			res = pst.executeQuery();
			
			if(res.next()) {
				tr = new Transcript();
				tr.setTranscriptId(res.getInt("transcript_id"));
				tr.setClassCourseId(res.getInt("class_course_id"));
				tr.setRegistrationId(res.getInt("registration_id"));
				tr.setHoursTaken(res.getInt("hours_taken"));
				tr.setGrad(res.getInt("grad"));
				tr.setGraded(res.getInt("graded"));
				tr.setGrade(res.getString("grade"));
				tr.setGp(res.getFloat("gp"));
				tr.setGpa(res.getFloat("gpa"));
				tr.setCreatedOn(res.getDate("created_on"));
				tr.setModifiedOn(res.getDate("modified_on"));
				tr.setCreatedBy(res.getInt("created_by"));
				tr.setModifiedBy(res.getInt("modified_by"));
				tr.setDeleted(res.getBoolean("deleted"));
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
		
		
		return tr;
	}
	
	public List<Transcript> studentsList(int classId, int periodId, int courseId) {
		String sql = "SELECT registration_id from registration where class_id=? and period_id=?";
		
		List<Transcript> transcriptList = new ArrayList<Transcript>();
		Transcript tr = null;
		
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet res = null;
		ResultSet res2 = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, classId);
			pst.setInt(2, periodId);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				tr = new Transcript();
				
				tr.setRegistrationId(res.getInt("registration_id"));
				tr.setRegistration(new RegistrationDAO().find(tr.getRegistrationId()));
				
				String req = "SELECT * FROM transcript_detail where registration_id=? and class_course_id = ?";
				
				pst2 = this.con.prepareStatement(req);
				pst2.setInt(1,tr.getRegistrationId());
				pst2.setInt(2, courseId);
				
				res2 = pst2.executeQuery();
				
				if(res2.next()) {
					tr.setTranscriptId(res2.getInt("transcript_detail_id"));
					tr.setClassCourseId(res2.getInt("class_course_id"));
					tr.setHoursTaken(res2.getInt("hours_taken"));
					tr.setGrad(res2.getInt("grad"));
					tr.setGraded(res2.getInt("graded"));
					tr.setGrade(res2.getString("grade"));
					tr.setGp(res2.getFloat("gp"));
					tr.setGpa(res2.getFloat("gpa"));
					tr.setCreatedOn(res2.getDate("created_on"));
					tr.setModifiedOn(res2.getDate("modified_on"));
					tr.setCreatedBy(res2.getInt("created_by"));
					tr.setModifiedBy(res2.getInt("modified_by"));
					tr.setDeleted(res2.getBoolean("deleted"));
				}
				
				transcriptList.add(tr);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				
				res2.close();
				res.close();
				pst2.close();
				pst.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return transcriptList;
	}
	
	public List<Transcript> studentGrades(int classId, int periodId, int registrationId) {
		
		String sql = "SELECT class_course_id from class_course where class_id=? and period_id=? and deleted = 0";
		
		List<Transcript> transcriptList = new ArrayList<Transcript>();
		Transcript tr = null;
		
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet res = null;
		ResultSet res2 = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, classId);
			pst.setInt(2, periodId);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				tr = new Transcript();
				tr.setClassCourseId(res.getInt("class_course_id"));
				tr.setClassCourse(new ClassCourseDAO().find(res.getInt("class_course_id")));
				
				String req = "SELECT * FROM transcript_detail where registration_id=? and class_course_id = ?";
				
				pst2 = this.con.prepareStatement(req);
				pst2.setInt(1,registrationId);
				pst2.setInt(2, tr.getClassCourseId());
				
				res2 = pst2.executeQuery();
				
				if(res2.next()) {
					tr.setTranscriptId(res2.getInt("transcript_detail_id"));
					tr.setRegistrationId(res2.getInt("registration_id"));
					tr.setRegistration(new RegistrationDAO().find(tr.getRegistrationId()));
					tr.setClassCourseId(res2.getInt("class_course_id"));
					tr.setHoursTaken(res2.getInt("hours_taken"));
					tr.setGrad(res2.getInt("grad"));
					tr.setGraded(res2.getInt("graded"));
					tr.setGrade(res2.getString("grade"));
					tr.setGp(res2.getFloat("gp"));
					tr.setGpa(res2.getFloat("gpa"));
					tr.setCreatedOn(res2.getDate("created_on"));
					tr.setModifiedOn(res2.getDate("modified_on"));
					tr.setCreatedBy(res2.getInt("created_by"));
					tr.setModifiedBy(res2.getInt("modified_by"));
					tr.setDeleted(res2.getBoolean("deleted"));
				}else{
					tr.setGrad(tr.getClassCourse().getCredits());
					tr.setGraded(tr.getClassCourse().getCredits());
					tr.setHoursTaken(tr.getClassCourse().getCredits());
				}
				
				transcriptList.add(tr);
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
		return transcriptList;
	}
	
	public List<Transcript> registrationTranscript(int registrationId) {
		List<Transcript> transcripts = new ArrayList<Transcript>();
		
		String req = "SELECT * FROM transcript_detail where registration_id=?";
		//System.out.println("SELECT * FROM transcript_detail where registration_id="+registrationId);
		
		PreparedStatement pst = null;
		ResultSet res = null;
		
		try {
			pst = this.con.prepareStatement(req);
			pst.setInt(1, registrationId);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				Transcript tr = new Transcript();
				tr.setTranscriptId(res.getInt("transcript_detail_id"));
				tr.setRegistrationId(res.getInt("registration_id"));
				tr.setClassCourseId(res.getInt("class_course_id"));
				tr.setClassCourse(new ClassCourseDAO().find(res.getInt("class_course_id")));
				tr.setHoursTaken(res.getInt("hours_taken"));
				tr.setGrad(res.getInt("grad"));
				tr.setGraded(res.getInt("graded"));
				tr.setGrade(res.getString("grade"));
				tr.setGp(res.getFloat("gp"));
				tr.setGpa(res.getFloat("gpa"));
				tr.setCreatedOn(res.getDate("created_on"));
				tr.setModifiedOn(res.getDate("modified_on"));
				tr.setCreatedBy(res.getInt("created_by"));
				tr.setModifiedBy(res.getInt("modified_by"));
				tr.setDeleted(res.getBoolean("deleted"));
				
				transcripts.add(tr);
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
		
		return transcripts;
	}
	
	public boolean transcriptGenerated(int registrationId) {
		boolean rep = false;
		
		String sql = "SELECT transcript_id from transcript where registration_id=?";
		
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, registrationId);
			
			res = pst.executeQuery();
			
			rep = res.next();
			
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
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}	
				
		return rep;
	}
	
	public boolean saveTotal(Transcript object) {
		boolean rep = false;
		
		String sql = "INSERT INTO transcript (registration_id,hours_taken,grad,graded,grade,gp,gpa,created_on,modified_on,created_by,modified_by)"
				+ " values(?,?,?,?,?,?,?,NOW(),NOW(),?,?)";
		
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, object.getRegistrationId());
			pst.setInt(2, object.getHoursTaken());
			pst.setInt(3, object.getGrad());
			pst.setInt(4, object.getGraded());
			pst.setString(5, object.getGrade());
			pst.setFloat(6, object.getGp());
			pst.setFloat(7, object.getGpa());
			pst.setInt(8, object.getCreatedBy());
			pst.setInt(9, object.getModifiedBy());
			
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
