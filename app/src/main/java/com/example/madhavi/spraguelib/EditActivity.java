package com.example.madhavi.spraguelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditActivity extends ActionBarActivity {

    EditText editText5;
    EditText editText6;
    EditText editText7;
    EditText editText8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);}

/*        Intent intent = getIntent();

        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

            DatabaseManager dbManager = new DatabaseManager(EditActivity.this);
            ArrayList<String> productRows = dbManager.retrieveRows();
            editText5 = (EditText) findViewById(R.id.edit_message5);
            editText6 = (EditText) findViewById(R.id.edit_message6);
            editText7 = (EditText) findViewById(R.id.edit_message7);
            editText8 = (EditText) findViewById(R.id.edit_message8);

            StringTokenizer tokens = new StringTokenizer(productRows.get(0), ",");
            String first = tokens.nextToken();// this will contain "Fruit"
            String second = tokens.nextToken();
            String three = tokens.nextToken();
            String four = tokens.nextToken();
            editText5.setText( first );
            editText6.setText(second);
            editText7.setText(three);
            editText8.setText(four);


    }*/
    //Updated data retrieved and send to database
/*public void updateBook(View view)
    {
       try {
        DatabaseManager dbManager = new DatabaseManager(EditActivity.this);
        editText5 = (EditText) findViewById(R.id.edit_message5);
        editText6 = (EditText) findViewById(R.id.edit_message6);
        editText7 = (EditText) findViewById(R.id.edit_message7);
        editText8 = (EditText) findViewById(R.id.edit_message8);
        StringBuffer str=new StringBuffer();
        str.append(editText5.getText().toString()+",");
        str.append(editText6.getText().toString()+",");
        str.append(editText7.getText().toString()+",");
        str.append( editText8.getText().toString()+",");
        String result=str.toString();
        dbManager.updateRow(result);
           editText5.setText("");
           editText6.setText("");
           editText7.setText("");
           editText8.setText("");
        }
        catch(Exception e) {
            Log.e("Error in inserting rows ", e.toString());

        }
    }*/

    public void mainPage(View view)
    {

        Intent intent=new Intent(this,MainPage.class);

        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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
