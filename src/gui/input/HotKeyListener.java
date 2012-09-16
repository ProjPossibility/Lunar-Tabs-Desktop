package gui.input;

import gui.GUI;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

import speech.PrateekAPI;
import tg_import.TuxGuitarUtil;

/**
 * Callback for instrution area hotkeys
 * @author prateek
 *
 */
public class HotKeyListener implements KeyListener {
	
	//obj
	private JTextArea instArea;
	private GUI gui;
	
	public HotKeyListener(JTextArea instArea, GUI gui) {
		this.instArea = instArea;
		this.gui = gui;
	}

	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar()=='w') {
			gui.speakInstruction();
		}
		else if(e.getKeyChar()=='d') {
			gui.incMeasure();
		}
		else if(e.getKeyChar()=='a') {
			gui.decMeasure();			
		}
		else if(e.getKeyChar()=='v') {
			gui.toggleVerbose();
			
			//refresh cursor
			try {
			    Robot robot = new Robot();
			    robot.keyPress(KeyEvent.VK_UP);
			    robot.keyRelease(KeyEvent.VK_UP);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
		else if(e.getKeyChar()=='p') {
			gui.playSample();
 		}
	}

	//unused methods
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}	
}