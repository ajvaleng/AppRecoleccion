package com.transporte.recolectapp;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class EnMovimiento extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_en_movimiento);
		
		Button llegadaParadero = (Button) findViewById(R.id.btLlegadaParadero);
        llegadaParadero.setOnClickListener(new View.OnClickListener() {
			
 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent paradero = new Intent("com.transporte.PARADERO");
				
				startActivity(paradero);
			}
		});
        
        Button finRecoleccion = (Button) findViewById(R.id.btFinalizarRecoleccion);
        finRecoleccion.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent EnviarDatos = new Intent("com.transporte.BOTONESINICIALES");
				
				startActivity(EnviarDatos);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.en_movimiento, menu);
		return true;
	}

}
