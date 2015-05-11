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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.catalog) {
            Intent i = new Intent(BookReturn.this.getApplicationContext(), Catalog.class);
            startActivity(i);
            return true;
        } else if (id == R.id.services) {
            if(LogActivity.loginflag==0)
            {
                MyDialogFragment dialog;
                dialog = new MyDialogFragment();
                dialog.show(getFragmentManager(), "MyDialogFragmentTag");
            }
            else {
                Intent i = new Intent(BookReturn.this.getApplicationContext(), Services.class);
                startActivity(i);
            }
            return true;
        }  else if (id == R.id.help) {
            if(LogActivity.loginflag==0)
            {
                MyDialogFragment dialog;
                dialog = new MyDialogFragment();
                dialog.show(getFragmentManager(), "MyDialogFragmentTag");
            }
            else {
                Intent i = new Intent(BookReturn.this.getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
            return true;
        }  else if (id == R.id.notifications) {
            if(LogActivity.loginflag==0)
            {
                MyDialogFragment dialog;
                dialog = new MyDialogFragment();
                dialog.show(getFragmentManager(), "MyDialogFragmentTag");
            }
            else {
                Intent i = new Intent(BookReturn.this.getApplicationContext(), Notifications.class);
                startActivity(i);
            }
            return true;
        }  else if (id == R.id.library_info) {
            Intent i = new Intent(BookReturn.this.getApplicationContext(), LibraryNews.class);
            startActivity(i);
            return true;
        }   else if (id == R.id.favorites) {
            if(LogActivity.loginflag==0)
            {
                MyDialogFragment dialog;
                dialog = new MyDialogFragment();
                dialog.show(getFragmentManager(), "MyDialogFragmentTag");
            }
            else {
                Intent i = new Intent(BookReturn.this.getApplicationContext(), Favorites.class);
                startActivity(i);
            }
            return true;
        }        if (id == R.id.create_datab) {
            Intent i = new Intent(BookReturn.this.getApplicationContext(), MainPage.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
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


