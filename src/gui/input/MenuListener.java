package gui.input;

import gui.GUI;
import gui.dep.AccessibilityAPI;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

public class MenuListener implements ItemListener {
	
	/**
	 * GUI
	 */
	private GUI gui;
	
	public MenuListener(GUI gui) {
		this.gui = gui;
	}
	
	public void itemStateChanged(ItemEvent arg0) {
		//set accessible label to new selection so screenreader recognizes.
		AccessibilityAPI.setAccessibleLabel(gui.getTrackBox(), "Choose track: " + gui.getTrackBox().getSelectedItem() + " selected.");
	}

	/**
	 * @return the gui
	 */
	public GUI getGui() {
		return gui;
	}

	/**
	 * @param gui the gui to set
	 */
	public void setGui(GUI gui) {
		this.gui = gui;
	}
}
