package gui;
import java.util.*;

import org.herac.tuxguitar.song.models.TGBeat;
import org.herac.tuxguitar.song.models.TGMeasure;
import org.herac.tuxguitar.song.models.TGSong;
import org.herac.tuxguitar.song.models.TGTrack;

import InstructionGenerator.InstructionGenerator;
import InstructionGenerator.SongLoader;

public class PresentationModel {
	
	public static void main(String[] args) {
		TGSong song  = SongLoader.loadSong("/Users/prateek/Desktop/guitar/bat.gp5");
		PresentationModel p = new PresentationModel();
		p.setSong(song);
		p.setTrackNum(1);
		p.setmRangeStart(1);
		p.setmRangeEnd(3);
		System.out.println(p.genInstructions());
	}

	//GUI params
	protected TGSong song;
	protected int trackNum;
	protected int mRangeStart;
	protected int mRangeEnd;
	protected String instructions;
	
	//state
	protected InstructionGenerator iGenerator;

	//ctr
	public PresentationModel() {
		song = null;
		trackNum = -1;
		mRangeStart = -1;
		mRangeEnd = -1;
		instructions = "";
		iGenerator = new InstructionGenerator();
	}
	
	/**
	 * Dynamically generates instructions
	 */
	public String genInstructions() {
		
		//see if can return
		if(song==null || trackNum==-1 || mRangeStart==-1 || mRangeEnd==-1) {
			return "";
		}
		
		//rtn
		StringBuffer rtn = new StringBuffer();
		
		//load beats
		TGTrack track  = SongLoader.getTrack(song,trackNum-1);
		List<TGMeasure> measures = SongLoader.getMeasures(track);
		List<TGBeat> beats = SongLoader.getBeatsforSelection(measures, mRangeStart-1, mRangeEnd-1);
		
		//generate instructions for beats
		for(TGBeat beat : beats) {
			rtn.append(iGenerator.getPlayInstruction(beat) + "\n");
		}
		
		//return
		instructions = rtn.toString();
		return rtn.toString();
	}
		
	/**
	 * @return the song
	 */
	public TGSong getSong() {
		return song;
	}
	/**
	 * @param song the song to set
	 */
	public void setSong(TGSong song) {
		this.song = song;
	}
	/**
	 * @return the mRangeStart
	 */
	public int getmRangeStart() {
		return mRangeStart;
	}
	/**
	 * @param mRangeStart the mRangeStart to set
	 */
	public void setmRangeStart(int mRangeStart) {
		this.mRangeStart = mRangeStart;
	}
	/**
	 * @return the mRangeEnd
	 */
	public int getmRangeEnd() {
		return mRangeEnd;
	}
	/**
	 * @param mRangeEnd the mRangeEnd to set
	 */
	public void setmRangeEnd(int mRangeEnd) {
		this.mRangeEnd = mRangeEnd;
	}	
	/**
	 * @return the instructions
	 */
	public String getInstructions() {
		return instructions;
	}


	/**
	 * @param instructions the instructions to set
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}


	/**
	 * @return the trackNum
	 */
	public int getTrackNum() {
		return trackNum;
	}

	/**
	 * @param trackNum the trackNum to set
	 */
	public void setTrackNum(int trackNum) {
		this.trackNum = trackNum;
	}

}
