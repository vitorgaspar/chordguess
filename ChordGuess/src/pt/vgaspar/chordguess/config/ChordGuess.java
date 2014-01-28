package pt.vgaspar.chordguess.config;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class ChordGuess {

    @Element
    public Options options;
    
    @Attribute
    public int padding;
    
}