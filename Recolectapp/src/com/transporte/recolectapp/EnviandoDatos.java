package com.transporte.recolectapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class EnviandoDatos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enviando_datos);
		
		
		Thread logoTimer = new Thread(){
			@Override
			public void run() {
				

				// TODO Auto-generated method stub
				MySQLiteHelper db = new MySQLiteHelper(EnviandoDatos.this);
			
				db.enviarDatos();
				startActivity(new Intent("com.transporte.BOTONESINICIALES"));

				finish();

			}
		};
		logoTimer.start();
	}

	@Override
	public void onBackPressed() {
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enviando_datos, menu);
		return true;
	}

}
