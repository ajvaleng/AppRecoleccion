package com.transporte.recolectapp;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Configuracion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracion);
		
		
        Button entrar = (Button) findViewById(R.id.btEntrar);
        
        entrar.setOnClickListener(new View.OnClickListener() {
			
        final MySQLiteHelper db = new MySQLiteHelper(Configuracion.this);	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText tbnombre = (EditText) findViewById(R.id.tbNombre);
				EditText tbapellido = (EditText) findViewById(R.id.tbApellido);
				EditText tbpatente = (EditText) findViewById(R.id.tbPatente);
				EditText tbrecorrido = (EditText) findViewById(R.id.tbRecorrido);
				EditText tbPuerta = (EditText) findViewById(R.id.tbNPuerta);
				String nombre = tbnombre.getText().toString() + " " + tbapellido.getText().toString();
				String patente = tbpatente.getText().toString();
				String recorrido = tbrecorrido.getText().toString();
				try{
					int puerta = Integer.parseInt(tbPuerta.getText().toString());
					if(nombre != "" && patente != "" && recorrido != "")
					{
						Random a = new Random();
						db.definirAtributosComunes(nombre, patente, puerta, recorrido,a.nextFloat());
						startActivity(new Intent("com.transporte.ENMOVIMIENTO"));
					}
					else
						((TextView) findViewById(R.id.textView1)).setVisibility(View.VISIBLE);
											
				}
				catch(Exception e)
				{
					((TextView) findViewById(R.id.textView1)).setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configuracion, menu);
		return true;
	}

}
