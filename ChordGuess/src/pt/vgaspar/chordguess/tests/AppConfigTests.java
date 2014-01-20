package pt.vgaspar.chordguess.tests;

import org.junit.Assert;
import org.junit.Test;

import pt.vgaspar.chordguess.AppConfig;
import pt.vgaspar.chordguess.exceptions.ConfigParsingException;

public class AppConfigTests extends ChordGuessBaseTestCase {

	@Test
	public void AppConfigCreation_WhenProcessingFile_CreatesCorrrectConfig() {
		// Arrange
		String configXml = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<config \n" + 
						"xmlns:xsi=\"w3.org/2001/XMLSchema-instance\"\n\t" +
						"xmlns:xsd=\"w3.org/2001/XMLSchema\"\n\t" + 
						"xmlns=\"http://www.vgaspar.pt/chordguess/config\">\n\t"+ 
					"<chords>\n\t" +
							"<chord id=\"A\" file=\"A.mp3\" />\n\t" +
							"<chord id=\"D\" file=\"D.mp3\" />\n\t" +
							"<chord id=\"E\" file=\"E.mp3\" />\n\t" +
					"</chords>\n\t" +
					"<screens>\n\t\t" + 
						"<chordGuess padding=\"2\">\n\t\t" +
							"<options>\n\t\t" +
								"<use id=\"A\" />\n\t\t" +
								"<use id=\"D\" />\n\t\t" +
								"<use id=\"E\" />\n\t\t" +
							"</options>\n\t\t" +
						"</chordGuess>\n\t" +
					"</screens>\n" +
				"</config>";
		AppConfig actualConfig = null;
		
		// Act
		try {
			actualConfig = AppConfig.createFromString(configXml);
		} catch (ConfigParsingException e) {
			Assert.fail("Parsing configuration file threw an exception: " + e.getMessage());
		}
		
		// Assert
		Assert.assertNotNull(actualConfig);
		Assert.assertEquals(actualConfig.chords.chord.size(), 3);
		Assert.assertEquals(actualConfig.chords.chord.get(1).id, "D");
		Assert.assertEquals(actualConfig.screens.chordGuess.options.use.size(), 3);
		Assert.assertEquals(actualConfig.screens.chordGuess.options.use.get(2).id, "E");
	}
}
