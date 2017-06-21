
package com.yamankod.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

   private static final String DATABASE_NAME   = "yamankodDB";
   // Contacts table name
   private static final String TABLE_COUNTRIES = "countries";


   public DBHelper(Context context) {
      super(context, DATABASE_NAME, null, 1);
   }


   @Override
   public void onCreate(SQLiteDatabase db) {
      String sql = "CREATE TABLE " + TABLE_COUNTRIES + "(id INTEGER PRIMARY KEY,country_name TEXT" + ")";
      Log.d("DBHelper", "SQL : " + sql);
      db.execSQL(sql);
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
      onCreate(db);
   }


   public void insertCountry(String name) {
      SQLiteDatabase db = this.getWritableDatabase();

      ContentValues values = new ContentValues();
      values.put("country_name", name);

      db.insert(TABLE_COUNTRIES, null, values);
      db.close();
   }

   public List<String> getAllCountries() {
      List<String> countries = new ArrayList<String>();
      SQLiteDatabase db = this.getWritableDatabase();


      Cursor cursor = db.query(TABLE_COUNTRIES, new String[]{"id", "country_name"}, null, null, null, null, null);
      while (cursor.moveToNext()) {
         countries.add(cursor.getString(1));
      }

      return countries;
   }

}
