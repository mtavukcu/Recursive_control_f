package edu.brown.cs.student.Request;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;

/**
 * Class to parse images from urls
 *
 */
public class ImageClient {
	private final Tesseract tesseract = new Tesseract();
	
	/**
	 * Constructor for Image Client. Configures the Tesseract Object
	 */
	public ImageClient() {
		tesseract.setDatapath("./data/tesseract/tessdata");
		tesseract.setTessVariable("user_defined_dpi", "300");
		org.apache.log4j.PropertyConfigurator.configure("./data/tesseract/log4j.properties.txt"); // sets properties file for log4j
	}
	
	
    public String run(String imageURL) {
    	//TODO: debug stack overflow error
    	String content = "";
    	try {
	    	URL url = new URL(imageURL);
	    	BufferedImage imageFile = ImageIO.read(url);        
	        if (imageFile != null) {
	        	content = tesseract.doOCR(imageFile);
	        }
	        
	    } catch (Exception e) {}	//bury all exceptions
    	return content;
    }
    
}
