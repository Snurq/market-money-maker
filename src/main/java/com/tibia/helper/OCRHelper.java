package com.tibia.helper;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRHelper {
	private Tesseract tesseract;
	
	public OCRHelper(String dataPath) {
		this.tesseract = new Tesseract();
		
		setup(dataPath);
	}
	
	private void setup(String dataPath) {
		this.tesseract.setDatapath(dataPath);
	}
	
	public String getTextFromImage(String filePath) throws TesseractException {
		return this.tesseract.doOCR(new File(filePath));
	}
}
