package edu.brown.cs.student.backend;

import java.util.Comparator;

/**
 * ResultComparator class used to make comparisons between Results.
 * Results which have a higher number of matches are given priority.
 * @author Eddy Li.
 */
public class ResultComparator implements Comparator<Result> {

  /**
   * Empty constructor.
   */
  public ResultComparator() {

  }

  /**
   * Returns positive when the first result has fewer matches than the second, negative otherwise.
   * @param r1 Result.
   * @param r2 Result.
   * @return Integer.
   */
  public int compare(Result r1, Result r2) {

    if (r1.getNumMatches() < r2.getNumMatches()) {
      return 1;
    }
    else {
      return -1;
    }
  }


}
