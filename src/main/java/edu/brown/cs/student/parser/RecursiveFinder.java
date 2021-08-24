package edu.brown.cs.student.parser;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import edu.brown.cs.student.backend.Html;
import edu.brown.cs.student.backend.Image;
import edu.brown.cs.student.backend.Pdf;
import edu.brown.cs.student.backend.Result;
import edu.brown.cs.student.backend.ResultComparator;
import edu.brown.cs.student.backend.Search;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Class that implments recursive control find
 *
 */
public class RecursiveFinder {
  private Set<String> links_set;
  private Set<Result> result_set;
  private static ExecutorService executorService;
  public static LoadingCache<String, Html> linkHtmlMap;
  public static LoadingCache<String, Image> linkImgMap;
  public static LoadingCache<String, Pdf> linkPdfMap;
  private final int HTML_CACHE_SIZE = 500;
  private final int IMG_CACHE_SIZE = 500;
  private final int PDF_CACHE_SIZE = 500;
  AtomicInteger atomicInt = new AtomicInteger(0);

  /**
   * Contstructor for RecursiveFinder
   */
  public RecursiveFinder() {
    linkHtmlMap = CacheBuilder.newBuilder().maximumSize(HTML_CACHE_SIZE)
            .build(new CacheLoader<String, Html>() {
              @Override
              public Html load(String url)  {
                return new Html(url);
              }
  });
    linkImgMap = CacheBuilder.newBuilder().maximumSize(IMG_CACHE_SIZE)
            .build(new CacheLoader<String, Image>() {
              @Override
              public Image load(String url) {
            	return new Image(url);
              }
  });
    linkPdfMap = CacheBuilder.newBuilder().maximumSize(PDF_CACHE_SIZE)
            .build(new CacheLoader<String, Pdf>() {
              @Override
              public Pdf load(String url) {
                return new Pdf(url);
              }
  });

  }

  /**
   * Start the recursive find algorithm
   * @param startUrl - the current url the user is on
   * @param search - a search object containing the search params
   * @return  - a list of Result objects
   */
  public List<Result> find(String startUrl, Search search) {
	long startTime = System.currentTimeMillis();
    links_set = new HashSet<>();
    result_set = new HashSet<Result>();
    executorService = Executors.newFixedThreadPool(10);
    find_helper(startUrl, 0, search);
    while (atomicInt.get() > 0) {
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    };
    executorService.shutdown();
    try {
        if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
          }
      } catch (InterruptedException e) {
          executorService.shutdownNow();
      }

    //create a list of result object from the result_map
    List<Result> results = new ArrayList<Result>(result_set);
    //Sort the results in order of proprietary metrics
    Collections.sort(results, new ResultComparator());
    long endTime = System.currentTimeMillis();
    System.out.println("Displayed " + results.size() + " results in " + (endTime-startTime)/1000.0 + " seconds");
    return results;


    
  }

  /**
   * Recursive function to iterate through all of the links on a page
   * @param url - the url to search through
   * @param currDepth - the current depth of recurssion
   * @param search - a Search object containin the search parameters
   */
  public void find_helper(String url, int currDepth, Search search) {

	if (result_set.size() >= search.resultLimit) {
		return;
	}
    if ((links_set.contains(url)) || (currDepth >= search.depth)) {
    	return;
    }
    	
    //add the link to the set to prevent searching the same link twice
      links_set.add(url);      
      
      if (url.length() < 4) {
  	    return;
  	  }
      
  	 String urlEnding = url.substring(url.length() - 4);
  	 
  	 
  	//check if the link is a pdf
	if (urlEnding.equals(".pdf")) {
		Pdf pdf = linkPdfMap.getUnchecked(url);
	    Result pdfRes = pdf.getResult(search);
	    if (pdfRes.getNumMatches() > 0) {
			result_set.add(pdfRes);
		}
		return; 
	}
	
	//check if the link is an image
	if (urlEnding.equals(".png") || urlEnding.equals(".jpg")) {
		Image img = linkImgMap.getUnchecked(url);
		Result imgRes = img.getResult(search);
		if (imgRes.getNumMatches() > 0) {
			result_set.add(imgRes);
		}
		return;
	  }
	
	
	//otherwise, parse as an html file
	Html html = linkHtmlMap.getUnchecked(url);
	Result htmlRes = html.getResult(search);
	if (htmlRes.getNumMatches() > 0) {
		result_set.add(htmlRes);
	}
	currDepth++;
	for (String link : html.getLinks()) {
		if (!links_set.contains(link)) {
            int finalCurrDepth = currDepth;
            Runnable runnable = () -> {
                find_helper(link, finalCurrDepth, search);
                atomicInt.decrementAndGet();
            };
            atomicInt.getAndIncrement();
            executorService.execute(runnable);
		}
	}
	
	if (search.searchImages) {	
		//run ocr on all of the embedded images (this takes a while!)
			for (String imgUrl : html.getImageLinks()) {
	            Runnable runnable = () -> {
	            	Image img = linkImgMap.getUnchecked(imgUrl);
	    			Result imgRes = img.getResult(search);
	    			if (imgRes.getNumMatches() > 0) {
	    				result_set.add(imgRes);
	    			}
	                atomicInt.decrementAndGet();
	            };
	            atomicInt.getAndIncrement();
	            executorService.execute(runnable);
			}
		}
	  }


}
