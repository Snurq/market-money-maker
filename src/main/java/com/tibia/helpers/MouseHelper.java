package com.tibia.helpers;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseHelper {
    Robot robot;
    
    public MouseHelper() throws AWTException {
    	this.robot = new Robot();
    }
    
	public void clickOn(int x, int y) throws AWTException {
		this.robot.mouseMove(x, y);    
	    this.robot.mousePress(InputEvent.BUTTON1_MASK);
	    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
