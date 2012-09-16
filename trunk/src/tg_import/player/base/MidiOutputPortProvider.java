package tg_import.player.base;

import java.util.List;

public interface MidiOutputPortProvider {
	
	public List listPorts() throws MidiPlayerException;
	
	public void closeAll() throws MidiPlayerException;
	
}
