package edu.unlv.mis768.project;

import java.util.Calendar;
import java.util.Date;

/**
 * PatientDependent Class Definition
 */
public class PatientDependent extends Patient {
	
	/**
	 * No-arg constructor for PatientDependent object
	 */
	public PatientDependent() {
		super();
	}

	/**
	 * PatientDependent object constructor
	 * @param patientID integer patient's id
	 * @param firstName String patient's first name
	 * @param lastName String patient's last name
	 * @param dateOfBirth Date patient's date of birth
	 */
	public PatientDependent(int patientID, String firstName, String lastName, Date dateOfBirth) {
		super(patientID, firstName, lastName, dateOfBirth);
	}
	
	/**
	 * PatientDependent object constructor
	 * @param patientID integer patient's id
	 * @param firstName String patient's first name
	 * @param lastName String patient's last name
	 * @param dateOfBirth String patient's date of birth (mm-dd-yyyy) format
	 * @throws Exception when dateOfBirth not in expected format
	 */
	public PatientDependent(int patientID, String firstName, String lastName, String dateOfBirth) 
			throws Exception {
		super(patientID, firstName, lastName, dateOfBirth);
	}
	
	/**
	 * @return String representation of PatientDependent object
	 */
	public String dependentToString() {
		return toString();
	}
	
	/**
	 * Determines if provided dependent equals this dependent
	 * by comparing their first name, last name, and date of birth
	 * @param dependent a PatientDependent object
	 * @return true if the objects are equal, false otherwise
	 */
	public boolean equals(PatientDependent dependent) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.dateOfBirth);
		
		Calendar dependentCalendar = Calendar.getInstance();
		dependentCalendar.setTime(dependent.dateOfBirth);
		
		if (this.firstName.equalsIgnoreCase(dependent.firstName) &&
				this.lastName.equalsIgnoreCase(dependent.lastName) &&
				calendar.YEAR == dependentCalendar.YEAR &&
				calendar.MONTH == dependentCalendar.MONTH &&
				calendar.DAY_OF_MONTH == dependentCalendar.DAY_OF_MONTH)
			return true;
		else
			return false;
	}
	

}
