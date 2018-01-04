package com.tibia.helper;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRHelper {
	private final String TESSERACT_DATA_PATH = "C:/Tesseract";
	
	private Tesseract tesseract;
	
	public OCRHelper() {
		this.tesseract = new Tesseract();
		
		setup(TESSERACT_DATA_PATH);
	}
	
	private void setup(String dataPath) {
		this.tesseract.setDatapath(dataPath);
	}
	
	public String getTextFromImage(String filePath) throws TesseractException {
		return this.tesseract.doOCR(new File(filePath));
	}
}
