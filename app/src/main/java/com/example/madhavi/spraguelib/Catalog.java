package com.example.madhavi.spraguelib;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
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
        if (id == R.id.catalog) {
            Intent i = new Intent(Catalog.this.getApplicationContext(), Catalog.class);
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
                Intent i = new Intent(Catalog.this.getApplicationContext(), Services.class);
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
                Intent i = new Intent(Catalog.this.getApplicationContext(), MainActivity.class);
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
                Intent i = new Intent(Catalog.this.getApplicationContext(), Notifications.class);
                startActivity(i);
            }
            return true;
        }  else if (id == R.id.library_info) {
            Intent i = new Intent(Catalog.this.getApplicationContext(), LibraryNews.class);
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
                Intent i = new Intent(Catalog.this.getApplicationContext(), Favorites.class);
                startActivity(i);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static ParseQuery<ParseObject> fetchDataFromParse(String tableName, String columnName, String value, String operation) {


        ParseQuery<ParseObject> query1 = ParseQuery.getQuery(tableName);
        if("like".equalsIgnoreCase(operation)) {
            query1.whereContains(columnName, value);
            return query1;
        }
        if("equalTo".equalsIgnoreCase(operation)) {
            query1.whereEqualTo(columnName, value);
            return query1;
        }
        return null;
    }

    public void searchCatalog(View view) {
        for(int i = 0;i<((TableLayout) findViewById(R.id.tableLayout1)).getChildCount();i++) {
            if(i == 0) {
                continue;
            }
            System.out.println("Deleting record with id : " + ((TableRow)((TableLayout) findViewById(R.id.tableLayout1)).getChildAt(i)).getId());
           ((TableRow)((TableLayout) findViewById(R.id.tableLayout1)).getChildAt(i)).removeAllViewsInLayout();
        }
        List<String> booksMatched = new ArrayList<>();
        EditText userInput = ((EditText)findViewById(R.id.search_catalog_input));
        if (userInput == null || userInput.getText().toString().length()<3) {
            Toast.makeText(getApplicationContext(), "Please enter minimum 3 characters!!",
                    Toast.LENGTH_LONG).show();
        } else {
            System.out.println("Connecting to database");
            try {

                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_Books");
                query1.whereContains("book_name", userInput.getText().toString());

               // ParseObject p1=new ParseObject("Table_Books");
                //int result = query1.find().size();
                StringBuilder bookRecord = new StringBuilder();
                String DELIMITER = "######";
                for (ParseObject record : query1.find()) {
                    System.out.println("********** objectId : = " + record.getObjectId());
                    bookRecord.append(record.getObjectId());
                    bookRecord.append(DELIMITER);
                    bookRecord.append(record.getString("Author"));
                    bookRecord.append(DELIMITER);
                    bookRecord.append(record.getString("book_name"));
                    bookRecord.append(DELIMITER);
                    bookRecord.append(record.getNumber("Availability"));
                    bookRecord.append(DELIMITER);
                    bookRecord.append(record.getString("Description"));
                    bookRecord.append(DELIMITER);
                    bookRecord.append(record.getString("ISBN"));
                    bookRecord.append(DELIMITER);
                    bookRecord.append(record.getString("year_of_publication"));
                    booksMatched.add(bookRecord.toString());
                    bookRecord = new StringBuilder();
                }
               // DatabaseManager db = new DatabaseManager(Catalog.this);
               // booksMatched = db.searchCatalog(userInput.getText().toString());
                //System.out.println("Number of records returned = " + booksMatched.size());
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
        TableRow headerRow = new TableRow(this);
        headerRow.setBackgroundColor(Color.parseColor("#01DFD7"));
        headerRow.setId(new Integer(1));
        headerRow.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));

        // Title column
        TextView titleColumn = new TextView(this);
        titleColumn.setId(10 * counter + 1);
        titleColumn.setTypeface(null, Typeface.BOLD);
        titleColumn.setTextSize(15);
        titleColumn.setText("Title");
        headerRow.addView(titleColumn);

        // Author column
        TextView authorColumn = new TextView(this);
        authorColumn.setTextSize(15);
        authorColumn.setTypeface(null, Typeface.BOLD);
        authorColumn.setId(10 * counter + 2);
        authorColumn.setText("Author");
        headerRow.addView(authorColumn);

        // Availability column
        TextView availabilityColumn = new TextView(this);
        availabilityColumn.setTextSize(15);
        availabilityColumn.setTypeface(null, Typeface.BOLD);
        availabilityColumn.setId(10 * counter + 3);
        availabilityColumn.setText("Availability");
        headerRow.addView(availabilityColumn);

        tl.addView(headerRow, new TableLayout.LayoutParams());
        int dataRecordCounter = 0;
        for (String book : booksMatched) {
            dataRecordCounter++;
            final String[] tokens = book.split(DatabaseManager.DELIMITER);
            TableRow tr = new TableRow(this);
            tr.setId(++counter);
            tr.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            // Title column
            TextView title = new TextView(this);
            title.setId(10 * counter + 1);
            title.setText(tokens[2]);
            tr.addView(title);

            // Author column
            TextView author = new TextView(this);
            author.setId(10 * counter + 2);
            author.setText(tokens[1]);
            tr.addView(author);

            // Availability column
            TextView availability = new TextView(this);
            availability.setId(10 * counter + 3);
            availability.setText(tokens[3]);
            tr.addView(availability);

            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(), BookDetails.class);
                    intent.putExtra("objectId",tokens[0]);
                    intent.putExtra("title",tokens[2]);
                    intent.putExtra("author",tokens[1]);
                    intent.putExtra("availability",tokens[3]);
                    intent.putExtra("description",tokens[4]);
                    intent.putExtra("ISBN",tokens[5]);
                    intent.putExtra("yearOfPublication",tokens[6]);
                    startActivity(intent);
                }
            });
            if(dataRecordCounter%2 == 0) {
                tr.setBackgroundColor(Color.parseColor("#E0ECF8"));
            }
            tr.setMinimumHeight(20);
            tl.addView(tr, new TableLayout.LayoutParams());
        }
    }
}

