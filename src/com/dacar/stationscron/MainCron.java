package com.dacar.stationscron;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.content.Intent; 

public class MainCron extends Activity {

	public final static String TIME_TOTAL = "com.dacar.stationscron.TOTAL";
	public final static String TIME_STEP = "com.dacar.stationscron.STEP";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cron);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_cron, menu);
        return true;
    }
    
    /** Called when the user clicks the Send button */
    public void initCron(View view) {
        
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	
    	//gets data
    	EditText timeTotal = (EditText) findViewById(R.id.edit_timeTotal);
    	EditText timeStep = (EditText) findViewById(R.id.edit_timeStep);
    	
    	//gets values and sets to intent
    	Long total = Long.parseLong(timeTotal.getText().toString());
    	Long step =  Long.parseLong(timeStep.getText().toString());
    	intent.putExtra(TIME_TOTAL, total);
    	intent.putExtra(TIME_STEP, step);
    	startActivity(intent);
    }    
    
}
