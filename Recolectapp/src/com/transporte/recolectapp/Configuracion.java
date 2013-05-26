package com.transporte.recolectapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

	boolean activado = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        /** Passing criteria and select only enabled provider */
        LocationManager locationManager = (LocationManager) Configuracion.this.getSystemService(Configuracion.this.LOCATION_SERVICE);
        
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        /** calls the Location Listner */
        
        
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
        		public void onLocationChanged(Location location) {
        	      // Called when a new location is found by the network location provider.
        	      activado = true;
        	    }

              public void onStatusChanged(String provider, int status, Bundle extras) {}

              public void onProviderEnabled(String provider) {}

              public void onProviderDisabled(String provider) {}
            });
        locationManager.requestLocationUpdates(locationManager.PASSIVE_PROVIDER, 1000, 0, new LocationListener() {
    		public void onLocationChanged(Location location) {
    	      // Called when a new location is found by the network location provider.
    	      activado = true;
    	    }

          public void onStatusChanged(String provider, int status, Bundle extras) {}

          public void onProviderEnabled(String provider) {}

          public void onProviderDisabled(String provider) {}
        });
        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 1000, 0, new LocationListener() {
    		public void onLocationChanged(Location location) {
    	      // Called when a new location is found by the network location provider.
    	      activado = true;
    	    }

          public void onStatusChanged(String provider, int status, Bundle extras) {}

          public void onProviderEnabled(String provider) {}

          public void onProviderDisabled(String provider) {}
        });
		
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
					
					if(nombre != "" && patente != "" && recorrido != "" && paraderoInicial != "" && correcto && activado)
					{
						Random a = new Random();
						db.definirAtributosComunes(nombre, patente, puerta, recorrido,a.nextFloat());
						db.addData(Calendar.getInstance().getTime().toLocaleString(), paraderoInicial ,0, 0, 0, 0);
						

						Criteria criteria = new Criteria();
				        criteria.setAccuracy(Criteria.ACCURACY_FINE);
				        criteria.setAltitudeRequired(false);
				        criteria.setBearingRequired(false);
				        criteria.setCostAllowed(true);
				        criteria.setPowerRequirement(Criteria.POWER_LOW);

				        /** Passing criteria and select only enabled provider */
				        LocationManager locationManager = (LocationManager) Configuracion.this.getSystemService(Configuracion.this.LOCATION_SERVICE);
				        
				        String provider = locationManager.getBestProvider(criteria, true);
				        String provider2 = locationManager.NETWORK_PROVIDER;
				        String provider3 = locationManager.PASSIVE_PROVIDER;
				        Location location = locationManager.getLastKnownLocation(provider);
				        if(location == null)
				        {
				        	location = locationManager.getLastKnownLocation(provider2);
				        	if(location!=null)
				        		activado = true;
				        }
				        if(location == null)
				        {
				        	location = locationManager.getLastKnownLocation(provider3);
				        	if(location!=null)
				        		activado = true;
				        }
						
						
						
						
						startActivity(new Intent("com.transporte.ENMOVIMIENTO"));
					}
					else if(activado)
					{
						((TextView) findViewById(R.id.textView1)).setText("Datos ingresados incorrectos..");
						((TextView) findViewById(R.id.textView1)).setVisibility(View.VISIBLE);
					}
					else
					{
						((TextView) findViewById(R.id.textView1)).setText("GPS activandose, espere y pruebe de nuevo.");
						((TextView) findViewById(R.id.textView1)).setVisibility(View.VISIBLE);
						Criteria criteria = new Criteria();
				        criteria.setAccuracy(Criteria.ACCURACY_FINE);
				        criteria.setAltitudeRequired(false);
				        criteria.setBearingRequired(false);
				        criteria.setCostAllowed(true);
				        criteria.setPowerRequirement(Criteria.POWER_LOW);

				        /** Passing criteria and select only enabled provider */
				        LocationManager locationManager = (LocationManager) Configuracion.this.getSystemService(Configuracion.this.LOCATION_SERVICE);
				        
				        String provider = locationManager.getBestProvider(criteria, true);
				        String provider2 = locationManager.NETWORK_PROVIDER;
				        String provider3 = locationManager.PASSIVE_PROVIDER;
				        Location location = locationManager.getLastKnownLocation(provider);
				        if(location == null)
				        {
				        	location = locationManager.getLastKnownLocation(provider2);
				        	if(location!=null)
				        		activado = true;
				        }
				        if(location == null)
				        {
				        	location = locationManager.getLastKnownLocation(provider3);
				        	if(location!=null)
				        		activado = true;
				        }
						
					}
											
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
