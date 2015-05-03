package com.example.madhavi.spraguelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class ListRentalActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rental);
        try {
            DatabaseManager dblist=new DatabaseManager(ListRentalActivity.this);
            ArrayList<String> rows =dblist.listRental();
            final ListView bookListView=(ListView) findViewById(R.id.mainListView1);
            ArrayAdapter<String> listAdapter= new ArrayAdapter<String>(this, R.layout.customlistrental,R.id.rowTextView1 ,rows);
            bookListView.setAdapter(listAdapter);
        }
        catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_rental, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_datab:

                Intent intent=new Intent(this,AdminActivity.class);
                startActivity(intent);

                break;
            case R.id.logout:
                Intent intent1=new Intent(this,LogActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }


}
