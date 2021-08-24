package edu.brown.cs.student.parser;

import edu.brown.cs.student.backend.Match;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which performs the KMP algorithm search.
 */
public final class KMPsearch {

	/**
	 * Create a list of match objects from the search text and pattern
	 * 
	 * @param text - the text to search for the pattern in
	 * @param pattern - the patter to look for
	 * @param surroundingTextChars - how many characters to include around a match
	 * @param location - the starting index of the match
	 * @return - a list of Matches
	 */

  public static List<Match> allMatches(String text, String pattern, int surroundingTextChars, String location) {
	ArrayList<Integer> occurrences = new ArrayList<>();
	KMP(text, pattern, occurrences);
	List<Match> matches = new ArrayList<Match>();
    for (int i : occurrences) {
      String surroundingText = text.substring(Math.max(0,i-surroundingTextChars), Math.min(text.length()-1, i+surroundingTextChars));
      matches.add(new Match(location, surroundingText));
    }
    return matches;
  }


  /**
   * Run the KMP algorithm
   * 
   * @param text - the text to search through
   * @param pattern - the pattern to look for
   * @param occurrences - a list to fill with the indices of matches
   */
  public static void KMP(String text, String pattern, List<Integer> occurrences)
  {
    occurrences.clear();
    // Base Case 1: Y is null or empty
    if (pattern == null || pattern.length() == 0)
    {
      return;
    }

    // Base Case 2: X is null or X's length is less than that of Y's
    if (text == null || pattern.length() > text.length())
    {
      return;
    }

    char[] chars = pattern.toCharArray();

    // next[i] stores the index of next best partial match
    int[] next = new int[pattern.length() + 1];
    for (int i = 1; i < pattern.length(); i++)
    {
      int j = next[i + 1];

      while (j > 0 && chars[j] != chars[i])
        j = next[j];

      if (j > 0 || chars[j] == chars[i])
        next[i + 1] = j + 1;
    }

    for (int i = 0, j = 0; i < text.length(); i++)
    {
      if (j < pattern.length() && text.charAt(i) == pattern.charAt(j))
      {
        if (++j == pattern.length())
        {
          occurrences.add(i - j + 1); //Location of needle instance in haystack, points to the first character
        }
      }
      else if (j > 0)
      {
        j = next[j];
        i--;	// since i will be incremented in next iteration
      }
    }
  }

}





