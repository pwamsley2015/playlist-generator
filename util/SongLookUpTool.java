package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SongLookUpTool {
private static ArrayList<String> songNames = new ArrayList<>(); //the dictionary 
	
	private static boolean isSetUp = false; 
	
	private SongLookUpTool() {}

	public static ArrayList<String> getCompleteSongList() {
		if (!isSetUp)
			setUp();
		return new ArrayList<String>(songNames); 
	}
	
	public static String[] getCompleteSongArray() {
		if (!isSetUp)
			setUp();
		return songNames.toArray(new String[]{}); 
	}
	
	public static boolean hasSong(String songName) {
		
		if (!isSetUp)
			setUp(); 
		
		for (String song : songNames)
			if (song.equalsIgnoreCase(songName))
				return true;
		
		return false;
	}
	
	public static String getRandomSong() {
		if (!isSetUp)
			setUp(); 
		return songNames.get((int)Math.random() * songNames.size()); 
	}
	
	public static void resetSongList() {
		isSetUp = false; 
		setUp(); 
	}
	
	public synchronized static void setUp() {

		if (isSetUp)
			return; // This shouldn't ever happen but ya know
		
		/*
		 * Creates file {~/artistList} (at the moment) containing all artists.
		 * 
		 * Format: 
		 * ,Alt-J,Artic\ Monkeys,Blink-182,
		 */
		Scripts.createSongListFile();  

		/*
		 * Give it some time to run that script
		 * (This is actually needed)
		 */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		
		/*
		 * Now lets fill up our "dictionary" which contains all the artists names
		 */
		try {
			loadArtistNames();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		/*
		 * Sort it, so users can find stuff easily and for fast searches later.
		 */
		Collections.sort(songNames); 
		
		isSetUp = true; 
		System.out.println("SongLookUpTool setup!");
	}

	private static void loadArtistNames() throws Exception {

		File ourFile = new File(System.getProperty("user.home") + File.separator + "songList"); 

		StringBuilder fileContents = new StringBuilder((int)ourFile.length()); 
		Scanner scanner = new Scanner(ourFile); 
		String lineSeperator = System.getProperty("line.seperator");

		String completeFileAsString = ""; 

		try {
			while (scanner.hasNextLine()) 
				fileContents.append(scanner.nextLine() + lineSeperator);

			completeFileAsString = fileContents.toString(); 
		} finally {
			scanner.close();
		}

		//we now have a String version of the file contents saved in completeFileAsString... time to parse

		//first get rid of the awkward " thing\ " thats a result of the script we used.
		String artistsAsCSV = removeAllInstancesOfCharacter('\\', completeFileAsString); 
		artistsAsCSV = artistsAsCSV.substring(1); //get rid of that first ","
		
		while (true) {
			try {
				songNames.add(artistsAsCSV.substring(0, artistsAsCSV.indexOf(","))); 
				artistsAsCSV = artistsAsCSV.substring(artistsAsCSV.indexOf(",") + 1); 
			} catch (Exception e) {
				break; //lazy way to not worry about how many artists are in the list etc.
			}
		}
		//need to get the last artist
		songNames.add(artistsAsCSV.substring(0, artistsAsCSV.indexOf("null")));

	}
	
	private static String removeAllInstancesOfCharacter(char toRemove, String orginal) {
		
		String returnString = ""; 
		
		for (char character : orginal.toCharArray()) 
			if (!(character == toRemove))
				returnString += character; 
		return returnString; 
	}
	public static void main(String[] args) {
		setUp(); 
	}
}
