package gui.input;

import speech.SpeechListener;
import speech.SphinxEngine;
import gui.*;

public class SpeechListenerImpl implements SpeechListener {
		
	//parent
	private GUI gui;
	
	/**
	 * Constructor
	 * @param gui The gui
	 */
	public SpeechListenerImpl(GUI gui) {
		this.gui = gui;
	}
	
	/**
	 * Speech call back
	 */
	public void speech_recog(String recognized) {
		if(recognized.equals("load tab"))
		{
			gui.loadTabFile();
		}
		else if(recognized.equals("up"))
		{
			gui.incMeasure();
		}
		else if(recognized.equals("down"))
		{
			gui.decMeasure();
		}
		else if(recognized.equals("play"))
		{
//			SphinxEngine.getInstance().stopRecording();
			gui.playSample();
//			SphinxEngine.getInstance().startRecording();
		}
	}
}
