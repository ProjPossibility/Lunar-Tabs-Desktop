package speech;
import java.util.*;

/*
 * Abstract speech recognition engine. An engine has a queue to which it puts
 * recognized strings on and a procedure for handing those strings.
 */
public abstract class AbstractSpeechRecEngine implements Runnable {

	/*
	 * Flag for whether speech recognition capability is on or not.
	 */
	protected boolean recOn;
	
	/*
	 * Call backs
	 */
	protected List<SpeechListener> listeners = new ArrayList<SpeechListener>();
	
	/**
	 * @return the voiceOn
	 */
	public boolean isOn() {
		return recOn;
	}
	
	/*
	 * A recognition engine needs to run.
	 */
	public abstract void run();

	/**
	 * @param voiceOn the voiceOn to set
	 */
	public void setRecognitionOn(boolean recOn) {
		this.recOn = recOn;
	}
	
	/**
	 * Add callback	
	 * @param listener A callback function
	 */
	public void addSpeechListener(SpeechListener listener) {
		if(listener!=null) {
			listeners.add(listener);
		}
	}
	
	//dummys
	public abstract void startRecording();
	public abstract void stopRecording();
	public abstract boolean isRecording();
}
