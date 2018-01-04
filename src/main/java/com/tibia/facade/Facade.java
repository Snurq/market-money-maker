package com.tibia.facade;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import com.tibia.helper.CoodinatesHelper;
import com.tibia.helper.ImageHelper;
import com.tibia.helper.KeyboardHelper;
import com.tibia.helper.MouseHelper;
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
		delay(3000);
		
		this.items = xmlHelper.getItemsList();
		
		delay(500);
		
		this.mouseHelper.clickOnAnonymous();
    	
    	delay(500);
		
		for (int i = 0; i < this.items.size(); i++) {
			startShopping(this.items.get(i));
			
			delay(3000);
		}
	}	
	
	private void startShopping(Item item) throws InterruptedException, AWTException, IOException, TesseractException {
    	this.mouseHelper.clickOnSearchBox();
    	
    	delay(1000);
    	
    	this.keyboardHelper.selectAllTextAndDelete();

    	delay(1000);
    	
    	keyboardHelper.type(item.getName());

    	delay(500);
    	
    	this.mouseHelper.clickOnFirstFound();

    	delay(1000);
    	
    	this.mouseHelper.clickOnBuyButton();

    	delay(500);
    	
    	String id = this.imageHelper.getTextFromImage(
    			this.coodinatesHelper.FIRST_SELLER_END_AT_X_TOP,
    			this.coodinatesHelper.FIRST_SELLER_END_AT_Y_TOP,
    			this.coodinatesHelper.FIRST_SELLER_END_AT_X_BOTTOM,
    			this.coodinatesHelper.FIRST_SELLER_END_AT_Y_BOTTOM).trim();
    	
    	if (id.equals(item.getId())) {
    		System.out.println("Não comprou.");
    	} else {
    		if (id.equals("")) {
    			int firstOffer = Math.round((item.getPrice() / 2));
    			
    			this.mouseHelper.clickOnPiecePriceBox();
    	    	
    	    	delay(1000);
    	    	
    	    	this.keyboardHelper.selectAllTextAndDelete();
    	    	
    			delay(1000);
    	    	
    			this.keyboardHelper.type(String.valueOf(firstOffer));
    			
    			delay(500);
    			
    			for (int i = 0; i < Math.round(this.coodinatesHelper.GOLD_PER_ITEM / firstOffer); i++) {
    				this.mouseHelper.clickOnIncreaseItemQuantity();
    				delay(50);
    			}
    			
    			/**
    			 * CLICK ON CREATE
    			 */
    			
    			delay(1000);
    			
    			this.xmlHelper.updateItemId(id, item.getName());
    			
    			System.out.println("Primeiro ao comprar por: " + firstOffer);
    		} else {
        		int price = Integer.parseInt(this.imageHelper.getTextFromImage(
            			this.coodinatesHelper.PIECE_PRICE_X_TOP,
            			this.coodinatesHelper.PIECE_PRICE_Y_TOP,
            			this.coodinatesHelper.PIECE_PRICE_X_BOTTOM,
            			this.coodinatesHelper.PIECE_PRICE_Y_BOTTOM).trim());
        		
        		price = (price + 1);
        		
        		if (price < item.getPrice()) {
        			this.mouseHelper.clickOnPiecePriceBox();
        	    	
        	    	delay(1000);
        	    	
        	    	this.keyboardHelper.selectAllTextAndDelete();
        	    	
        			delay(1000);
        	    	
        			keyboardHelper.type(String.valueOf(price));
        			
        			delay(500);
        			
        			for (int i = 0; i < Math.round(this.coodinatesHelper.GOLD_PER_ITEM / price); i++) {
        				this.mouseHelper.clickOnIncreaseItemQuantity();
        				delay(50);
        			}
        			
        			/**
        			 * CLICK ON CREATE
        			 */
        			
        			delay(1000);
        			
        			this.xmlHelper.updateItemId(id, item.getName());
        			
        			System.out.println("Comprando por: " + price);
        		}
    		}
    		
    		System.out.println("Comprou.");
    	}
	}
	
	private void delay(int milliseconds) throws InterruptedException {
		Thread.sleep(milliseconds);
	}
}
