package gui.input;

import gui.GUI;
import gui.dep.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class FormListener implements ActionListener {
	
	/**
	 * parent gui
	 */
	private GUI gui;
	
	/**
	 * Constructor
	 * @param gui parent
	 */
	public FormListener(GUI gui) {
		this.gui = gui;
	}
	
	/**
	 * Action callback
	 */
	public void actionPerformed(ActionEvent e) {
		
		//load tab file
		if(e.getSource()==gui.getFileButton()) {
			gui.loadTabFile();
		}
		
		//save text instructions
		if(e.getSource()==gui.getGenInstButton()) {
			gui.generateInstructions();
		}
	}	
}