package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.gs.dao.ClassCourseDAO;
import org.gs.dao.StaffDAO;
import org.gs.dao.StructureDAO;
import org.gs.dao.TeacherCourseDAO;
import org.gs.dao.UserDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.ClassCourse;
import org.gs.model.SchoolPeriod;
import org.gs.model.Structure;
import org.gs.model.Teacher;
import org.gs.model.TeacherCourse;
import org.gs.model.User;
import org.gs.util.CommonUtils;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.gs.util.HashUtil;
import org.gs.util.RessourceBundleUtil;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

@ManagedBean
@ViewScoped
public class TeacherBean {

	public void saveTeacher() {
	
		//1. first save user if grant access checked. If it's not checked you don't wanna registrer the teacher.
		String successMsg = RessourceBundleUtil.getMessage("teacherSaved");
		String failMsg = RessourceBundleUtil.getMessage("saveTeacherFailure");
		FacesMessage message = null;
		boolean error = false;
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		
		
		if(this.newUser.isActive()) {
			//save as user
			System.out.println("Saving user data : ");
			this.newUser.setDisplayName(this.newUser.getUsername());
			this.newUser.setUserRoleId(2);
			this.newUser.setForcePasswordReset(true);
			this.newUser.setBanned(false);
			this.newUser.setUserPassword(HashUtil.hash(this.newUser.getUserPassword()));
			this.newUser.setLanguage("en-US");
			this.newUser.setDisplayName(this.newUser.getUsername());
			this.newUser.setEmail(this.newTeacher.getEmail());
			
			UserDAO userDao = new UserDAO();
			
			
			if(userDao.create(this.newUser)) {
				this.newTeacher.setUserId(CommonUtils.getLastId("users", "id"));
			}
			
		}
		
		int teacherId = 0;
		//2. Save the teacher
		this.newTeacher.setCreatedBy(u.getUserId());
		this.newTeacher.setModifiedBy(u.getUserId());
		if(this.teacherDao.create(this.newTeacher)) {
			teacherId = CommonUtils.getLastId("staff", "staff_id");
		}else {
			System.out.println("Failed to save the teacher... returning... Can't save teacher course");
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"",failMsg);
			FacesUtil.addMessage(null, message);
			return;
		}
		
		
		//3. Save teacherCourse	
		TeacherCourseDAO teacherCourseDao = new TeacherCourseDAO();
		List<ClassCourse> teacherCourses = this.classCourses.getTarget();
		for(ClassCourse cc : teacherCourses) {
			TeacherCourse tc = new TeacherCourse();
			tc.setTeacherId(teacherId);
			tc.setClassCourseId(cc.getClassCourseId());
			tc.setCreatedBy(u.getUserId());
			tc.setModifiedBy(u.getUserId());
			
			if(!teacherCourseDao.create(tc))
				error = true;
		}
		
		if(teacherId>0 && !error) {
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,"",successMsg);
			FacesUtil.addMessage(null, message);
			
			this.newTeacher = new Teacher();
			this.newUser = new User();
			
			this.init();
			
		}else {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"",failMsg);
			FacesUtil.addMessage(null, message);
		}
	
	}
	
	
	public void onClassChanged(ValueChangeEvent event) {
		if(event==null)
			return;
		int classId = 0;
		classId = Integer.parseInt(""+event.getNewValue());
		
		this.selectedClass = this.findSelectedClass(classId);
		
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		this.classCourseSource = this.classCourseDao.findClassCourses(this.selectedClass.getId(), sp.getSchoolPeriodId());
		
		List<ClassCourse> existingCourses = new ArrayList<ClassCourse>();
		
		for(ClassCourse cc : this.classCourseSource) {
			ClassCourse found = this.existInTargets(cc);
			if(found!=null) {
				existingCourses.add(found);
			}
		}
		
		if(!existingCourses.isEmpty()) {
			this.classCourseSource.removeAll(existingCourses);
		}
		
		this.classCourses.setSource(this.classCourseSource);
	}
	
	private ClassCourse existInTargets(ClassCourse classCourse) {
		List<ClassCourse> targetsCourses = this.classCourses.getTarget();
		
		if(targetsCourses==null || targetsCourses.isEmpty())
			return null;
		for(ClassCourse cc : targetsCourses) {
			if(cc.getClassCourseId()==classCourse.getClassCourseId())
				return classCourse;
		}
		
		return null;
	}
	
	private Structure findSelectedClass(int id) {
		
		for(Structure s : this.classes) {
			if(s.getId()==id)
				return s;
		}
		return null;
	}
	
	
	public TeacherBean() {
		// TODO Auto-generated constructor stub
		this.newTeacher = new Teacher();
		this.newUser = new User();
		
		this.structureDao = new StructureDAO();		
		this.classCourseDao = new ClassCourseDAO();
		this.teacherDao = new StaffDAO();
	}
	
	@PostConstruct
	private void init() {
		
		this.classes = this.structureDao.getClassHierarchy(Constantes.CURRENT_SCHOOL);
			
		
		if(this.classes!=null && !this.classes.isEmpty()) {
			this.selectedClass = this.classes.get(0);
		}else {
			this.selectedClass = new Structure();
		}
		
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		
		this.classCourseSource = this.classCourseDao.findClassCourses(this.selectedClass.getId(), sp.getSchoolPeriodId());
		
		this.classCourseTarget = new ArrayList<ClassCourse>();
		
		this.classCourses = new DualListModel<ClassCourse>(this.classCourseSource,this.classCourseTarget);
		
	}
	
	public Teacher getNewTeacher() {
		return newTeacher;
	}

	public void setNewTeacher(Teacher newTeacher) {
		this.newTeacher = newTeacher;
	}
	
	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
	
	public int getSelectedClassId() {
		return selectedClassId;
	}

	public void setSelectedClassId(int selectedClassId) {
		this.selectedClassId = selectedClassId;
	}
	
	public Structure getSelectedClass() {
		return selectedClass;
	}

	public void setSelectedClass(Structure selectedClass) {
		this.selectedClass = selectedClass;
	}

	public List<Structure> getClasses() {
		return classes;
	}

	public void setClasses(List<Structure> classes) {
		this.classes = classes;
	}
	
	public List<ClassCourse> getClassCourseSource() {
		return classCourseSource;
	}

	public void setClassCourseSource(List<ClassCourse> classCourseSource) {
		this.classCourseSource = classCourseSource;
	}

	public List<ClassCourse> getClassCourseTarget() {
		return classCourseTarget;
	}

	public void setClassCourseTarget(List<ClassCourse> classCourseTarget) {
		this.classCourseTarget = classCourseTarget;
	}

	public DualListModel<ClassCourse> getClassCourses() {
		return classCourses;
	}

	public void setClassCourses(DualListModel<ClassCourse> classCourses) {
		this.classCourses = classCourses;
	}

	public SchoolTreeBean getSchoolTreeBean() {
		return schoolTreeBean;
	}

	public void setSchoolTreeBean(SchoolTreeBean schoolTreeBean) {
		this.schoolTreeBean = schoolTreeBean;
	}
	
	public List<Teacher> getTeachers() {
		if(teachers==null) {
			teachers = this.teacherDao.findAllTeachers();
		}
		return teachers;
	}


	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}






	private Teacher newTeacher;
	private User newUser;
	private Structure selectedClass;
	
	private StructureDAO structureDao;
	private ClassCourseDAO classCourseDao;
	private StaffDAO teacherDao;
	
	private int selectedClassId;
	
	private List<Structure> classes;
	
	private List<ClassCourse> classCourseSource;
	private List<ClassCourse> classCourseTarget;
	private List<Teacher> teachers;
	
	private DualListModel<ClassCourse> classCourses;
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;
}
