package gui;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gui.dep.AccessibilityAPI;
import gui.dep.LinePainter;
import gui.input.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import speech.PrateekAPI;
import tg_import.TuxGuitarUtil;
import tg_import.song.models.*;
import speech.*;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	
	//user params
	private boolean USE_SPEECH = false;
	
	//main method for program
	public static void main(String[] args) {
		new GUI();
	}
	
	//gui elements
	protected JButton fileButton;
	protected JTextField fileField;
	protected JLabel trackNumLabel;
	protected JComboBox trackBox;
	protected JButton genInstButton;
	protected JTextArea instArea;	

	//speech engine
	protected AbstractSpeechRecEngine speechEngine;

	//Listeners
	protected FormListener formListener;
	protected HotKeyListener keyListener;
	protected SpeechListener speechListener;
	
	//state
	protected DataModel data;
		
	//ctr
	public GUI() {
		
		//init
		fileButton = new JButton("Load Tab File");
		fileField = new JTextField(10);
		fileField.setEnabled(false);
		trackNumLabel = new JLabel("Choose Track: ");
		trackBox = new JComboBox();
		instArea = new JTextArea(5,30);
		genInstButton = new JButton("Generate Instructions");
		new LinePainter(instArea);
		instArea.setEditable(false);
		instArea.addKeyListener(new HotKeyListener(instArea,this));
				
		//screen-reader labels
		AccessibilityAPI.setAccessibleLabel(fileButton, "Click to load tab file.");
		AccessibilityAPI.setAccessibleLabel(trackBox, "Choose track");
		AccessibilityAPI.setAccessibleLabel(genInstButton,"Click to export accessible instructions.");
		MenuListener m = new MenuListener(this);
		trackBox.addItemListener(m);
		
		//place on panel
		JPanel p1 = new JPanel();		
		p1.setLayout(new GridLayout(3,2));
		p1.add(fileButton);
		p1.add(fileField);
		p1.add(trackNumLabel);
		p1.add(trackBox);
		p1.add(genInstButton);
		
		//instruction area
		JPanel p2 = new JPanel();
		p2.add(new JScrollPane(instArea));
		
		//overall layout
		this.setLayout(new GridLayout(2,1));
		add(p1);
		add(p2);
		
		//action listeners
		formListener = new FormListener(this);
		fileButton.addActionListener(formListener);
		genInstButton.addActionListener(formListener);
		speechListener = new SpeechListenerImpl(this);
		
		//start speech (if needed)
		if(USE_SPEECH) {
			speechEngine = SphinxEngine.getInstance();
			Thread t =new Thread(speechEngine);
			t.start();
			speechEngine.addSpeechListener(speechListener);
		}

		//model inits
		data = new DataModel();
		
		//init
		setTitle("Lunar Tabs (Alpha)");
		setSize(380,225);
		setVisible(true);
		setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
		
    
	
	/**
	 * Load tab file operation
	 */
	public void loadTabFile() {
		String choiceFile = AccessibilityAPI.showAccessibleFileDialog(this);
		if(choiceFile!=null) {
	    	//populate file field and combo box
	    	String errors = populateComboBox(choiceFile);
	    	if(errors.length() > 0) {
	    		fileField.setText("");
	    		data.setFilePath(null);
	    		AccessibilityAPI.showAccessibleErrorDialog("Failed to load tab", errors);
	    	}
	    	else {
		    	fileField.setText(choiceFile);
		    	data.setFilePath(choiceFile);
	    	}
		} else {
			//clear instruction area			
			instArea.setText(""); //clear
			data.setMeasureInst(null);
			data.setSfInst(null);
	    }	 
	}
	
	/**
	 * Helper for loading file
	 * @param file file to be loaded
	 */
	private String populateComboBox(String file) {
		StringBuffer rtn = new StringBuffer();
		try {
			TGSong song = TuxGuitarUtil.loadSong(file);
			DefaultComboBoxModel aModel = new DefaultComboBoxModel();
			Map<String,Integer> uniq_id = new HashMap<String,Integer>();
			for(int x=0; x < song.countTracks(); x++) {
				String trackName = song.getTrack(x).getName().trim();
				if(uniq_id.containsKey(trackName)) {
					int id = uniq_id.get(trackName) + 1;
					String newTrackName = trackName + " ("+ id + ")";
					aModel.addElement(newTrackName);
					uniq_id.put(trackName,id);
				}
				else {
					aModel.addElement(trackName);
					uniq_id.put(trackName, 1);
				}
			}
			trackBox.setModel(aModel);
			AccessibilityAPI.setAccessibleLabel(trackBox, "Choose track: " + trackBox.getSelectedItem() + " selected.");			
	    	AccessibilityAPI.setAccessibleLabel(fileButton,"Click to load tab file. Tab file currently loaded: " + file);
		}
		catch(Exception e) {
			trackBox.setModel(new DefaultComboBoxModel());
			rtn.append("Tab could not be loaded.");
		}
    	return rtn.toString();
	}
	
	/**
	 * Generate Instructions operations
	 */
	public void generateInstructions() {
		String errors = parseGUI();
		if(errors.length()==0) {								
			//populate current measure
			setMeasure(0,true);
	    	AccessibilityAPI.setAccessibleLabel(instArea,"Instructions for Measure " + data.getCurrentMeas() + ".");			
		}
		else {
			JOptionPane.showMessageDialog(this, errors, "Failed to export tab", JOptionPane.ERROR_MESSAGE);
		}
	}
	
    /**
     * Helper for parsing gui for generating instructions.
     * @return
     */
	private String parseGUI() {
		StringBuffer rtn = new StringBuffer();
		TGSong song  = null;
		int trackNum= -1;
		boolean songOkay = false;
		boolean trackNumOkay = false;
		
		try {
			song = TuxGuitarUtil.loadSong(fileField.getText());
			if(song==null) {
				rtn.append("Tab could not be loaded.\n");				
			}
			else {
				songOkay = true;
			}
		}
		catch(Exception e){
			rtn.append("Tab could not be loaded.\n");
		}
		try {
			trackNum = trackBox.getSelectedIndex()+1;
			trackNumOkay = true;
		}
		catch(Exception e) {
			rtn.append("Invalid Track Selection. \n");
		}
		
		//if all okay
		if(songOkay && trackNumOkay) {
			data.setSong(song);
			data.setTrackNum(trackNum);
			data.genInstructions();			
			return "";
		}
		else {
			return rtn.toString();
		}
	}		
	
	/**
	 * Set text area view to a new measure
	 * @param c_new The measure to change to 
	 * @param reinitCaret Whether to reinit caret position or not
	 */
	public void setMeasure(int c_new, boolean reinitCaret) {
		if(data.isVerbose()) {
			if(data.getSfInst()!=null && c_new >= 0 && c_new < data.getSfInst().size()) {
				
				//create new text
				data.setCurrentMeas(c_new);
				List<String> cMeasList = data.getSfInst().get(c_new);
				String toShow="";
				for(String str : cMeasList) {
					toShow = toShow + str + "\n";
				}
				toShow = toShow.substring(0,toShow.length()-1); //take off last new line
				
				//display
				instArea.setText(toShow);
				if(reinitCaret) {
					instArea.setCaretPosition(0);							
				}				
			}		
		}
		else {
			if(data.getMeasureInst()!=null && c_new >= 0 && c_new < data.getMeasureInst().size()) {
				
				//create new text
				data.setCurrentMeas(c_new);
				List<String> cMeasList = data.getMeasureInst().get(c_new);
				String toShow="";
				for(String str : cMeasList) {
					toShow = toShow + str + "\n";
				}
				
				//show if have text
				if(toShow.length()>1) {
					toShow = toShow.substring(0,toShow.length()-1); //take off last new line
				
					//display
					instArea.setText(toShow);
					if(reinitCaret) {
						instArea.setCaretPosition(0);							
					}				
				}
			}
		}
	}	
	
	/**
	 * Increment measure view by 1.
	 */
	public void incMeasure() {
		setMeasure(data.getCurrentMeas()+1,true);
    	AccessibilityAPI.setAccessibleLabel(instArea,"Instructions for Measure " + data.getCurrentMeas() + ".");					
	}
	
	/**
	 * Decrement measure view by 1.
	 */
	public void decMeasure() {
		setMeasure(data.getCurrentMeas()-1,true);
    	AccessibilityAPI.setAccessibleLabel(instArea,"Instructions for Measure " + data.getCurrentMeas() + ".");							
	}
	
	/**
	 * Pipe current highlighted instruction to text to speech.
	 */
	public void speakInstruction() {
		
		String text = instArea.getText();
		if(!text.equals("")) {
			
			//extract instruction step and speak
			int caret = instArea.getCaretPosition();			
			int nextNewLine = text.indexOf("\n", caret);
			String instruction="";
			if(nextNewLine > -1) {
				instruction = text.substring(caret,nextNewLine);
			}
			else {
				instruction = text.substring(caret);
			}
			PrateekAPI.say(instruction);			
		}
	}
	
	/**
	 * Toggle verbose setting on instruction area.
	 */
	public void toggleVerbose() {
		
		//extract state
		String file = data.getFilePath();
		int trackInd = data.getTrackNum()-1;
		
		//check if can
		if(file!=null && trackInd > -1 && data.getSfInst()!=null && data.getMeasureInst()!=null) {
			
			//flip verbose flag and redisplay gui
			data.setVerbose(!data.isVerbose());
			setMeasure(data.getCurrentMeas(), true);
		}
	}
	
	/**
	 * Play MIDI synthesized sample of current highlighted 
	 * measure.
	 */
	public void playSample() {
		
		//extract state
		String file = data.getFilePath();
		int trackInd = data.getTrackNum()-1;
		
		//check if can
		if(file!=null && trackInd > -1) {
			
			//play clip
			TuxGuitarUtil.playClip(file, data.getCurrentMeas(), data.getCurrentMeas(),trackInd);
		}
	}
	
	/**
	 * @return the instArea
	 */
	public JTextArea getInstArea() {
		return instArea;
	}

	/**
	 * @param instArea the instArea to set
	 */
	public void setInstArea(JTextArea instArea) {
		this.instArea = instArea;
	}

	/**
	 * @return the fileButton
	 */
	public JButton getFileButton() {
		return fileButton;
	}

	/**
	 * @return the fileField
	 */
	public JTextField getFileField() {
		return fileField;
	}

	/**
	 * @return the trackBox
	 */
	public JComboBox getTrackBox() {
		return trackBox;
	}

	/**
	 * @return the genInstButton
	 */
	public JButton getGenInstButton() {
		return genInstButton;
	}

	/**
	 * @return the data
	 */
	public DataModel getData() {
		return data;
	}	
}