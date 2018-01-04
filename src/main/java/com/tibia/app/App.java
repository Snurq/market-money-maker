package com.tibia.app;

import java.awt.AWTException;
import java.io.IOException;

import com.tibia.facade.Facade;

import net.sourceforge.tess4j.TesseractException;

public class App 
{
    public static void main(String[] args) throws AWTException, InterruptedException, IOException, TesseractException
    {
    	Facade facade = new Facade();
    	
    	facade.run();
    }
}
