package org.gs.model;

public class MyTreeNodeModel {

	public MyTreeNodeModel() {
		// TODO Auto-generated constructor stub
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCollapsedIcon() {
		return collapsedIcon;
	}
	public void setCollapsedIcon(String collapsedIcon) {
		this.collapsedIcon = collapsedIcon;
	}
	public String getExpandedIcon() {
		return expandedIcon;
	}
	public void setExpandedIcon(String expandedIcon) {
		this.expandedIcon = expandedIcon;
	}
	public String getRendered() {
		return rendered;
	}
	public void setRendered(String rendered) {
		this.rendered = rendered;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}



	private String type;
	private String icon;
	private String id;
	private String collapsedIcon;
	private String expandedIcon;
	private String rendered;
	private String styleClass;
}
