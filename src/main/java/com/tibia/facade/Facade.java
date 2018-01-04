package com.tibia.facade;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibia.helper.CoodinatesHelper;
import com.tibia.helper.ImageHelper;
import com.tibia.helper.KeyboardHelper;
import com.tibia.helper.MouseHelper;
import com.tibia.helper.OCRHelper;
import com.tibia.helper.XMLHelper;
import com.tibia.model.Item;

import net.sourceforge.tess4j.TesseractException;

public class Facade {
	private ImageHelper imageHelper;
	private MouseHelper mouseHelper;
	private KeyboardHelper keyboardHelper;
	private XMLHelper xmlHelper;
	private CoodinatesHelper coodinatesHelper;
	
	private List<Item> items;
	
	public Facade() throws AWTException {
		this.imageHelper = new ImageHelper();
		this.mouseHelper = new MouseHelper();
		this.keyboardHelper = new KeyboardHelper();
		this.xmlHelper = new XMLHelper();
		this.coodinatesHelper = new CoodinatesHelper();
	}
	
	public void run() throws InterruptedException, AWTException, IOException, TesseractException {
		this.items = xmlHelper.getItemsList();
		
		startShopping("rotten p");
	}	
	
	private void startShopping(String itemName) throws InterruptedException, AWTException, IOException, TesseractException {
    	delay(2000);
    	
    	this.mouseHelper.clickOnSearchBox();
    	
    	delay(1000);
    	
    	this.keyboardHelper.selectAllTextAndDelete();

    	delay(1000);
    	
    	keyboardHelper.type(itemName);

    	delay(500);
    	
    	this.mouseHelper.clickOnFirstFound();

    	delay(1000);
    	
    	this.mouseHelper.clickOnBuyButton();

    	delay(500);
    	
    	this.mouseHelper.clickOnAnonymous();

    	delay(2000);
    	
    	String test = this.imageHelper.getTextFromImage(
    			this.coodinatesHelper.FIRST_SELLER_END_AT_X_TOP,
    			this.coodinatesHelper.FIRST_SELLER_END_AT_Y_TOP,
    			this.coodinatesHelper.FIRST_SELLER_END_AT_X_BOTTOM,
    			this.coodinatesHelper.FIRST_SELLER_END_AT_Y_BOTTOM);
    	
    	System.out.println(test);
	}
	
	private void delay(int milliseconds) throws InterruptedException {
		Thread.sleep(milliseconds);
	}
}
