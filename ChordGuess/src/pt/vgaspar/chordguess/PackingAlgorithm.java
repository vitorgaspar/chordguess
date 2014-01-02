package pt.vgaspar.chordguess;

public class PackingAlgorithm implements IPackingAlgorithm {

	private int screenWidth;
	private int screenHeight;
	private int numberButtons;
	
	public PackingAlgorithm(
			int screenWidth, 
			int screenHeight, 
			int numberButtons
	) 
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.numberButtons = numberButtons;
	}

	@Override
	public ButtonPlacing getNext() {
		return null;
	}

}
