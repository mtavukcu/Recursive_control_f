package edu.brown.cs.student.backend;

/**
 * Class to hold the location and surrounding text of a match
 * 
 */
public class Match {
	private final String location;
	private final String surroundingText;
	
	public Match(String location, String surroundingText) {
		this.location = location;
		this.surroundingText = surroundingText;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the surroundingText
	 */
	public String getSurroundingText() {
		return surroundingText;
	}

}
