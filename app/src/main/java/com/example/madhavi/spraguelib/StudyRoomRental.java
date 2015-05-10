package com.example.madhavi.spraguelib;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;


public class StudyRoomRental extends ActionBarActivity {
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_room_rental);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_study_room_rental, menu);
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

    public void request(View view)
    {
        EditText request_date = (EditText) findViewById(R.id.editText1);
        EditText request_time = (EditText) findViewById(R.id.editText2);

        CheckBox laptop = (CheckBox) findViewById(R.id.checkBox6);
        CheckBox room = (CheckBox) findViewById(R.id.checkBox7);

       // Button submit = (Button) findViewById(R.id.button1);

        String requestdate = request_date.getText().toString();
        String requesttime = request_time.getText().toString();
        try {
            if(laptop.isChecked()==false && room.isChecked()==false)

            {
                Toast.makeText(getApplicationContext(), "Choose any service",
                        Toast.LENGTH_SHORT).show();
            }
            else if(request_date==null || request_time==null)
            {
                Toast.makeText(getApplicationContext(), "Enter both Date & Time",
                        Toast.LENGTH_SHORT).show();
            }
            else {

                if (laptop.isChecked()) {

                    ParseObject p1 = new ParseObject("Table_Service");

                    p1.put("user_name", LogActivity.loginuser);
                    p1.put("service", "Laptop Reserved on "+ requestdate + " at " + requesttime);
                    p1.put("notification_service",1);

                    p1.save();
                }
                if (room.isChecked()) {
                    ParseObject p1 = new ParseObject("Table_Service");

                    p1.put("user_name", LogActivity.loginuser);
                    p1.put("service", "Room Reserved on  "+ requestdate + " at " + requesttime );
                    p1.put("notification_service",1);

                    p1.save();
                }
                request_date.setText("");
                request_time.setText("");
                laptop.setChecked(false);
                room.setChecked(false);

                Toast.makeText(getApplicationContext(), "Request submitted ",Toast.LENGTH_SHORT).show();
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
