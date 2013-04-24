package com.transporte.recolectapp;

import java.sql.Date;
import java.util.Calendar;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Paradero extends Activity {

	String tiempoLlegada;
	String tiempoSalida;
	Location ubicacion;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paradero);
		LocationManager locationManager = (LocationManager) Paradero.this.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        //Acordarse de descomentar
        String bestProvider = locationManager.getBestProvider(criteria, true);
		ubicacion = locationManager.getLastKnownLocation(bestProvider);
		java.util.Date dt = Calendar.getInstance().getTime();
		tiempoLlegada = "/date("+Date.UTC(dt.getYear(), dt.getMonth(), dt.getDay(), dt.getHours(),dt.getMinutes(), dt.getSeconds())+")/";


		
		Button subida = (Button) findViewById(R.id.btSuben);
        subida.setOnClickListener(new View.OnClickListener() {
			
        	//falta configurar click para terminar recoleccion
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText subida = (EditText) findViewById(R.id.tbSuben);
				int cantidad = (int)Integer.parseInt(subida.getText().toString());
				subida.setText((cantidad+1) + "");
			}
		});
        Button corregirSubida = (Button) findViewById(R.id.btSubenCorregir);
        corregirSubida.setOnClickListener(new View.OnClickListener() {
			
        	//falta configurar click para terminar recoleccion
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText subida = (EditText) findViewById(R.id.tbSuben);
				int cantidad = (int)Integer.parseInt(subida.getText().toString());
				if(cantidad>0)
					subida.setText((cantidad-1) + "");
			}
		});
        
        Button bajada = (Button) findViewById(R.id.btBajan);
        bajada.setOnClickListener(new View.OnClickListener() {
			
        	//falta configurar click para terminar recoleccion
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText bajada = (EditText) findViewById(R.id.tbBajan);
				int cantidad = (int)Integer.parseInt(bajada.getText().toString());
				bajada.setText((cantidad+1) + "");
			}
		});
        Button corregirBajada = (Button) findViewById(R.id.btBajanCorregir);
        corregirBajada.setOnClickListener(new View.OnClickListener() {
			
        	//falta configurar click para terminar recoleccion
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText bajada = (EditText) findViewById(R.id.tbBajan);
				int cantidad = (int)Integer.parseInt(bajada.getText().toString());
				if(cantidad>0)
					bajada.setText((cantidad-1) + "");
			}
		});
        
        
        Button salirParadero = (Button) findViewById(R.id.btSalidaParadero);
        salirParadero.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				java.util.Date dt = Calendar.getInstance().getTime();
				Paradero.this.tiempoSalida = "/date("+Date.UTC(dt.getYear(), dt.getMonth(), dt.getDay(), dt.getHours(),dt.getMinutes(), dt.getSeconds())+")/";

				int cantidadsube = Integer.parseInt(((EditText) findViewById(R.id.tbSuben)).getText().toString());
				int cantidadbaja = Integer.parseInt(((EditText) findViewById(R.id.tbBajan)).getText().toString());
				//ACA SE GUARDA LA INFORMACION EN LA BASE DE DATOS!!!
				MySQLiteHelper db = new MySQLiteHelper(Paradero.this);
				//ACORDARSE DE BORRAR
				db.addData(Paradero.this.tiempoLlegada, Paradero.this.tiempoSalida,
						cantidadsube, cantidadbaja, Paradero.this.ubicacion.getLongitude(), 
						Paradero.this.ubicacion.getLatitude());
				
				//db.addData(Paradero.this.tiempoLlegada, Paradero.this.tiempoSalida,	cantidadsube, 
				//			cantidadbaja, 4, 5);
				
				startActivity(new Intent("com.transporte.ENMOVIMIENTO"));
			}
		});
	}
	
	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.paradero, menu);
		return true;
	}

}
