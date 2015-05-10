package com.example.madhavi.spraguelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class BookDetails extends ActionBarActivity {
    String book_id = null;
    static String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Intent intent = getIntent();
        String author = intent.getStringExtra("author");
        title = intent.getStringExtra("title");
        book_id = intent.getStringExtra("objectId");
        String description = intent.getStringExtra("description");
        String ISBN = intent.getStringExtra("ISBN");
        String availability = intent.getStringExtra("availability");
        String yearOfPublication = intent.getStringExtra("yearOfPublication");
        showData(author, title, description, ISBN, yearOfPublication, availability, true);
      //  ParseQuery<ParseObject> query = Catalog.fetchDataFromParse("table_books", "objectId", book_id, "equalTo");
      //  try {
       //     System.out.println("************************ book_id = " + book_id + " ***** " + query.find().size());
       // } catch (Exception e) {
       //     e.printStackTrace();
       // }
    }

    public void enableButton(View view){
        ((Button)findViewById(R.id.saveButton)).setEnabled(true);
    }

    public void saveChanges(View view)
    {
        //System.out.println("Called save changes");
         /** ParseQuery<ParseObject> query = Catalog.fetchDataFromParse("table_books", "objectId", book_id, "equalTo");
          try {
             System.out.println("************************ book_id = " + book_id + " ***** " + query.find().size());
         } catch (Exception e) {
             e.printStackTrace();
         }
          */
        if(LogActivity.loginflag==0)
        {
            MyDialogFragment dialog;
            dialog = new MyDialogFragment();
            dialog.show(getFragmentManager(), "MyDialogFragmentTag");
        }
else {

            CheckBox fav = (CheckBox) findViewById(R.id.isFavorite);
            CheckBox placeHold = (CheckBox) findViewById(R.id.placeHold);

            Button submit = (Button) findViewById(R.id.button1);


            try {
                if (fav.isChecked() == false && placeHold.isChecked() == false)

                {
                    Toast.makeText(getApplicationContext(), "No changes to save",
                            Toast.LENGTH_SHORT).show();
                } else {

                    if (fav.isChecked()) {

                        ParseObject p1 = new ParseObject("Table_Favorites");

                        p1.put("user_name", LogActivity.loginuser);
                        p1.put("book_name", title);
                        p1.put("display_fav",1);
                        p1.save();
                    }
                    if (placeHold.isChecked()) {
                        final ParseQuery<ParseObject> q1 = ParseQuery.getQuery("Table_Books");
                        q1.whereEqualTo("book_name", title);
                        ParseObject p1 = q1.getFirst();
                        p1.put("placed_hold", 1);
                        p1.save();
                        final ParseQuery<ParseObject> q2 = ParseQuery.getQuery("Table_BookRental");
                        q2.whereEqualTo("user_name", LogActivity.loginuser);
                        q2.whereEqualTo("book_name", title);
                        ParseObject p2 = q2.getFirst();
                        p2.put("placed_hold", 1);
                        p2.save();
                        ParseObject p3 = new ParseObject("Table_PlaceHold");
                        p3.put("user_name", LogActivity.loginuser);
                        p3.put("book_name", title);
                        p3.put("notification_flag", 1);
                        p3.save();
                    }

                    fav.setChecked(false);
                    placeHold.setChecked(false);

                    Toast.makeText(getApplicationContext(), "Details Saved ",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void showData(String author, String title, String description, String ISBN, String yearOfPublication, String availability, boolean isFavorite) {
        System.out.println("Inside show Data with author : " + author);
        ((TextView) findViewById(R.id.title)).setText(title);
        ((TextView) findViewById(R.id.description)).setText(description);
        ((TextView) findViewById(R.id.author)).setText(author);
        ((TextView) findViewById(R.id.ISBN)).setText(ISBN);
        ((TextView) findViewById(R.id.yearOfPublication)).setText(yearOfPublication);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
