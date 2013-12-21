package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.SchoolPeriod;

public class SchoolPeriodDAO extends DAO<SchoolPeriod> {
	
	public SchoolPeriodDAO() {
		super(SingletonConnection.getInstance());
	}
	
	public SchoolPeriodDAO(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(SchoolPeriod object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(SchoolPeriod object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(SchoolPeriod object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SchoolPeriod find(int id) {
		// TODO Auto-generated method stub
		SchoolPeriod sp = null;
		ResultSet res = null;
		PreparedStatement pst = null;
		
		String sql = "SELECT * FROM school_period where school_period_id = ?";
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, id);
			res = pst.executeQuery();
			
			if(res.next()) {
				sp = new SchoolPeriod();
				sp.setSchoolPeriodId(res.getInt("school_period_id"));
				sp.setSchoolYearId(res.getInt("school_year_id"));
				sp.setPeriod(res.getString("period"));
				sp.setPeriodDescription(res.getString("period_description"));
				sp.setStartDate(res.getDate("start_date"));
				sp.setEndDate(res.getDate("end_date"));
				sp.setCreatedOn(res.getDate("created_on"));
				sp.setCreatedBy(res.getInt("created_by"));
				sp.setDeleted(res.getBoolean("deleted"));
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
		
		return sp;
	}
	
	public List<SchoolPeriod> findBySchoolYear(int schoolYear) {
		List<SchoolPeriod> periods = new ArrayList<SchoolPeriod>();
		ResultSet res = null;
		PreparedStatement pst = null;
		
		String sql = "SELECT school_period_id FROM school_period where school_year_id = ?";
				
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, schoolYear);
			res = pst.executeQuery();
			
			while(res.next()) {
				periods.add(this.find(res.getInt("school_period_id")));
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
		
		return periods;
	}

}
