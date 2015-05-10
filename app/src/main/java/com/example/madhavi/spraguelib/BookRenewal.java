package com.example.madhavi.spraguelib;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class BookRenewal extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_renewal);

        String userid = LogActivity.loginuser;




        final ArrayList<String> rows = new ArrayList<>();

        try {




                //retrieving books to be renewed

                final ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_BookRental");
                //    String s = "03/30/2013";
                //    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                //   Date date = simpleDateFormat.parse(s);
            Date date = new Date();


                query1.whereEqualTo("user_name", userid);
                query1.whereLessThan("return_date", date);


                for (ParseObject objects1 : query1.find()) {
                    rows.add(objects1.getString("book_name"));
                }

            if(rows.size()==0 )
            {
                rows.add("No books to renew !                                                            ");
            }
            Button btnRen = (Button) findViewById(R.id.btnRen);


            final ListView bookListView = (ListView) findViewById(R.id.renewListView);



            final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, rows);
            bookListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            View.OnClickListener listenerDel = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /** Getting the checked items from the listview */


                    SparseBooleanArray checkedItemPositions = bookListView.getCheckedItemPositions();


                    int itemCount = bookListView.getCount();

                    for (int i = itemCount - 1; i >= 0; i--) {
                        if (checkedItemPositions.get(i)) {

                            final ParseQuery<ParseObject> renew = ParseQuery.getQuery("Table_BookRental");
                            renew.whereEqualTo("user_name", LogActivity.loginuser);
                            renew.whereEqualTo("book_name", rows.get(i));
                            try {
                                for(ParseObject p1:renew.find())
                                {

                                    int value=p1.getInt("placed_hold");
                                    if(value==1)
                                    {
                                        Toast.makeText(getApplicationContext(), "Book cannot be renewed! Contact Library",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    else
                                    {
                                        Calendar cal = Calendar.getInstance();
                                        cal.add(Calendar.DATE,30);
                                        Date renew_date=cal.getTime();

                                        p1.put("return_date",renew_date);
                                        p1.save();
                                        listAdapter.remove(rows.get(i));
                                    }


                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    checkedItemPositions.clear();

                    listAdapter.notifyDataSetChanged();

                }
            };
            btnRen.setOnClickListener(listenerDel);

            /** Setting the adapter to the ListView */
            bookListView.setAdapter(listAdapter);


            Toast.makeText(getApplicationContext(), "Book Renewed Successfully",
                    Toast.LENGTH_SHORT).show();
        }
            catch(ParseException e){
                e.printStackTrace();
            }
        }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_renewal, menu);
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
