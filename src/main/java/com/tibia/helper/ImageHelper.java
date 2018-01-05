package com.tibia.helper;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.sourceforge.tess4j.TesseractException;

public class ImageHelper {	
	private final String SAMPLE_IMAGE_PATH = "images/sample.png";
	
	private Robot robot;
	private OCRHelper ocrHelper;
	
	public ImageHelper() throws AWTException {
		this.robot = new Robot();
		this.ocrHelper = new OCRHelper();
	}
	
	public BufferedImage resize(BufferedImage image, int newWidth, int newHeight) throws IOException {
		return Thumbnails.of(image).forceSize(newWidth, newHeight).asBufferedImage();
	}
	
	public String getTextFromImage(int topX, int topY, int bottomX, int bottomY) throws IOException, TesseractException {
		int rateToResizeImage = 10;
		int[] rectangleSize = getSizeFromTwoPoints(topX, topY, bottomX, bottomY);
		
    	Rectangle rectArea = new Rectangle(topX, topY, rectangleSize[0], rectangleSize[1]);
        BufferedImage screenFullImage = resize(this.robot.createScreenCapture(rectArea), rectangleSize[0] * rateToResizeImage, rectangleSize[1] * rateToResizeImage);
        ImageIO.write(screenFullImage, "png", new File(SAMPLE_IMAGE_PATH));
    	
    	return this.ocrHelper.getTextFromImage(SAMPLE_IMAGE_PATH);
	}
	
	private int[] getSizeFromTwoPoints(int topX, int topY, int bottomX, int bottomY) {
		int[] size = new int[2];
		
		size[0] = (bottomX - topX);
		size[1] = (bottomY - topY);
				
		return size;
	}
}
