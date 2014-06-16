package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.Structure;

public class StructureDAO extends DAO<Structure>{

	public StructureDAO(Connection con) {
		// TODO Auto-generated constructor stub
		super(con);
	}
	
	public StructureDAO() {
		// TODO Auto-generated constructor stub
		
		super(SingletonConnection.getInstance());
	}

	@Override
	public boolean create(Structure object) {
		// TODO Auto-generated method stub
		String req = "";
		boolean res = false;
		PreparedStatement statement = null;
		try{
			if(object.getParentId()==0) {
				req = "INSERT INTO school_structure_details(title,description,is_root,is_leaf,school_structure_id,created_on,created_by)"
						+ "VALUES(?,?,?,?,?,NOW(),?)";
				statement = this.con.prepareStatement(req);
				statement.setString(1, object.getTitle());
				statement.setString(2, object.getDescription());
				//statement.setInt(3, object.getStructureTypeId());
				statement.setInt(3, (object.isRoot()?1:0));
				statement.setInt(4, object.isLeaf()?1:0);
				statement.setInt(5, object.getSchoolStructureId());
				statement.setInt(6, object.getCreatedBy());
			}else{
				req = "INSERT INTO school_structure_details(title,description,parent_id,is_root,is_leaf,school_structure_id,created_on,created_by)"
						+ "VALUES(?,?,?,?,?,?,NOW(),?)";
				
				
				statement = this.con.prepareStatement(req);
				statement.setString(1, object.getTitle());
				statement.setString(2, object.getDescription());
				statement.setInt(3, object.getParentId());
				//statement.setInt(4, object.getStructureTypeId());
				statement.setInt(4, (object.isRoot()?1:0));
				statement.setInt(5, object.isLeaf()?1:0);
				statement.setInt(6, object.getSchoolStructureId());
				statement.setInt(7, object.getCreatedBy());
			}
			
			res = statement.executeUpdate()>0;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return res;
	}

	@Override
	public boolean update(Structure object) {
		// TODO Auto-generated method stub
		String req = "";
		boolean res = false;
		PreparedStatement statement = null;
		try{
			if(object.getParentId()==null || object.getParentId()==0) {
				req = "UPDATE school_structure_details SET title=?,description=?,is_root=?,is_leaf=?,school_structure_id=? WHERE school_structure_details_id=?";
				statement = this.con.prepareStatement(req);
				statement.setString(1, object.getTitle());
				statement.setString(2, object.getDescription());
				//statement.setInt(3, object.getStructureTypeId());
				statement.setInt(3, (object.isRoot()?1:0));
				statement.setInt(4, object.isLeaf()?1:0);
				statement.setInt(5, object.getSchoolStructureId());
				statement.setInt(6, object.getId());
			}else{
				
				req = "UPDATE school_structure_details SET title=?,description=?,parent_id=?,is_root=?,is_leaf=?,school_structure_id=? WHERE school_structure_details_id=?";
				statement = this.con.prepareStatement(req);
				statement.setString(1, object.getTitle());
				statement.setString(2, object.getDescription());
				statement.setInt(3, object.getParentId());
				//statement.setInt(4, object.getStructureTypeId());
				statement.setInt(4, (object.isRoot()?1:0));
				statement.setInt(5, object.isLeaf()?1:0);
				statement.setInt(6, object.getSchoolStructureId());
				statement.setInt(7, object.getId());
			}
			
			res = statement.executeUpdate()>0;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return res;
	}

	@Override
	public boolean delete(Structure object) {
		// TODO Auto-generated method stub
		String req="DELETE FROM school_structure_details where school_structure_details_id=?";
		boolean res = false;
		PreparedStatement statement = null;
		try {
			statement = this.con.prepareStatement(req);
			statement.setInt(1, object.getId());
			
			res = statement.executeUpdate()>0;
			
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public Structure find(int id) {
		
		String req = "SELECT * FROM school_structure_details where school_structure_details_id=?";
		
		ResultSet res = null;
		Structure s = null;
		try {
			PreparedStatement statement = this.con.prepareStatement(req);
			statement.setInt(1, id);
			
			res = statement.executeQuery();
			
			if(res.first()) {
				s = new Structure();
				s.setId(res.getInt("school_structure_details_id"));
				s.setTitle(res.getString("title"));
				s.setDescription(res.getString("description"));
				s.setParentId((Integer)res.getInt("parent_id"));
				s.setRoot(res.getBoolean("is_root"));
				s.setLeaf(res.getBoolean("is_leaf"));
				s.setCreatedBy(res.getInt("created_by"));
				s.setCreatedOn(res.getDate("created_on"));
				s.setDeleted(res.getBoolean("deleted"));
				//s.setStructureTypeId(res.getInt("structure_type_id"));
				s.setSchoolStructureId(res.getInt("school_structure_id"));
				//s.setStructureType(res.getString("structure_type"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return s;
	}
	
	public List<Structure> getChildren(int parent_id,final List<Integer> access) {
		
		String req = "SELECT school_structure_details_id FROM school_structure_details where parent_id=?";
		List<Structure> rootNodes = new ArrayList<Structure>();
		
		ResultSet res = null;
		Structure s = null;
		
		try {
			PreparedStatement statement = this.con.prepareStatement(req);
			
			statement.setInt(1,parent_id);
			res = statement.executeQuery();
			
			while(res.next()) {
				
				s = this.find(res.getInt("school_structure_details_id"));
				if(access.contains(s.getId())) {
					s.setChildren(this.getChildren(s.getId(),access));
					rootNodes.add(s);
				}
			}
			statement.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			
			try {
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return rootNodes;
	}
	
	public List<Structure> getClassHierarchy(int school_structure_Id) {
		List<Structure> classes = new ArrayList<Structure>();
		
		String req = "SELECT * FROM school_structure_details where school_structure_id = ? and is_leaf = 1";
		
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(req);
			pst.setInt(1, school_structure_Id);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				Structure classe = new Structure();
				
				classe.setId(res.getInt("school_structure_details_id"));
				int id= classe.getId();
				classe.setDescription(res.getString("description"));
				String desc = classe.getDescription();				
				classe.setParentId(res.getInt("parent_id"));
				classe.setTitle(res.getString("title"));
				List<String> titles= new ArrayList<String>();
				String chaine = "";				
				do{
					titles.add(classe.getTitle());
					
					classe = this.find(classe.getParentId());
				}while(classe!=null && classe.getParentId()!=null);
				
				int titlesNumbers = titles.size();
				
				for(int i=titlesNumbers; i>0; i--) {
					chaine+=">"+titles.get(i-1);
				}
				
				chaine = chaine.substring(chaine.indexOf(">")+1);
				classe = new Structure();
				classe.setId(id);
				classe.setDescription(desc);
				classe.setTitle(chaine);
				
				classes.add(classe);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return classes;
	}
	
	public List<Structure> getRoots(int school_structure_id, List<Integer> access) {
		
		String req = "SELECT school_structure_details_id FROM school_structure_details where school_structure_id=? and is_root=1";
		List<Structure> rootNodes = new ArrayList<Structure>();
		
		ResultSet res = null;
		Structure s = null;
		try {
			PreparedStatement statement = this.con.prepareStatement(req);
			
			statement.setInt(1, school_structure_id);
			res = statement.executeQuery();
			
			while(res.next()) {				
				s = this.find(res.getInt("school_structure_details_id"));
				if(access.contains(s.getId())) {
					s.setChildren(this.getChildren(s.getId(),access));
					rootNodes.add(s);
				}
			}
			statement.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			
			try {
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return rootNodes;
	}
	
	public static void main(String args[]) {
		StructureDAO sdao = new StructureDAO(SingletonConnection.getInstance());
		List<Structure> nodes = sdao.getClassHierarchy(1);
		
		for(Structure s : nodes) {
			System.out.println(s.getTitle());
		}
	}
	
	public static void readChildren(Structure parent) {
		
		if(!parent.isLeaf()) {
			List<Structure> children = parent.getChildren();
			for(Structure s : children) {
				System.out.println(""+s.getTitle()+" is child of "+parent.getTitle());
				readChildren(s);
			}
		}
	}

}
