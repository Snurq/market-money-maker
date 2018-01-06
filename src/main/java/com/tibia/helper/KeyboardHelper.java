package com.tibia.helper;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeyboardHelper {
    private Robot robot;
    
    public KeyboardHelper() throws AWTException {
    	this.robot = new Robot();
    }

    public void type(String word) {
    	for (int i = 0; i < word.length(); i++) {
    		this.robot.keyPress(this.getKey(word.charAt(i)));
    		this.robot.delay(50);
    		this.robot.keyRelease(this.getKey(word.charAt(i)));
    		this.robot.delay(50); 
    	}
    }
    
    public void selectAllTextAndDelete() {
    	this.robot.keyPress(KeyEvent.VK_CONTROL);
		this.robot.delay(200);
    	this.robot.keyPress(KeyEvent.VK_A);
		this.robot.delay(200);
		this.robot.keyRelease(KeyEvent.VK_CONTROL);
		this.robot.delay(200); 
		this.robot.keyRelease(KeyEvent.VK_A);
		this.robot.delay(200);
    	this.robot.keyPress(KeyEvent.VK_DELETE);
		this.robot.delay(200);
    }
    
    private int getKey(char c) {
    	if (c == 'A' || c == 'a')
    		return KeyEvent.VK_A;
    	else if (c == 'B' || c == 'b')
    		return KeyEvent.VK_B;
    	else if (c == 'C' || c == 'c')
    		return KeyEvent.VK_C;
    	else if (c == 'D' || c == 'd')
    		return KeyEvent.VK_D;
    	else if (c == 'E' || c == 'e')
    		return KeyEvent.VK_E;
    	else if (c == 'F' || c == 'f')
    		return KeyEvent.VK_F;
    	else if (c == 'G' || c == 'g')
    		return KeyEvent.VK_G;
    	else if (c == 'H' || c == 'h')
    		return KeyEvent.VK_H;
    	else if (c == 'I' || c == 'i')
    		return KeyEvent.VK_I;
    	else if (c == 'J' || c == 'j')
    		return KeyEvent.VK_J;
    	else if (c == 'K' || c == 'k')
    		return KeyEvent.VK_K;
    	else if (c == 'L' || c == 'l')
    		return KeyEvent.VK_L;
    	else if (c == 'M' || c == 'm')
    		return KeyEvent.VK_M;
    	else if (c == 'N' || c == 'n')
    		return KeyEvent.VK_N;
    	else if (c == 'O' || c == 'o')
    		return KeyEvent.VK_O;
    	else if (c == 'P' || c == 'p')
    		return KeyEvent.VK_P;
    	else if (c == 'Q' || c == 'q')
    		return KeyEvent.VK_Q;
    	else if (c == 'R' || c == 'r')
    		return KeyEvent.VK_R;
    	else if (c == 'S' || c == 's')
    		return KeyEvent.VK_S;
    	else if (c == 'T' || c == 't')
    		return KeyEvent.VK_T;
    	else if (c == 'U' || c == 'u')
    		return KeyEvent.VK_U;
    	else if (c == 'V' || c == 'v')
    		return KeyEvent.VK_V;
    	else if (c == 'W' || c == 'w')
    		return KeyEvent.VK_W;
    	else if (c == 'Y' || c == 'y')
    		return KeyEvent.VK_Y;
    	else if (c == 'X' || c == 'x')
    		return KeyEvent.VK_X;
    	else if (c == 'Z' || c == 'z')
    		return KeyEvent.VK_Z;
    	else if (c == '0')
    		return KeyEvent.VK_0;
    	else if (c == '1')
    		return KeyEvent.VK_1;
    	else if (c == '2')
    		return KeyEvent.VK_2;
    	else if (c == '3')
    		return KeyEvent.VK_3;
    	else if (c == '4')
    		return KeyEvent.VK_4;
    	else if (c == '5')
    		return KeyEvent.VK_5;
    	else if (c == '6')
    		return KeyEvent.VK_6;
    	else if (c == '7')
    		return KeyEvent.VK_7;
    	else if (c == '8')
    		return KeyEvent.VK_8;
    	else if (c == '9')
    		return KeyEvent.VK_9;
    	else if (c == ' ')
    		return KeyEvent.VK_SPACE;
    	else if (c == '-')
    		return KeyEvent.VK_MINUS;
    	else
    		return 0;
    }
}
