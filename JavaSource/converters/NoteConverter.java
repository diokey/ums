package converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("noteConverter")
public class NoteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		if (arg0 == null || arg1 == null)
			return null;
		if(arg2 == null || arg2.isEmpty())
			return null;
		Float value;
		try{
			value = (Float) Float.parseFloat(arg2);
		} catch (NumberFormatException e) {
			value = null;
		}
		return value;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		if(arg2 == null)
			return "";
		return arg2.toString();
	}

}
