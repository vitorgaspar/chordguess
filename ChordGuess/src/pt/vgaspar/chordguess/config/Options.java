package pt.vgaspar.chordguess.config;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Options {

    @ElementList(inline=true)
    public List<Use> use;

}
