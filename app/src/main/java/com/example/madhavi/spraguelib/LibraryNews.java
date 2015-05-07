package com.example.madhavi.spraguelib;

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

public class LibraryNews extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_news);

        try {
            final ArrayList<String> rows2 = new ArrayList<>();
            final ParseQuery<ParseObject> q2 = ParseQuery.getQuery("Table_Library");
            Date date = new Date();

            q2.whereGreaterThan("valid_till", date);


            if (q2.find().size() > 0) {


                for (ParseObject objects2 : q2.find()) {
                    rows2.add(objects2.getString("library_events"));
                }
            }

            final ListView bookListView2 = (ListView) findViewById(R.id.listView1);
            final ArrayAdapter<String> listAdapter2 = new ArrayAdapter<String>(this, R.layout.notifications_custom_list,R.id.rowTextView2, rows2);
            bookListView2.setAdapter(listAdapter2);
        }
        catch(Exception e)
        {
            Log.e("error",e.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library_news, menu);
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

/*public void saveNews(View view)
{
    EditText validText=(EditText) findViewById(R.id.editText1);
    EditText eventText=(EditText)findViewById(R.id.editText2);
    String valid_date =  validText.getText().toString();
    String event = eventText.getText().toString();
    try {
        DatabaseManager db = new DatabaseManager(LibraryNews.this);
        db.addNews(valid_date, event);
    }
    catch (Exception e) {
        Log.e("Error in inserting rows ", e.toString());
        e.printStackTrace();

    }
    validText.setText("");
    eventText.setText("");
}*/
}
