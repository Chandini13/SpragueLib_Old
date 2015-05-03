package com.example.madhavi.spraguelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class AdminActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
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

    //Renting Book

    public void bookBorrow(View view) {

        try {
            Intent intent = new Intent(this, RentalActivity.class);
            startActivity(intent);

        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());}

    }

    //Returning Book

    public void bookReturn(View view) {

        try {
            Intent intent = new Intent(this, BookReturn.class);
            startActivity(intent);

        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());}

    }

    //Creating Users

    public void createUsers(View view)
    {
        Intent intent=new Intent(this,CreateUser.class);
        startActivity(intent);
    }

    //

    //Viewing user settings

    public void listAllBooks(View view)
    {
        try {
            Intent intent=new Intent(this,ListActivity.class);
            startActivity(intent);

        }
        catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());

        }

    }
    public void libraryNews(View view)
    {
        try {
            Intent intent=new Intent(this,LibraryNews.class);
            startActivity(intent);

        }
        catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());

        }

    }
}
