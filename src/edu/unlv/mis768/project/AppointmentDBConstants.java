package edu.unlv.mis768.project;

/**
 * This class defines the strings used to establish a database connection
 */

public class AppointmentDBConstants {
	static public final String DB_URL = "jdbc:mysql://localhost:3306/appointmentDB";
    static public final String USER_NAME = "root";
    static public final String PASSWORD = "";
    static public final String ADMIN_TABLE_NAME  = "admin";
    static public final String ADMIN_PK_NAME = "AdminID";
    static public final String APPOINTMENT_TABLE_NAME  = "appointment";
    static public final String PATIENT_TABLE_NAME = "patient";
    static public final String PATIENT_PK_NAME = "PatientID";
    static public final String DEPENDENT_TABLE_NAME  = "PatientDependent";
    static public final String INSURANCE_TABLE_NAME  = "PatientInsurance";
    static public final String PROVIDER_TABLE_NAME  = "provider";
    static public final String PROVIDER_PK_NAME  = "ProviderID";
    static public final String SPECIALTY_TABLE_NAME = "specialty";
    static public final String USER_TABLE_NAME = "user";
    static public final String USER_PK_NAME = "username";
}
