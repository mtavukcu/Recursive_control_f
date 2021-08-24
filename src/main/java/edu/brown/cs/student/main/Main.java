package edu.brown.cs.student.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.brown.cs.student.backend.Match;
import edu.brown.cs.student.backend.Result;
import edu.brown.cs.student.backend.Search;
import edu.brown.cs.student.parser.RecursiveFinder;
import freemarker.template.Configuration;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 *
 */
public final class Main {

  private static final int DEFAULT_PORT = 8080;
  private static RecursiveFinder rf = new RecursiveFinder();
  private static final Gson GSON = new Gson();

  private static String pageURL;
  private static String searchText;
  private static int rDepth;
  private static int resLimit;
  private static int surroundingChars;
  private static String cSensitive;
  private static String wholeWords;
  private static String searchImages;
  private static List<Result> results;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    runSparkServer(DEFAULT_PORT);
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());
    FreeMarkerEngine freeMarker = createEngine();
    // Setup Spark Routes
    Spark.get("/results", new MainHandler(), freeMarker);
    Spark.post("/dynamicresults", new DynamicResultsHandler());
    Spark.post("/iterateresults", new SelectMatchHandler());
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }


  /**
   * A handler to produce our main service site.
   *
   * @return ModelAndView to render. (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      //initialize values using the user input
      pageURL = req.queryParams("pageURL");
      searchText = req.queryParams("searchText");
      rDepth = Integer.parseInt(req.queryParams("rDepth"));
      resLimit = Integer.parseInt(req.queryParams("resLimit"));
      resLimit = (resLimit <= 0) ? Integer.MAX_VALUE : resLimit; //default unlimited results
      cSensitive = req.queryParamOrDefault("cSensitive", "off");
      wholeWords = req.queryParamOrDefault("wholeWords", "off");
      searchImages = req.queryParamOrDefault("searchImages", "off");
      //TODO: query this
      int sChars = 100;
      try {
        sChars = Integer.parseInt(req.queryParamOrDefault("sChars", "100"));
      } catch (NumberFormatException | NullPointerException e) {
        sChars = 100;
      }
      // surroundingChars =
      //         Integer.parseInt(req.queryParamOrDefault("sChars", "100"));
      surroundingChars = sChars;

      //perform search
      //TODO: be careful with ordering. can these be labeled?
      Search search = new Search(searchText, rDepth, surroundingChars, resLimit, searchImages);
      results = rf.find(pageURL, search);

      JsonArray json = new JsonArray();

      for (Result result : results) {
          JsonArray temp = new JsonArray();
          temp.add(result.getUrl());
          temp.add(result.getNumMatches());
          temp.add(result.getMatches().get(0).getSurroundingText());
          json.add(temp);
        }

      Map<String, Object> variables = ImmutableMap.of("pageURL", pageURL,
          "searchText", searchText, "rDepth", rDepth, "resLimit", resLimit,
          "results", json);
      return new ModelAndView(variables, "main.ftl");
    }
  }


  /**
   * Handles new results when a user changes their search using the search bar.
   * @return ModelAndView to render. (main.ftl).
   */
  private static class DynamicResultsHandler implements Route {
    @Override
    public JsonArray handle(Request req, Response res) {
      searchText = req.queryParams("searchInput");

      //TODO: be careful with ordering. can these be labeled?
      Search search = new Search(searchText, rDepth, surroundingChars, resLimit, searchImages);
      results = rf.find(pageURL, search);

      JsonArray json = new JsonArray();
      for (Result result : results) {
          JsonArray temp = new JsonArray();
          temp.add(result.getUrl());
          temp.add(result.getNumMatches());
          temp.add(result.getMatches().get(0).getSurroundingText());
          json.add(temp);
        }

      return json;
    }
  }


  /**
   * Handles the left and right arrow keys to show prev and next matches
   * @return ModelAndView to render. (main.ftl).
   */
  private static class SelectMatchHandler implements Route {
    @Override
    public JsonObject handle(Request req, Response res) {
    	int matchIndex = Integer.parseInt(req.queryParams("matchIndex"));
    	int rowIndex = Integer.parseInt(req.queryParams("rowIndex"));
    	Result result = results.get(rowIndex);
    	int newMatchIndex = Math.min(Math.max(0, matchIndex), result.getNumMatches()-1);
    	String newPeekText = result.getMatches().get(newMatchIndex).getSurroundingText();
    	JsonObject json = new JsonObject();
    	json.addProperty("newPeekText",newPeekText);
    	json.addProperty("newMatchIndex", newMatchIndex);
    	return json;
    }
  }


}
