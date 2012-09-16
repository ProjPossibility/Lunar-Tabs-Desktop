package speech;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

public class SphinxEngine extends AbstractSpeechRecEngine {
	
	//obj
	private Recognizer recognizer;
	private Microphone microphone;
	private static SphinxEngine instance;
	
	//debug
	private boolean DEBUG = true;
		
	//singleton
	public static SphinxEngine getInstance() {
		if(instance==null) {
			String configFile = "src/speech/helloworld.config.xml";
			instance = new SphinxEngine(configFile);
		}
		return instance;
	}
	
	public static void main(String[] args) {
		Thread t = new Thread(getInstance());
		t.start();
	}
	
	//ctr
	protected SphinxEngine(String configFile) {
		try {
			File f = new File(configFile);
	    	URL url = f.toURI().toURL();
	    	System.out.println(url.getPath());
	        ConfigurationManager cm = new ConfigurationManager(url);
	        recognizer = (Recognizer) cm.lookup("recognizer");
	        microphone = (Microphone) cm.lookup("microphone");
        } catch (IOException e) {
            System.err.println("Problem when loading HelloWorld: " + e);
            e.printStackTrace();
        } catch (PropertyException e) {
            System.err.println("Problem configuring HelloWorld: " + e);
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.err.println("Problem creating HelloWorld: " + e);
            e.printStackTrace();
        }
	}
	
	@Override
	public void run() {
		try {
	        recognizer.allocate();
	        setRecognitionOn(true);
	        
	        //main  recog loop
	        startRecording();
	        while(true) {
        		if(microphone.isRecording()) {	        		
        			Result result = recognizer.recognize();
        			if (result != null) {
        				String resultText = result.getBestFinalResultNoFiller().trim();
        				if(!resultText.equals("")) {
        					
        					//tell listeners that something has been recognized
        					for(SpeechListener l : listeners) {
        						l.speech_recog(resultText);
        					}
        					
        					//debug print
        					if(DEBUG) {
        						System.out.println("You said: " + resultText);
        					}
        					
        				}	
        			}
	            }
        	}
        }
		catch(IOException e) {
            System.err.println("Problem when loading HelloWorld: " + e);
            e.printStackTrace();			
		}
		
	}
	
	/**
	 * Start recording.
	 */
	@Override
	public void startRecording() {
		microphone.startRecording();
	}

	/**
	 * Start recording.
	 */
	@Override
	public void stopRecording() {
		microphone.stopRecording();
	}
	
	@Override
	public boolean isRecording() {
		return microphone.isRecording();
	}
	
}