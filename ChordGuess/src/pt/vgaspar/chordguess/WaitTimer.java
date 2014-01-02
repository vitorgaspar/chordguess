package pt.vgaspar.chordguess;

import java.util.concurrent.Callable;

import android.os.CountDownTimer;

public class WaitTimer extends CountDownTimer {
	
	private Callable<Integer> onFinishCallback;
	
    public WaitTimer(long millisInFuture, Callable<Integer> callback) {
        super(millisInFuture, millisInFuture);
        onFinishCallback = callback;
    }

    @Override
    public void onFinish() {
    	try {
    		onFinishCallback.call();
    	} catch (Exception ex) {
    		// Not interested
    	}
    }

	@Override
	public void onTick(long millisUntilFinished) {
		
	}
}