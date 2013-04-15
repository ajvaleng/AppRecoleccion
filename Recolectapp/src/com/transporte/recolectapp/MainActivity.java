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
        
        Thread logoTimer = new Thread(){
			@Override
			public void run() {
				
				//CAMBIAR ESTO POR ENVIAR DATOS
				// TODO Auto-generated method stub
				try {
					sleep(3000);
					//Intent i = new Intent("android.intent.action.MAIN");
					//startActivity(i);
					startActivity(new Intent("com.transporte.CONFIGURACION"));
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				finally{
					//finish();
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
