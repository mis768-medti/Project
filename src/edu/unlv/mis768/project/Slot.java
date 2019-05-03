package edu.unlv.mis768.project;

import java.util.Calendar;
import java.util.Date;


public class Slot {
	//fields
	private Date date;
	private int hour;
	private boolean bookedIndicator;
	private Calendar calendar;
	
	public Slot(Date date, int hour) {
		this.date = date;
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		this.hour = hour;
		
	}
	
	public int getDayIndex() {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	public int getWeekIndex() {
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}
	public int getMonthIndex() {
		return calendar.get(Calendar.MONTH);
	}
	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}
	public boolean getBookedIndicator() {
		return bookedIndicator;
	}
	public void setBookedIndicator(boolean booked) {
		this.bookedIndicator = booked;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour)	{
		this.hour = hour;
	}

}
