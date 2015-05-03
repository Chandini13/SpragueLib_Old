package com.example.madhavi.spraguelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class RentalActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);
        } catch (Exception e) {
            Log.e("Error", e.toString());}


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rental, menu);
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

    public void insert_rental_details(View view) {

        try {
            DatabaseManager dbManager;
            dbManager = new DatabaseManager(RentalActivity.this);
            String message1;
            String message2;
            String message3;

            EditText editText2 = (EditText) findViewById(R.id.editText2);
            EditText editText3 = (EditText) findViewById(R.id.editText3);
            EditText editText5 = (EditText) findViewById(R.id.editText5);

            message1 = editText2.getText().toString();
            message2 = editText3.getText().toString();
            message3 = editText5.getText().toString();

            dbManager.insert_rental_details(message1,message2,message3);

            editText2.setText("");
            editText3.setText("");
            editText5.setText("");

        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());}

    }
    public void list_rental_details(View view)
    {
        Intent intent=new Intent(this,ListRentalActivity.class);
        startActivity(intent);
    }
}
