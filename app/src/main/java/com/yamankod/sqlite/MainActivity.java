
package com.yamankod.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

   final static int PERMISSIONS_REQUEST_CODE = 1;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      getPermissionWriteExternalStorage();
      getPermissionReadExternalStorage();

      final ListView customListView = (ListView) findViewById(R.id.listview);
      DBHelper dbHelper = new DBHelper(getApplicationContext());

      SharedPreferences settings = getSharedPreferences("SQL", 0);
      boolean firstTime = settings.getBoolean("firstTime", true);

      if (firstTime) {
         dbHelper.insertCountry("Turkiye");
         dbHelper.insertCountry("Amerika");
         dbHelper.insertCountry("Ingiltere");
         dbHelper.insertCountry("Almanya");

         SharedPreferences.Editor editor = settings.edit();
         editor.putBoolean("firstTime", false);
         editor.commit();
      }


      List<String> countriesGet = new ArrayList<>();
      countriesGet = dbHelper.getAllCountries();


      ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
              (this, android.R.layout.simple_list_item_1, android.R.id.text1,countriesGet );


      customListView.setAdapter(veriAdaptoru);


      List<String> countries = dbHelper.getAllCountries();
      customListView.setOnItemClickListener(new OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            Toast.makeText(getApplicationContext(),
                           ((String) customListView.getAdapter().getItem(position)),
                           Toast.LENGTH_LONG).show();
         }
      });
   }


   public void getPermissionReadExternalStorage() {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
              != PackageManager.PERMISSION_GRANTED) {


         if (shouldShowRequestPermissionRationale(
                 Manifest.permission.READ_EXTERNAL_STORAGE)) {
         }
         requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                 PERMISSIONS_REQUEST_CODE);
      }
   }

   public void getPermissionWriteExternalStorage() {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
              != PackageManager.PERMISSION_GRANTED) {


         if (shouldShowRequestPermissionRationale(
                 Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
         }
         requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                 PERMISSIONS_REQUEST_CODE);
      }
   }

   @Override
   public void onRequestPermissionsResult(int requestCode,
                                          @NonNull String permissions[],
                                          @NonNull int[] grantResults) {
      if (requestCode == PERMISSIONS_REQUEST_CODE) {
         if (grantResults.length == 1 &&
                 grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
         } else {
            Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
         }
      } else {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
      }
   }

}
