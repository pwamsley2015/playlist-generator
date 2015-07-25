package util;

import java.io.IOException;

public class Scripts {

	private static final String TELL = "tell application \"iTunes\"\n"; 

	private Scripts() {}

	private static void run(String commands) {

		String[] args = new String[3]; 

		args[0] = "osascript"; 
		args[1]	= "-e"; 
		args[2] = commands; 

		try {
			Runtime.getRuntime().exec(args).waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} 

	}

	private static void testDisplay() {
		run( 
				"display dialog \"Hello world\""
						+ "\ndisplay dialog \"herro world\""
				); 
	}

	public static void open_iTunes() {
		run(
				TELL + 
				"activate" + "\n" +
				"end tell"
				); 
	}

	public static void createPlaylistByArtist(String playlistName, String[] artists) {

		String artistListCompleteString = "{"; 

		for (String artist : artists)
			artistListCompleteString += "\"" + artist + "\","; 

		//get rid of the last comma and add the closing bracket
		artistListCompleteString = artistListCompleteString.substring(0, artistListCompleteString.length() - 1); 
		artistListCompleteString += "}"; 

		run(
				TELL + 
				"	set myArtists to " + artistListCompleteString + "\n" +
				"	set newPlaylist to (make new user playlist with properties {name:\"" + playlistName + "\"})" + "\n" +
				"	repeat with anItem in myArtists" + "\n" +
				"		set anItem to (contents of anItem)" + "\n" +
				"		if anItem is not \"\" then try" + "\n" +
				"			set myTracks to (location of file tracks of playlist \"Music\" whose artist is anItem)"+ "\n" +
				"			add myTracks to newPlaylist" + "\n" +
				"		on error failed" + "\n" +
				"			display dialog \"Failed to create playlist\"" + "\n" +
				"		end try" + "\n" +
				"	end repeat" + "\n" +	
				"end tell" + "\n" +

				TELL + 
				"	activate" + "\n" +
				"end tell"

				); 
	}



	public static void createArtistListFile() {

		/* 
		 * First delete the file. 
		 * This won't do anything if its the first time running, 
		 * but otherwise we want to make sure this file is up to date
		 */ 	
		run("do shell script \"rm ~/artistList\""); 

		//now we can move on to creating the file and filling it with the artists

		run(
				//		System.out.println(
				TELL + 
				"	set allArtists to (get artist of file tracks of library playlist 1)" + "\n" +  
				"	set artistList to {}" + "\n" +  
				"	repeat with i from 1 to count items in allArtists" + "\n" +  
				"		set art to item i of allArtists" + "\n" +  
				"		if art is not \"\" and art is not in artistList then set end of artistList to art" + "\n" +  
				"	end repeat" + "\n" +  
				"end tell" + "\n" +  
				"set artistListString to \"\"" + "\n" +  
				"repeat with currentArtist in artistList" + "\n" +  
				"	set artistListString to artistListString & \",\" & currentArtist" + "\n" +  
				"end repeat" + "\n" +  
				"set artistListString to replaceString(artistListString,\" \", \"\\\\ \")" + "\n" +  
				"do shell script \"cd ~; touch artistList; echo \" & \"\\\"\" & artistListString & \"\\\"\" & \" >> artistList\"" + "\n" +  
				"on replaceString(theText, oldString, newString)" + "\n" +  
				"	local ASTID, theText, oldString, newString, lst" + "\n" +  
				"	set ASTID to AppleScript's text item delimiters" + "\n" +  
				"	try"  + "\n" +  
				"		considering case" + "\n" +  
				"			set AppleScript's text item delimiters to oldString" + "\n" +  
				"			set lst to every text item of theText" + "\n" +  
				"			set AppleScript's text item delimiters to newString" + "\n" +  
				"			set theText to lst as string" + "\n" +  
				"		end considering" + "\n" +  
				"		set AppleScript's text item delimiters to ASTID" + "\n" +  
				"		return theText" + "\n" +  
				"	on error eMsg number eNum"  + "\n" +  
				" 		set AppleScript's text item delimiters to ASTID" + "\n" +  
				"		error \"Can't replaceString: \" & eMsg number eNum" + "\n" +  
				"	end try" + "\n" + 
				"end replaceString" 
				); 
	}

	//extremely similar to createArtistListFile()
	public static void createSongListFile() {

		//same idea as with artists... delted this if its here.
		run("do shell script \"rm ~/songList\""); 

		run(
//		System.out.println(
				TELL + 
				"set songNames to name of tracks of library playlist 1" + "\n" +
				"end tell" + "\n" +
				"set songNamesAsString to \"\"" + "\n" +
				"repeat with currSong in songNames" + "\n" +
				"	set songNamesAsString to songNamesAsString & \",\" & currSong" + "\n" +
				"end repeat" + "\n" +
				"set songNamesAsString to replaceString(songNamesAsString, \" \", \"\\\\ \")" + "\n" +
				"do shell script \"cd ~; touch songList; echo \" & \"\\\"\" & songNamesAsString & \"\\\"\" & \" >> songList\"" + "\n" +
				"on replaceString(theText, oldString, newString)" + "\n" +  
				"	local ASTID, theText, oldString, newString, lst" + "\n" +  
				"	set ASTID to AppleScript's text item delimiters" + "\n" +  
				"	try"  + "\n" +  
				"		considering case" + "\n" +  
				"			set AppleScript's text item delimiters to oldString" + "\n" +  
				"			set lst to every text item of theText" + "\n" +  
				"			set AppleScript's text item delimiters to newString" + "\n" +  
				"			set theText to lst as string" + "\n" +  
				"		end considering" + "\n" +  
				"		set AppleScript's text item delimiters to ASTID" + "\n" +  
				"		return theText" + "\n" +  
				"	on error eMsg number eNum"  + "\n" +  
				" 		set AppleScript's text item delimiters to ASTID" + "\n" +  
				"		error \"Can't replaceString: \" & eMsg number eNum" + "\n" +  
				"	end try" + "\n" + 
				"end replaceString" 

				); 
	}

}


