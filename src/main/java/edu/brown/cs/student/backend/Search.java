package edu.brown.cs.student.backend;

/**
 * Class to store all of the search parameters
 *
 */
public class Search {
	public final String pattern;
	public final int depth;
	public final int surroundingChars;
	public final int resultLimit;
	public final boolean searchImages;
	
	public Search(String pattern, int depth, int surroundingChars, int resultLimit, String searchImages) {
		this.pattern = pattern;
		this.depth = depth;
		this.surroundingChars = surroundingChars;
		this.resultLimit = resultLimit;
		this.searchImages = (searchImages.equals("off") ? false : true);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + depth;
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		result = prime * result + resultLimit;
		result = prime * result + (searchImages ? 1231 : 1237);
		result = prime * result + surroundingChars;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Search))
			return false;
		Search other = (Search) obj;
		if (depth != other.depth)
			return false;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		if (resultLimit != other.resultLimit)
			return false;
		if (searchImages != other.searchImages)
			return false;
		if (surroundingChars != other.surroundingChars)
			return false;
		return true;
	}





}
