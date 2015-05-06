package com.example.madhavi.spraguelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;


public class Notifications extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        String userid = LogActivity.loginuser;

        //Notification Table Entry


        final ArrayList<String> rows = new ArrayList<>();
        final ArrayList<String> rows1 = new ArrayList<>();
        final ArrayList<String> rows2 = new ArrayList<>();

        try {


            //checking renew alert in settings

            final ParseQuery<ParseObject> settings = ParseQuery.getQuery("Table_Settings");
            settings.whereEqualTo("username", userid);
            settings.whereEqualTo("renewal_alert", 1);

            if (settings.find().size() > 0) {

                //retrieving books to be renewed

                final ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_BookRental");
                //    String s = "03/30/2013";
                //    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                //   Date date = simpleDateFormat.parse(s);
                Date date = new Date();
                query1.whereEqualTo("user_name", userid);
                query1.whereLessThan("return_date", date);
                query1.whereEqualTo("notification_renew", 1);
                query1.find();
                int result = query1.find().size();
                if (result > 0) {
                    rows.add("FOLLOWING BOOKS NEED TO BE RETURNED:                                    ");
                }
                for (ParseObject objects1 : query1.find()) {
                    rows.add(objects1.getString("book_name"));
                }

            }

            // Notification for the recalled books


            final ParseQuery<ParseObject> settings1 = ParseQuery.getQuery("Table_Settings");
            settings1.whereEqualTo("username", userid);
            settings1.whereEqualTo("recall_alert", 1);

            if (settings1.find().size() > 0) {

                final ParseQuery<ParseObject> settings2 = ParseQuery.getQuery("Table_BookRental");
                settings2.whereEqualTo("user_name", userid);
                settings2.whereEqualTo("placed_hold", 1);
                settings2.whereEqualTo("notification_recall", 1);

                if (settings2.find().size() > 0) {
                    settings2.find();
                    int result = settings2.find().size();

                    if (result > 0) {
                        rows1.add("FOLLOWING BOOKS ARE RECALLED :                                      ");
                    }
                    for (ParseObject objects2 : settings2.find()) {
                        rows1.add(objects2.getString("book_name"));
                    }

                }
            }
            //Notification on Book Availability


            final ParseQuery<ParseObject> q1 = ParseQuery.getQuery("Table_Books");
            q1.whereEqualTo("Availability", 1);
            q1.whereEqualTo("placed_hold", 1);

            ArrayList<String> books = new ArrayList<>();

            for (ParseObject objects2 : q1.find()) {
                books.add(objects2.getString("book_name"));

            }
            if (books.size() > 0) {
                final ParseQuery<ParseObject> q2 = ParseQuery.getQuery("Table_PlaceHold");
                for (int i = 0; i < books.size(); i++) {
                    q2.whereEqualTo("user_name", userid);
                    q2.whereEqualTo("book_name", books.get(i));

                    if (q2.find().size() > 0) {
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
            q2.whereEqualTo("notification_flag", 1);

            if (q2.find().size() > 0) {
                q2.find();
                int result1 = q2.find().size();
                if (result1 > 0) {
                    rows2.add("FOLLOWING REQUESTED BOOKS ARE NOW AVAILABLE :                            ");
                }
                for (ParseObject objects2 : q2.find()) {
                    rows2.add(objects2.getString("book_name"));
                }
            }

            if(rows.size()==0 && rows1.size()==0&&rows2.size()==0)
            {
                rows2.add("No Notifications Right Now !                                        ");
            }
            /** Reference to the delete button of the layout main.xml */
            Button btnDel = (Button) findViewById(R.id.btnDel);


            final ListView bookListView = (ListView) findViewById(R.id.mainListView);

            final ListView bookListView1 = (ListView) findViewById(R.id.mainListView1);

            final ListView bookListView2 = (ListView) findViewById(R.id.mainListView2);

            final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, rows);
            final ArrayAdapter<String> listAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, rows1);
            final ArrayAdapter<String> listAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, rows2);
            bookListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            bookListView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            bookListView2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            //  bookListView1.setAdapter(listAdapter);

            /** Defining a click event listener for the button "Delete" */
            View.OnClickListener listenerDel = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /** Getting the checked items from the listview */


                    SparseBooleanArray checkedItemPositions = bookListView.getCheckedItemPositions();
                    SparseBooleanArray checkedItemPositions1 = bookListView1.getCheckedItemPositions();
                    SparseBooleanArray checkedItemPositions2 = bookListView2.getCheckedItemPositions();

                    int itemCount = bookListView.getCount();

                    for (int i = itemCount - 1; i >= 0; i--) {
                        if (checkedItemPositions.get(i)) {

                            final ParseQuery<ParseObject> renew = ParseQuery.getQuery("Table_BookRental");
                            renew.whereEqualTo("user_name", LogActivity.loginuser);
                            renew.whereEqualTo("book_name", rows.get(i));
                            try {
                                for(ParseObject p1:renew.find())
                                {
                                    p1.put("notification_renew",0);
                                    p1.save();
                                }
                                listAdapter.remove(rows.get(i));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    int itemCount1 = bookListView1.getCount();

                    for (int i1 = itemCount1 - 1; i1 >= 0; i1--) {
                        if (checkedItemPositions1.get(i1)) {

                            final ParseQuery<ParseObject> renew = ParseQuery.getQuery("Table_BookRental");
                            renew.whereEqualTo("user_name", LogActivity.loginuser);
                            renew.whereEqualTo("book_name", rows1.get(i1));
                            try {
                                for(ParseObject p1:renew.find())
                                {
                                    p1.put("notification_recall",0);
                                    p1.save();
                                }
                                listAdapter1.remove(rows1.get(i1));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    int itemCount2 = bookListView2.getCount();

                    for (int i2 = itemCount2 - 1; i2 >= 0; i2--) {
                        if (checkedItemPositions2.get(i2)) {

                            final ParseQuery<ParseObject> renew = ParseQuery.getQuery("Table_PlaceHold");
                            renew.whereEqualTo("user_name", LogActivity.loginuser);
                            renew.whereEqualTo("book_name", rows2.get(i2));
                            try {
                                for(ParseObject p1:renew.find())
                                {
                                    p1.put("notification_flag",0);
                                    p1.save();
                                }
                                listAdapter2.remove(rows2.get(i2));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    checkedItemPositions.clear();
                    checkedItemPositions1.clear();
                    checkedItemPositions2.clear();
                    listAdapter.notifyDataSetChanged();
                    listAdapter1.notifyDataSetChanged();
                    listAdapter2.notifyDataSetChanged();

                }
            };


            /** Setting the event listener for the delete button */
            btnDel.setOnClickListener(listenerDel);

            /** Setting the adapter to the ListView */
            bookListView.setAdapter(listAdapter);
            bookListView1.setAdapter(listAdapter1);
            bookListView2.setAdapter(listAdapter2);


//        final ListView bookListView = (ListView) findViewById(R.id.mainListView);
//        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.notifications_custom_list, R.id.rowTextView2, rows);
//        bookListView.setAdapter(listAdapter);

        } catch (Exception e) {
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
