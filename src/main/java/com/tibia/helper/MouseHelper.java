package com.tibia.helper;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseHelper {
    private Robot robot;
	private ConstantsHelper constantsHelper;
    
    public MouseHelper() throws AWTException {
    	this.robot = new Robot();
		this.constantsHelper = new ConstantsHelper();
    }
    
	public void clickOn(int x, int y) throws AWTException {
		this.robot.mouseMove(x, y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnIncreaseItemQuantity() throws AWTException {
		this.robot.mouseMove(this.constantsHelper.ITEM_QUANTITY_X, this.constantsHelper.ITEM_QUANTITY_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnCloseMarket() throws AWTException {
		this.robot.mouseMove(this.constantsHelper.CLOSE_MARKET_X, this.constantsHelper.CLOSE_MARKET_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnSearchBox() throws AWTException {
		this.robot.mouseMove(this.constantsHelper.SEARCH_BOX_X, this.constantsHelper.SEARCH_BOX_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnPiecePriceBox() throws AWTException {
		this.robot.mouseMove(this.constantsHelper.PIECE_PRICE_X, this.constantsHelper.PIECE_PRICE_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnFirstFound() {
		this.robot.mouseMove(this.constantsHelper.FIRST_FOUND_X, this.constantsHelper.FIRST_FOUND_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnAnonymous() {
		this.robot.mouseMove(this.constantsHelper.ANONYMOUS_CHECKBOX_X, this.constantsHelper.ANONYMOUS_CHECKBOX_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnBuyButton() {
		this.robot.mouseMove(this.constantsHelper.BUY_RADIO_X, this.constantsHelper.BUY_RADIO_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void clickOnCreateOffer() {
		this.robot.mouseMove(this.constantsHelper.CREATE_OFFER_X, this.constantsHelper.CREATE_OFFER_Y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
