package com.example.madhavi.spraguelib;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;


public class Catalog extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
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

    public void searchCatalog(View view) {
        // TableLayout tl = (TableLayout) findViewById(R.id.tableLayout1);
        // tl.removeAllViews();
        List<String> booksMatched = null;
        EditText userInput = ((EditText)findViewById(R.id.search_catalog_input));
        if (userInput == null || userInput.getText().toString().isEmpty()) {
            //throw a popup asking min 3 characters from user
        } else {
            System.out.println("Inside else");
            try {
                DatabaseManager db = new DatabaseManager(Catalog.this);
                booksMatched = db.searchCatalog(userInput.getText().toString());
                System.out.println("Number of records returned = " + booksMatched.size());
            } catch (Exception e) {
                Log.e("Error fetching records ", e.toString());
                e.printStackTrace();
                //Show user a popup with proper message
            }
        }
        if (booksMatched != null && !booksMatched.isEmpty()) {
            showBooks(booksMatched);
        }
    }


    public void showBooks(List<String> booksMatched) {


      /*  ListView bookListView=(ListView) findViewById(R.id.mainListView1);
        ArrayAdapter<String> listAdapter= new ArrayAdapter<String>(this, R.layout.customlistrental,R.id.rowTextView1 ,booksMatched);
        bookListView.setAdapter(listAdapter); */

        System.out.println("Inside showBooks");
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout1);
        int counter = 0;
        for (String book : booksMatched) {
            System.out.println("Inside for loop");
            String[] tokens = book.split(DatabaseManager.DELIMITER);
            System.out.println("Token size = " + tokens.length);
            TableRow tr = new TableRow(this);
            tr.setId(++counter);
            tr.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            for (String token : tokens) {
                System.out.println("Tkn = " + token);
            }
            // Author column
            TextView author = new TextView(this);
            author.setId(10 * counter + 1);
            author.setText(tokens[2]);
            // author.setVisibility(View.VISIBLE);
            //author.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            tr.addView(author);
            tl.addView(tr, new TableLayout.LayoutParams());
            // tr.addView(author);
            //tr.addView(author);
            //tr.addView(author);

            /** // Title column
             TextView title = new TextView(this);
             author.setId(10*counter+2);
             author.setText(tokens[2]);
             author.setLayoutParams(new ActionBar.LayoutParams(
             ActionBar.LayoutParams.FILL_PARENT,
             ActionBar.LayoutParams.WRAP_CONTENT));
             //tr.addView(title);
             //
             //            Caused by: java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
             // Description column
             TextView description = new TextView(this);
             author.setId(10*counter+3);
             author.setText(tokens[3]);
             author.setLayoutParams(new ActionBar.LayoutParams(
             ActionBar.LayoutParams.FILL_PARENT,
             ActionBar.LayoutParams.WRAP_CONTENT));
             //tr.addView(description);
             **/
        }
    }
}

