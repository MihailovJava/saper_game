package game.saperappgame;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {	
	  
	  GridView gvMain;
	  GameAdapter adapter;
	  View startIm;
	  GameCore gc;
	  Chronometer chron;
	  TextView tv;  
	  final static int ROW = 10;
	  final static int COL = 10;
	  final static int MINE = 10;
	  
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);	 
	        setContentView(R.layout.activity_main);
	        initUI();	       
	        gvMain.setAdapter(adapter);	
	        adjustGridView();
	    }	
	    
	    private void initUI(){	    	 
	    	 tv= (TextView) findViewById(R.id.tvMineCount);
	 		 chron = (Chronometer) findViewById(R.id.chron);
	    	 startIm = (View) findViewById(R.id.startIm);
	    	 gvMain = (GridView) findViewById(R.id.gvMain); 	    	 
	    	 gc = new GameCore(ROW,COL,MINE);
	    	 adapter = new GameAdapter(this,gc,tv,startIm,chron); 	
	    	 startIm.setOnClickListener(new View.OnClickListener() {		   
				@Override
				public void onClick(View v) {					 
					 chron.setBase(SystemClock.elapsedRealtime());
					 gc.initData();
			    	 startIm.setBackgroundResource(R.drawable.start);
			    	 adapter.notifyDataSetChanged();
				
				}
			});
	    }
    
	    private void adjustGridView() {
	       gvMain.setNumColumns(COL);         
	      }
}
