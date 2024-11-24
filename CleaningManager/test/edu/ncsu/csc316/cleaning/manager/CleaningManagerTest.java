package edu.ncsu.csc316.cleaning.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc316.cleaning.data.CleaningLogEntry;
import edu.ncsu.csc316.cleaning.dsa.DataStructure;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.list.List;

class CleaningManagerTest {

    @Test
    void testCleaningManagerStringStringDataStructure() {

	 	String pathToRoomFile = "input/RoomInfo.txt";
	 	String pathToLogFile = "input/CleaningEvents.txt";
        
        try {
            CleaningManager manager = new CleaningManager(pathToRoomFile, pathToLogFile, DataStructure.SEARCHTABLE);
            assertNotNull(manager);
        } catch (Exception e) {
            fail("Exception thrown when initializing CleaningManager");
        }
    }

    @Test
    void testCleaningManagerStringString() {

	 	String pathToRoomFile = "input/RoomInfo.txt";
	 	String pathToLogFile = "input/CleaningEvents.txt";
        
        try {
            CleaningManager manager = new CleaningManager(pathToRoomFile, pathToLogFile);
            assertNotNull(manager);
        } catch (Exception e) {
            fail("Exception thrown when initializing CleaningManager");
        }
    }

    @Test
    void testGetEventsByRoom() {

	 	String pathToRoomFile = "input/RoomInfo.txt";
	 	String pathToLogFile = "input/CleaningEvents.txt";
        
        try {
            CleaningManager manager = new CleaningManager(pathToRoomFile, pathToLogFile);
            Map<String, List<CleaningLogEntry>> eventsByRoom = manager.getEventsByRoom();
            assertNotNull(eventsByRoom);
            assertFalse(eventsByRoom.isEmpty());
        } catch (Exception e) {
            fail("Exception thrown when initializing CleaningManager");
        }
    }

    @Test
    void testGetCoverageSince() {

	 	String pathToRoomFile = "input/RoomInfo.txt";
	 	String pathToLogFile = "input/CleaningEvents.txt";
        
        try {
            CleaningManager manager = new CleaningManager(pathToRoomFile, pathToLogFile);
            LocalDateTime time = LocalDateTime.now(); 
            int coverage = manager.getCoverageSince(time);
         
            assertEquals(0, coverage);
        } catch (Exception e) {
            fail("Exception thrown when initializing CleaningManager");
        }
        
    }
}