package org.gs.controller;

import org.gs.model.Role;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.gs.util.RessourceBundleUtil;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

public class ChartBean {

	private CartesianChartModel categoryModel;  
	  
    public ChartBean() {
    	//First of all check if the user has access to this page.
    	// Meaning if the user is connected.
    	// TODO Should possibly check some other user right
    	
    	
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		if(u==null)
			FacesUtil.redirect("/",RessourceBundleUtil.getMessage("notConnected"));
		
        createCategoryModel();  
    }  
  
    public CartesianChartModel getCategoryModel() {  
        return categoryModel;  
    }  
  
    private void createCategoryModel() {  
        categoryModel = new CartesianChartModel();  
  
        ChartSeries boys = new ChartSeries();  
        boys.setLabel("Boys");  
  
        boys.set("2004", 120);  
        boys.set("2005", 100);  
        boys.set("2006", 44);  
        boys.set("2007", 150);  
        boys.set("2008", 25);  
  
        ChartSeries girls = new ChartSeries();  
        girls.setLabel("Girls");  
  
        girls.set("2004", 52);  
        girls.set("2005", 60);  
        girls.set("2006", 110);  
        girls.set("2007", 135);  
        girls.set("2008", 120);  
  
        categoryModel.addSeries(boys);  
        categoryModel.addSeries(girls);  
    }  

}
