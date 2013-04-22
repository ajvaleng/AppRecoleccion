package com.transporte.recolectapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //esta actividad es solo un splash
        
        Thread logoTimer = new Thread(){
			@Override
			public void run() {
				
				try {
					sleep(3000);
					//Intent i = new Intent("android.intent.action.MAIN");
					//startActivity(i);
					MySQLiteHelper db = new MySQLiteHelper(MainActivity.this);
					//revisa si quedan datos por enviar primero.
					if(db.quedanDatosPorEnviar())
						startActivity(new Intent("com.transporte.EnviandoDatos"));
					else
						startActivity(new Intent("com.transporte.CONFIGURACION"));
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				finally{
					finish();
				}
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
