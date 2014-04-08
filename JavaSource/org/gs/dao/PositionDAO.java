package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.gs.db.SingletonConnection;
import org.gs.model.Position;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;

public class PositionDAO extends DAO<Position>{
	
	public PositionDAO() {
		super(SingletonConnection.getInstance());
		// TODO Auto-generated constructor stub
	}

	public PositionDAO(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public boolean create(Position object) {
		// TODO Auto-generated method stub
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		System.out.println("Id User:"+u.getUserId());
		FacesMessage messages = null;
		String sql="INSERT INTO staff_position(position_title, position_description, created_by, created_on, modifed_by, modified_on, deleted)"
				+ " VALUES(?, ?, ?, NOW(), ?, NOW(), ?)";
		
		PreparedStatement pst = null;
		boolean rep = false;
		System.out.println("Insertion:"+sql);
		try {
			pst=this.con.prepareStatement(sql);
			
			pst.setString(1, object.getPositionTitle().trim());
			pst.setString(2, object.getPositionDescription().trim());
			pst.setInt(3, u.getUserId());
			pst.setInt(4, u.getUserId());
			pst.setInt(5, 0);//Provisoire
			
			int counter=this.count(object);
			
			//before saving the position, it checks if the position is not already registered, to avoid recording 2 times the same position
			if(counter>0){
				System.out.println("this position is already registered");
				messages=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not saved", "This position is already registered");
			}else{
			rep=pst.executeUpdate()>0;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return rep;
	}

	@Override
	public boolean update(Position object) {
		// TODO Auto-generated method stub
		String sql="UPDATE staff_position SET position_title=?, position_description=? WHERE position_id=?";
		
		PreparedStatement pst = null;
		boolean rep = false;
		 
		try {
			pst=this.con.prepareStatement(sql);
			pst.setString(1, object.getPositionTitle().trim());
			pst.setString(2, object.getPositionDescription().trim());
			pst.setInt(3, object.getPositionId());
			rep=pst.executeUpdate()>0;
			System.out.println("Requete:"+sql+" "+object.getPositionTitle()+" "+rep);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return rep;
	}

	@Override
	public boolean delete(Position object) {
		// TODO Auto-generated method stub
		if(object==null)
			return false;
		
		String sql="UPDATE staff_position SET deleted=1 WHERE position_id=?";
		PreparedStatement pst=null;
		boolean rep=false;
		
		try {
			pst=this.con.prepareStatement(sql);
			pst.setInt(1, object.getPositionId());
			
			rep=pst.executeUpdate()>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return rep;
	}


	@Override
	public Position find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	//method that counts the number of times a record appears in the table
	public int count(Position p){
		//Position p=new Position();
		
		String sql="SELECT COUNT(*) AS number FROM staff_position WHERE position_title=? AND position_description=?";
		PreparedStatement statement = null;
		ResultSet res = null;
		int counter=0;
		try {
			statement = this.con.prepareStatement(sql);		
			statement.setString(1, p.getPositionTitle().trim());
			statement.setString(2, p.getPositionDescription().trim());
			res = statement.executeQuery();
			
			if(res.next()){
				p=new Position();
				
				counter=res.getInt("number");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				res.close();
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return counter;
		
	}
	
	//get all Position
	public List<Position> getAll(){
		List<Position> positions=new ArrayList<Position>();
		String req="SELECT * FROM staff_position WHERE deleted=0";
		PreparedStatement statement = null;
		Position p=null;
		ResultSet res = null;
		
		try {
			statement=this.con.prepareStatement(req);
			res=statement.executeQuery();
			
			while(res.next()){ 
				p=new Position();
				p.setPositionId(res.getInt("position_id"));
				p.setPositionTitle(res.getString("position_title"));
				p.setPositionDescription(res.getString("position_description"));
				
				positions.add(p);
				//System.out.println(p.getPositionId()+" "+p.getPositionTitle()+" "+p.getPositionDescription());
		//System.out.println(p.setPositionId(Integer.parseInt(""+res.getInt("position_id")))+" "+p.setPositionTitle(res.getString("position_title"))+" "+p.setPositionDescription(res.getString("position_description")));
		//System.out.println(res.getInt("position_id")+" "+res.getString("position_title")+" "+res.getString("position_description"));
						
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				res.close();
				statement.close();
			}catch (Exception e) {
				
			}
		}
		
		
		return positions;
		
	}
	
	public Position findByTitle(String positionTitle){
		Position position=null;
		String sql="SELECT * FROM staff_position WHERE position_title=?";
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst=this.con.prepareStatement(sql);
			pst.setString(1, positionTitle);
			
			res=pst.executeQuery();
			
			if(res.next()){
				position=new Position();
				
				position.setPositionId(res.getInt(""));
				position.setPositionTitle(res.getString(""));
				position.setPositionDescription(res.getString(""));
				position.setCreatedOn(res.getDate(""));
				position.setModifiedOn(res.getDate(""));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				res.close();
				pst.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		
		return position;
		
	}
	
	/*public List<Position> getPosition(){
		List<Position> getPosition=new ArrayList<Position>();
		String req="SELECT * FROM staff_position";
		PreparedStatement statement = null;
		Position p=null;
		ResultSet res = null;
		
		
			try {
				statement=this.con.prepareStatement(req);
				res=statement.executeQuery();
				
				while(res.next()){
					p=new Position();
					p.setPositionId(res.getInt(""));
					p.setPositionTitle(res.getString(""));
					p.setPositionDescription(res.getString(""));
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//statement.setP
			//statement;
		
		
		
		return null;
		
		
	}*/
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PositionDAO p=new PositionDAO();
		//System.out.println("Nombre:"+p.count());
		p.getAll();
	}
}
