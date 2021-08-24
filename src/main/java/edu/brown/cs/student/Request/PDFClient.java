package edu.brown.cs.student.Request;

import java.io.File;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

/**
 * Class to parse PDF files from urls
 *
 */
public class PDFClient {

	/**
	 * runs the parser
	 * @param PDFurl - url to the pdf
	 * @return - a map from page number to string content
	 */
    public static Map<Integer, String> run(String PDFurl) {
    	Map<Integer, String> pageMap = new HashMap<Integer, String>();
    	try {
    		String tempPath = "temp" + new Integer(PDFurl.hashCode()).toString();
	    	URL url = new URL(PDFurl);
	    	InputStream in = url.openStream();
	    	Files.copy(in, Paths.get(tempPath), StandardCopyOption.REPLACE_EXISTING);
	    	File inFile = new File(tempPath);
	    	if (inFile == null || !inFile.exists()) {
	    		return null;
	    	}
	        PdfReader reader = new PdfReader(inFile);
	        PdfDocument pdfDoc = new PdfDocument(reader);
	        // get the number of pages in PDF
	        int noOfPages = pdfDoc.getNumberOfPages();
	        for(int i = 1; i <= noOfPages; i++) {
	            // Extract content of each page
	            String pageContent = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
	            pageMap.put(i, pageContent);
	        }

	        pdfDoc.close();
	        inFile.delete();
	        return pageMap;
        } catch (Exception e) {
            return new HashMap<Integer, String>();
        } 
    }
    
    

}
