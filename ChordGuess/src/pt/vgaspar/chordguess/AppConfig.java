package pt.vgaspar.chordguess;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import pt.vgaspar.chordguess.config.Chord;
import pt.vgaspar.chordguess.exceptions.ConfigParsingException;

public class AppConfig extends pt.vgaspar.chordguess.config.Config {

	private AppConfig() {
		
	}

	public static AppConfig createFromString(String configXml) throws ConfigParsingException {
		AppConfig newConfig = null;
		
		try {
			Reader reader = new StringReader(configXml);
			
			newConfig = createFromReader(reader);
			
		} catch (Exception e) {
			
			throw new ConfigParsingException(
					"Failed to parse [" + configXml + "]", e);
		}
		
		return newConfig;
	}
	
	public static AppConfig createFromAsset(String filename) throws ConfigParsingException {
		return createFromFile("file:///android_asset/" + filename);
	}
	
	public static AppConfig createFromFile(String url) throws ConfigParsingException {
		AppConfig newConfig = null;
		
		try {
			File configFile = new File(url);
			Reader reader = new FileReader(configFile);

			newConfig = createFromReader(reader);
			
		} catch (Exception e) {
			
			throw new ConfigParsingException(
					"Failed to parse [" + url + "].", e);
		}

		return newConfig;
	}
	
	protected static AppConfig createFromReader(Reader reader) throws Exception {
		Serializer serializer = new Persister();
		return (AppConfig) serializer.read(AppConfig.class, reader);
	}

	public Chord getChordWithId(String id) {
		
		for (int i = 0; i < chords.chord.size(); ++i) {
			Chord chord = chords.chord.get(i);
			
			if (chord.id == id) {
				return chord;
			}
		}
		
		return null;
	}
}
