package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.gs.db.SingletonConnection;
import org.gs.model.Settings;

public class SettingsDAO extends DAO<Settings>{

	public SettingsDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}
	
	public SettingsDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(Settings object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Settings object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Settings object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Settings find(int id) {
		// TODO Auto-generated method stub
		String req = "SELECT * FROM Settings where settings_id=?";
		
		ResultSet res = null;
		PreparedStatement pst = null;
		Settings setting = null;
		try {
			pst = this.con.prepareStatement(req);
			pst.setInt(1, id);
			
			res = pst.executeQuery();
			
			if(res.next()) {
				setting = new Settings();
				setting.setSettingsId(res.getInt("settings_id"));
				setting.setLabel(res.getString("label"));
				setting.setLanguage(res.getString("language"));
				setting.setName(res.getString("name"));
				setting.setDescription(res.getString("setting_description"));
				setting.setValue(res.getString("value"));
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
		
		return setting;
	}
	
	public Settings find(String name, String lang) {
		
		String req = "SELECT * FROM Settings where name=? and language=?";
		
		ResultSet res = null;
		PreparedStatement pst = null;
		Settings setting = null;
		try {
			pst = this.con.prepareStatement(req);
			pst.setString(1, name);
			pst.setString(2, lang);
			
			res = pst.executeQuery();
			
			if(res.next()) {
				setting = new Settings();
				setting.setSettingsId(res.getInt("settings_id"));
				setting.setLabel(res.getString("label"));
				setting.setLanguage(res.getString("language"));
				setting.setName(res.getString("name"));
				setting.setDescription(res.getString("setting_description"));
				setting.setValue(res.getString("value"));
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
		
		return setting;
	}

}
