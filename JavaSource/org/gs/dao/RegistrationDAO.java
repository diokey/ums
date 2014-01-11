package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.Registration;
import org.gs.model.SchoolPeriod;
import org.gs.model.SchoolYear;
import org.gs.model.Transcript;

public class RegistrationDAO extends DAO<Registration> {

	public RegistrationDAO() {
		super(SingletonConnection.getInstance());
	}
	
	public RegistrationDAO(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Registration object) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO registration (student_id,class_id,period_id,comment,created_on,created_by,modified_on,modified_by,deleted)"
				+ " values (?,?,?,?,NOW(),?,NOW(),?,?)";
		PreparedStatement pst = null;
		boolean rep = false;
		
		try {
			pst = this.con.prepareStatement(sql);
			
			pst.setInt(1, object.getStudentId());
			pst.setInt(2, object.getClassId());
			pst.setInt(3, object.getPeriodId());
			pst.setString(4, object.getComment());
			pst.setInt(5, object.getCreatedBy());
			pst.setInt(6, object.getModifiedBy());
			pst.setBoolean(7, object.isDeleted());
			
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
			} catch (Exception e ) {
				e.printStackTrace();
			}
		}
		
		return rep;
	}

	@Override
	public boolean update(Registration object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Registration object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Registration find(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM registration where registration_id=?";
		ResultSet res = null;
		PreparedStatement pst = null;
		Registration registration = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, id);
			
			res = pst.executeQuery();
			
			if(res.next()) {
				registration = new Registration();
				registration.setRegistrationId(res.getInt("registration_id"));
				registration.setStudentId(res.getInt("student_id"));
				registration.setStudent(new StudentDAO().find(registration.getStudentId()));
				registration.setNotes(new TranscriptDAO().registrationTranscript(res.getInt("registration_id")));
				registration.setClassId(res.getInt("class_id"));
				registration.setPeriodId(res.getInt("period_id"));
				registration.setPeriod(new SchoolPeriodDAO().find(registration.getPeriodId()));
				registration.setComment(res.getString("comment"));
				registration.setCreatedOn(res.getDate("created_on"));
				registration.setModifiedOn(res.getDate("modified_on"));
				registration.setDeleted(res.getBoolean("deleted"));
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
				
			}			
			
		}
		return registration;
	}
	
	public List<Registration> classRegistrations(int classId, int periodId) {
		List<Registration> registrations = new ArrayList<Registration>();
		
		String sql = "SELECT registration_id from registration where class_id=? and period_id=?";
		
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, classId);
			pst.setInt(2, periodId);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				registrations.add(this.find(res.getInt("registration_id")));
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
		
		return registrations;
	}
	
	public List<Registration> studentRegistration(int studentId) {
		
		List<Registration> registrations = new ArrayList<Registration>();
		
		String sql = "SELECT registration_id from registration where student_id=?";
		
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, studentId);
		
			res = pst.executeQuery();
			
			while(res.next()) {
				Registration r = this.find(res.getInt("registration_id"));
				
				registrations.add(r);
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
		
		return registrations;
	}
	
	public static void main(String args[]) {
		List<Registration> t = new RegistrationDAO().studentRegistration(8);
				
		for(Registration r : t) {
			SchoolPeriod sp = r.getPeriod();
			
			SchoolYear sy = sp.getSchoolYear();
			
			List<Transcript> transcript = r.getNotes();
			
			
			for(Transcript tra : transcript) {
				System.out.println(" grade "+tra.getGrade());
			}
			
			System.out.println("School Period : "+sy.getSchoolYearTitle());
		}
		
	}

}
