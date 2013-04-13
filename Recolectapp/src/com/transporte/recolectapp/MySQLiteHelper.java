package com.transporte.recolectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{
	
		private String patente;
		private String recorrido;
		private int puerta;
		private String nombre;
	
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
	      + " text not null, " + COLUMN_PUERTA
	      + " integer not null, " + COLUMN_NOMBRE
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
	  
	  public void borrarDatosYaEnviados(){
		  SQLiteDatabase db = this.getWritableDatabase();
		  db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
	  }
}

