package com.tibia.helper;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseHelper {
    private Robot robot;
	private CoodinatesHelper coodinatesHelper;
    
    public MouseHelper() throws AWTException {
    	this.robot = new Robot();
		this.coodinatesHelper = new CoodinatesHelper();
    }
    
	public void clickOn(int x, int y) throws AWTException {
		this.robot.mouseMove(x, y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnIncreaseItemQuantity() throws AWTException {
		this.robot.mouseMove(this.coodinatesHelper.ITEM_QUANTITY_X, this.coodinatesHelper.ITEM_QUANTITY_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnSearchBox() throws AWTException {
		this.robot.mouseMove(this.coodinatesHelper.SEARCH_BOX_X, this.coodinatesHelper.SEARCH_BOX_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnPiecePriceBox() throws AWTException {
		this.robot.mouseMove(this.coodinatesHelper.PIECE_PRICE_X, this.coodinatesHelper.PIECE_PRICE_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnFirstFound() {
		this.robot.mouseMove(this.coodinatesHelper.FIRST_FOUND_X, this.coodinatesHelper.FIRST_FOUND_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnAnonymous() {
		this.robot.mouseMove(this.coodinatesHelper.ANONYMOUS_CHECKBOX_X, this.coodinatesHelper.ANONYMOUS_CHECKBOX_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnBuyButton() {
		this.robot.mouseMove(this.coodinatesHelper.BUY_RADIO_X, this.coodinatesHelper.BUY_RADIO_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnCreateOffer() {
		this.robot.mouseMove(this.coodinatesHelper.CREATE_OFFER_X, this.coodinatesHelper.CREATE_OFFER_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
