package edu.unlv.mis768.project;

import java.util.Date;

public class Slot {
	//fields
	private Date date;
	private boolean bookedIndicator;
	
	public Slot(Date date) {
		this.date = date;	
	
		
	}
	
	public int getDay() {
		return date.get(Calendar.DAY_OF_WEEK)
		
	}

}
