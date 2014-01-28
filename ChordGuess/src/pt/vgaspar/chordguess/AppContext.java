package pt.vgaspar.chordguess;

public class AppContext {
	
	private AppScreenLayout screenLayout;
	private AppConfig config;
	
	public AppContext(AppConfig config, AppScreenLayout screenLayout) {
		this.screenLayout = screenLayout;
		this.config = config;
	}

	public AppScreenLayout getScreenLayout() {
		return screenLayout;
	}

	public AppConfig getConfig() {
		return config;
	}
}
