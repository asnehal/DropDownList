package com.dropdownlistdemo;

import java.util.ArrayList;

import com.popupwindowtrial.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DropDownListDemo extends Activity {
	private PopupWindow pw;
	private boolean expanded; 
	public static boolean[] checkSelected;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initialize();
    }
    
    private void initialize(){
        final ArrayList<String> items = new ArrayList<String>();
    	items.add("Item 1");
    	items.add("Item 2");
    	items.add("Item 3");
    	items.add("Item 4");
    	items.add("Item 5");

    	checkSelected = new boolean[items.size()];
    	for (int i = 0; i < checkSelected.length; i++) {
    		checkSelected[i] = false;
    	}

    	final TextView tv = (TextView) findViewById(R.DropDownList.SelectBox);
    	tv.setOnClickListener(new OnClickListener() {

    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			if(!expanded){
    			String selected = "";
    			int flag = 0;
    			for (int i = 0; i < items.size(); i++) {
    				if (checkSelected[i] == true) {
    					 selected += items.get(i);
    					 selected += ", ";
    					flag = 1;
    				}
    			}
    			if(flag==1)
    			tv.setText(selected);
    			expanded =true;
    			}
    			else{
    				tv.setText(DropDownListAdapter.getSelected());
    				expanded = false;
    			}
    				
    			System.out.println(DropDownListAdapter.getSelected());
    		}
    	});
        
        Button createButton = (Button)findViewById(R.DropDownList.create);
        createButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initiatePopUp(items,tv);
			}
		});
    }
    
    private void initiatePopUp(ArrayList<String> items, TextView tv){
    	LayoutInflater inflater = (LayoutInflater)DropDownListDemo.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.pop_up_window, (ViewGroup)findViewById(R.id.PopUpView));
    	RelativeLayout layout1 = (RelativeLayout)findViewById(R.id.relativeLayout1);
    	pw = new PopupWindow(layout, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
    	pw.setBackgroundDrawable(new BitmapDrawable());
    	pw.setTouchable(true);
    	pw.setOutsideTouchable(true);
    	pw.setHeight(LayoutParams.WRAP_CONTENT);
    	pw.setTouchInterceptor(new OnTouchListener() {
    		
    		public boolean onTouch(View v, MotionEvent event) {
    			// TODO Auto-generated method stub
    			if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
    				pw.dismiss();
        			return true;    				
    			}
    			return false;
    		}
    	});
    	
    	pw.setContentView(layout);
    	
    	pw.showAsDropDown(layout1);
    	
    	final ListView list = (ListView) layout.findViewById(R.DropDownList.dropDownList);
    	DropDownListAdapter adapter = new DropDownListAdapter(this, items, tv);
    	list.setAdapter(adapter);
    }
}