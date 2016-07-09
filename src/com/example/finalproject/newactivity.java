package com.example.finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.example.finalproject.CivilWar.Locations;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class newactivity extends Activity {	
int markerdestroy = 0 ;
	public static Activity inew = null;
	boolean alreadytouched = false;
	private HashMap<Marker,Locations> mMarkersHashMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inew= this;
        mMarkersHashMap = new HashMap<Marker,Locations>();
		setContentView(R.layout.newactivity);
		
		Intent intent = this.getIntent();
		int numLocInt = intent.getIntExtra("Number_of_Locations", 5);
		String myUrl = intent.getStringExtra("infoWindowUrl");
		if(myUrl!= null){
		Uri myUriUrl = Uri.parse(myUrl);
        Intent intent2 = new Intent(Intent.ACTION_VIEW, myUriUrl);

        startActivity(intent2);
		}

		final LatLng cameracenter= new LatLng(38.59,-77.73);
		
		final TextView markercounter = (TextView)findViewById(R.id.textView1);
		 final GoogleMap mMap;
		 
		 Button surrender = (Button) findViewById(R.id.button1);
			surrender.setOnClickListener(new OnClickListener(){
				Handler mHandler1 = new Handler();
	        	private Runnable mLaunchTask1 = new Runnable(){
	        		@Override
					public void run(){
		        		Intent intent1=new Intent();
		        		intent1.setClass(newactivity.this,StartScreen.class);
		        		intent1.putExtra("Which_Game", "1");
		           		startActivity(intent1);
		           	
	        		}
	        		
	        	};
	        	@Override
		    	 public void onClick(View v) {
	        		Toast.makeText(getApplicationContext(),"Player A wins the game!", Toast.LENGTH_SHORT).show();
	        		mHandler1.postDelayed(mLaunchTask1, 2000);
	        	}  	
	        });

	    mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	            .getMap();
	    CivilWar virCiv = new CivilWar();
	    ArrayList<CivilWar.Locations> myVirLoc = virCiv.setVirginiaLocations();
	    final int size = createMarkers(numLocInt, myVirLoc, mMap);
	    
	    mMap.getUiSettings().setAllGesturesEnabled(false);
	    mMap.getUiSettings().setZoomControlsEnabled(false);
	    mMap.getUiSettings().setScrollGesturesEnabled(false);
	    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameracenter, (float)6.9));



	        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
            {	
	        	Handler mHandler = new Handler();
	        	private Runnable mLaunchTask = new Runnable(){
	        		@Override
					public void run(){
		        		Intent intent1=new Intent();
		        		intent1.setClass(newactivity.this,MainActivity.class);

		           		startActivity(intent1);
	        		}
	        	};
	        	Handler mHandler1 = new Handler();
	        	private Runnable mLaunchTask1 = new Runnable(){
	        		@Override
					public void run(){
		        		Intent intent1=new Intent();
		        		intent1.setClass(newactivity.this,StartScreen.class);
		        		intent1.putExtra("Which_Game", "1");
		           		startActivity(intent1);

		           		

		           	

	        		}
	        		
	        	};
                @Override
                public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker)
                {
                	if(!alreadytouched)
                	{
           
                	
                	if(marker.getAlpha() == 0)
                	{
                 		alreadytouched = true;
                    marker.setVisible(true);
                    marker.setAlpha(1);
                    markerdestroy++;
                    markercounter.setText(markerdestroy +"/"+size+ " destroyed");
                    Toast.makeText(getApplicationContext(), "This location was destroyed: " + marker.getTitle()+ "\nTotal locations destroyed: "+ markerdestroy, Toast.LENGTH_SHORT).show();
                    marker.showInfoWindow();
                    
                    if (markerdestroy == size )
                    	{Toast.makeText(getApplicationContext(),"You win \nAll locations destroyed!", Toast.LENGTH_SHORT).show();
                    markercounter.setText(markerdestroy +"/"+size+ " destroyed  You won!");
                    mHandler1.postDelayed(mLaunchTask1, 2000);}
                    else                  
                    	mHandler.postDelayed(mLaunchTask, 2000);
                	}         	
                	else
                		Toast.makeText(getApplicationContext(),"You already destroyed this location, try again." , Toast.LENGTH_SHORT).show();
                        marker.showInfoWindow();
                	}

                    return true;
                }
            });
	        
	        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
	        	Handler mHandler = new Handler();
	        	private Runnable mLaunchTask = new Runnable(){
	        		@Override
					public void run(){
		        		Intent intent1=new Intent();
		        		intent1.setClass(newactivity.this,MainActivity.class);

		           		startActivity(intent1);
	        		}
	        	};
	        	@Override
	        	public void onMapClick (LatLng arg0){
	        		if(!alreadytouched)
	        		{
	        		alreadytouched = true;
	        		Toast.makeText(getApplicationContext(),"You missed", Toast.LENGTH_SHORT).show();
	        		mHandler.postDelayed(mLaunchTask, 2000);
	        		}

	        	}
	        });
	}
    public class MarkerInfoWindowAdapter implements InfoWindowAdapter{

        public MarkerInfoWindowAdapter()

        {

        }

        @Override

        public View getInfoWindow(Marker marker)

        {

            return null;

        }



        @Override

        public View getInfoContents(Marker marker)

        {

            View v  = getLayoutInflater().inflate(R.layout.info, null);

            Locations myMarker = mMarkersHashMap.get(marker);

            TextView markerName = (TextView)v.findViewById(R.id.textView1);

            markerName.setText(myMarker.getname());

            return v;

        }

    }
	public int createMarkers(final int c, final ArrayList<Locations> state, GoogleMap mMap){
    	Random randomGeneratorRandom = new Random();
        mMarkersHashMap = new HashMap<Marker,Locations>();
    	while (state.size()>c){
    		int randomInt=randomGeneratorRandom.nextInt(state.size()-1);
    		state.remove(randomInt);   
    	}
        for (Locations Loc : state)

        {

        

            // Create user marker with custom icon and other options

        MarkerOptions markerOption = new MarkerOptions().position(Loc.getLatLng()).alpha(0).title(Loc.getname());

        Marker currentMarker = mMap.addMarker(markerOption);

        mMarkersHashMap.put(currentMarker, Loc);

        mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());  

            mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

    

 
    public void onInfoWindowClick(Marker arg0) {

    // TODO Auto-generated method stub

   // Toast.makeText(getApplicationContext(), "good", Toast.LENGTH_LONG).show();

    	if(!alreadytouched){
    for (int i=0;i<c;i++){

    if (arg0.getPosition().longitude==state.get(i).getLatLng().longitude){

        Intent intent = new Intent(Intent.ACTION_VIEW, state.get(i).getUri_marker());

        startActivity(intent);

    }

    }
    	}
    	else
    	{
    		Toast.makeText(getApplicationContext(), "Got to the else", Toast.LENGTH_SHORT).show();
    		Log.d("Entered the Else", "alreadyTouched = true");
    	    for (int i=0;i<c;i++){

    	        if (arg0.getPosition().longitude==state.get(i).getLatLng().longitude){

    	            Uri myUrl = state.get(i).getUri_marker();
	        		Intent intent1=new Intent();
	        		intent1.setClass(newactivity.this,MainActivity.class);
	        		intent1.putExtra("infoWindowUrl", myUrl.toString());
	           		startActivity(intent1);
    	            

    	        }

    	        }
    		
    	}

    }

    

    });

        }
    	return state.size();
    }
	@Override
	public void finish (){
		super.finish();
		inew=null;
		}
	@Override
	protected void onRestart(){
		alreadytouched = false;
		super.onStart();
	}

}
