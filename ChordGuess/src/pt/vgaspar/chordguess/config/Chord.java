package pt.vgaspar.chordguess.config;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Chord {

    @Attribute
    public String file;
    
    @Attribute
    public String id;

}
