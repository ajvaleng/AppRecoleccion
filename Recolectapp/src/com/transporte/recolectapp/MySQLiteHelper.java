package com.transporte.recolectapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ProgressBar;

public class MySQLiteHelper extends SQLiteAssetHelper{
	
		static String patente;
		static String recorrido;
		static int puerta;
		static String nombre;
		static float idRecoleccion;
	
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
		public static final String COLUMN_IDRECOLECCION = "idrecoleccion";
		
		public static final String COLUMN_PARADERO = "paradero";
		
		public static final String TABLE_RECORRIDOS = "recorridos";
		public static final String TABLE_PARADEROS = "paraderos";
		
		private static final String DATABASE_NAME = "recoleccion.db";
		private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_INFO + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_PUERTA
	      + " integer not null, " + COLUMN_RECORRIDO
	      + " text not null, " + COLUMN_NOMBRE
	      + " text not null, " + COLUMN_PATENTE
	      + " real not null, " + COLUMN_LLEGADA
	      + " text not null, " + COLUMN_SALIDA
	      + " text not null, " + COLUMN_CANTIDAD_SUBE
	      + " integer not null, " + COLUMN_CANTIDAD_BAJA
	      + " integer not null, " + COLUMN_LATITUD
	      + " real not null, " + COLUMN_LONGITUD
	      + " real not null, " + COLUMN_IDRECOLECCION
	      + " text not null" +");";
	  
	  private static final String DATABASE2_CREATE = "create table "
			  + TABLE_RECORRIDOS +"(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN_RECORRIDO
		      + " text not null);";

	  private static final String DATABASE3_CREATE = "create table "
			  + TABLE_PARADEROS +"(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN_RECORRIDO
		      + " text not null, " + COLUMN_PARADERO
		      + " text not null);";

			  
	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  //@Override
	  //public void onCreate(SQLiteDatabase database) {
	  //  database.execSQL(DATABASE_CREATE);
	  //  database.execSQL(DATABASE2_CREATE);
	  //  database.execSQL(DATABASE3_CREATE);
	  //}
	  
	  
	  public void definirAtributosComunes(String nombre, String patente, int puerta, String recorrido,float id){
		  this.nombre = nombre;
		  this.puerta = puerta;
		  this.recorrido = recorrido;
		  this.patente = patente;
		  this.idRecoleccion = id;
		  
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
		    values.put(COLUMN_IDRECOLECCION, idRecoleccion);
		    
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
	    db.close();
	  }
	  
	  public Intent enviarDatos()
	  {
		  
		  
		    // Select All Query
		    String selectQuery = "SELECT  * FROM " + TABLE_INFO;
		    String textToJson = "{\"recoleccion\":[";
		    ArrayList<Integer> datosPorBorrar = new ArrayList<Integer>();
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);

		    // looping through all rows and adding to list
			String anterior = "";
			String paraderoInicial = "";
		    List<JSONObject> lista = new ArrayList<JSONObject>();
		    int recoleccionesEnviadas = 0;
		    boolean falla= false;
		    if (cursor.moveToFirst()) {
		        do {
		        	datosPorBorrar.add(cursor.getInt((0)));
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
		    		String actual = cursor.getString(11);
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
		            boolean prueba = !anterior.equals("");
		    		if(!actual.equals(anterior) && !anterior.equals(""))
		    		{
		    			boolean correcto = false;
		    			int intentos = 0;
		    			while(!correcto)
		    			{
			    			try {
								correcto = enviarPorJson(new JSONObject(textToJson+"],\"paradero_inicial\":\""+paraderoInicial+"\",\"linea\":\""+recorrido
										+"\",\"patente\":\""+patente +"\",\"nombre\":\""+nombre +"\",\"puerta\":\""+puerta+"\"}"));
								if(correcto)
								{
									borrarDatos(datosPorBorrar, db);
									anterior = "";
									paraderoInicial = "";
									recoleccionesEnviadas++;
								}
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    			intentos++;
			    			if(intentos>5)
			    			{
			    				falla = true;
			    				break;
			    			}
		    			}
		    			datosPorBorrar = new ArrayList<Integer>();
		    			textToJson = "{\"recoleccion\":[";
		    		}
		    		else if(!anterior.equals(""))
		    			textToJson += ",";
		    		
		    		if(falla)
		    			break;

					if(paraderoInicial.equals(""))
					{
						paraderoInicial = Salida;
					}
					else
					{
						if(!(!actual.equals(anterior) && !anterior.equals("")))
			    		{
						//enviarPorJson(new JSONObject("{\"latitude\":\""+ latitud + "\",\"longitude\":\"" + longitud + "\",\"llegada_paradero\":\""+
						//		 Llegada +"\",\"salida_paradero\":\"" + Salida + "\",\"nombre\":\"" + nombre + "\",\"patente\":\""+ patente
						//		 + "\",\"periodo\":\"" + periodo + "\",\"presonas_suben\":\"" + cantidadsube + "\",\"personas_bajan\":\"" +
						//		 cantidadbaja + "\",\"puerta\":\"" + puerta + "\",\"recorrido\":\"" + recorrido + "\"}"));
		            	textToJson +="{\"latitude\":\""+ latitud + "\",\"longitude\":\"" + longitud + "\",\"llegada_paradero\":\""+
										 Llegada +"\",\"salida_paradero\":\"" + Salida + "\",\"presonas_suben\":\"" + cantidadsube + "\",\"personas_bajan\":\"" +
										 cantidadbaja + "\"}";
			    		}
						anterior = actual;
//						db.delete(this.TABLE_INFO, this.COLUMN_ID + "=" + cursor.getString(0), null);
					}
					
		         if(cursor.isLast())
		         {
		        	 boolean correcto = false;
		        	 int intentos = 0;
		    			while(!correcto)
		    			{
			    			try {
			    				correcto = enviarPorJson(new JSONObject(textToJson+"],\"paradero_inicial\":\""+paraderoInicial+"\",\"linea\":\""+recorrido
										+"\",\"patente\":\""+patente +"\",\"nombre\":\""+nombre +"\",\"puerta\":\""+puerta+"\"}"));
								if(correcto)
								{
									borrarDatos(datosPorBorrar, db);
									anterior = "";
									paraderoInicial = "";
									recoleccionesEnviadas++;
								}
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    			intentos++;
			    			if(intentos>5)
			    			{
			    				falla = true;
			    				break;
			    			}
		    			}
		         }
		          
		         
		        } while (cursor.moveToNext());
		        
		        cursor.close();
		        
		    }
		    Intent pasar = new Intent("com.transporte.BOTONESINICIALES");
		    pasar.putExtra("fallo", (Serializable)falla);
		    pasar.putExtra("recolecciones", (Serializable)recoleccionesEnviadas);
		    db.close();
		    return pasar;
		    
	  }
	  
	  public boolean enviarPorJson(JSONObject json)
	  {
		  
		  
		  	//instantiates httpclient to make request
		    HttpClient httpclient = new DefaultHttpClient();

		    //url with the post data
		    HttpPost httpost = new HttpPost("http://recolectserver.herokuapp.com/recoleccions");
		    //HttpPost httpost = new HttpPost("192.168.0.126:3000/recoleccions");
		    boolean correcto = false;

		    	correcto = false;
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
							if(respuesta != "" && respuesta != null)
								if(respuesta.indexOf("created_at")!=-1)
										correcto = true;
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
		  		
			return correcto;
	  }
	  
	  public void borrarDatos(ArrayList<Integer> datosPorBorrar, SQLiteDatabase db)
	  {
		  
		  for(int i = 0; i<datosPorBorrar.size();i++)
		  {
			  db.delete(TABLE_INFO, COLUMN_ID + "=" + datosPorBorrar.get(i), null);
		  }
		  
	  }
	  
	  public boolean quedanDatosPorEnviar()
	  {
		  String selectQuery = "SELECT  * FROM " + TABLE_INFO;
			 
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		 
		    // looping through all rows and adding to list
			
		    if (cursor.moveToFirst()) {
		    	cursor.close();
		    	db.close();
		    	return true;
		    }
		    cursor.close();
		    db.close();
		    return false;
	  }
	  
	  public void borrarDatosYaEnviados(){
		  SQLiteDatabase db = this.getWritableDatabase();
		  db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
	  }
	  
	  public void BajarParaderos(ProgressBar pb, Bajar_datos c)
	  {
		  final ProgressBar pb2 = pb;
		  boolean correcto = true;
		  final String[] lineas = getLineasFromApi();
		  String[][] cruce = null;
		  if(lineas!=null)
		  {
			  cruce = new String[lineas.length][];
			  //bajo los paraderos
			  //dummys por mientras
			  for(int i = 0; i < lineas.length;i++)
              {
				  cruce[i]=getParaderosLineaFromApi(i+1);
				  if(cruce[i] ==null)
					  correcto = false;
				  
				  final int numero = i;
				  if(numero%(lineas.length/20)==0)
				  c.runOnUiThread(new Runnable() {
					    public void run() {
					    	pb2.setProgress((int)Math.floor(((numero+1)/lineas.length)));
					    }
					});
			  }
		  }
		  else correcto = false;
		  
		  
		  SQLiteDatabase db = this.getWritableDatabase();
		  
		  
		  
		  if (correcto)
		  {
			  db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORRIDOS);
			  db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARADEROS);
			  db.execSQL(DATABASE2_CREATE);
			  db.execSQL(DATABASE3_CREATE);
			//si se bajan correctamente, boto la base de datos y la creo denuvo.
			//db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORRIDOS);
			//db.execSQL(DATABASE2_CREATE);
			for(int i = 0; i < lineas.length;i++)
			{
			    ContentValues values = new ContentValues();
			    values.put(COLUMN_RECORRIDO, lineas[i]);
			    db.insert(TABLE_RECORRIDOS, null, values);
			    
			    for(int j = 0; j < cruce[i].length;j++)
			    {
			    	values = new ContentValues();
			    	values.put(COLUMN_RECORRIDO, lineas[i]);
			    	values.put(COLUMN_PARADERO,cruce[i][j]);
			    	db.insert(TABLE_PARADEROS, null, values);
			    }
			}
			
		  }
		  db.close(); // Closing database connection
	  }
	  
	  public List<String> getLineas()
	  {
		 String selectQuery = "SELECT * FROM " + TABLE_RECORRIDOS;
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		 Cursor cursor = db.rawQuery(selectQuery, null);
		 ArrayList<String> recorridos = new ArrayList<String>();
		 if (cursor.moveToFirst())
			 do{
				 recorridos.add(cursor.getString(0));
			 }
			 while (cursor.moveToNext());
		 cursor.close();
		 db.close();
		  return recorridos;
	  }
	  
	  public boolean comprobarCruce(String linea, String paradero)
	  {
		  boolean correcto = false;
		  String selectQuery = "SELECT " + COLUMN_PARADERO + " FROM " + TABLE_PARADEROS + " WHERE " + 
		  COLUMN_RECORRIDO + " = \"" + linea + "\"";
		  SQLiteDatabase db = this.getReadableDatabase();
		  Cursor cursor = db.rawQuery(selectQuery, null);
		  if(cursor.moveToFirst())
			  do{
				  String p = cursor.getString(0);
				  if(p.equalsIgnoreCase(paradero))
					  return true;
			  } 
			  while (cursor.moveToNext());
		  cursor.close();
		  db.close();
		  return correcto;
	  }
	  
	  public String[] getLineasFromApi()
	  {
		  String[] lineas; //= new ArrayList<String>();

		  try {
			  InputStream is = new URL("http://citppuc.cloudapp.net/api/"+"lineas").openStream();

		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject("{\"lineas\":"+jsonText+"}");
		      JSONArray lineasEnJson = json.getJSONArray("lineas");
		      lineas = new String[lineasEnJson.length()];

		      for(int i =0; i<lineasEnJson.length();i++)
		      {
		    	  JSONObject aux = lineasEnJson.getJSONObject(i);
                  String linea_codigo = aux.getString("linea_id");
                  int lineas_codig_int = (Integer.parseInt(linea_codigo)-1);
                  String codigo =lineasEnJson.getJSONObject(i).getString("codigo_linea");
                  lineas[lineas_codig_int]=codigo;
		      }
		      
		    }
		    catch(Exception e)
		    {
		    	lineas = null;
		    }
		  
		  return lineas;
	  }
	  
	  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	  
	  public String[] getParaderosLineaFromApi(int linea)
	  {
		  String[] paraderos= null;
		  ArrayList<String> listaParaderos = new ArrayList<String>();
		  
		  try {
			  InputStream is = new URL("http://citppuc.cloudapp.net/api/"+"lineas/"+linea).openStream();
		  
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      JSONArray secuencias = json.getJSONArray("secuencias");
		      
		      for(int i =0; i<secuencias.length();i++)
		      {
		    	  JSONArray secuenciaParaderos = secuencias.getJSONObject(i).getJSONArray("secuencia_paraderos");
		    	  for(int j = 0; j<secuenciaParaderos.length();j++)
		    	  {
		    		  String paradero = secuenciaParaderos.getJSONObject(j).getString("codigo_paradero");
		    		  if(!listaParaderos.contains(paradero))
		    			  listaParaderos.add(paradero);
		    	  }
		      }
		      
		    }
		    catch(Exception e)
		    {
		    	listaParaderos = null;
		    }
		  	//sacar copias
		  if(listaParaderos!=null){
			  if(listaParaderos.size()!=0)
			  {
				  Object[] lista = listaParaderos.toArray();
				  paraderos = new String[lista.length];
				  for(int i = 0; i < lista.length;i++)
					  paraderos[i] = (String)lista[i];
			  }
			  else
				  paraderos = new String[]{""};
		  }
		  return paraderos;
	  }
}

