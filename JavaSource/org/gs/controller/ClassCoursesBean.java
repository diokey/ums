package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.gs.dao.ClassCourseDAO;
import org.gs.dao.CourseDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.ClassCourse;
import org.gs.model.Course;
import org.gs.model.SchoolPeriod;
import org.gs.model.Structure;
import org.gs.util.FacesUtil;

@ManagedBean
@ViewScoped
public class ClassCoursesBean {

	
	
	public void addCourses() {
		
		for(Course c : this.selectedCourses) {
			ClassCourse classCourse = new ClassCourse();
			classCourse.setCourse(c);
			classCourse.setCourseId(c.getCourseId());
			classCourse.setCourseDescription(c.getCourseDescription());
			
			this.classCourses.add(classCourse);
			
			this.modifiedClassCourses.add(classCourse);			
		}
		
		this.allCourses.removeAll(this.selectedCourses);
		if(this.filteredCourses!=null)
			this.filteredCourses.clear();
		if(this.selectedCourses!=null)
			this.selectedCourses.clear();
	}
	
	public void saveSelectedCourses() {		
		
		ClassCourseDAO ccDao = new ClassCourseDAO();
		
		this.schoolTreeBean.getSelectedNode();
		
		Structure classe = this.schoolTreeBean.getSelectedNodeData();
		
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		
		/*
		 * for(ClassCourse cc : this.classCourses) {
		 
			cc.setClassId(classe.getId());
			cc.setPeriodId(sp.getSchoolPeriodId());
			cc.setCourseId(cc.getCourse().getCourseId());
			
			ccDao.create(cc);
		}
		 */
		
		String message = "";
		int addedCounter = 0;
		int modifiedCounter = 0;
		int deletedCounter = 0;
		
		for(ClassCourse cc : this.deletedClassCourses) {
			
			System.out.println(cc.getCourse().getCourseName());
			cc.setClassId(classe.getId());
			cc.setPeriodId(sp.getSchoolPeriodId());
			cc.setCourseId(cc.getCourse().getCourseId());
			
			if(cc.getClassCourseId()!=0) {
				ccDao.softDelete(cc);
				deletedCounter++;
			} 
		}
		
		
		for(ClassCourse cc : this.modifiedClassCourses) {
			System.out.println(cc.getCourse().getCourseName());
			cc.setClassId(classe.getId());
			cc.setPeriodId(sp.getSchoolPeriodId());
			cc.setCourseId(cc.getCourse().getCourseId());
			
			if(cc.getClassCourseId()==0) {
				if(!ccDao.isSoftDeleted(cc))
					ccDao.create(cc);
				else
					ccDao.restore(cc);
				
				addedCounter++;
				
			} else {
				ccDao.update(cc);
				modifiedCounter++;
			}
		}
		
		boolean comma = false;
		
		if(deletedCounter!=0) {
			message = deletedCounter+ " courses deleted";
			comma = true;
		}
		
		if(addedCounter!=0) {
			message+=comma?"":" , ";
			message+= addedCounter+" courses added";
			comma = true;
		}
		
		if(modifiedCounter!=0) {
			message+=comma?"":" , ";
			message+= modifiedCounter+" courses modified";
		}
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", message);
		
		FacesUtil.addMessage(null, msg);
		
		if(this.selectedCourses!=null)		
			this.selectedClassCourses.clear();
		if(this.modifiedClassCourses!=null)
			this.modifiedClassCourses.clear();
		if(this.deletedClassCourses!=null)
			this.deletedClassCourses.clear();
		
		if(this.filteredCourses!=null)
			this.filteredCourses.clear();
		if(this.selectedCourses!=null)
			this.selectedCourses.clear();
		
	}
	
	
	public void onClassCourseRemove() {
		for (ClassCourse c : this.selectedClassCourses) {
			this.allCourses.add(c.getCourse());
			this.deletedClassCourses.add(c);
			if(this.modifiedClassCourses.contains(c)) {
				this.modifiedClassCourses.remove(c);
			}
		}
		
		this.classCourses.removeAll(this.selectedClassCourses);
		this.selectedClassCourses.clear();
		if(this.filteredCourses!=null)
			this.filteredCourses.clear();
	}
	
	public void classCourseModified(Integer index) {
		ClassCourse modified = this.classCourses.get(index);
		
		if(modified!=null) {
			if(!this.modifiedClassCourses.contains(modified)) {
				this.modifiedClassCourses.add(modified);
			}
		}
	}
	
	private void removeAssignedCourses() {
		for(ClassCourse c : this.classCourses) {
			removeIfExists(c.getCourse());
		}
	}
	
	private void removeIfExists(Course course) {
		Course found = null;
		for(Course c : this.allCourses) {
			if(c.getCourseId()==course.getCourseId()) {
				found = c;
				System.out.println("To be removed : "+found.getCourseName());
				break;
			}
				
		}
		
		if(found!=null)
			this.allCourses.remove(found);	
	}
	
	public void onClassChanged() {
		
		this.init();
	}
	
	public ClassCoursesBean() {
		// TODO Auto-generated constructor stub
		
		this.classCourses = new ArrayList<ClassCourse>();
		this.modifiedClassCourses = new ArrayList<ClassCourse>();
		this.deletedClassCourses = new ArrayList<ClassCourse>();
		
		this.courseDao = new CourseDAO();
		this.classCourseDao = new ClassCourseDAO();
		
	}
	
	
	@PostConstruct
	private void init() {
		
		allCourses = this.courseDao.findAll();
		
		this.schoolTreeBean.getSelectedNode();
		
		Structure classe = this.schoolTreeBean.getSelectedNodeData();
		
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		
		this.classCourses = this.classCourseDao.findClassCourses(classe.getId(), sp.getSchoolPeriodId());
		
		System.out.println("ALl courses "+this.classCourses.size());
		this.removeAssignedCourses();
	}
		
	public List<Course> getAllCourses() {
		return allCourses;
	}
	public void setAllCourses(List<Course> allCourses) {
		this.allCourses = allCourses;
	}

	public List<Course> getSelectedCourses() {
		return selectedCourses;
	}

	public void setSelectedCourses(List<Course> selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	public List<Course> getFilteredCourses() {
		return filteredCourses;
	}

	public void setFilteredCourses(List<Course> filteredCourses) {
		this.filteredCourses = filteredCourses;
	}
	
	public List<ClassCourse> getClassCourses() {
		return classCourses;
	}

	public void setClassCourses(List<ClassCourse> classCourses) {
		this.classCourses = classCourses;
	}

	public List<ClassCourse> getSelectedClassCourses() {
		return selectedClassCourses;
	}

	public void setSelectedClassCourses(List<ClassCourse> selectedClassCourses) {
		this.selectedClassCourses = selectedClassCourses;
	}
	
	public SchoolTreeBean getSchoolTreeBean() {
		return schoolTreeBean;
	}

	public void setSchoolTreeBean(SchoolTreeBean schoolTreeBean) {
		this.schoolTreeBean = schoolTreeBean;
	}


	private List<Course> allCourses;
	private List<Course> selectedCourses;
	private List<Course> filteredCourses;
	private List<ClassCourse> classCourses;
	private List<ClassCourse> selectedClassCourses;
	private List<ClassCourse> modifiedClassCourses;
	private List<ClassCourse> deletedClassCourses;
	private CourseDAO courseDao;
	private ClassCourseDAO classCourseDao;
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;

}
