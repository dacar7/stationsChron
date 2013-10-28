package com.dacar.stationscron;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DisplayMessageActivity extends Activity {
	
	//global vars
	Long lngTotalTime;
	Long lngStepTime;
	Boolean bolFinish = false;
	int intNotfs = 1;
	
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        //Gets input values
        Intent intent = getIntent();
        Long lngTimeTotal = intent.getLongExtra(MainCron.TIME_TOTAL, 0);
        Long lngTimeStep = intent.getLongExtra(MainCron.TIME_STEP, 0);
        
        //set units
        lngTimeTotal = lngTimeTotal * 1000;
        lngTimeStep = lngTimeStep * 1000;      

        //set global vars
        lngTotalTime = lngTimeTotal;
        lngStepTime = lngTimeStep;
        
        
        //Total counter
        new CountDownTimer(lngTimeTotal, 1000) {

    	   TextView txtTimeTotal = (TextView)findViewById(R.id.txtTotalValue);    	  
    	  
            public void onTick(long millisUntilFinished) {
            	txtTimeTotal.setText("" + millisUntilFinished / 1000);            	
            }

            public void onFinish() {
            	bolFinish = true;  
            	txtTimeTotal.setText("Done!");
            }
         }.start();      
         
         
         //Lap counter
         new CountDownTimer(lngTimeStep, 1000) {
      	 
      	   TextView txtTimeLap = (TextView)findViewById(R.id.txtTimeLapValue);
      	  
              public void onTick(long millisUntilFinished) {              	
              	txtTimeLap.setText("" + millisUntilFinished / 1000);
              }

              public void onFinish() {
            	  //show notification
            	  displayNotification();
            	  
            	  //init the other cicle
            	  initLapTimer(lngStepTime);
              }
           }.start();          
        
    }
    
    
    public void initLapTimer(Long lngTime){
    	
        //Lap counter
        new CountDownTimer(lngTime, 1000) {
     	 
     	   TextView txtTimeLap = (TextView)findViewById(R.id.txtTimeLapValue);
     	  
             public void onTick(long millisUntilFinished) {              	
             	txtTimeLap.setText("" + millisUntilFinished / 1000);
             }

             public void onFinish() {
	           	  //show notification
	           	  displayNotification();
	           	  
	           	  //recursive call
	           	  if(!bolFinish)
	           		  initLapTimer(lngStepTime);
	           	  else
	           		txtTimeLap.setText("Done!");
             }
          }.start();   
    	
    }

    
    protected void displayNotification(){
    	Intent i = new Intent(this,  DisplayNotification.class);
        i.putExtra("notificationID", intNotfs);
         
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);

        Notification n  = new Notification.Builder(this)
		        .setContentTitle("Step - Time Out")
		        .setContentText("Change Station, GO GO!")
		        .setSmallIcon(R.drawable.ic_final)
		        .setContentIntent(pendingIntent)
		        .setAutoCancel(true)		        
		        .setVibrate(new long[] {100, 250, 100, 500} ).build();        
        
        NotificationManager notificationManager = 
        		  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(intNotfs, n); 
        
        intNotfs++;
    }    
    

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
  

}
