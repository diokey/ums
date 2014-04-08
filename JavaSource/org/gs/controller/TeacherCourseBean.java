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
import org.gs.layout.SchoolTreeBean;
import org.gs.model.ClassCourse;
import org.gs.model.SchoolPeriod;
import org.gs.model.Structure;
import org.gs.model.Teacher;
import org.gs.model.TeacherCourse;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.gs.util.RessourceBundleUtil;

@ManagedBean
@ViewScoped
public class TeacherCourseBean {


	public void saveTeacherCourses() {
		int removedCounter = 0;
		int addedCounter = 0;
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		
		System.out.println("Called");
		
		if(!this.toBeRemoved.isEmpty()) {
			
			for(ClassCourse cca : this.toBeRemoved) {
				TeacherCourse tc = new TeacherCourse();
				tc.setClassCourseId(cca.getClassCourseId());
				tc.setTeacherId(this.currentTeacher.getStaffId());
				tc.setModifiedBy(u.getUserId());
				if(this.teacherCourseDao.delete(tc)) {
					removedCounter++;
				}
			}
			
		}
		
		if(!this.toBeAdded.isEmpty()) {
			for(ClassCourse cc : this.toBeAdded) {
				TeacherCourse tc = new TeacherCourse();
				tc.setClassCourseId(cc.getClassCourseId());
				tc.setTeacherId(this.currentTeacher.getStaffId());
				tc.setModifiedBy(u.getUserId());
				tc.setCreatedBy(cc.getClassCourseId());
				System.out.println("Added : "+cc.getClassCourseId());
				if(this.teacherCourseDao.create(tc)) {
					addedCounter++;
				}
			}
		}
		
		String msgText = "";
		if(removedCounter>0) {
			msgText=removedCounter+" Courses unassigned ";
		}
		
		if(addedCounter>0) {
			msgText+=addedCounter+" Courses assigned";
		}
		
		if(!msgText.isEmpty()) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"",msgText);
			
			FacesUtil.addMessage(null, message);
		}
	}
	
	public void onClassChanged(ValueChangeEvent event) {
		if(event==null || event.getSource()==null)
			return;
		
		int classId = Integer.parseInt(""+event.getNewValue());
		this.selectedClass = this.findSelectedClass(classId);
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		this.classCourses = this.classCourseDao.findClassCourses(this.selectedClass.getId(), sp.getSchoolPeriodId());
		List<ClassCourse> tobeRemoved = new ArrayList<ClassCourse>();
		List<ClassCourse> courses = this.currentTeacher.getLectures();
		for(ClassCourse cc : courses) {
			ClassCourse found = existInTargets(cc);			
			if(found!=null)
				tobeRemoved.add(found);
		}
		
		this.classCourses.removeAll(tobeRemoved);
	}
	
	public void removeCourse() {
		ClassCourse cc = this.findTeacherCourse(this.removedCourse.getClassCourseId());
		
		if(cc!=null) {
			
			if(!this.toBeRemoved.contains(cc))
				this.toBeRemoved.add(cc);
			
			this.currentTeacher.getLectures().remove(cc);
		
		}
	}
	
	private ClassCourse findTeacherCourse(int courseId) {
		
		List<ClassCourse> teacherCourses = this.currentTeacher.getLectures();
		
		for(ClassCourse cc : teacherCourses) {
			if(cc.getClassCourseId()==courseId)
				return cc;
		}
		
		return null;
	}
	
	public void addNewCourse() {
		ClassCourse cc = this.findSelectedCourse(this.selectedClassCourse.getClassCourseId());
		
		if(cc!=null) {
			this.classCourses.remove(cc);
			List<ClassCourse> lectures = this.currentTeacher.getLectures();
			lectures.add(cc);
			this.currentTeacher.setLectures(lectures);
			
			if(!this.toBeAdded.contains(cc))
				this.toBeAdded.add(cc);
		}
	}
	
	private ClassCourse findSelectedCourse(int classCourseId) {
		
		for(ClassCourse cc : this.classCourses) {
			if(cc.getClassCourseId()==classCourseId)
				return cc;
		}
		return null;
	}
	
	private Structure findSelectedClass(int classId) {
		
		for(Structure s : this.classes) {
			if(s.getId()==classId)
				return s;
		}
		return null;
	}
	
	public void findTeacherCourses() {
		
		ClassCourseDAO ccDao = new ClassCourseDAO();
		List<ClassCourse> classCoursesFound = ccDao.findTeacherCourses(this.currentTeacher.getStaffId());
		List<ClassCourse> tobeRemoved = new ArrayList<ClassCourse>();
		
		
		this.currentTeacher.setLectures(classCoursesFound);
		for(ClassCourse cc : classCoursesFound) {
			ClassCourse found = existInTargets(cc);
			
			if(found!=null)
				tobeRemoved.add(found);
		}
		
		this.classCourses.removeAll(tobeRemoved);
	}
	
	private ClassCourse existInTargets(ClassCourse classCourse) {
		//List<ClassCourse> targetsCourses = this.classCourses;
		
		if(this.classCourses==null || this.classCourses.isEmpty())
			return null;
		for(ClassCourse cc : this.classCourses) {
			
			if(cc.getClassCourseId()==classCourse.getClassCourseId())
				return cc;
		}
		
		return null;
	}
	
		
	@PostConstruct
	private void init() {
		this.teachers = this.teacherDAO.findAllTeachers();
		
		this.classes = this.structureDao.getClassHierarchy(Constantes.CURRENT_SCHOOL);
			
		
		if(this.classes!=null && !this.classes.isEmpty()) {
			this.selectedClass = this.classes.get(0);
		}else {
			this.selectedClass = new Structure();
		}
		
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		
		this.classCourses = this.classCourseDao.findClassCourses(this.selectedClass.getId(), sp.getSchoolPeriodId());
	}
	
	public TeacherCourseBean() {
		
		//First of all check if the user has access to this page.
    	// Meaning if the user is connected.
    	// TODO Should possibly check some other user right
    	
    	
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		if(u==null)
			FacesUtil.redirect("/",RessourceBundleUtil.getMessage("notConnected"));
		
    	//-------------------- end checking connection checkings--------------------------------
		
		
		this.teacherDAO = new StaffDAO();
		this.classCourseDao = new ClassCourseDAO();
		this.structureDao = new StructureDAO();
		this.teacherCourseDao = new TeacherCourseDAO();
		this.selectedClassCourse = new ClassCourse();
		
		this.toBeAdded = new ArrayList<ClassCourse>();
		this.toBeRemoved = new ArrayList<ClassCourse>();
		
		
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
	public List<ClassCourse> getClassCourses() {
		return classCourses;
	}
	public void setClassCourses(List<ClassCourse> classCourses) {
		this.classCourses = classCourses;
	}
	
	public Teacher getCurrentTeacher() {
		return currentTeacher;
	}

	public void setCurrentTeacher(Teacher currentTeacher) {
		this.currentTeacher = currentTeacher;
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
	
	public SchoolTreeBean getSchoolTreeBean() {
		return schoolTreeBean;
	}

	public void setSchoolTreeBean(SchoolTreeBean schoolTreeBean) {
		this.schoolTreeBean = schoolTreeBean;
	}
	
	public ClassCourse getSelectedClassCourse() {
		return selectedClassCourse;
	}

	public void setSelectedClassCourse(ClassCourse selectedClassCourse) {
		this.selectedClassCourse = selectedClassCourse;
	}

	public List<ClassCourse> getToBeRemoved() {
		return toBeRemoved;
	}

	public void setToBeRemoved(List<ClassCourse> toBeRemoved) {
		this.toBeRemoved = toBeRemoved;
	}

	public List<ClassCourse> getToBeAdded() {
		return toBeAdded;
	}

	public void setToBeAdded(List<ClassCourse> toBeAdded) {
		this.toBeAdded = toBeAdded;
	}

	public ClassCourse getRemovedCourse() {
		return removedCourse;
	}

	public void setRemovedCourse(ClassCourse removedCourse) {
		this.removedCourse = removedCourse;
	}






	private StaffDAO teacherDAO;
	private StructureDAO structureDao;
	private ClassCourseDAO classCourseDao;
	private TeacherCourseDAO teacherCourseDao;
	
	private Teacher currentTeacher;
	private Structure selectedClass;
	private ClassCourse selectedClassCourse;
	private ClassCourse removedCourse;
	
	private List<Teacher> teachers;
	private List<ClassCourse> classCourses;
	private List<ClassCourse> toBeRemoved;
	private List<ClassCourse> toBeAdded;
	private List<Structure> classes;
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;
	
}
