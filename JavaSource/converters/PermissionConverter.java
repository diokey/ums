package converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.gs.model.Permission;

@FacesConverter("permissionConverter")
public class PermissionConverter implements Converter{

	public PermissionConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		String permissionString[] = arg2.split("-");
		if(permissionString.length==4) {
			Permission p = new Permission();
			p.setPermissionId(Integer.parseInt(permissionString[0]));
			p.setName(permissionString[1]);
			p.setDescription(permissionString[2]);
			p.setStatus(permissionString[3]);
			
			return p;
		}
		 
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		Permission p = (Permission) arg2;
		if(p!=null)
			return p.getPermissionId()+"-"+p.getName()+"- "+p.getDescription()+"-"+p.getStatus()+" ";
		return null;
	}

}
