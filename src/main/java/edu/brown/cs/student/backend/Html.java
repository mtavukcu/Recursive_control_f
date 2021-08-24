package edu.brown.cs.student.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import edu.brown.cs.student.parser.KMPsearch;

/**
 * Class that holds html data
 *
 */
public class Html implements Searchable {
	private final String url;
	private final Document doc;
	private final Set<String> imageLinks = new HashSet<String>();
	private final Set<String> links = new HashSet<String>();
	
	public Html(String url) {
		this.url = url;
		this.doc = getDoc(url);	
		this.scrapeImageLinks();
		this.scrapeLinks();
	}

	@Override
	public Result getResult(Search search) {
	List<Match> matches = new ArrayList<Match>();
  	  if (doc == null) {
  		  return new Result(url, matches);
  	  }
  	  //TODO: make it so this ignores punctuation around the pattern (i.e. comma or period)
  	  Elements elements = doc.select(":containsOwn(" + search.pattern + " )");
  	  for (Element element : elements) {
  		//TODO: insert THE LOCATION FOR getMatches
  		matches.addAll(KMPsearch.allMatches(element.text(), search.pattern, search.surroundingChars, ""));
  	  }
  	  return new Result(url, matches);
	}
	
	/**
	 * Get the Document
	 * @param url - url of html site
	 * @return a jsoup Document object or null
	 */
	private Document getDoc(String url) {
	    Document doc;
		try {
			doc = Jsoup.connect(url)
			        .userAgent("Chrome")
			        .get();
		} catch (Exception e) {
			return null;
		}
	    return doc;
	  }
	
	/**
	 * Scrape all of the links to embedded images
	 */
	private void scrapeImageLinks() {
		if (this.doc != null) {
			Elements imageEls = doc.select("img[src]");
			for (Element img : imageEls) {
				String img_url = img.attr("abs:src");
				this.imageLinks.add(img_url);
			}
		}
	}
	
	/**
	 * Scrape all of href links
	 */
	private void scrapeLinks() {
		if (this.doc != null) {
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String new_url = link.attr("abs:href");
				this.links.add(new_url);
			}
		}
	}
	
	public Set<String> getLinks() {
		return this.links;
	}
	
	public Set<String> getImageLinks() {
		return this.imageLinks;
	}

}
