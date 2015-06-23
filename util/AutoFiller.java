package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class AutoFiller {

	public static enum Type {SONGS, ARTISTS}; 

	private ArrayList<String> possibleResults; 
	
	private String lastInput; 

	private Type type;
	private int returnListSize; 

	private boolean configured; 

	public AutoFiller(Type type, int returnListSize) {
		config(type, returnListSize); 
	} 

	public void config(Type type, int returnListSize) {

		this.type = type; 
		this.returnListSize = returnListSize; 

		possibleResults = type == Type.SONGS ?
							  SongLookUpTool.getCompleteSongList() :
							  ArtistLookUpTool.getCompleteArtistList();  
		lastInput = ""; 
		
		configured = true; 
	}

	public void reset() {
		config(type, returnListSize); 
	}

	public void reset(Type type, int returnListSize) {
		config(type, returnListSize); 
	}

	public ArrayList<String> get(String currentInput) {

		if (!configured)
			throw new IllegalStateException("AutoFiller cannot be used until it is configured using AutoFiller.config()"); 

		//if they deleted something
		if (!(currentInput.contains(lastInput))) 
			possibleResults = ArtistLookUpTool.getCompleteArtistList(); 
		
		//Get rid of every result that doesn't contain whats already given as input		
		for (int i = possibleResults.size() - 1; i >= 0; i--)
			if (!(possibleResults.get(i).contains(currentInput)))
				possibleResults.remove(i); 

		if (possibleResults.size() > returnListSize) { 
			ArrayList<String> returnList = new ArrayList<>();  
			for (int pos = 0; pos < returnListSize; pos++)
				returnList.add(possibleResults.get(pos));
			lastInput = currentInput; 
			return returnList;  
		}
		
		lastInput = currentInput; 
		return possibleResults; 
	}
	
}
