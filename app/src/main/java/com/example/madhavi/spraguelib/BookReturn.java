package com.example.madhavi.spraguelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class BookReturn extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_return);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_return, menu);
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
    public void bookReturn1(View view) {

        try {
            DatabaseManager dbManager;
            dbManager = new DatabaseManager(BookReturn.this);
            String message1;
            String message2;


            EditText editText1 = (EditText) findViewById(R.id.editText1);
            EditText editText2 = (EditText) findViewById(R.id.editText2);


            message1 = editText1.getText().toString();
            message2 = editText2.getText().toString();

            dbManager.deleteEntry(message1,message2);

            editText1.setText("");
            editText2.setText("");

        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());}

    }
    public void list_rental_details(View view)
    {
        Intent intent=new Intent(this,ListRentalActivity.class);
        startActivity(intent);
    }
}


