package pt.vgaspar.chordguess.exceptions;

public class ConfigParsingException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConfigParsingException(String message) {
        super(message);
    }
	
	public ConfigParsingException(Throwable throwable) {
		super(throwable);
	}
	
	public ConfigParsingException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
