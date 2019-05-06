package edu.unlv.mis768.project;

import java.util.Calendar;
import java.util.Date;

public class PatientDependent extends Patient {
	
	public PatientDependent() {
		super();
	}

	public PatientDependent(int patientID, String firstName, String lastName, Date dateOfBirth) {
		super(patientID, firstName, lastName, dateOfBirth);
	}
	
	public PatientDependent(int patientID, String firstName, String lastName, String dateOfBirth) 
			throws Exception {
		super(patientID, firstName, lastName, dateOfBirth);
	}
	
	
	public String dependentToString() {
		return toString();
	}
	
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
