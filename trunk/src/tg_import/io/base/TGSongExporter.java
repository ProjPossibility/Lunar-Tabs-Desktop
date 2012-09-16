package tg_import.io.base;

import java.io.OutputStream;

import tg_import.song.models.*;

public interface TGSongExporter {
	
	public String getExportName();
	
	public TGFileFormat getFileFormat();
	
	public boolean configure(boolean setDefaults);
	
	public void exportSong(OutputStream stream,TGSong song) throws TGFileFormatException;
}
