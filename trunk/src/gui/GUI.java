package gui;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.accessibility.AccessibleComponent;
import javax.swing.*;
import org.herac.tuxguitar.song.models.TGSong;
import org.herac.tuxguitar.song.models.TGTrack;
import InstructionGenerator.SongLoader;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener {
	
	public static void main(String[] args) {
		new GUI();
	}
	
	//gui elements
	protected JButton fileButton;
	protected JTextField fileField;
	protected JLabel trackNumLabel;
	protected JTextField trackNumField;
	protected JLabel mStartLabel;
	protected JTextField mStartField;
	protected JLabel mEndLabel;
	protected JTextField mEndField;
	protected JButton genInstButton;
//	protected JTextArea instArea;
	
	//state
	protected PresentationModel data;
	
	//ctr
	public GUI() {
		
		//init
		fileButton = new JButton("Load Tab File");
		fileField = new JTextField(10);
		fileField.setEnabled(false);
		trackNumLabel = new JLabel("Enter Track Number: ");
		trackNumField = new JTextField(10);
		mStartLabel = new JLabel("Enter Measure Start: ");
		mStartField = new JTextField(10);
		mEndLabel = new JLabel("Enter Measure End: ");
		mEndField = new JTextField(10);
//		instArea = new JTextArea(40,40);
		genInstButton = new JButton("Generate Instructions");
		
		//screen-reader labels
		setAccessibleLabel(fileButton, "Click to load tab file.");
		setAccessibleLabel(trackNumField, "Enter Track Number to extract.");
		setAccessibleLabel(mStartField,"Enter Measure Range (Start) to generate instructions for.");
		setAccessibleLabel(mEndField,"Enter Measure Range (End) to generate instructions for.");
		setAccessibleLabel(genInstButton,"Click to export accessible instructions.");
		
		//place on panel
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(5,2));
		p1.add(fileButton);
		p1.add(fileField);
		p1.add(trackNumLabel);
		p1.add(trackNumField);
		p1.add(mStartLabel);
		p1.add(mStartField);
		p1.add(mEndLabel);
		p1.add(mEndField);
		p1.add(genInstButton);
		
		//place in JScrollPane
//		JScrollPane instPane = new JScrollPane(instArea);
		
		//overall layout
		add(p1);
//		add(instPane);
		
		//action listeners
		fileButton.addActionListener(this);
		genInstButton.addActionListener(this);
		
		//model inits
		data = new PresentationModel();
		
		//init
		setTitle("Lunar Tabs Alpha");
		setSize(400,130);
		setVisible(true);
		setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void setAccessibleLabel(JComponent c, String label) {
		c.getAccessibleContext().setAccessibleName(label);
		c.getAccessibleContext().setAccessibleDescription(label);
		c.setToolTipText(label);
	}
	
    //returns errors or ""
	public String parseGUI() {
		StringBuffer rtn = new StringBuffer();
		TGSong song  = null;
		int trackNum= -1;
		int mStart = -1;
		int mEnd = -1;
		boolean songOkay = false;
		boolean trackNumOkay = false;
		boolean mStartOkay = false;
		boolean mEndOkay = false;
		
		try {
			song = SongLoader.loadSong(fileField.getText());
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
			trackNum = Integer.parseInt(trackNumField.getText());
			if(songOkay && (trackNum < 1 || trackNum > song.countTracks())) {
				rtn.append("Invalid Track Number. \n");
			}
			else {
				trackNumOkay = true;
			}
		}
		catch(Exception e) {
			rtn.append("Invalid Track Number. \n");
		}
		try {
			mStart = Integer.parseInt(mStartField.getText());
			if(song!=null && trackNumOkay) {
				TGTrack track = song.getTrack(trackNum-1);
				if(mStart < 1 || mStart >  track.countMeasures()) {
					rtn.append("Invalid Start Measure. \n");
				}
				else {
					mStartOkay = true;
				}
				
			}
		}
		catch(Exception e) {
			rtn.append("Invalid Start Measure. \n");			
		}
		try {
			mEnd = Integer.parseInt(mEndField.getText());
			if(song!=null && trackNumOkay && mStartOkay) {
				TGTrack track = song.getTrack(trackNum-1);
				if(mEnd < mStart || mEnd >  track.countMeasures()) {
					rtn.append("Invalid End Measure. \n");
				}
				else {
					mEndOkay = true;
				}				
			}
		}
		catch(Exception e) {
			rtn.append("Invalid End Measure. \n");			
		}
		
		//if all okay
		if(songOkay && trackNumOkay && mStartOkay && mEndOkay) {
			data.setSong(song);
			data.setTrackNum(trackNum);
			data.setmRangeStart(mStart);
			data.setmRangeEnd(mEnd);
			data.genInstructions();			
			return "";
		}
		else {
			return rtn.toString();
		}
	}

	public void saveFile(String file, String content) throws IOException {
		PrintWriter w = new PrintWriter(new FileWriter(file));
		w.print(content);
		w.flush();
		w.close();
	}
		
	public void actionPerformed(ActionEvent e) {
		
		//load tab file
		if(e.getSource()==fileButton) {
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("Guitar Pro and Power Tab Files", "gp1", "gp2", "gp3", "gp4", "gp5", "ptb");
	        chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	String choiceFile = chooser.getSelectedFile().getPath();
		    	fileField.setText(choiceFile);
				setAccessibleLabel(fileButton,"Click to load tab file. Tab file currently loaded: " + chooser.getSelectedFile().getName());
		    }		 
		}
		
		//save text instructions
		if(e.getSource()==genInstButton) {
			String errors = parseGUI();
			if(errors.length()==0) {
				
				//get save file
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		        chooser.setFileFilter(filter);
			    int returnVal = chooser.showSaveDialog(this);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	String choiceFile = chooser.getSelectedFile().getPath();
			    	if(!choiceFile.endsWith(".txt")) {
			    		choiceFile = choiceFile + ".txt";
			    	}
			    	try {
			    		saveFile(choiceFile,data.getInstructions());
			    		JOptionPane.showMessageDialog(this, "Accessible instructions exported to " + choiceFile + ".", "Instructions exported successfully.", JOptionPane.PLAIN_MESSAGE);
			    	}
			    	catch(IOException ex) {
						JOptionPane.showMessageDialog(this, "File could not be written.", "Failed to export tab", JOptionPane.ERROR_MESSAGE);
			    	}
			    }
//				instArea.setText(data.getInstructions());
			}
			else {
				JOptionPane.showMessageDialog(this, errors, "Failed to export tab", JOptionPane.ERROR_MESSAGE);
//				instArea.setText(errors);
			}
		}
	}	
}
