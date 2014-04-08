package converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.gs.model.ClassCourse;
import org.gs.model.Course;

@FacesConverter("classcourseConverter")
public class ClasscourseConverter implements Converter{

	public ClasscourseConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		String[] data = arg2.split("-");
		String classeCourseString = data[0];
		String courseString = data[1];
		
		String[] classeCourseData = classeCourseString.split(":");
		String[] courseData = courseString.split(":");
				
		ClassCourse cc = new ClassCourse();
		cc.setClassCourseId(Integer.parseInt(classeCourseData[0]));
		cc.setCredits(Integer.parseInt(classeCourseData[1]));
		cc.setHours(Integer.parseInt(classeCourseData[2]));
		
		Course c = new Course();
		c.setCourseId(Integer.parseInt(courseData[0]));
		c.setCourseName(courseData[1]);
		
		cc.setCourse(c);
		
		return cc;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		ClassCourse cc = (ClassCourse) arg2;
		String toString = "";
		toString = cc.getClassCourseId()+":"+cc.getCredits()+":"+cc.getHours();
		if(cc.getCourse()!=null) {
			toString+="-"+cc.getCourse().getCourseId()+":"+cc.getCourse().getCourseName();
		}
		System.out.println("String : "+toString);
		return toString;
	}
	
	public static void main(String args[]) {
		String req = "1:5:45-3:test:test2";
		String[] data = req.split("-");
		String classeCourseString = data[0];
		String courseString = data[1];
		
		String[] classeCourseData = classeCourseString.split(":");
		String[] courseData = courseString.split(":");
				
		ClassCourse cc = new ClassCourse();
		cc.setClassCourseId(Integer.parseInt(classeCourseData[0]));
		cc.setCredits(Integer.parseInt(classeCourseData[1]));
		cc.setHours(Integer.parseInt(classeCourseData[2]));
		
		System.out.println("classe course id : "+cc.getClassCourseId());
		
		Course c = new Course();
		c.setCourseId(Integer.parseInt(courseData[0]));
		c.setCourseName(courseData[1]);
		c.setCourseDescription(courseData[2]);
		
		System.out.println("Course name : "+c.getCourseName());
		
		cc.setCourse(c);
	}

}
