package org.gs.util;

public class Constantes {

	 //Constants for the database identification
    public static final String DB_URL="jdbc:mysql://localhost:3306/university";
    public static final String DB_PASSWORD="diokey";
    public static final String DB_USERNAME="root";
    
    //session constants
    public static final String CONNECTED_USER="connectedUser";
    
    //TODO change this to handle mutiple schools
    public static final int CURRENT_SCHOOL = 1;
    
    public static final String ERROR = "Error";
    public static final String WARNING = "Warning";
    public static final String INFO = "Info";
    public static final String CRITICAL = "Critical";
    
    public static final String ACTIF = "active";
    public static final String INACTIF = "inactive";
    public static final String DELETED = "deleted";

}
