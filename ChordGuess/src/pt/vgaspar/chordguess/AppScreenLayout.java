package pt.vgaspar.chordguess;

public class AppScreenLayout {
	
	private int width;
	private int height;
	private Orientation orientation;
	
	public AppScreenLayout(int width, int height, Orientation orientation) {
		this.width = width;
		this.height = height;
		this.orientation = orientation;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Orientation getOrientation() {
		return orientation;
	}
}
