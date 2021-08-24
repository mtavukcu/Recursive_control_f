package edu.brown.cs.student.backend;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.student.Request.ImageClient;
import edu.brown.cs.student.parser.KMPsearch;

/** 
 * Class to hold image data
 *
 */
public class Image implements Searchable {
	private final String url;
	private final String content;
	
	public Image(String url) {
		this.url = url;
		ImageClient imgCli = new ImageClient();
		this.content = imgCli.run(url);
	}
	
	@Override
	public Result getResult(Search search) {
		List<Match> matches = new ArrayList<Match>();
		if ((this.content == null) || (this.content.isBlank())) {
			return new Result(url, matches);
		}
		matches.addAll(KMPsearch.allMatches(content, search.pattern, search.surroundingChars, ""));
  	  	return new Result(url, matches);
	}
	

}
