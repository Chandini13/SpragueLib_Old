package com.example.madhavi.spraguelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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


public class Favorites extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        String userid = LogActivity.loginuser;




        final ArrayList<String> rows = new ArrayList<>();

        try {




            //retrieving books to be renewed

            final ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Table_Favorites");
            //    String s = "03/30/2013";
            //    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            //   Date date = simpleDateFormat.parse(s);
            query1.whereEqualTo("user_name", userid);
            query1.whereEqualTo("display_fav",1);

            for (ParseObject objects1 : query1.find()) {
                rows.add(objects1.getString("book_name"));
            }

            if(rows.size()==0 )
            {
                rows.add("No books added to collection  !                                                  ");
            }
            Button btnRem = (Button) findViewById(R.id.btnRem);


            final ListView bookListView = (ListView) findViewById(R.id.favListView);



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

                            final ParseQuery<ParseObject> renew = ParseQuery.getQuery("Table_Favorites");
                            renew.whereEqualTo("user_name", LogActivity.loginuser);
                            renew.whereEqualTo("book_name", rows.get(i));
                            try {
                                for(ParseObject p1:renew.find())
                                {
                                    p1.put("display_fav",0);
                                    p1.save();
                                }
                                listAdapter.remove(rows.get(i));


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    checkedItemPositions.clear();

                    listAdapter.notifyDataSetChanged();

                }
            };
            btnRem.setOnClickListener(listenerDel);

            /** Setting the adapter to the ListView */
            bookListView.setAdapter(listAdapter);


        }
        catch(ParseException e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
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
            Intent i = new Intent(Favorites.this.getApplicationContext(), Catalog.class);
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
                Intent i = new Intent(Favorites.this.getApplicationContext(), Services.class);
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
                Intent i = new Intent(Favorites.this.getApplicationContext(), MainActivity.class);
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
                Intent i = new Intent(Favorites.this.getApplicationContext(), Notifications.class);
                startActivity(i);
            }
            return true;
        }  else if (id == R.id.library_info) {
            Intent i = new Intent(Favorites.this.getApplicationContext(), LibraryNews.class);
            startActivity(i);
            return true;
        }        if (id == R.id.create_datab) {
            Intent i = new Intent(Favorites.this.getApplicationContext(), MainPage.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
