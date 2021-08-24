package edu.brown.cs.student.backend;

/**
 * Interface for getting results from links
 *
 */
public interface Searchable {
  Result getResult(Search search);
}
