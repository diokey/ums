package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.AssessmentGrade;
import org.gs.model.ClassCourse;
import org.gs.model.Grade;
import org.gs.model.Transcript;
import org.gs.util.SchoolType;

public class ClassRosterDAO extends DAO<AssessmentGrade> {

	public ClassRosterDAO() {
		super(SingletonConnection.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public ClassRosterDAO(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(AssessmentGrade object) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO class_roster(registration_id, class_course_id, note_1, note_2, note_3, note_4,"
				+ "note_5, note_6, note_7, note_8, note_9,note_10, total_cont_ass, total_final_exam, total_ass_exam, final_grade, letter_code) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		List<Float> notes = object.getAssessmentGrades();
		
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setFloat(1, object.getRegistration().getRegistrationId());
			pst.setFloat(2, object.getClassCourse().getClassCourseId());
			int i=3;
			for(Float note : notes) {
				pst.setObject(i, note);
				i++;
			}
			
			pst.setObject(i, object.getTotalContiniousAssessement());
			pst.setObject(i+1, object.getFinalExamMarks());
			pst.setObject(i+2, object.getTotalAssessmentPlusExam());
			pst.setObject(i+3, object.getFinalGrade());
			pst.setObject(i+4, object.getLetterCode());
			
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
	public boolean update(AssessmentGrade object) {
		String sql = "UPDATE class_roster set registration_id = ?, class_course_id = ?, note_1 = ?, note_2 = ?, note_3 = ?, note_4 = ?,"
				+ "note_5 = ?, note_6 = ?, note_7 = ?, note_8 = ?, note_9 = ?,note_10 = ?, total_cont_ass = ?, total_final_exam = ?, "
				+ "total_ass_exam = ?, final_grade = ?, letter_code = ? WHERE class_roster_id = ?";
		
		List<Float> notes = object.getAssessmentGrades();
		
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setFloat(1, object.getRegistration().getRegistrationId());
			pst.setFloat(2, object.getClassCourse().getClassCourseId());
			int i=3;
			for(Float note : notes) {
				pst.setObject(i, note);
				i++;
			}
			
			pst.setObject(i, object.getTotalContiniousAssessement());
			pst.setObject(i+1, object.getFinalExamMarks());
			pst.setObject(i+2, object.getTotalAssessmentPlusExam());
			pst.setObject(i+3, object.getFinalGrade());
			pst.setObject(i+4, object.getLetterCode());
			
			pst.setInt(i+5, object.getId());
			
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
	public boolean delete(AssessmentGrade object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AssessmentGrade find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<AssessmentGrade> studentsList(int classId, int periodId, int courseId) {
		String sql = "SELECT registration_id from registration where class_id=? and period_id=?";
		
		List<AssessmentGrade> gradesList = new ArrayList<AssessmentGrade>();
		AssessmentGrade ag = null;
		
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
				ag = new AssessmentGrade();
				ag.setRegistration(new RegistrationDAO().find(res.getInt("registration_id")));
				
				String req = "SELECT * FROM class_roster where registration_id=? and class_course_id = ?";
				
				pst2 = this.con.prepareStatement(req);
				pst2.setInt(1,ag.getRegistration().getRegistrationId());
				pst2.setInt(2, courseId);
				
				res2 = pst2.executeQuery();
				
				if(res2.next()) {
					ag.setId(res2.getInt("class_roster_id"));
					ag.setClassCourse(new ClassCourseDAO().find(res2.getInt("class_course_id")));
					
					List<Float> notes = new ArrayList<Float>();
					
					for(int i=1;i <= 10;i++) {
						Float n;
						if(res2.getString("note_"+i) == null || res2.getString("note_"+i).equalsIgnoreCase("null")) {
							n = null;
						}else {
							n = res2.getFloat("note_"+i);		
						}
						notes.add(n);
						
					}
					ag.setAssessmentGrades(notes);
					
					if(res2.getString("total_cont_ass") == null || res2.getString("total_cont_ass").equalsIgnoreCase("null") ){
						ag.setTotalContiniousAssessement(null);
					} else {
						ag.setTotalContiniousAssessement(res2.getFloat("total_cont_ass"));
					}
					
					
					if(res2.getString("total_final_exam") == null || res2.getString("total_final_exam").equalsIgnoreCase("null") ){
						ag.setFinalExamMarks(null);
					} else {
						ag.setFinalExamMarks(res2.getFloat("total_final_exam"));
					}
					
					if(res2.getString("total_ass_exam") == null || res2.getString("total_ass_exam").equalsIgnoreCase("null") ){
						ag.setTotalAssessmentPlusExam(null);
					} else {
						ag.setTotalAssessmentPlusExam(res2.getFloat("total_final_exam"));
					}
					
					if(res2.getString("final_grade") == null || res2.getString("final_grade").equalsIgnoreCase("null") ){
						ag.setFinalGrade(null);
					} else {
						ag.setFinalGrade(res2.getFloat("final_grade"));
					}
					if(res2.getString("letter_code") == null || res2.getString("letter_code").equalsIgnoreCase("null") ){
						ag.setLetterCode("");
					} else {
						ag.setLetterCode(res2.getString("letter_code"));
					}
					
					
				} else {
					List<Float> notes = new ArrayList<Float>();
					for(int i=1;i <= 10;i++) {
						notes.add(null);
					}
					ag.setAssessmentGrades(notes);
					ag.setTotalContiniousAssessement(null);
					ag.setFinalExamMarks(null);
					ag.setTotalAssessmentPlusExam(null);
					ag.setFinalGrade(null);
					ag.setLetterCode(null);
					
				}
				
				//total continious assessment : index 0
				ag.getOtherGrades().set(0, ag.getTotalContiniousAssessement());
				// final exam : index 1
				ag.getOtherGrades().set(1, ag.getFinalExamMarks());
				//total assessment and exam index 2
				ag.getOtherGrades().set(2, ag.getTotalAssessmentPlusExam());
				//final grade : index 3
				ag.getOtherGrades().set(3, ag.getFinalGrade());
				//letter code :  index 4
				ag.getOtherGrades().set(4, ag.getLetterCode());
				
				gradesList.add(ag);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				
				if(res2 != null)
					res2.close();
				res.close();
				if(pst2 != null)
					pst2.close();
				pst.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return gradesList;
	}

}
