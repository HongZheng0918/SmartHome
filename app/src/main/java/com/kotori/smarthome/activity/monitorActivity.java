package com.kotori.smarthome.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kotori.smarthome.R;
import com.kotori.smarthome.view.MySurfaceView;

public class monitorActivity extends Activity {
	
	private MySurfaceView msv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		Log.e("you", "monitorActivity init" + 36);

		setContentView(R.layout.activity_monitor);
		msv = (MySurfaceView) findViewById(R.id.view_surf);

	}



}
