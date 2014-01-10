package pt.vgaspar.chordguess;

public class PackingAlgorithm implements IPackingAlgorithm {

	private int frameWidth;
	private int frameHeight;
	private int numberButtons;
	private int gap;
	
	private int currentColumn;
	private int currentRow;
	
	public PackingAlgorithm(
			int frameWidth, 
			int frameHeight, 
			int numberButtons,
			int gap
	) 
	{
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.numberButtons = numberButtons;
		this.gap = gap;
		
		this.currentColumn = 0;
		this.currentRow = 0;
	}
	
	private double availableArea() {
		return (double)frameWidth * frameHeight;
	}
	
	private double eachButtonArea() {
		return availableArea() / numberButtons;
	}
	
	// Relation between width and height assumes a square button
	private int buttonWidth() {
		return (int)Math.floor(
				Math.max( 
						-2 * gap + ( Math.sqrt(eachButtonArea()) / 2 ),
						-2 * gap - ( Math.sqrt(eachButtonArea()) / 2 )
					)
				);
	}
	
	private int buttonHeight() {
		return buttonWidth();
	}

	@Override
	public ButtonPlacing getNext() {
		
		ButtonPlacing placing = new ButtonPlacing();
		placing.width = buttonWidth();
		placing.height = buttonHeight();
		
		int nextXOnNextColumn = gap + ((placing.width + gap) * currentColumn);
		int otherEdge = nextXOnNextColumn + placing.width + gap;
		if (otherEdge > frameWidth) {
			placing.x = gap;
			currentRow++;
			currentColumn = 0;
		} else {
			placing.x = nextXOnNextColumn;
			currentColumn++;
		}
		
		placing.y = gap + ((placing.height + gap) * currentRow);
		
		return placing;
	}

}
