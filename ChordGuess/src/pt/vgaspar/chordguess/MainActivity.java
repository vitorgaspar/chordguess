package pt.vgaspar.chordguess;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import pt.vgaspar.chordguess.config.Chord;
import pt.vgaspar.chordguess.config.Use;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

	private MediaPlayer mPlayer;
	private List<String> chordIndex;
	private Map<String, String> chordLibrary;
	private String currentChord;
	private Random rand;
	
	private int TXT_WRONG_RIGHT_ID = 100;
	private int TXT_CHORD_NAME_ID = 110;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        try {
        	AppConfig config = AppConfig.createFromAsset("config.xml");
        	AppScreenLayout screenLayout = createScreenLayout();
        	AppContext context = new AppContext(config, screenLayout);
        	
        	buildLibrary(config);
        	rand = new Random();
        	 
        	createScreen(context);
        	
        	resetTexts();
        	playNewChord();
        	
        } catch (Exception ex) {
        	ExceptionHandler.handle(this, ex, null);
        }
    }
    
    private void createScreen(AppContext context) {    	
    	createAnswersSection(context);    	
    	createButtonsSection(context);
    }

	private void createAnswersSection(AppContext context) {
		ControlLayout answersSection = new ControlLayout();
    	answersSection.width = context.getScreenLayout().getWidth();
    	answersSection.height = // 10% of total screen height 
    			(int)Math.floor((double)context.getScreenLayout().getHeight() * .1);
    	answersSection.x = 0;
    	answersSection.y = // place 90% to the bottom of the screen
    			context.getScreenLayout().getHeight() - answersSection.height;
    	
    	RelativeLayout rlMain = (RelativeLayout)findViewById(R.id.rlMain);
    	LayoutParams lpRlAnswers = new LayoutParams(answersSection.width, answersSection.height);
    	lpRlAnswers.leftMargin = answersSection.x;
    	lpRlAnswers.topMargin = answersSection.y;
    	
    	RelativeLayout rlAnswers = new RelativeLayout(this);
    	rlMain.addView(rlAnswers);
    	
    	TextView txtWrongRight = new TextView(this);
    	txtWrongRight.setId(TXT_WRONG_RIGHT_ID);
    	LayoutParams lpTxtWrongRight = new LayoutParams(answersSection.width / 2, answersSection.height / 2);
    	lpTxtWrongRight.leftMargin = 0;
    	lpTxtWrongRight.topMargin = 0;
    	rlAnswers.addView(txtWrongRight, lpTxtWrongRight);
    	
    	TextView txtChordName = new TextView(this);
    	txtWrongRight.setId(TXT_CHORD_NAME_ID);
    	LayoutParams lpTxtChordName = new LayoutParams(answersSection.width / 2, answersSection.height / 2);
    	lpTxtChordName.leftMargin = answersSection.width / 2;
    	lpTxtChordName.topMargin = 0;
    	rlAnswers.addView(txtChordName, lpTxtChordName);
	}

	private void createButtonsSection(AppContext context) {
    	ControlLayout buttonsSection = new ControlLayout();
    	buttonsSection.width = context.getScreenLayout().getWidth();
    	buttonsSection.height = // 90% of total screen height 
    			(int)Math.floor((double)context.getScreenLayout().getHeight() * .9);
    	buttonsSection.x = 0;
    	buttonsSection.y = 0;
    	
    	List<Use> chordsUsed = 
    			context.getConfig().screens.chordGuess.options.use;
    	IPackingAlgorithm packingAlgorithm = new PackingAlgorithm
		(
				buttonsSection.width,
				buttonsSection.height,
				chordsUsed.size(),
				0,
				buttonsSection.x,
				buttonsSection.y
		);
    	
    	for (int i = 0; i < chordsUsed.size(); ++i) {
    		Use use = chordsUsed.get(i);
    		Chord chord = context.getConfig().getChordWithId(use.id);
    		createGuessButton(packingAlgorithm, chord);
    	}
	}
    
    private void createGuessButton(IPackingAlgorithm packingAlgorithm, Chord chord) {
    	ControlLayout buttonLayout = packingAlgorithm.getNext();
    	
    	final String chordName = chord.id;
    	
    	Button btnChord = new Button(this);
    	btnChord.setText(chordName);
    	
    	RelativeLayout rlMain = (RelativeLayout)findViewById(R.id.rlMain);
    	LayoutParams lp = new LayoutParams(buttonLayout.width, buttonLayout.height);
    	lp.leftMargin = buttonLayout.x;
    	lp.topMargin = buttonLayout.y;
    	
    	rlMain.addView(btnChord, lp);
    	
    	btnChord.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	answer(chordName);
            }
        });
    }
    
    private void answer(String chordAnswer) {
    	TextView txtChordName = (TextView) findViewById(TXT_CHORD_NAME_ID);
    	TextView txtWrongRight = (TextView) findViewById(TXT_WRONG_RIGHT_ID);
    	
		txtChordName.setText(currentChord);
    	
    	if (currentChord.compareTo(chordAnswer) == 0) {
    		txtWrongRight.setText("Correct!");
    	} else {
    		txtWrongRight.setText("Wrong!");	            		
    	}
    	
    	WaitTimer timer = new WaitTimer(2000, new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					resetTexts();
					playNewChord();
    				
    				return 0;
				}
    	});
    	timer.start();
    }
    
    private void resetTexts() {
    	TextView txtChordName = (TextView) findViewById(TXT_CHORD_NAME_ID);
    	TextView txtWrongRight = (TextView) findViewById(TXT_WRONG_RIGHT_ID);
    	
    	txtChordName.setText("");
    	txtWrongRight.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void buildLibrary(AppConfig config) throws IOException {

    	chordLibrary = new TreeMap<String, String>();
    	chordIndex = new LinkedList<String>();
    	
    	List<Chord> chords = config.chords.chord;
    	
    	for (int i = 0; i < chords.size(); ++i) {
    		
    		Chord chord = chords.get(i);
    		   		
    		String file = chord.file;
    		String name = chord.id;
    		
    		chordLibrary.put(name, file);
    		chordIndex.add(name);
    	}
    }
    
    private void playNewChord() {
    	String chord = pickChord();
    	currentChord = chord;
    	play(chord);
    }
    
    private void play(String chordName) {
    	String chordFile = chordLibrary.get(chordName);
    	
    	if (mPlayer != null) {
    		if (mPlayer.isPlaying()) {
    			mPlayer.stop();
    		}
        	mPlayer.release();
        }
        
    	mPlayer = new MediaPlayer();
    	
    	try {
	    	AssetFileDescriptor descriptor = getAssets().openFd(chordFile);
	        mPlayer.setDataSource(
	        		descriptor.getFileDescriptor(), 
	        		descriptor.getStartOffset(), 
	        		descriptor.getLength()
	        );
	        descriptor.close();
    	} catch (Exception ex) {
    		ExceptionHandler.handle(this, ex, "File not found '" + chordFile + "'");
    	}
        
        try {
        	mPlayer.prepare();
        } catch (Exception ex) {
        	ExceptionHandler.handle(this, ex, null);
        }
        
        mPlayer.setVolume(1f, 1f);
        
        mPlayer.start();
    }
    
    private String pickChord() {
    	int i = rand.nextInt(chordIndex.size());
    	return chordIndex.get(i);
    }
    
    private AppScreenLayout createScreenLayout() {
    	Display display = getWindowManager().getDefaultDisplay();
    	Point size = new Point();
    	display.getSize(size);
    	
    	int width = size.x;
    	int height = size.y;
    	
    	Orientation orientation = Orientation.UNKNOWN;
    	if(width == height){
            orientation = Orientation.SQUARE;
        } else{ 
            if(width < height){
                orientation = Orientation.PORTRAIT;
            }else { 
                 orientation = Orientation.LANDSCAPE;
            }
        }
    	
    	AppScreenLayout screenLayout = 
    			new AppScreenLayout(width, height, orientation);
    	return screenLayout;
    }
    
    public void onDestroy() {
    	if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
    	}

        super.onDestroy();
    }
}
