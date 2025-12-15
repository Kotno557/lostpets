package br.lostpets.project.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service for providing current date and time information.
 * 
 * Refactored from ServiceGeral to DateTimeService for clearer naming.
 * This service provides formatted date and time strings for use in entities
 * and audit logs.
 */
@Service
public class DateTimeService {
	
	private static final Logger logger = LoggerFactory.getLogger(DateTimeService.class);
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String TIME_FORMAT = "HH:mm:ss";
	
	/**
	 * Gets current date and time as a formatted string.
	 * Format: "dd/MM/yyyy HH:mm:ss"
	 * 
	 * @return formatted date and time string
	 */
	public String getDateHour() {
		Date currentDateTime = new Date();
		String date = new SimpleDateFormat(DATE_FORMAT).format(currentDateTime);
		String time = new SimpleDateFormat(TIME_FORMAT).format(currentDateTime);
		return date + " " + time;
	}
	
	/**
	 * Gets current date as a formatted string.
	 * Format: "dd/MM/yyyy"
	 * 
	 * @return formatted date string
	 */
	public String getDate() {
		Date currentDateTime = new Date();
		return new SimpleDateFormat(DATE_FORMAT).format(currentDateTime);
	}
	
	/**
	 * Gets current time as a formatted string.
	 * Format: "HH:mm:ss"
	 * 
	 * @return formatted time string
	 */
	public String getHour() {
		Date currentDateTime = new Date();
		return new SimpleDateFormat(TIME_FORMAT).format(currentDateTime);
	}
}
