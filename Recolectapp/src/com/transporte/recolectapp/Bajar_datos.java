package com.transporte.recolectapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Bajar_datos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bajar_datos);
		
				
		Thread bajardatos = new Thread(){
			@Override
			public void run() {
				

				// TODO Auto-generated method stub
				MySQLiteHelper db = new MySQLiteHelper(Bajar_datos.this);
				ProgressBar progress = (ProgressBar) findViewById(R.id.pbBajando);
				db.BajarParaderos(progress,Bajar_datos.this);
				startActivity(new Intent("com.transporte.BOTONESINICIALES"));
				
				
				
				finish();

			}
		};
		bajardatos.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bajar_datos, menu);
		return true;
	}
	@Override
	public void onBackPressed() {
	} 

}
