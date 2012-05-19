package InstructionGenerator;
import org.herac.tuxguitar.song.models.TGBeat;
import org.herac.tuxguitar.song.models.TGNote;
import org.herac.tuxguitar.song.models.TGVoice;
import org.herac.tuxguitar.song.models.TGDuration;
import java.util.*;

public class InstructionGenerator {
	
	//constants
	public String[] langMod = {"First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "Eighth", "Ninth", "Tenth","Eleventh", "Twelvth", "Thirteenth", "Fourteenth", "Fiveteenth", "Sixteenth", "Seventeenth", "Eighteenth", "Nineteenth", "Twentieth", "Twenty-first", "Twenty-second", "Twenty-third", "Twenty-fourth", "Twenty-fifth", "Twenty-sixth", "Twenty-seventh", "Twenty-eight", "Twenty-ninth", "Thirtieth"};
		
	//state
	protected GuitarModel guitarModel;
	
	//ctr
	public InstructionGenerator() {
	}
	
	/*
	 * Get play instruction
	 * ***CALLS ANKIT's FUNCTION***
	 */
	public String getPlayInstruction(TGBeat beat) {
		if(beat.isRestBeat()) {
			return "Rest Beat.";
		}
		else {
			List<TGNote> notes = SongLoader.getNotesForBeat(beat);		
			if(notes.size() > 1) {
				return "Play " + ChordRecognizer.getChordName(notes) + ".";
			}
			else {
				TGNote singleNote = notes.get(0);
				int string = singleNote.getString();
				int fret = singleNote.getValue();
				return "Play " + guitarModel.getNoteName(string, fret)[0].replaceAll("#", " SHARP") + ".";
			}
		}
	}
	
	/*
	 * Generates instructions of the form (string, fret).
	 */
	public List<String> getStringFretInstruction(TGBeat beat) {
		
		//rtn
		List<String> rtn = new ArrayList<String>();		

		//loop
		List<TGNote> notes = SongLoader.getNotesForBeat(beat);
		for(TGNote note : notes) {
			
			//get string, fret
			int string = note.getString();
			int fret = note.getValue();
				
			//create instruction
			String instruction = langMod[string] + " string, " + langMod[fret] + "fret.";
			rtn.add(instruction);
		}
		
		//rtn
		return rtn;		
	}
		
	/*
	 * Returns an instruction about the duration of the beat.
	 */
	public String getDurationInstruction(TGBeat beat) {

		//get duration of beat
		TGVoice voice = beat.getVoice(0);
		TGDuration duration = voice.getDuration();
				
		if(beat.isRestBeat())
		{
			if(duration.getValue() == TGDuration.EIGHTH)
			{
				return "Rest eighth note.";
			}
			if(duration.getValue() == TGDuration.HALF)
			{
				return "Rest half note.";
			}
			if(duration.getValue() == TGDuration.QUARTER)
			{
				return "Rest quarter note.";
			}
			if(duration.getValue() == TGDuration.SIXTEENTH)
			{
				return "Rest sixteenth note.";
			}
			if(duration.getValue() == TGDuration.SIXTY_FOURTH)
			{
				return "Rest sixty-fourth note.";
			}
			if(duration.getValue() == TGDuration.THIRTY_SECOND)
			{
				return "Rest thirty-second note.";
			}
			if(duration.getValue() == TGDuration.WHOLE)
			{
				return "Rest whole note.";
			}

		}
		else
		{
			if(voice.getNotes().size() > 1)
			{
				if(duration.getValue() == TGDuration.EIGHTH)
				{
					return "This chord is an eighth note.";
				}
				if(duration.getValue() == TGDuration.HALF)
				{
					return "This chord is a half note.";
				}
				if(duration.getValue() == TGDuration.QUARTER)
				{
					return "This chord is a quarter note.";
				}
				if(duration.getValue() == TGDuration.SIXTEENTH)
				{
					return "This chord is a sixteenth note.";
				}
				if(duration.getValue() == TGDuration.SIXTY_FOURTH)
				{
					return "This chord is a sixty-fourth note.";
				}
				if(duration.getValue() == TGDuration.THIRTY_SECOND)
				{
					return "This chord is a thirty-second note.";
				}
				if(duration.getValue() == TGDuration.WHOLE)
				{
					return "This chord is a whole note.";
				}
			}
			else
			{
				if(duration.getValue() == TGDuration.EIGHTH)
				{
					return "Eighth note.";
				}
				if(duration.getValue() == TGDuration.HALF)
				{
					return "Half note.";
				}
				if(duration.getValue() == TGDuration.QUARTER)
				{
					return "Quarter note.";
				}
				if(duration.getValue() == TGDuration.SIXTEENTH)
				{
					return "Sixteenth note.";
				}
				if(duration.getValue() == TGDuration.SIXTY_FOURTH)
				{
					return "Sixty-fourth note.";
				}
				if(duration.getValue() == TGDuration.THIRTY_SECOND)
				{
					return "Thirty-second note.";
				}
				if(duration.getValue() == TGDuration.WHOLE)
				{
					return "Whole note.";
				}
			}
		}
		
		//no instruction - error
		return null;
	}
	
	/*
	 * Returns instructions about the effect modifiers of the note.
	 */
	public List<String> getNoteEffectsInstructions(TGNote n) {
		
		//rtn structure
		List<String> rtn = new ArrayList<String>();
		
		//create instructions for effects
		if(n.getEffect().hasAnyEffect())
		{
			if(n.isTiedNote())
			{
				rtn.add("This is a tied note.");
			}
			if(n.getEffect().isAccentuatedNote())
			{
				rtn.add("This is an accentuated note.");
			}
			if(n.getEffect().isBend())
			{
				rtn.add("This note has a bend.");
			}
			if(n.getEffect().isDeadNote())
			{
				rtn.add("This is a dead note.");
			}
			if(n.getEffect().isFadeIn())
			{
				rtn.add("You must fade in for this note.");
			}
			if(n.getEffect().isGhostNote())
			{
				rtn.add("This is a ghost note.");
			}
			if(n.getEffect().isGrace())
			{
				rtn.add("This is a grace note.");
			}
			if(n.getEffect().isHammer())
			{
				rtn.add("This note is a hammer-on.");
			}
			if(n.getEffect().isHarmonic())
			{
				rtn.add("This note is a harmonic.");
			}
			if(n.getEffect().isHeavyAccentuatedNote())
			{
				rtn.add("This is a heavey accentuated note.");
			}
			if(n.getEffect().isPalmMute())
			{
				rtn.add("This note requires palm muting.");
			}
			if(n.getEffect().isPopping())
			{
				rtn.add("This note requires popping.");
			}
			if(n.getEffect().isSlapping())
			{
				rtn.add("This note requires slapping.");
			}
			if(n.getEffect().isSlide())
			{
				rtn.add("This note has a slide.");
			}
			if(n.getEffect().isTremoloBar())
			{
				rtn.add("This note requires use of the Tremolo bar.");
			}
			if(n.getEffect().isStaccato())
			{
				rtn.add("This note is staccato.");
			}
			if(n.getEffect().isTapping())
			{
				rtn.add("This note requires tapping.");
			}
			if(n.getEffect().isTremoloPicking())
			{
				rtn.add("This note requires Tremolo picking.");
			}
			if(n.getEffect().isTrill())
			{
				rtn.add("This note is played with trilling.");
			}
			if(n.getEffect().isVibrato())
			{
				rtn.add("This note is vibrato.");
			}
		}
		
		return rtn;
	}

}
