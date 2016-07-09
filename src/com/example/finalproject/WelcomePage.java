package com.example.finalproject;


import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;




public class WelcomePage extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomepage);
	    getWindow().setFormat(PixelFormat.RGBA_8888);
	    Animation fadeIn = new AlphaAnimation(0, 1);
	    fadeIn.setInterpolator(new AccelerateInterpolator()); //add this
	    fadeIn.setDuration(3000);

	    Button b=(Button)findViewById(R.id.button1);


	    AnimationSet animation = new AnimationSet(false); //change to false
	    animation.addAnimation(fadeIn);
	    b.setAnimation(animation);
	    animation.start();

		 b.setOnClickListener(new OnClickListener(){
	        	@Override
		    	 public void onClick(View v) {
	        		Intent intent1=new Intent();
	        		intent1.setClass(WelcomePage.this,StartScreen.class);
	        		intent1.putExtra("Which_Game", "0");
	           		startActivity(intent1);
	           		
	            	}
	        	}  	
	        );

	}
  
     
}