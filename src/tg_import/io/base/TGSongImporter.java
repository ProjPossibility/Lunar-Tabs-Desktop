package tg_import.io.base;

import java.io.InputStream;

import tg_import.song.factory.TGFactory;
import tg_import.song.models.TGSong;

public interface TGSongImporter {
	
	public String getImportName();
	
	public TGFileFormat getFileFormat();
	
	public boolean configure(boolean setDefaults);
	
	public TGSong importSong(TGFactory factory,InputStream stream) throws TGFileFormatException;
	
}
