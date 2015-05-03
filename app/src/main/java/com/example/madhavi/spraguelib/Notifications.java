package com.example.madhavi.spraguelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;


public class Notifications extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        final ArrayList<String> rows = new ArrayList<>();
        String userid = LogActivity.loginuser;

        try {

            //checking renew alert in settings

            final ParseQuery<ParseObject> settings = ParseQuery.getQuery("Table_Settings");
            settings.whereEqualTo("username", userid);
            settings.whereEqualTo("renewal_alert", 1);

            if (settings.count() > 0) {

                //retrieving books to be renewed

                final ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_BookRental");
                //    String s = "03/30/2013";
                //    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                //   Date date = simpleDateFormat.parse(s);
                Date date = new Date();
                query1.whereEqualTo("user_name", userid);
                query1.whereLessThan("return_date", date);
                query1.find();
                int result = query1.count();
                if (result > 0) {
                    rows.add("FOLLOWING BOOKS NEED TO BE RETURNED:");
                }
                for (ParseObject objects1 : query1.find()) {
                    rows.add(objects1.getString("book_name"));
                }

            }

            // Notification for the recalled books


            final ParseQuery<ParseObject> settings1 = ParseQuery.getQuery("Table_Settings");
            settings1.whereEqualTo("username", userid);
            settings1.whereEqualTo("recall_alert", 1);

            if (settings1.count() > 0) {

                final ParseQuery<ParseObject> settings2 = ParseQuery.getQuery("Table_BookRental");
                settings2.whereEqualTo("placed_hold", 1);

                if (settings2.count() > 0) {
                    settings2.find();
                    int result = settings2.count();

                    if (result > 0) {
                        rows.add("FOLLOWING BOOKS ARE RECALLED :");
                    }
                    for (ParseObject objects2 : settings2.find()) {
                        rows.add(objects2.getString("book_name"));
                    }

                }
            }
                    //Notification on Book Availability


                    final ParseQuery<ParseObject> q1= ParseQuery.getQuery("Table_Books");
                    q1.whereEqualTo("Availability",1);
                    q1.whereEqualTo("placed_hold",1);
            ArrayList<String> books =  new ArrayList<>();

            for (ParseObject objects2 : q1.find())
            {
                books.add(objects2.getString("book_name"));

            }
                    if(books.size()>0)
                       {
                        final ParseQuery<ParseObject> q2 = ParseQuery.getQuery("Table_PlaceHold");
                           for (int i=0;i<books.size();i++)
                           {
                               q2.whereEqualTo("user_name", userid);
                               q2.whereEqualTo("book_name", books.get(i));
                               if (q2.count() > 0) {
                                   for (ParseObject objects1 : q2.find()) {
                                       objects1.put("availability", 1);
                                       objects1.save();
                                   }
                               }
                           }
    }
    final ParseQuery<ParseObject> q2 = ParseQuery.getQuery("Table_PlaceHold");
    q2.whereEqualTo("user_name", userid);
    q2.whereEqualTo("availability", 1);

    if (q2.count() > 0) {
        q2.find();
        int result1 = q2.count();

        if (result1 > 0) {
            rows.add("FOLLOWING REQUESTED BOOKS ARE NOW AVAILABLE :");
        }
        for (ParseObject objects2 : q2.find()) {
            rows.add(objects2.getString("book_name"));
        }
    }

                    ListView bookListView = (ListView) findViewById(R.id.mainListView);
                    ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.notifications_custom_list, R.id.rowTextView2, rows);
                    bookListView.setAdapter(listAdapter);


        }
            catch(Exception e)
            {
                Log.e("error", e.toString());
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            case R.id.create_datab:
                Intent intent=new Intent(this,MainPage.class);
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
