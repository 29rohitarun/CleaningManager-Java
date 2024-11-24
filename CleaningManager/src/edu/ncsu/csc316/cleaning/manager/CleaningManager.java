package edu.ncsu.csc316.cleaning.manager;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import edu.ncsu.csc316.cleaning.data.CleaningLogEntry;
import edu.ncsu.csc316.cleaning.data.RoomRecord;
import edu.ncsu.csc316.cleaning.dsa.Algorithm;
import edu.ncsu.csc316.cleaning.dsa.DSAFactory;
import edu.ncsu.csc316.cleaning.dsa.DataStructure;
import edu.ncsu.csc316.cleaning.io.InputReader;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;

public class CleaningManager {
	
	private Map<String, List<CleaningLogEntry>> eventsByRoom;
	private List<CleaningLogEntry> cleaningEntries;
	private List<RoomRecord> rooms;

	/**
	 * Class constructor
	 * 
	 * @param pathToRoomFile
	 * @param pathToLogFile
	 * @param mapType
	 * @throws FileNotFoundException
	 */
    public CleaningManager(String pathToRoomFile, String pathToLogFile, DataStructure mapType) throws FileNotFoundException {

    	DSAFactory.setMapType(mapType);
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
        DSAFactory.setComparisonSorterType(Algorithm.MERGESORT);
        DSAFactory.setNonComparisonSorterType(Algorithm.COUNTING_SORT);
       
        cleaningEntries = InputReader.readLogFile(pathToLogFile);
        rooms = InputReader.readRoomFile(pathToRoomFile);
        eventsByRoom = DSAFactory.getMap(null);
        populateEventsByRoom(cleaningEntries, rooms);
    }
    
    /**
     * Class constructor
     * 
     * @param pathToRoomFile
     * @param pathToLogFile
     * @throws FileNotFoundException
     */
    public CleaningManager(String pathToRoomFile, String pathToLogFile) throws FileNotFoundException {
        this(pathToRoomFile, pathToLogFile, DataStructure.SEARCHTABLE);
    }

    public Map<String, List<CleaningLogEntry>> getEventsByRoom() {
    	
    	return eventsByRoom;
    }

    /**
     * Outputs the square feet covered by the vacuum 
     * since the last bag replacement date and time

     * @param time time
     * @return square feet covered
     */
    public int getCoverageSince(LocalDateTime time) {
        
    	int coverage = 0;
    	int cleanedArea = 1;
    	
    	for (CleaningLogEntry e : cleaningEntries) {
    		for (RoomRecord r : rooms) {
	    		if ((e.getTimestamp().compareTo(time) > 0) && (e.getRoomID().equals(r.getRoomID()))) { //MIGHT HAVE TO BE GREATER THAN OR EQUAL TO
	    			
    				cleanedArea = (r.getLength() * r.getWidth() * e.getPercentCompleted()) / 100;
    				coverage += cleanedArea;
	    			
	    		}
    		}
    	}
    	
    	return coverage;
    }
    
    /**
     * Helper method that populates the eventsByRoom
     * private field
     * 
     * @param entryLog list of cleaning entries
     * @param roomLog list of rooms
     * 
     * @return evByRoom events by room
     */
    private void populateEventsByRoom(List<CleaningLogEntry> entryLog, List<RoomRecord> roomLog) {
    	
    	eventsByRoom = DSAFactory.getMap(null);
    	List<CleaningLogEntry> entries = DSAFactory.getIndexedList();
    	
    	for (RoomRecord room : roomLog) {   		
    		for (CleaningLogEntry entry : entryLog) {   			
    			if (entry.getRoomID().equals(room.getRoomID())) {
    				entries.addLast(entry);
    			}
    		}
    		eventsByRoom.put(room.getRoomID(), entries);
    	}
    }
      

}