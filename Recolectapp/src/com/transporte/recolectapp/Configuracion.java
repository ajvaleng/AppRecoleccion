package com.transporte.recolectapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Configuracion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracion);
		Spinner ddLineas = (Spinner) findViewById(R.id.ddLineas);
		
		MySQLiteHelper db = new MySQLiteHelper(Configuracion.this);
		ArrayList<String> lineas = (ArrayList<String>) db.getLineas();
		ArrayAdapter <String> adapter =
				  new ArrayAdapter <String> (Configuracion.this, android.R.layout.simple_spinner_item );
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for(int i = 0;i<lineas.size();i++)
			adapter.add(lineas.get(i));
		ddLineas.setAdapter(adapter);
       
		
		
		Button entrar = (Button) findViewById(R.id.btEntrar);
        
        entrar.setOnClickListener(new View.OnClickListener() {
			
        final MySQLiteHelper db = new MySQLiteHelper(Configuracion.this);	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText tbnombre = (EditText) findViewById(R.id.tbNombre);
				EditText tbapellido = (EditText) findViewById(R.id.tbApellido);
				EditText tbpatente = (EditText) findViewById(R.id.tbPatente);
				//EditText tbrecorrido = (EditText) findViewById(R.id.tbRecorrido);
				EditText tbPuerta = (EditText) findViewById(R.id.tbNPuerta);
				EditText tbParaderoInicial = (EditText) findViewById(R.id.tbParaderoInicial);
				Spinner ddLineas = (Spinner) findViewById(R.id.ddLineas);
				String nombre = tbnombre.getText().toString() + " " + tbapellido.getText().toString();
				String patente = tbpatente.getText().toString();
				//String recorrido = tbrecorrido.getText().toString();
				String recorrido = (String) ddLineas.getSelectedItem();
				String paraderoInicial = tbParaderoInicial.getText().toString();
				try{
					int puerta = Integer.parseInt(tbPuerta.getText().toString());
					boolean correcto = db.comprobarCruce(recorrido, paraderoInicial);
					
					if(nombre != "" && patente != "" && recorrido != "" && paraderoInicial != "" && correcto)
					{
						Random a = new Random();
						db.definirAtributosComunes(nombre, patente, puerta, recorrido,a.nextFloat());
						db.addData(Calendar.getInstance().getTime().toLocaleString(), paraderoInicial ,0, 0, 0, 0);
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
