package edu.brown.cs.student.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.brown.cs.student.Request.PDFClient;
import edu.brown.cs.student.parser.KMPsearch;


/**
 * Class to hold pdf data
 *
 */
public class Pdf implements Searchable {
	
	private final String url;
	private final Map<Integer, String> pageContentMap;
	
	public Pdf(String url) {
		this.url = url;
		this.pageContentMap = PDFClient.run(url);
	}
	
	
	@Override
	public Result getResult(Search search) {
		List<Match> matches = new ArrayList<Match>();
		if (pageContentMap == null) {
			return new Result(url, matches);
		}
		  for (Integer pageNum : pageContentMap.keySet()) {
			  	String content = pageContentMap.get(pageNum);
				matches.addAll(KMPsearch.allMatches(content, search.pattern, search.surroundingChars, pageNum.toString()));
		  }
		  return new Result(url, matches);
	}
	

}
