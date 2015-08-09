package com.liyu.widget;

import com.liyu.superprogresswheel.R;
import com.liyu.widget.SuperProgressWheel.onProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements onProgressListener{

	SuperProgressWheel spw1,spw2,spw3;
	Thread mThread;
	boolean running = false;
	Button btn ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn = (Button) findViewById(R.id.button1);
		
		spw1 = (SuperProgressWheel) findViewById(R.id.spw1);
		spw2 = (SuperProgressWheel) findViewById(R.id.spw2);
		spw3 = (SuperProgressWheel) findViewById(R.id.spw3);
		spw1.setProgress(20);
		spw2.setProgress(40);
		spw3.setMaxProgress(50);
		spw1.setOnProgressListener(this);
		spw2.setOnProgressListener(this);
		spw3.setOnProgressListener(this);
		
		mThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					if(running){
				        spw1.setProgress(spw1.getProgress()+1);
				        spw2.setProgress(spw2.getProgress()+1);
				        spw3.setProgress(spw3.getProgress()+1);
				
				        try {
					        Thread.sleep(100);
				        } catch (InterruptedException e) {
					        e.printStackTrace();
				        }
				     }
			      }
			}
		});
		mThread.start();
	}

	public void onClick(View v){
		if(running){
			running = false;
			spw1.setStartDrawableAnim(false);
			btn.setText("Start");
		}else{
			running = true;
			spw1.setStartDrawableAnim(true);
			btn.setText("Stop");
		}
	}

	@Override
	public void onCompleted(View v) {
		switch (v.getId()) {
		case R.id.spw1:
			Toast.makeText(MainActivity.this, "spw1 completed!", Toast.LENGTH_SHORT).show();
			spw1.setProgress(0);
			break;
        case R.id.spw2:
        	Toast.makeText(MainActivity.this, "spw2 completed!", Toast.LENGTH_SHORT).show();
        	spw2.setProgress(0);
			break;
        case R.id.spw3:
        	Toast.makeText(MainActivity.this, "spw3 completed!", Toast.LENGTH_SHORT).show();
        	spw3.setProgress(0);
	        break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(running){
			running = false;
			mThread.interrupt();
		}
	}
	
}
