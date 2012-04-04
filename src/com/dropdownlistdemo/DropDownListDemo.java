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
	private boolean expanded; 		//to  store information whether the selected values are displayed completely or in shortened representatn
	public static boolean[] checkSelected;	// store select/unselect information about the values in the list
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initialize();
    }
    
    /*
     * Function to set up initial settings: Creating the data source for drop-down list, initialising the checkselected[], set the drop-down list
     * */
    private void initialize(){
    	//data source for drop-down list
        final ArrayList<String> items = new ArrayList<String>();
    	items.add("Item 1");
    	items.add("Item 2");
    	items.add("Item 3");
    	items.add("Item 4");
    	items.add("Item 5");

    	checkSelected = new boolean[items.size()];
    	//initialize all values of list to 'unselected' initially
    	for (int i = 0; i < checkSelected.length; i++) {
    		checkSelected[i] = false;
    	}

	/*SelectBox is the TextView where the selected values will be displayed in the form of "Item 1 & 'n' more".
    	 * When this selectBox is clicked it will display all the selected values 
    	 * and when clicked again it will display in shortened representation as before.
    	 * */
    	final TextView tv = (TextView) findViewById(R.DropDownList.SelectBox);
    	tv.setOnClickListener(new OnClickListener() {

    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			if(!expanded){
    				//display all selected values
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
    				//display shortened representation of selected values
    				tv.setText(DropDownListAdapter.getSelected());
    				expanded = false;
    			}
    		}
    	});
        
        //onClickListener to initiate the dropDown list
        Button createButton = (Button)findViewById(R.DropDownList.create);
        createButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initiatePopUp(items,tv);
			}
		});
    }
    
    /*
     * Function to set up the pop-up window which acts as drop-down list
     * */
    private void initiatePopUp(ArrayList<String> items, TextView tv){
    	LayoutInflater inflater = (LayoutInflater)DropDownListDemo.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	
    	//get the pop-up window i.e.  drop-down layout
    	LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.pop_up_window, (ViewGroup)findViewById(R.id.PopUpView));
    	
    	//get the view to which drop-down layout is to be anchored
    	RelativeLayout layout1 = (RelativeLayout)findViewById(R.id.relativeLayout1);
    	pw = new PopupWindow(layout, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
    	
    	//Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
    	pw.setBackgroundDrawable(new BitmapDrawable());
    	pw.setTouchable(true);
    	
    	//let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
    	pw.setOutsideTouchable(true);
    	pw.setHeight(LayoutParams.WRAP_CONTENT);
    	
    	//dismiss the pop-up i.e. drop-down when touched anywhere outside the pop-up
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
    	
    	//provide the source layout for drop-down
    	pw.setContentView(layout);
    	
    	//anchor the drop-down to bottom-left corner of 'layout1'
    	pw.showAsDropDown(layout1);
    	
    	//populate the drop-down list
    	final ListView list = (ListView) layout.findViewById(R.DropDownList.dropDownList);
    	DropDownListAdapter adapter = new DropDownListAdapter(this, items, tv);
    	list.setAdapter(adapter);
    }
}