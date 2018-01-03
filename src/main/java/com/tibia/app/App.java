package com.tibia.app;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.tibia.helpers.ImageHelper;
import com.tibia.helpers.KeyboardHelper;
import com.tibia.helpers.MouseHelper;
import com.tibia.helpers.OCRHelper;

import net.sourceforge.tess4j.TesseractException;

public class App 
{
    public static void main(String[] args) throws TesseractException, InterruptedException, AWTException, IOException
    {
    	final String TESSERACT_DATA_PATH = "C:/Tesseract"; 
    	
    	ImageHelper imageHelper = new ImageHelper();
    	MouseHelper mouseHelper = new MouseHelper();
    	KeyboardHelper keyboardHelper = new KeyboardHelper();
    	OCRHelper ocrHelper = new OCRHelper(TESSERACT_DATA_PATH);
    	
    	Thread.sleep(2000);
    	
    	/**
    	 * Mouse Click
    	 */
    	mouseHelper.clickOn(340, 595);
    	
    	/**
    	 * Type Something
    	 */
    	keyboardHelper.type("amazon helmet");

    	Thread.sleep(500);
    	
    	/**
    	 * Taking a screenshot
    	 */
    	Robot robot = new Robot();
    	String imageFile = "images/sample.png";
    	
    	Rectangle rectArea = new Rectangle(317, 280, 108, 30);
    	//rectArea.add(new Point(425, 310));
        BufferedImage screenFullImage = imageHelper.resize(robot.createScreenCapture(rectArea), 108 * 3, 30 * 3);
        ImageIO.write(screenFullImage, "png", new File(imageFile));
    
    	/**
    	 * Image recognizing
    	 */
    	System.out.println(ocrHelper.getTextFromImage("images/sample.png"));
    }
}
