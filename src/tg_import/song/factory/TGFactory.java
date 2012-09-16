package tg_import.song.factory;

import tg_import.song.models.TGBeat;
import tg_import.song.models.TGChannel;
import tg_import.song.models.TGChord;
import tg_import.song.models.TGColor;
import tg_import.song.models.TGDuration;
import tg_import.song.models.TGLyric;
import tg_import.song.models.TGMarker;
import tg_import.song.models.TGMeasure;
import tg_import.song.models.TGMeasureHeader;
import tg_import.song.models.TGNote;
import tg_import.song.models.TGNoteEffect;
import tg_import.song.models.TGScale;
import tg_import.song.models.TGSong;
import tg_import.song.models.TGString;
import tg_import.song.models.TGStroke;
import tg_import.song.models.TGTempo;
import tg_import.song.models.TGText;
import tg_import.song.models.TGTimeSignature;
import tg_import.song.models.TGTrack;
import tg_import.song.models.TGTupleto;
import tg_import.song.models.TGVoice;
import tg_import.song.models.effects.TGEffectBend;
import tg_import.song.models.effects.TGEffectGrace;
import tg_import.song.models.effects.TGEffectHarmonic;
import tg_import.song.models.effects.TGEffectTremoloBar;
import tg_import.song.models.effects.TGEffectTremoloPicking;
import tg_import.song.models.effects.TGEffectTrill;

public class TGFactory {
	
	public TGSong newSong(){
		return new TGSong() {
			//TGSong Implementation
		};
	}
	
	public TGLyric newLyric(){
		return new TGLyric(){
			//TGLyric Implementation
		};
	}
	
	public TGMarker newMarker(){
		return new TGMarker(this){
			//TGMarker Implementation
		};
	}
	
	public TGChord newChord(int length){
		return new TGChord(length){
			//TGChord Implementation
		};
	}
	
	public TGScale newScale(){
		return new TGScale(){
			//TGScale Implementation
		};
	}
	
	public TGColor newColor(){
		return new TGColor(){
			//TGColor Implementation
		};
	}
	
	public TGTupleto newTupleto(){
		return new TGTupleto(){
			//TGTupleto Implementation
		};
	}
	
	public TGDuration newDuration(){
		return new TGDuration(this){
			//TGDuration Implementation
		};
	}
	
	public TGTimeSignature newTimeSignature(){
		return new TGTimeSignature(this){
			//TGTimeSignature Implementation
		};
	}
	
	public TGTempo newTempo(){
		return new TGTempo(){
			//TGTempo Implementation
		};
	}
	
	public TGChannel newChannel(){
		return new TGChannel(){
			//TGChannel Implementation
		};
	}
	
	public TGTrack newTrack(){
		return new TGTrack(this){
			//TGTrack Implementation
		};
	}
	
	public TGMeasureHeader newHeader(){
		return new TGMeasureHeader(this){
			//TGMeasureHeader Implementation
		};
	}
	
	public TGMeasure newMeasure(TGMeasureHeader header){
		return new TGMeasure(header){
			//TGMeasure Implementation
		};
	}
	
	public TGBeat newBeat(){
		return new TGBeat(this){
			//TGBeat Implementation
		};
	}
	
	public TGVoice newVoice(int index){
		return new TGVoice(this, index){
			//TGVoice Implementation
		};
	}
	
	public TGNote newNote(){
		return new TGNote(this){
			//TGNote Implementation
		};
	}
	
	public TGString newString(){
		return new TGString(){
			//TGString Implementation
		};
	}
	
	public TGStroke newStroke(){
		return new TGStroke(){
			//TGString Implementation
		};
	}
	
	public TGText newText(){
		return new TGText(){
			//TGString Implementation
		};
	}
	
	public TGNoteEffect newEffect(){
		return new TGNoteEffect(){
			//TGNoteEffect Implementation
		};
	}
	
	public TGEffectBend newEffectBend(){
		return new TGEffectBend(){
			//TGEffectBend Implementation
		};
	}
	
	public TGEffectTremoloBar newEffectTremoloBar(){
		return new TGEffectTremoloBar(){
			//TGEffectTremoloBar Implementation
		};
	}
	
	public TGEffectGrace newEffectGrace(){
		return new TGEffectGrace(){
			//TGEffectGrace Implementation
		};
	}
	
	public TGEffectHarmonic newEffectHarmonic(){
		return new TGEffectHarmonic(){
			//TGEffectHarmonic Implementation
		};
	}
	
	public TGEffectTrill newEffectTrill(){
		return new TGEffectTrill(this){
			//TGEffectTrill Implementation
		};
	}
	
	public TGEffectTremoloPicking newEffectTremoloPicking(){
		return new TGEffectTremoloPicking(this){
			//TGEffectTremoloPicking Implementation
		};
	}
}
