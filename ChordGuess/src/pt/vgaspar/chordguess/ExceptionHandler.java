package pt.vgaspar.chordguess;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.app.AlertDialog;
import android.content.Context;

public class ExceptionHandler {

    public static void handle(Context context, Exception ex, String message) {
    	
    	Writer writer = new StringWriter();
    	PrintWriter printWriter = new PrintWriter(writer);
    	ex.printStackTrace(printWriter);
    	String stack = writer.toString();
    	
    	new AlertDialog.Builder(context)
        .setTitle("Error")
        .setMessage(message + " " + ex.toString() + " " + stack)
        .show();
    	
    }
}
