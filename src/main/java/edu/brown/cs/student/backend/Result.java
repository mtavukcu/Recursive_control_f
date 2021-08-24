package edu.brown.cs.student.backend;

import java.util.List;

/**
 * Class that represents the result of a search on a link
 *
 */
public class Result {
	private final String url;
	private final int numMatches;
	private final List<Match> matches;

  public Result(String url, List<Match> matches) {
	this.url = url;
	this.numMatches = matches.size();
	this.matches = matches;
  }

/**
 * @return the url
 */
public String getUrl() {
	return this.url;
}

/**
 * @return the numMatches
 */
public int getNumMatches() {
	return this.numMatches;
}

/**
 * @return the matches
 */
public List<Match> getMatches() {
	return this.matches;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((matches == null) ? 0 : matches.hashCode());
	result = prime * result + numMatches;
	result = prime * result + ((url == null) ? 0 : url.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (!(obj instanceof Result))
		return false;
	Result other = (Result) obj;
	if (matches == null) {
		if (other.matches != null)
			return false;
	} else if (!matches.equals(other.matches))
		return false;
	if (numMatches != other.numMatches)
		return false;
	if (url == null) {
		if (other.url != null)
			return false;
	} else if (!url.equals(other.url))
		return false;
	return true;
}

}