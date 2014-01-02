package pt.vgaspar.chordguess;

import android.app.AlertDialog;
import android.content.Context;

public class Debug {
	
    public static void debug(Context context, String message) {
    	new AlertDialog.Builder(context)
        .setTitle("Debug")
        .setMessage(message)
        .show();
    }
    
}
