package org.gs.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.gs.dao.PositionDAO;
import org.gs.model.Position;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.gs.util.RessourceBundleUtil;
import org.primefaces.event.RowEditEvent;


@ManagedBean
@ViewScoped
public class PositionBean implements Serializable{
	
	
	
	public PositionBean() {
		// TODO Auto-generated constructor stub
		this.newPosition=new Position();
		this.positions=new ArrayList<Position>();
		
	}
	
	@PostConstruct
	public void init() {
		PositionDAO pdao= new PositionDAO();
		positions= pdao.getAll();
	}

	
	
	
	public Position getNewPosition() {
		return newPosition;
	}

	public void setNewPosition(Position newPosition) {
		this.newPosition = newPosition;
	}

	//This function saves a position
	public void savePosition(){
		
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		FacesMessage messages = null;
		System.out.println("Id User:"+u.getUserId());
		
		String msgText="";
		
		PositionDAO p=new PositionDAO();
		
		//before saving the position, we first check if all fields are filled
		if(!this.newPosition.getPositionTitle().isEmpty() && !this.newPosition.getPositionDescription().isEmpty()){
			
			System.out.println("Current insertion ....");
			if(p.create(this.newPosition)){
				
				msgText=RessourceBundleUtil.getMessage("positionSaved");
				System.out.println("insertion performed:"+msgText);
				//After insertion, the fields are empty
				this.newPosition.setPositionTitle("");
				this.newPosition.setPositionDescription("");
				this.init();
				messages = new FacesMessage(FacesMessage.SEVERITY_INFO, RessourceBundleUtil.getMessage("success"), msgText);
			}else{
				msgText=RessourceBundleUtil.getMessage("positionAlreadyExist");
				System.out.println("insertion failed:"+msgText);
				messages=new FacesMessage(FacesMessage.SEVERITY_ERROR, RessourceBundleUtil.getMessage("error"), msgText);
			}
		}else{
			msgText=RessourceBundleUtil.getMessage("allFieldsNotCompleted");
			System.out.println(msgText);
			
			messages=new FacesMessage(FacesMessage.SEVERITY_ERROR, RessourceBundleUtil.getMessage("error"), msgText);
		}
		FacesUtil.addMessage(null, messages);
		
	}
	
	
	
	//list of all positions which are recorded
	public List<Position> getPositions() {
				
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	
	
	//This function modifies a position
	public void onPositionEdit(RowEditEvent event){
		Position position=(Position) event.getObject();
		System.out.println("Position:"+position.getPositionId()+" "+position.getPositionTitle()+" "+position.getPositionDescription());
		
		PositionDAO pdao=new PositionDAO();
		String msgText="";
		FacesMessage message=null;
		
		//Avant de modifier, on verifie si tous les champs sont complet�s
			if(position.getPositionTitle().trim().equals("") ||  position.getPositionDescription().trim().equals("")){
				msgText=RessourceBundleUtil.getMessage("allFieldsNotCompleted");
				System.out.println("New Position:"+position.getPositionId()+" "+position.getPositionTitle()+" "+position.getPositionDescription());
				this.init();
				message=new FacesMessage(FacesMessage.SEVERITY_ERROR, RessourceBundleUtil.getMessage("error"), msgText);
				
			}else{
		if(pdao.update(position)){
			msgText=RessourceBundleUtil.getMessage("positionUpdated");
			System.out.println("New Position:"+position.getPositionId()+" "+position.getPositionTitle()+" "+position.getPositionDescription());
			message=new FacesMessage(FacesMessage.SEVERITY_INFO, RessourceBundleUtil.getMessage("success"), msgText);
		}else {
			msgText="Failded to update the position";
			System.out.println("New Position:"+position.getPositionId()+" "+position.getPositionTitle()+" "+position.getPositionDescription());
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, RessourceBundleUtil.getMessage("error"), msgText);
				
		} }
		FacesUtil.addMessage(null, message);

	}
	
	public void onRowEditCancel(RowEditEvent event) {
		
	}
	
	/*Fonction qui change le status de la position:si la position a comme status=0 , la position est affiche dans la liste, 
	 * si elle a comme status=1, elle est cach�e */
	public void onPositionDeleted(Position position){
		PositionDAO pdao=new PositionDAO();
		FacesMessage message=null;
		
		if(pdao.delete(position)){
			String msg=RessourceBundleUtil.getMessage("recordDeleteSuccessful");
			this.init();
			message=new FacesMessage(FacesMessage.SEVERITY_INFO, RessourceBundleUtil.getMessage("deletionSuccessful"), msg);
			
		}else{
			String msg=RessourceBundleUtil.getMessage("recordDeleteFailed");
			this.init();
			message=new FacesMessage(FacesMessage.SEVERITY_ERROR, RessourceBundleUtil.getMessage("deletionFailed"), msg);
		}
		
		FacesUtil.addMessage(null, message);
		this.init();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PositionBean p=new PositionBean();
		p.getPositions();

	}
	
	private List<Position> positions;
	private static final long serialVersionUID = 1L;
	private Position newPosition;
	

}
