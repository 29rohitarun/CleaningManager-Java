package edu.ncsu.csc316.cleaning.manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import edu.ncsu.csc316.cleaning.dsa.DataStructure;

public class ReportManagerTest {

    private ReportManager reportManager;



    @Test
    public void testGetVacuumBagReportOverdue() {
    	
	 	String pathToRoomFile = "input/RoomInfo.txt";
	 	String pathToLogFile = "input/CleaningEvents.txt";
      	try {
			reportManager = new ReportManager(pathToRoomFile, pathToLogFile, DataStructure.SEARCHTABLE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        String timestamp = "01/01/2022 12:00:00"; 
        String report = reportManager.getVacuumBagReport(timestamp);
        assertEquals("Vacuum Bag Report (last replaced 01/01/2022 12:00:00) [\n"
        		+ "Bag is due for replacement in 5280SQ FT\n"
        		+ "]", report);
    }

    @Test
    public void testGetVacuumBagReportDue() {

    	
	 	String pathToRoomFile = "input/RoomInfo.txt";
	 	String pathToLogFile = "input/CleaningEvents.txt";
      	try {
			reportManager = new ReportManager(pathToRoomFile, pathToLogFile, DataStructure.SEARCHTABLE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
      	
        String timestamp = "01/01/2022 12:00:00"; 
        String report = reportManager.getVacuumBagReport(timestamp);
        assertTrue(report.contains("due for replacement"));
    }

    @Test
    public void testGetVacuumBagReportInvalidTimestamp() {
    	
	 	String pathToRoomFile = "input/RoomInfo.txt";
	 	String pathToLogFile = "input/CleaningEvents.txt";
      	try {
			reportManager = new ReportManager(pathToRoomFile, pathToLogFile, DataStructure.SEARCHTABLE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
        String timestamp = "invalid timestamp";
        String expectedError = "Date & time must be in the format: MM/DD/YYYY HH:MM:SS";
        String report = reportManager.getVacuumBagReport(timestamp);
        assertEquals(expectedError, report);
    }


    @Test
    public void testGetFrequencyReport() {
    	
	 	String pathToRoomFile = "input/RoomInfo.txt";
	 	String pathToLogFile = "input/CleaningEvents.txt";
      	try {
			reportManager = new ReportManager(pathToRoomFile, pathToLogFile, DataStructure.SEARCHTABLE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        int number = 5;
        String report = reportManager.getFrequencyReport(number);
        assertTrue(report.contains("Frequency of Cleanings"));
      
    }

    @Test
    public void testGetFrequencyReportInvalidNumber() {
    	
	 	String pathToRoomFile = "input/RoomInfo.txt";
	 	String pathToLogFile = "input/CleaningEvents.txt";
      	try {
			reportManager = new ReportManager(pathToRoomFile, pathToLogFile, DataStructure.SEARCHTABLE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        int number = 0;
        String expectedError = "Number of rooms must be greater than 0";
        String report = reportManager.getFrequencyReport(number);
        assertEquals(expectedError, report);
    }

    @Test
    public void testGetRoomReport() {
    	
	 	String pathToRoomFile = "input/RoomInfo.txt";
	 	String pathToLogFile = "input/CleaningEvents.txt";
      	try {
			reportManager = new ReportManager(pathToRoomFile, pathToLogFile, DataStructure.SEARCHTABLE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        String report = reportManager.getRoomReport();
        assertTrue(report.contains("Room Report"));
    }
}
