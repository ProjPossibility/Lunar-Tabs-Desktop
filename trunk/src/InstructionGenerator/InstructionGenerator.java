package InstructionGenerator;
import org.herac.tuxguitar.song.models.TGNote;
import java.util.*;

public class InstructionGenerator {
	
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
