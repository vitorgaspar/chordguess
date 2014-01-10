package pt.vgaspar.chordguess;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import pt.vgaspar.chordguess.autogenconfig.Chord;
import pt.vgaspar.chordguess.autogenconfig.Chords;
import pt.vgaspar.chordguess.autogenconfig.Use;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
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
	private IPackingAlgorithm packingAlgorithm;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        try {
        	AppConfig config = AppConfig.Create("config.xml");
        	
        	buildLibrary(config);
        	rand = new Random();
        	
        	createScreen(config);
        	
        	resetTexts();
        	playNewChord();
        	
        } catch (Exception ex) {
        	ExceptionHandler.handle(this, ex, null);
        }
    }
    
    private void createScreen(AppConfig config) {
    	List<Use> chordsUsed = 
    			config.getScreens().getChordGuess().getOptions().getUse();
    	
    	for (int i = 0; i < chordsUsed.size(); ++i) {
    		Use use = chordsUsed.get(i);
    		Chord chord = config.getChordWithId(use.getId());
    		createGuessButton(chord);
    	}
    }
    
    private void createGuessButton(Chord chord) {
    	
    	/*
<Button
    android:id="@+id/btnEChord"
    style="?android:attr/buttonStyleSmall"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_alignBaseline="@+id/btnDChord"
    android:layout_alignBottom="@+id/btnDChord"
    android:layout_marginLeft="23dp"
    android:layout_toRightOf="@+id/btnDChord"
    android:text="E" />
    	 */
    	
    	final String chordName = chord.getId();
    	
    	Button btnChord = new Button(this);
    	btnChord.setText(chordName);
    	
    	RelativeLayout rlMain = (RelativeLayout)findViewById(R.id.rlMain);
    	LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	
    	ButtonPlacing buttonPlacing = packingAlgorithm.getNext();
    	// TODO: instantiate packingAlgorithm
    	// TODO: implement it: http://stackoverflow.com/questions/7439560/whats-the-algorithm-to-pack-squares-and-rectangles
    	
    	rlMain.addView(btnChord, lp);
    	
    	btnChord.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	answer(chordName);
            }
        });
    	
    }
    
    private void answer(String chordAnswer) {
    	TextView txtChordName = (TextView) findViewById(R.id.txtChordName);
    	TextView txtWrongRight = (TextView) findViewById(R.id.txtWrongRight);
    	
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
    	TextView txtChordName = (TextView) findViewById(R.id.txtChordName);
    	TextView txtWrongRight = (TextView) findViewById(R.id.txtWrongRight);
    	
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
    	
    	List<Chord> chords = config.getChords().getChord();
    	
    	for (int i = 0; i < chords.size(); ++i) {
    		
    		Chord chord = chords.get(i);
    		   		
    		String file = chord.getFile();
    		String name = chord.getId();
    		
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
    
    public void onDestroy() {
    	if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
    	}

        super.onDestroy();
    }
}
