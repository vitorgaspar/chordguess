package pt.vgaspar.chordguess.config;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root
@Namespace(reference="http://www.vgaspar.pt/chordguess/config")
public class Config {

    @Element
    public Chords chords;

    @Element
    public Screens screens;

}
