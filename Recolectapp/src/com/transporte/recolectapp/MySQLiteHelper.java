package com.transporte.recolectapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{
	
		static String patente;
		static String recorrido;
		static int puerta;
		static String nombre;
	
		public static final String TABLE_INFO = "datos";
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_PUERTA = "puerta";
		public static final String COLUMN_RECORRIDO = "recorrido";
		public static final String COLUMN_NOMBRE = "nombre";
		public static final String COLUMN_PATENTE = "patente";
		public static final String COLUMN_LLEGADA = "horallegada";
		public static final String COLUMN_SALIDA = "horasalida";
		public static final String COLUMN_CANTIDAD_SUBE = "cantidadsubida";
		public static final String COLUMN_CANTIDAD_BAJA = "cantidadbajada";
		public static final String COLUMN_LATITUD = "latitud";
		public static final String COLUMN_LONGITUD = "longitud";
		
		private static final String DATABASE_NAME = "recoleccion.db";
		private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_INFO + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_PUERTA
	      + " integer not null, " + COLUMN_RECORRIDO
	      + " text not null, " + COLUMN_NOMBRE
	      + " text not null, " + COLUMN_PATENTE
	      + " text not null, " + COLUMN_LLEGADA
	      + " text not null, " + COLUMN_SALIDA
	      + " text not null, " + COLUMN_CANTIDAD_SUBE
	      + " integer not null, " + COLUMN_CANTIDAD_BAJA
	      + " integer not null, " + COLUMN_LATITUD
	      + " real not null, " + COLUMN_LONGITUD
	      + " real not null" + ");";

	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }
	  
	  public void definirAtributosComunes(String nombre, String patente, int puerta, String recorrido){
		  this.nombre = nombre;
		  this.puerta = puerta;
		  this.recorrido = recorrido;
		  this.patente = patente;
		  
	  }
	  
	  public void addData(String horallegada, String horasalida, int cantidadsube, int cantidadbaja,
			  double longitud, double latitud) {
		    SQLiteDatabase db = this.getWritableDatabase();
		 
		    ContentValues values = new ContentValues();
		    values.put(COLUMN_NOMBRE, nombre);
		    values.put(COLUMN_CANTIDAD_BAJA, cantidadbaja);
		    values.put(COLUMN_CANTIDAD_SUBE, cantidadsube);
		    values.put(COLUMN_LATITUD, latitud);
		    values.put(COLUMN_LLEGADA, horallegada);
		    values.put(COLUMN_LONGITUD, longitud);
		    values.put(COLUMN_PATENTE,patente);
		    values.put(COLUMN_PUERTA, puerta);
		    values.put(COLUMN_RECORRIDO, recorrido);
		    values.put(COLUMN_SALIDA, horasalida);
		    
		    // Inserting Row
		    db.insert(TABLE_INFO, null, values);
		    db.close(); // Closing database connection
		}

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
	    onCreate(db);
	  }
	  
	  public void enviarDatos()
	  {
		  
		    // Select All Query
		    String selectQuery = "SELECT  * FROM " + TABLE_INFO;
		 
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		 
		    // looping through all rows and adding to list
			
		    List<JSONObject> lista = new ArrayList<JSONObject>();
		    if (cursor.moveToFirst()) {
		        do {
		    		String puerta  = cursor.getString(1);
		    		String recorrido = cursor.getString(2);
		    		String nombre = cursor.getString(3);
		    		String patente = cursor.getString(4);
		    		String Llegada = cursor.getString(5);
		    		String Salida = cursor.getString(6);
		    		String cantidadsube = cursor.getString(7);
		    		String cantidadbaja = cursor.getString(8);
		    		String latitud = cursor.getString(9);
		    		String longitud = cursor.getString(10);
		    		//String periodo = Integer.parseInt(Llegada.substring(Llegada.indexOf(" ")+1,Llegada.indexOf(" ")+2)) + 
		    		//		(Integer.parseInt(Llegada.substring(Llegada.indexOf(" ")+4,Llegada.indexOf(" ")+)))"";
		    		//String horaEnString = Llegada.substring(Llegada.lastIndexOf(" ")-8,Llegada.lastIndexOf(" ")-6);
		    		//if(horaEnString.indexOf(" ")!=-1)
		    		//	horaEnString = horaEnString.substring(1,2);
		    		
		    		//int hora = Integer.parseInt(horaEnString);
		    		//int minuto = Integer.parseInt(Llegada.substring(Llegada.lastIndexOf(" ")-4,Llegada.lastIndexOf(" ")-3));
		    		//String periodo = ((int)(hora/2)+(int)(minuto/30)) + "";
		    		String periodo = "null";
		    		
		            // Adding contact to list
		            
		            try {
						
						enviarPorJson(new JSONObject("{\"lat\":\""+ latitud + "\",\"long\":\"" + longitud + "\",\"llegada_paradero\":\""+
								 Llegada +"\",\"salida_paradero\":\"" + Salida + "\",\"nombre\":\"" + nombre + "\",\"patente\":\""+ patente
								 + "\",\"periodo\":\"" + periodo + "\",\"presonas_suben\":\"" + cantidadsube + "\",\"personas_bajan\":\"" +
								 cantidadbaja + "\",\"puerta\":\"" + puerta + "\",\"recorrido\":\"" + recorrido + "\"}"));
						db.delete(this.TABLE_INFO, this.COLUMN_ID + "=" + cursor.getString(0), null);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		          
		         
		        } while (cursor.moveToNext());
		    }

	  }
	  
	  public void enviarPorJson(JSONObject json)
	  {
		  	//instantiates httpclient to make request
		    HttpClient httpclient = new DefaultHttpClient();

		    //url with the post data
		    HttpPost httpost = new HttpPost("http://recolectserver.herokuapp.com/recoleccions");
		    boolean correcto = false;
		    while (!correcto)
		    {
		    	correcto = true;
			    //passes the results to a string builder/entity
			    StringEntity se;
				try {
					se = new StringEntity(json.toString());
					//sets the post request as the resulting string
				    httpost.setEntity(se);
				    //se.setContentEncoding((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				    //sets a request header so the page receving the request
				    //will know what to do with it
				    httpost.setHeader("Accept", "application/json");
				    
				    httpost.setHeader("Content-type", "application/json");
		
				    //Handles what is returned from the page 
	
				    
				    try {
						HttpResponse response = httpclient.execute(httpost);
						String respuesta = EntityUtils.toString(response.getEntity());
						if(respuesta == "" && respuesta == "")
							correcto = false;
						//response.getParams()
						//SIESSQUE RESPONSE ESTA BUENO!!!!
						//if(response.getHeader){
							//db.execSQL("DELETE FROM "+this.DATABASE_NAME+" WHERE usuario='usu1' ");
	
							//db.delete(this.TABLE_INFO, this.COLUMN_LLEGADA+"="+json.getString("llegada_paradero"), null);
				    	//}
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						correcto = false;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						correcto = false;
					}
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						correcto = false;
					}
		  		}
			
	  }
	  
	  public boolean quedanDatosPorEnviar()
	  {
		  String selectQuery = "SELECT  * FROM " + TABLE_INFO;
			 
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		 
		    // looping through all rows and adding to list
			
		    if (cursor.moveToFirst()) {
		    	return true;
		    }
		    return false;
	  }
	  
	  public void borrarDatosYaEnviados(){
		  SQLiteDatabase db = this.getWritableDatabase();
		  db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
	  }
}

