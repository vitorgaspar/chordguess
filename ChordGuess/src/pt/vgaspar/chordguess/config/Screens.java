package pt.vgaspar.chordguess.config;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Screens {

    @Element
    public ChordGuess chordGuess;
    
}