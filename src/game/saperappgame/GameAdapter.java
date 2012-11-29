package game.saperappgame;

import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.TextView;


public class GameAdapter extends BaseAdapter {	
	
	public static final int EMPTY = 0;
	public static final int ZERO = 100;
	public static final int ONE = 101;
	public static final int TWO = 102;
	public static final int THREE = 103;
	public static final int FOUR = 104;
	public static final int FIVE = 105;
	public static final int SIX = 106;
	public static final int SEVEN = 107;
	public static final int EIGHT = 108;
	public static final int MINE = 109;
	public static final int FLAG = 2;
	
	
	private LayoutInflater mInflater;	
	private GameCore gc;
    private TextView tvMC;
    private View vB;
    private Chronometer chron;
    private boolean flag = true;
    private Dialog dialog;
    private View result_view;
    private Context context;
	
	public GameAdapter(Context context,GameCore gc,TextView tvMC,View vB,Chronometer chron){
		mInflater = (LayoutInflater) context.getApplicationContext().
										getSystemService(Context.LAYOUT_INFLATER_SERVICE);				
		notifyDataSetChanged();			
		this.gc = gc;		
		this.tvMC = tvMC;
		this.vB = vB;
		this.chron = chron;
		this.context = context;
		dialog = new Dialog(context);
		result_view = mInflater.inflate(R.layout.result_layout, null);		
		dialog.setContentView(result_view);
	}
	
	public int getCount() {	
		return gc.getLength();
	}

	@Override
	public Object getItem(int position) {	
		if (position>-1 & position < gc.getLength())
			return gc.getElement(position);
		return 0;
	}


	@Override
	public long getItemId(int position) {		
		return position;
	}
	
	private void adaptData(View imb){
		int position = imb.getId();
		if (gc.getElement(position)/100 == EMPTY)
			imb.setBackgroundResource(R.drawable.cells);
		else
		if (gc.getElement(position)/100 == FLAG)	
			imb.setBackgroundResource(R.drawable.cells_flag);
		else
			switch (gc.getElement(position)){
			 case ZERO: imb.setBackgroundResource(R.drawable.cells_0);
				break;
			 case ONE: imb.setBackgroundResource(R.drawable.cells_1);
				 break;
			 case TWO: imb.setBackgroundResource(R.drawable.cells_2);
				 break;
			 case THREE: imb.setBackgroundResource(R.drawable.cells_3);
				 break;
			 case FOUR: imb.setBackgroundResource(R.drawable.cells_4);
				 break;
			 case FIVE: imb.setBackgroundResource(R.drawable.cells_5);
				 break;
			 case SIX: imb.setBackgroundResource(R.drawable.cells_6);
			 	break;
			 case SEVEN: imb.setBackgroundResource(R.drawable.cells_7);
			 	break;
			 case EIGHT: imb.setBackgroundResource(R.drawable.cells_8);
			 	break;
			 case MINE: imb.setBackgroundResource(R.drawable.cells_bomb);
				 break;			 				
			}
	}	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		convertView = mInflater.inflate(R.layout.imgbut, null);
		View v = (View) convertView.findViewById(R.id.imb);			
		v.setId(position);		
		adaptData(v);					
		tvMC.setText(String.valueOf(gc.getMine()-gc.getFlagCount()).toString());
		v.setOnLongClickListener(new View.OnLongClickListener() {				
				@Override
			public boolean onLongClick(View v) {
					final int position = v.getId();
					gc.setFlag(position);						
					notifyDataSetChanged();	
					return true;
				}
			});
			
			v.setOnClickListener(new View.OnClickListener() {
				
				@Override
			public void onClick(View v) {
					final int position = v.getId();	
					if (flag){
						chron.setBase(SystemClock.elapsedRealtime());
						chron.start();
						flag = false;
					}
					gc.action(position);
					notifyDataSetChanged();
				}			 
				
			});			 
		
		
		if (gc.isLoose()){			
			vB.setBackgroundResource(R.drawable.loose);
			chron.stop();
			flag = true;	
			dialog.setTitle(context.getString(R.string.loose_string));
			dialog.show();
		}
		
		if (gc.isWin()){			
			vB.setBackgroundResource(R.drawable.smile);
			chron.stop();
			flag = true;
			dialog.setTitle(context.getString(R.string.win_string));
			dialog.show();
		}
		
			
		return convertView;
		
	}
	

}
