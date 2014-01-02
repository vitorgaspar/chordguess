package pt.vgaspar.chordguess;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import pt.vgaspar.chordguess.autogenconfig.Chord;
import pt.vgaspar.chordguess.exceptions.ConfigParsingException;

public class AppConfig extends pt.vgaspar.chordguess.autogenconfig.Config {

	private AppConfig() {
		
	}
	
	public static AppConfig Create(String filename) throws ConfigParsingException {
		AppConfig newConfig = null;
		
		try {
			JAXBContext context = JAXBContext.newInstance(AppConfig.class);
			
			File configFile = new File("file:///android_asset/" + filename);
			
			newConfig = (AppConfig) context.createUnmarshaller().unmarshal(configFile);
			
		} catch (JAXBException e) {
			
			throw new ConfigParsingException(
					"Failed to parse [" + filename + "] from assets.", e);
		}

		return newConfig;
	}

	public Chord getChordWithId(String id) {
		List<Chord> chords = getChords().getChord();
		
		for (int i = 0; i < chords.size(); ++i) {
			Chord chord = chords.get(i);
			
			if (chord.getId() == id) {
				return chord;
			}
		}
		
		return null;
	}
}
