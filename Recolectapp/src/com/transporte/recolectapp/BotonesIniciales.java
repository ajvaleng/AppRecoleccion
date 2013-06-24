package com.transporte.recolectapp;

import java.io.Serializable;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BotonesIniciales extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_botones_iniciales);
		Button nuevarecoleccion = (Button) findViewById(R.id.btNuevaRecoleccion);
		findViewById(R.id.tbDatosEnviados).setVisibility(View.INVISIBLE);
        
		MySQLiteHelper db = new MySQLiteHelper(BotonesIniciales.this);
		int numero = 0;
		boolean falla = false;
		try{
			Intent a = getIntent();
			Serializable b = a.getSerializableExtra("fallo");
			falla = Boolean.parseBoolean(b.toString());
			Serializable c = a.getSerializableExtra("recolecciones");
			if(c!=null)
				numero = Integer.parseInt(c.toString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(falla)
		{
			((TextView)findViewById(R.id.tbDatosEnviados)).setText("Los datos no fueron enviados\nProblema de servidor ");
			findViewById(R.id.tbDatosEnviados).setVisibility(View.VISIBLE);
		}
		else if(numero!=0)
		{
			((TextView)findViewById(R.id.tbDatosEnviados)).setText("Fueron enviadas "+ numero+ " recolecciones");
			findViewById(R.id.tbDatosEnviados).setVisibility(View.VISIBLE);
		}
        nuevarecoleccion.setOnClickListener(new View.OnClickListener() {
			
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					startActivity(new Intent("com.transporte.CONFIGURACION"));
											
				}
				
		});
        
        Button actualizar = (Button) findViewById(R.id.btActualizar);
		actualizar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent("com.transporte.BAJARDATOS"));
			}
		});
        
        Button enviarDatos = (Button) findViewById(R.id.btEnviarDatos);
        enviarDatos.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MySQLiteHelper db = new MySQLiteHelper(BotonesIniciales.this);
				if(db.quedanDatosPorEnviar())
					startActivity(new Intent("com.transporte.ENVIANDODATOS"));
				else
					findViewById(R.id.textView1).setVisibility(View.VISIBLE);
			}
		});
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.botones_iniciales, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
	}
}
