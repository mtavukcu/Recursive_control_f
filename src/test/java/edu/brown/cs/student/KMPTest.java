package edu.brown.cs.student;

import edu.brown.cs.student.backend.Match;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static edu.brown.cs.student.parser.KMPsearch.KMP;
import static edu.brown.cs.student.parser.KMPsearch.allMatches;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class KMPTest {

  String text;
  String pattern;
  List<Integer> occurrences;
  int surroundingTextChars;
  String location;

  @Before
  public void setUp() {
    occurrences = new ArrayList<>();
    text = "aasfdasdfasdf";
    pattern = "asdf";
    surroundingTextChars = 100;
    location = "This is a sentence aasfdasdfasdf with long pieces of text.";
  }

  @After
  public void tearDown() {
    occurrences = null;
    text = "";
    pattern = "";
    surroundingTextChars = 0;
    location = "";
  }

  @Test
  public void testKMP() {
    setUp();
    //regular test
    KMP(text,pattern, occurrences);
    assertEquals(occurrences, Arrays.asList(5, 9));
    //empty string test
    KMP("", "sdf", occurrences);
    assertEquals(occurrences, new ArrayList<>());
    //no pattern test
    KMP("aasfdasdfasdf","xsdf", occurrences);
    assertEquals(occurrences, new ArrayList<>());
    //close but still no pattern test
    KMP("aasfdasdfasdf","aaa", occurrences);
    assertEquals(occurrences, new ArrayList<>());
    //many matches test
    KMP("aasfdasdfasdf","a", occurrences);
    assertEquals(occurrences, Arrays.asList(0, 1, 5, 9));
    tearDown();


  }

  @Test
  public void allMatchesTest() {
    //allMatches tests
    setUp();
    List<Match> matches = new ArrayList<>();
    String surroundingText = "aasfdasdfasd";
    matches.add(new Match(location, surroundingText));
    matches.add(new Match(location, surroundingText));
    List<Match> matchesTest = allMatches(text, pattern, 100, location);
    assertEquals(matchesTest.get(0).getLocation(), matches.get(0).getLocation());
    assertEquals(matchesTest.get(0).getSurroundingText(), matches.get(0).getSurroundingText());
    tearDown();
  }


}



