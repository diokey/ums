package org.gs.layout;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.gs.dao.ProgramDAO;
import org.gs.dao.SchoolDAO;
import org.gs.dao.StructureDAO;
import org.gs.model.MyTreeNodeModel;
import org.gs.model.Program;
import org.gs.model.School;
import org.gs.model.SchoolPeriod;
import org.gs.model.SchoolYear;
import org.gs.model.Structure;
import org.gs.util.Constantes;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean
@SessionScoped
public class SchoolTreeBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void onSchoolProgramChanged(ValueChangeEvent event) {
		int schoolProgramId = 0;
		if(event==null || event.getNewValue()==null)
			return;
		
		schoolProgramId = Integer.parseInt(""+event.getNewValue());
		
		this.selectedProgram = this.findSelectedProgram(schoolProgramId);
	}
	
	public void onSchoolChanged(ValueChangeEvent event) {
		if(event==null || event.getNewValue()==null)
			return;
		int schoolId = 0;
		schoolId = Integer.parseInt(""+event.getNewValue());
		
		this.selectedSchool = this.findSelectedSchool(schoolId);
	}
	
	public void onSchoolYearChanged(ValueChangeEvent event) {
		int schoolYearId = 0;
		if(event==null || event.getNewValue()==null)
			return;
		
		schoolYearId = Integer.parseInt(""+event.getNewValue());
		
		
		this.selectedSchoolYear = this.findSelectedSchoolYear(schoolYearId);
	}
	
	public void onSchoolPeriodChanged(ValueChangeEvent event) {
		if(event==null || event.getNewValue()==null)
			return;
		int periodId = 0;
		
		periodId = Integer.parseInt(""+event.getNewValue());
		
		this.selectedSchoolPeriod = findSelectedPeriod(periodId);
		
		System.out.println("Changed to : "+this.selectedSchoolPeriod.getSchoolPeriodId());
	}
	
	public Program findSelectedProgram(int programId) {
		
		for (Program pro : this.schoolPrograms) {
			
			if(pro.getProgramId() == programId)
				return pro;
		}
		return null;
	}
	public SchoolPeriod findSelectedPeriod(int periodId) {
		SchoolPeriod sp = null;
		
		for(SchoolPeriod period : this.schoolPeriods) {
			if(period.getSchoolPeriodId()==periodId)
				return period;
		}
		
		return sp;
	}
	
	private SchoolYear findSelectedSchoolYear(int yearId) {
		SchoolYear sy = null;
		
		for(SchoolYear year : this.schoolYears) {
			if(year.getSchoolYearId()==yearId)
				return year;
		}
		
		return sy;
	}
	
	private School findSelectedSchool(int schoolId) {
		
		for(School s : this.schools) {
			if(s.getSchoolId()==schoolId)
				return s;
		}
		
		return null;
	}
	
	public void deleteStructure(ActionEvent e) {
		StructureDAO sd = new StructureDAO();
		FacesMessage messages = null;
		
		this.deleteAll(this.selectedNodeData, sd);
		
		this.buildTree();
		
		messages = new FacesMessage(FacesMessage.SEVERITY_INFO,"Deleted","Deleted");
		FacesContext.getCurrentInstance().addMessage(null, messages);
	}
	
	public void nodeSelected(NodeSelectEvent event) {
		this.selectedNode = event.getTreeNode();
		this.selectedNodeData = (Structure) this.selectedNode.getData();
		
		System.out.println("Selected Strucutre : "+this.selectedNodeData.getTitle());
	}
	
	public void deleteAll(Structure s,StructureDAO sDao) {
		if(s==null)
			return;
		sDao.delete(s);
		List<Structure> children = s.getChildren();
		if(children==null)
			return;
		for(Structure child : children) {
			deleteAll(child,sDao);
		}
	}
	
	
	public void saveChild() {
		StructureDAO sDao = new StructureDAO();
		FacesMessage messages = null;
		if(sDao.create(newStructure)) {
			this.newStructure.setTitle("");
			this.newStructure.setDescription("");
			messages = new FacesMessage(FacesMessage.SEVERITY_INFO,"Saved","Saved successfully");
		}else{
			messages = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Not saved","Error occured while saving");
		}
		
		FacesContext.getCurrentInstance().addMessage(null, messages);
		
		this.buildTree();
	}
	
	public void save() {
		
		
		if(this.newStructure.getId()!=0) {
			this.updateStructure();
			
		}else {
			this.addNewStructure();
			this.newStructure.setTitle("");
			this.newStructure.setDescription("");
		}
		
		
		this.buildTree();		
		
	}
	
	public void updateStructure() {
		
		StructureDAO sDao = new StructureDAO();
		FacesMessage messages = null;
		if(sDao.update(newStructure)) {
			messages = new FacesMessage(FacesMessage.SEVERITY_INFO,"Updated","Updated successfully");
		}else{
			messages = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Not Updated","Update Failed");
		}
		
		FacesContext.getCurrentInstance().addMessage(null, messages);
	}
	
	
	public void addNewStructure() {
		
		StructureDAO sDao = new StructureDAO();
		FacesMessage messages = null;
		if(sDao.create(newStructure)) {
			
			messages = new FacesMessage(FacesMessage.SEVERITY_INFO,"Saved","Saved successfully");
		}else{
			messages = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Not saved","Error occured while saving");
		}
		
		FacesContext.getCurrentInstance().addMessage(null, messages);
				
	}
	
	public void onAddContextMenu(ActionEvent selectedmenu) {
				
		if(selectedNode != null) {  			
			this.selectedNodeData = (Structure) this.selectedNode.getData();
			this.newStructure = new Structure();
			this.newStructure.setParentId(this.selectedNodeData.getParentId());
			
			if(this.newStructure.getParentId()==0) {
				this.newStructure.setRoot(true);
			}else{
				this.newStructure.setRoot(false);
			}
			
			//this.newStructure.setStructureTypeId(1);
			//this.newStructure.setStructureType(this.selectedNodeData.getStructureType());
			
			// TODO : Adjust static values here.
			// Get this from session
			this.newStructure.setCreatedBy(1);
			// Get this from selected school
			this.newStructure.setSchoolStructureId(1);
			//END TO DO 
        } 
	}
	
	public void onEditContextMenu(ActionEvent event) {
		
		if(selectedNode != null) {  			
			this.selectedNodeData = (Structure) this.selectedNode.getData();
			
			this.newStructure = this.selectedNodeData;
			
        }
		
		
	}
	
	public void onAddChildContextMenu(ActionEvent event) {
		if(selectedNode != null) {  			
			this.selectedNodeData = (Structure) this.selectedNode.getData();
			
			this.newStructure = new Structure();
			
			this.newStructure.setParentId(this.selectedNodeData.getId());
			if(this.newStructure.getParentId()==0) {
				this.newStructure.setRoot(true);
			}else{
				this.newStructure.setRoot(false);
			}
			
			/*StructureTypeDAO sTypeDAO = new StructureTypeDAO();
			StructureType sType = sTypeDAO.findSubLevel(this.selectedNodeData.getStructureTypeId());
			if(sType!=null) {
				this.newStructure.setStructureTypeId(sType.getStructureTypeId());
				this.newStructure.setStructureType(sType.getTitle());
			}*/
			
			// TODO : Adjust static values here.
			// Get this from session
			this.newStructure.setCreatedBy(1);
			// Get this from selected school
			this.newStructure.setSchoolStructureId(1);
			//END TO DO 
        } 
	}
	
	@PostConstruct
	public void buildTree() {
				
		root = null;
		root = new DefaultTreeNode("root",null);
		StructureDAO sdao = new StructureDAO();
		List<Structure> nodes = sdao.getRoots(Constantes.CURRENT_SCHOOL);
		
		for(Structure node : nodes) {
			addNode(node,(DefaultTreeNode)root);
		}
		
		
		this.selectedNode  = this.selectFirstNode(root);
		if(this.selectedNode!=null) {
			this.selectedNode.setSelected(true);
			this.selectedNodeData = (Structure) this.selectedNode.getData();
		}
		
	}
	
	private TreeNode selectFirstNode(TreeNode node) {
		if(node==null)
			return null;
		if(node.isLeaf()) {
			return node;
		}else{
			return selectFirstNode(node.getChildren().get(0));
		}
	}
	
	private void addNode(Structure node_data, TreeNode root) {
		if(node_data==null)
			return;
		DefaultTreeNode node = new DefaultTreeNode(node_data.isLeaf()?"leaf":"parent",node_data, root);
		node.setExpanded(true);
		
		if(!node_data.isLeaf()) {
			List<Structure> children = node_data.getChildren();
			for(Structure s : children) {				
				addNode(s,node);
				
			}
		}
	}
	
	
	private String getFullClassName(TreeNode node,String fullName) {
		
		if(node.toString().equalsIgnoreCase("root")) {
			
			return fullName;
			
		} else {
		
			Structure s = (Structure) node.getData();
			
			if(s!=null) {
				if(!fullName.equals("")) {
					fullName = " > "+fullName;
				}
				fullName= s.getTitle()+fullName;
			}
			
			return getFullClassName(node.getParent(),fullName);
		
		}
		
	}
	
	/*
	 * Constructor
	 * 
	 * */
	public SchoolTreeBean() {
		// TODO Auto-generated constructor stub
		this.schools = new SchoolDAO().findAll();
		
		if(!this.schools.isEmpty()) {
			this.selectedSchool = this.schools.get(0);
				if(this.selectedSchool!=null) {
				this.schoolYears = this.selectedSchool.getSchoolYears();
				
				if(this.schoolYears!=null && !this.schoolYears.isEmpty()) {
					this.selectedSchoolYear = this.schoolYears.get(0);
					
					if(this.selectedSchoolYear!=null) {
					
						this.schoolPeriods = this.selectedSchoolYear.getSchoolPeriods();
						
						if(this.schoolPeriods!=null && !this.schoolPeriods.isEmpty()) {
							this.selectedSchoolPeriod = this.schoolPeriods.get(0);
							
							if(this.selectedSchoolPeriod==null) {
								this.selectedSchoolPeriod = new SchoolPeriod();
							}
							
							
						}else{
							this.selectedSchoolPeriod = new SchoolPeriod();
						}
					}else{
						this.selectedSchoolYear = new SchoolYear();
					}
					
				}else {
					this.selectedSchoolYear = new SchoolYear();
				}
			}else {
				this.selectedSchool = new School();
			}
		} else {
			this.selectedSchool = new School();
			
		}
		
		this.schoolPrograms = new ProgramDAO().findAll();
		
		if(this.schoolPrograms!=null && ! this.schoolPrograms.isEmpty())
			this.selectedProgram = this.schoolPrograms.get(0);
		else
			this.selectedProgram = new Program();
		
	}
	
	/*
	 * getters and setters
	 */
	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}
	
	
	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}
	
	public List<MyTreeNodeModel> getNodeTypes() {
		return nodeTypes;
	}

	public void setNodeTypes(List<MyTreeNodeModel> nodeTypes) {
		this.nodeTypes = nodeTypes;
	}
		
	public String getSelectedTreeNodeString() {
		if(this.selectedNodeData!=null) {
			
			this.selectedTreeNodeString = this.getFullClassName(this.selectedNode,"");
			
		}
		return selectedTreeNodeString;
	}

	public void setSelectedTreeNodeString(String selectedTreeNodeString) {
		this.selectedTreeNodeString = selectedTreeNodeString;
	}
	
	public Structure getSelectedNodeData() {
		return selectedNodeData;
	}

	public void setSelectedNodeData(Structure selectedNodeData) {
		this.selectedNodeData = selectedNodeData;
	}

	public Structure getNewStructure() {
		return newStructure;
	}

	public void setNewStructure(Structure newStructure) {
		this.newStructure = newStructure;
	}
	
	public List<School> getSchools() {
		return schools;
	}

	public void setSchools(List<School> schools) {
		this.schools = schools;
	}
	
	public School getSelectedSchool() {
		return selectedSchool;
	}

	public void setSelectedSchool(School selectedSchool) {
		this.selectedSchool = selectedSchool;
	}
	
	public SchoolYear getSelectedSchoolYear() {
		return selectedSchoolYear;
	}

	public void setSelectedSchoolYear(SchoolYear selectedSchoolYear) {
		this.selectedSchoolYear = selectedSchoolYear;
	}
	
	public List<SchoolYear> getSchoolYears() {
		return schoolYears;
	}

	public void setSchoolYears(List<SchoolYear> schoolYears) {
		this.schoolYears = schoolYears;
	}

	public List<SchoolPeriod> getSchoolPeriods() {
		return schoolPeriods;
	}

	public void setSchoolPeriods(List<SchoolPeriod> schoolPeriods) {
		this.schoolPeriods = schoolPeriods;
	}

	public SchoolPeriod getSelectedSchoolPeriod() {
		return selectedSchoolPeriod;
	}

	public void setSelectedSchoolPeriod(SchoolPeriod selectedSchoolPeriod) {
		this.selectedSchoolPeriod = selectedSchoolPeriod;
	}

	public List<Program> getSchoolPrograms() {
		return schoolPrograms;
	}

	public void setSchoolPrograms(List<Program> schoolPrograms) {
		this.schoolPrograms = schoolPrograms;
	}

	public Program getSelectedProgram() {
		return selectedProgram;
	}

	public void setSelectedProgram(Program selectedProgram) {
		this.selectedProgram = selectedProgram;
	}

	private TreeNode root ;
	private TreeNode selectedNode;
	private List<MyTreeNodeModel> nodeTypes;
	private String selectedTreeNodeString;
	private Structure selectedNodeData;
	private Structure newStructure = new Structure();
	private List<School> schools;
	private List<SchoolYear> schoolYears;
	private List<SchoolPeriod> schoolPeriods;
	private List<Program> schoolPrograms;
 	private School selectedSchool;
	private SchoolYear selectedSchoolYear;
	private SchoolPeriod selectedSchoolPeriod;
	private Program selectedProgram;

}
