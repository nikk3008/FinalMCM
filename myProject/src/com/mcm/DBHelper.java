package com.mcm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper implements Constants {
     //declare all constants::
	 public static final String MEMBER_DATABASE = "MemberDatabase.db";
	 public static final String MEMBER = "Member";  //TABLE NAME
	 public static final int    DBVERSION = 3;
	 
	 public static final String TAG = "DBHelper";
	
	 
	 SQLiteDatabase SQLitedb;
	 Context context;
	//must have cons.
	public DBHelper(Context context) 
			
	{
		super(context, MEMBER_DATABASE, null, DBVERSION);
		                    //cursorfactory
		this.context = context;
		
   	}//end cons...
	

	@Override //will be called once, will be called when u say getWritableDatabase()...
	public void onCreate(SQLiteDatabase SQLitedb)
	  {
        //Prepare a String for MEMBER table: be careful dear with this String!! risky ...
		String sql = "create table    " +  MEMBER + "(" 
				        + ID            +  " integer primary key autoincrement, "
						+ MEMBER_ID 	+  " int ,   "
						+ CLIENT_ID 	+  " int,    "
						+ FIRST_NAME 	+  " text,   "
						+ SUR_NAME 	    +  " text,   "   
						+ EMAIL_ID      +  " text,   "
						+ PASSWORD      +  " text ); " ;
		
						
				
		 //now create table: pass string to db.exec()
		  this.SQLitedb = SQLitedb; //update our global db object		
		  this.SQLitedb.execSQL(sql);  //table created!!
		//Put a log
		   Log.e(TAG,"Under  onCreate()..Member table created successfully. "+sql);
	  }//end onCreate()..

	@Override //called when u change db version.
	//Must .. called when newVersion != oldVersion
	public void onUpgrade(SQLiteDatabase SQLitedb, int arg1, int arg2) 
	 {
	       //need if u altering ur table.
		 Log.d("onUpgrade","under dbHelper");
		 SQLitedb.execSQL("Drop table if exists " + MEMBER);
		 onCreate(SQLitedb);
		
	 }//end onUpgrade()...
	// CLOSE THE DATABASE//
		 public void closeDb()
		 {
			 SQLitedb.close();
		 } //end closeDb..
		 //Open the database:
		  public void openDb()
		  {
			  SQLitedb = getWritableDatabase(); //this in turn will
			    //call onCreate() and whole life cycle starts here.
		  }//end openDb()....
		  
		 //ALL MUNDANE JOB IS DONE ABOVE THIS LINE//
		/*************    Below all Insert,Update,Delete functions will appear   **********/
		 public void insertRow(ContentValues cv)
		 {
			 int id; //will be return value.. not sure..
			 /*
			  * At first open database for writable
			  * SQLitedb = getWritableDatabase(); method of SQLiteOpenHelper class.
			  * First create ContentValues object, to hold column values (col,value) pairs :
			  * 
			  */
			
			 //Done.
			 //db.insert(table,nullColumnHack,values);
			 SQLitedb.insert(MEMBER, null, cv); //return long.
			 Log.e(TAG,"New row  inserted.");
		
		 }//end insertRow()...
		
	public Cursor showRecord() //show/select all records.
		  {
			// SQLitedb.query(table, columns, selection, selectionArgs, groupBy, having, 
					  // orderBy)
			Cursor c = SQLitedb.query(MEMBER, null, null, null, null, null, null);
					                //(1,     2,     3,      4,    5,      6,       7)
			            //2: null means return all column values
			 
			 return c; //return cursor back, let caller do what he/she likes with it.
		  } //end showRecord()
	
	//show/get record with specific column(s) one or more :	 
	public Cursor showRecord(String[] whichColumns) //show/select all records.
	  {
		// SQLitedb.query(table, columns, selection, selectionArgs, groupBy, having, 
				  // orderBy)
		Cursor c = SQLitedb.query(MEMBER, whichColumns, null, null, null, null, null);
				                //(1,     2,     3,      4,    5,      6,       7)
		            //2: null means return all column values
		 
		 return c; //return cursor back, let caller do what he/she likes with it.
	  } //end showRecord()
	 	 
		 
		 
		 
		 
		 
		 
		 
		 
		 //steps 1 : first do group by : get Cursor and count how many rows fetched... 
		/*
		 public Cursor showRecord() 
		 {
//Cursor c = SQLitedb.query(String table,String[] cols,String selection,String[] selArgs,String groupBy, String having, String orderBy);
			String cols[] = new String[10];
			cols[0] =  C_TOPIC_ID       ;        // 0
			cols[1] =  C_TOPIC          ;        // 1
			cols[2] =  "max(" +C_AVERAGE+ ")" ;  // 2
			cols[3] =  "avg(" +C_AVERAGE+ ")" ;  // 3
			cols[4] =  "count(*)"       ;        // 4
			
			Cursor c = SQLitedb.query(STATSTB,cols , null, null, C_TOPIC_ID, null, null);
			return c; 
		 }//end showRecord().. 
		 */
		 /***************** Show Rec. based on ID *******not require as such********/
		 /* 
		 public Cursor showRecord(int topicId)
		  {
	// SQLitedb.query(table, columns, selection, selectionArgs, groupBy, having, 
			  // orderBy)
	Cursor c = SQLitedb.query(STATSTB, null, C_TOPIC_ID +"="+ topicId, null, null, null, null);
			                //(1,          2,     3,                   4,    5,      6,       7)
	            //2: null means return all column values
	 
	     return c; //return cursor back, let caller do what he/she likes with it.
		  } //end showRecord()

	     */
		
	
	

}//end class DBHelper...
