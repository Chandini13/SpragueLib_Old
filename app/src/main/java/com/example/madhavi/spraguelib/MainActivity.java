package com.example.madhavi.spraguelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;


public class MainActivity extends ActionBarActivity {
//Menu action bar
   private TextView selectedOpt;
    String userid=LogActivity.loginuser;
    public final static String EXTRA_MESSAGE = "error 123";
    private DatabaseManager dbManager;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;


    String message ;
    String message2 ;
    String message3 ;
    String message4 ;
    String message5 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedOpt=(TextView)findViewById(R.id.selectedopt);

        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);

// values on opening the 'settings screen'

 try {
     ParseQuery<ParseObject> query5 = ParseQuery.getQuery("Table_Settings");
     query5.whereEqualTo("username", userid);
     ParseObject p = query5.getFirst();
     int alert1= p.getInt("renewal_alert");
     int alert2= p.getInt("recall_alert");
     int alert3= p.getInt("availability_alert");
     int alert4= p.getInt("library_events");

        if (alert1 == 1) {
            checkBox2.setChecked(true);
        } else {
            checkBox2.setChecked(false);
        }
        ;
        if (alert2 == 1) {
            checkBox3.setChecked(true);
        } else {
            checkBox3.setChecked(false);
        }
        ;
        if (alert3 == 1) {
            checkBox4.setChecked(true);
        } else {
            checkBox4.setChecked(false);
        }
        ;
        if (alert4 == 1) {
            checkBox5.setChecked(true);
        } else {
            checkBox5.setChecked(false);
        }
        ;
 }catch (Exception e)
 {
     Log.e("error",e.toString());
 }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.create_datab:
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                Intent intent1=new Intent(this,LogActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

    //Inserting user settings
    public void insertBook(View view)

    {

        try {

            //recall alert

            if (checkBox2.isChecked()) {
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_Settings");
                query1.whereEqualTo("username", userid);

                ParseObject p1=new ParseObject("Table_Settings");
                p1 = query1.getFirst();
                p1.put("renewal_alert",1);
                p1.saveInBackground();

            } else
            {
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_Settings");
                query1.whereEqualTo("username", userid);

                ParseObject p1=new ParseObject("Table_Settings");
                p1 = query1.getFirst();
                p1.put("renewal_alert",0);
                p1.saveInBackground();
                p1.saveInBackground();
            }

            //renew alert

            if (checkBox3.isChecked()) {
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_Settings");
                query1.whereEqualTo("username", userid);

                ParseObject p1=new ParseObject("Table_Settings");
                p1 = query1.getFirst();
                p1.put("recall_alert",1);
                p1.saveInBackground();


            } else
            {
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_Settings");
                query1.whereEqualTo("username", userid);

                ParseObject p1=new ParseObject("Table_Settings");
                p1 = query1.getFirst();
                p1.put("recall_alert",0);
                p1.saveInBackground();
            }

            //availability alert

            if (checkBox4.isChecked()) {
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_Settings");
                query1.whereEqualTo("username", userid);

                ParseObject p1=new ParseObject("Table_Settings");
                p1 = query1.getFirst();
                p1.put("availability_alert",1);
                p1.saveInBackground();
            } else
            {
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_Settings");
                query1.whereEqualTo("username", userid);

                ParseObject p1=new ParseObject("Table_Settings");
                p1 = query1.getFirst();
                p1.put("availability_alert",0);
                p1.saveInBackground();
            }

            //library events

            if (checkBox5.isChecked()) {
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_Settings");
                query1.whereEqualTo("username", userid);

                ParseObject p1=new ParseObject("Table_Settings");
                p1 = query1.getFirst();
                p1.put("library_events",1);
                p1.saveInBackground();
            } else
            {
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_Settings");
                query1.whereEqualTo("username", userid);

                ParseObject p1=new ParseObject("Table_Settings");
                p1 = query1.getFirst();
                p1.put("library_events",0);
                p1.saveInBackground();
            }

        } catch (Exception e) {
            Log.e("error", e.toString());
        }
    }

}