import java.util.Iterator;
import java.util.List;
import org.herac.tuxguitar.song.models.TGBeat;
import org.herac.tuxguitar.song.models.TGDuration;
import org.herac.tuxguitar.song.models.TGMeasure;
import org.herac.tuxguitar.song.models.TGNote;
import org.herac.tuxguitar.song.models.TGSong;
import org.herac.tuxguitar.song.models.TGTrack;
import org.herac.tuxguitar.song.models.TGVoice;
import prateekapi.PrateekAPI;

public class Instructions
{
	public static void main (String [] args)
	{
		TGSong song = PrateekAPI.loadSong("C:/Users/ATandon/Desktop/Comp Sci/sunshine_of_your_love_ver2.gp3");
		System.out.println("These are instructions for the song  " + song.getName());
		int t = 1;
		int m = 1;
		Iterator<TGTrack> tracks = song.getTracks();
		while (tracks.hasNext())
		{
			System.out.println("track number " + t);
			m = 1;
			Iterator<TGMeasure> measures = song.getTrack(t-1).getMeasures();
			while(measures.hasNext())
			{
				System.out.println("measure number " + m);
				List beats = song.getTrack(t-1).getMeasure(m-1).getBeats();
				for(int i = 0; i < beats.size(); i++)
				{
					TGBeat beat = (TGBeat) beats.get(i);
					int length = 0;
					int index = 0;
					for(int x = 0; x < beat.countVoices(); x++)
					{
						TGVoice voice = beat.getVoice(x);
						List notes = voice.getNotes();
						for(int z = 0; z < notes.size(); z++)
						{
							TGNote n = (TGNote) notes.get(z);
							TGDuration duration = voice.getDuration();
							if(beat.isRestBeat())
							{
								if(duration.getValue() == TGDuration.EIGHTH)
								{
									System.out.println("This rest is an eighth note");
								}
								if(duration.getValue() == TGDuration.HALF)
								{
									System.out.println("This rest is a half note");
								}
								if(duration.getValue() == TGDuration.QUARTER)
								{
									System.out.println("This rest is a quarter note");
								}
								if(duration.getValue() == TGDuration.SIXTEENTH)
								{
									System.out.println("This rest is a sixteenth note");
								}
								if(duration.getValue() == TGDuration.SIXTY_FOURTH)
								{
									System.out.println("This rest is a sixty-fourth note");
								}
								if(duration.getValue() == TGDuration.THIRTY_SECOND)
								{
									System.out.println("This rest is a thirty second note");
								}
								if(duration.getValue() == TGDuration.WHOLE)
								{
									System.out.println("this rest is a whole note");
								}

							}
							else
							{
								if(voice.getNotes().size()> 1)
								{
									System.out.println("You will now play a chord with " + voice.getNotes().size() + " notes");
									for(int a = 0; a < voice.getNotes().size(); a++)
									{
										System.out.println("for note number " + (a+1) + " play string number " + voice.getNote(a).getString() + " and fret number " + voice.getNote(a).getValue());
									}
									if(duration.getValue() == TGDuration.EIGHTH)
									{
										System.out.println("This chord is an eighth note");
									}
									if(duration.getValue() == TGDuration.HALF)
									{
										System.out.println("This chord is a half note");
									}
									if(duration.getValue() == TGDuration.QUARTER)
									{
										System.out.println("This chord is a quarter note");
									}
									if(duration.getValue() == TGDuration.SIXTEENTH)
									{
										System.out.println("This chord is a sixteenth note");
									}
									if(duration.getValue() == TGDuration.SIXTY_FOURTH)
									{
										System.out.println("This chord is a sixty-fourth note");
									}
									if(duration.getValue() == TGDuration.THIRTY_SECOND)
									{
										System.out.println("This chord is a thirty second note");
									}
									if(duration.getValue() == TGDuration.WHOLE)
									{
										System.out.println("this chord is a whole note");
									}
								}
								else
								{
									System.out.println("Play String number " + n.getString() + " and fret number " + n.getValue());
									if(duration.getValue() == TGDuration.EIGHTH)
									{
										System.out.println("This is an eighth note");
									}
									if(duration.getValue() == TGDuration.HALF)
									{
										System.out.println("This is a half note");
									}
									if(duration.getValue() == TGDuration.QUARTER)
									{
										System.out.println("This is a quarter note");
									}
									if(duration.getValue() == TGDuration.SIXTEENTH)
									{
										System.out.println("This is a sixteenth note");
									}
									if(duration.getValue() == TGDuration.SIXTY_FOURTH)
									{
										System.out.println("This is a sixty-fourth note");
									}
									if(duration.getValue() == TGDuration.THIRTY_SECOND)
									{
										System.out.println("This is a thirty second note");
									}
									if(duration.getValue() == TGDuration.WHOLE)
									{
										System.out.println("this is a whole note");
									}
								}
								if(n.getEffect().hasAnyEffect())
								{
									if(n.isTiedNote())
									{
										System.out.println("This is a tied note");
									}
									if(n.getEffect().isAccentuatedNote())
									{
										System.out.println("This is an accenturated note");
									}
									if(n.getEffect().isBend())
									{
										System.out.println("This note has a bend");
									}
									if(n.getEffect().isDeadNote())
									{
										System.out.println("This is a dead note");
									}
									if(n.getEffect().isFadeIn())
									{
										System.out.println("You must fade in for this note");
									}
									if(n.getEffect().isGhostNote())
									{
										System.out.println("This is a ghost note ");
									}
									if(n.getEffect().isGrace())
									{
										System.out.println("This note has a grace of ");
									}
									if(n.getEffect().isHammer())
									{
										System.out.println("This note has a hammer");
									}
									if(n.getEffect().isHarmonic())
									{
										System.out.println("This note has a harmonic of ");
									}
									if(n.getEffect().isHeavyAccentuatedNote())
									{
										System.out.println("This is a heavey accentuated note");
									}
									if(n.getEffect().isPalmMute())
									{
										System.out.println("This note requires palm muting");
									}
									if(n.getEffect().isPopping())
									{
										System.out.println("This note requires popping");
									}
									if(n.getEffect().isSlapping())
									{
										System.out.println("This note requires slapping");
									}
									if(n.getEffect().isSlide())
									{
										System.out.println("This note has a slide");
									}
									if(n.getEffect().isTremoloBar())
									{
										System.out.println("This note requires Tremollo bar");
									}
									if(n.getEffect().isStaccato())
									{
										System.out.println("This note has stacatto");
									}
									if(n.getEffect().isTapping())
									{
										System.out.println("This note has tapping");
									}
									if(n.getEffect().isTremoloPicking())
									{
										System.out.println("This note requires Tremollo picking");
									}
									if(n.getEffect().isTrill())
									{
										System.out.println("This note has a trill");
									}
									if(n.getEffect().isVibrato())
									{
										System.out.println("This note has a vibrato");
									}
								}
							}
						}
						}

					}

				m++;
			}
			t++;
		}
	}
}







