package edu.ncsu.csc316.cleaning.manager;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ncsu.csc316.cleaning.data.CleaningLogEntry;
import edu.ncsu.csc316.cleaning.dsa.Algorithm;
import edu.ncsu.csc316.cleaning.dsa.DSAFactory;
import edu.ncsu.csc316.cleaning.dsa.DataStructure;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;

/**
 * Report manager class supports functionality 
 * for the different report types available through
 * the CleaningManager software
 * 
 * @author rohitarun
 *
 */
public class ReportManager {

	/** Date time formatter field */
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    /** Maximum coverage before bag change field */
    public static final int MAX_COVERAGE = 5280;
    /** Cleaning manager field */
	private CleaningManager manager;
	
	/**
	 * Class Constructor
	 * 
	 * @param pathToRoomFile path to room file
	 * @param pathToLogFile path to log file
	 * @param mapType map type
	 * @throws FileNotFoundException if file doesn't exist
	 */
    public ReportManager(String pathToRoomFile, String pathToLogFile, DataStructure mapType) throws FileNotFoundException {
    	manager = new CleaningManager(pathToRoomFile, pathToLogFile, mapType);
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
        DSAFactory.setComparisonSorterType(Algorithm.MERGESORT);
        DSAFactory.setNonComparisonSorterType(Algorithm.COUNTING_SORT);
        DSAFactory.setMapType(mapType);
        
    }
    
    /**
     * Class constructor
     * 
     * @param pathToRoomFile path to room file
     * @param pathToLogFile path to log file
     * @throws FileNotFoundException if path is invalid or file 
     * does not exist
     */
    public ReportManager(String pathToRoomFile, String pathToLogFile) throws FileNotFoundException {
        this(pathToRoomFile, pathToLogFile, DataStructure.SEARCHTABLE);
    }

    /**
     * Returns vacuum bag report 
     * 
     * @param timestamp time
     * @return When bag is due for replacement
     */
    public String getVacuumBagReport(String timestamp) {
    	StringBuilder vacReport = new StringBuilder("Vacuum Bag Report (last replaced " + timestamp + ") [\n");
    	try {
    		LocalDateTime time = LocalDateTime.parse(timestamp, DATE_TIME_FORMAT);
    		int coverage = manager.getCoverageSince(time);
           	if (coverage > MAX_COVERAGE) {
           		vacReport.append("   Bag is overdue for replacement!\n");
           	} else {
           		int sqft = MAX_COVERAGE - coverage;
           		vacReport.append("   Bag is due for replacement in " + sqft + " SQ FT\n");
           	}
    	} catch (Exception e) {
    		return "Date & time must be in the format: MM/DD/YYYY HH:MM:SS";
    	}
       
    	vacReport.append("]");
        return vacReport.toString();
    }

    /**
     * returns frequency of cleaning events per room
     * sorted from lowest to highest frequencies
     * 
     * @param number
     * @return freqReport.toString()
     */
    public String getFrequencyReport(int number) {
    	
        if (number <= 0) {
    		return "Number of rooms must be greater than 0";
    	}
        
        String holder = "";
        String firstRoom = "";
        String secondRoom = "";
        
        int counter = 0;
        int sizeComparator = 0;
        int size = 0;
       
    	StringBuilder freqReport = new StringBuilder("Frequency of Cleanings [\n");
        Map<String, List<CleaningLogEntry>> evByRoom =  manager.getEventsByRoom();
        List<String> freqRooms = DSAFactory.getIndexedList();
        
        for (String room : evByRoom) {
        	freqRooms.addLast(room);
        }

        size = freqRooms.size();
        int i = 0;
        int j = 0;
        while (i < size - 1) { 
        	while(j < size - i - 1) {
        		firstRoom = freqRooms.get(j);
        		secondRoom = freqRooms.get(j + 1);
        		
        		sizeComparator = Integer.compare(evByRoom.get(secondRoom).size(), evByRoom.get(firstRoom).size());
        		
        		if (sizeComparator > 0 || (sizeComparator == 0 && firstRoom.compareTo(secondRoom) > 0)) {
        			holder = freqRooms.get(j);
        			freqRooms.set(j, freqRooms.get(j + 1));
        			freqRooms.set(j + 1, holder);
        		}
        		j++;
        	}  
        	i++;
        }
        
        counter = 0;
        while (counter >= number) {
	        for (String room : freqRooms) {
	        	int events = evByRoom.get(room).size();
	        	freqReport.append("   " + room + " has been cleaned " + events + " times\n");
	        }
	        counter++;
        }
        
        freqReport.append("]");
        
        return freqReport.toString(); 
    	
    }

    /**
     * Returns room report
     * 
     * @return roomReport.toString();
     */
    public String getRoomReport() {
    	
        StringBuilder roomReport = new StringBuilder("Room Report [\n");
        Map<String, List<CleaningLogEntry>> evByRoom = manager.getEventsByRoom();

        boolean roomsCleaned = false;

        for (Map.Entry<String, List<CleaningLogEntry>> entry : evByRoom.entrySet()) {
            String room = entry.getKey();
            List<CleaningLogEntry> entries = entry.getValue();

            if (!entries.isEmpty()) {
                roomsCleaned = true;
                roomReport.append("   " + room + " was cleaned on [\n");

                for (CleaningLogEntry e : entries) {
                    roomReport.append(e.getTimestamp() + "\n");
                }

                roomReport.append("]\n");
            }
        }

        if (!roomsCleaned) {
            return "No rooms have been cleaned.";
        }

        roomReport.append("]");

        return roomReport.toString();
    }
}