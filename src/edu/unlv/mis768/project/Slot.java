package edu.unlv.mis768.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Slot Class Definition
 */
public class Slot {
	//fields
	private Date date;
	private boolean bookedIndicator = false;
	private Calendar calendar;
	
	/**
	 * Creates an instance of a Slot object
	 * @param date A date object
	 * @param hour An integer representing the hour (24-hour clock)
	 * @throws Exception if Date to string conversion fails
	 */
	public Slot(Date date, int hour) throws Exception {
		calendar = Calendar.getInstance();
		
		// Set date
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		dateString += " " + hour + ":00:00";
		
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.date = formatter.parse(dateString);
		
		// Set calendar
		this.calendar = Calendar.getInstance();
		calendar.setTime(this.date);
		
	}
	
	/**
	 * Creates an instance of a Slot object
	 * @param dateTime A String representing a date time
	 * 	in the format YYYY-MM-DD HH:MM:SS 
	 * @throws Exception if dateTime format does not match expected format
	 */
	public Slot(String dateTime) throws Exception {
		// Set calendar
		/*int year = Integer.parseInt(dateTime.substring(0,4));
		int month = Integer.parseInt(dateTime.substring(5,7));
		int date = Integer.parseInt(dateTime.substring(8,10));
		
		int hourOfDay = Integer.parseInt(dateTime.substring(11,13));
		int minute = Integer.parseInt(dateTime.substring(14,16));
		
		calendar = Calendar.getInstance();
		calendar.set(year, month, date, hourOfDay, minute);*/
		
		// Set date
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.date = formatter.parse(dateTime);
		
		// Set calendar
		this.calendar = Calendar.getInstance();
		calendar.setTime(this.date);
	}
	
	/**
	 * @return integer day of week
	 */
	public int getDayIndex() {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	/**
	 * @return integer week of month
	 */
	public int getWeekIndex() {
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}
	/**
	 * @return integer month of year
	 */
	public int getMonthIndex() {
		return calendar.get(Calendar.MONTH);
	}
	/**
	 * @return integer year
	 */
	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}
	/**
	 * @return true if slot is booked, false otherwise
	 */
	public boolean getBookedIndicator() {
		return bookedIndicator;
	}
	/**
	 * Set slot booked status
	 * @param booked boolean true if slot is booked, false otherwise
	 */
	public void setBookedIndicator(boolean booked) {
		this.bookedIndicator = booked;
	}
	/**
	 * @return integer hour of day
	 */
	public int getHour() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * @return Date
	 */
	public Date getDate() {
		return this.date;
	}
	/**
	 * @return String representation of Slot object
	 */
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(this.date);
		return dateString;
	}

}
