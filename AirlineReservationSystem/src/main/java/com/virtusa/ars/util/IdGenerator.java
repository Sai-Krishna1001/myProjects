package com.virtusa.ars.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IdGenerator {
	
	private IdGenerator() {
		
	}
    
    private static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }
    
    public static String generateUserId(boolean isCustomer) {   
        String prefix = "U";   
        if (isCustomer) {
            prefix += "C";
        } else {
            prefix += "M";
        }
        return prefix + getCurrentDateTime();
    }
    
    public static String generateBookingId() {
        String prefix = "B";
        return prefix + getCurrentDateTime();
    }
}
