package com.example.madhavi.spraguelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class CreateUser extends ActionBarActivity {
    static String user_id;
    static String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_datab:
                LogActivity.loginflag=0;
                Intent intent=new Intent(this,MainPage.class);
                startActivity(intent);

                break;

        }
        return true;
    }

    public void saveUser(View view)
    {
        EditText username=(EditText) findViewById(R.id.editText1);
        EditText pass_word=(EditText)findViewById(R.id.editText2);
        EditText mail=(EditText)findViewById(R.id.editText3);
        String user_id =  username.getText().toString();
        String password =pass_word.getText().toString();
        String email =mail.getText().toString();

        ParseUser user = new ParseUser();
        user.setUsername(user_id);
        user.setPassword(password);
        user.setEmail(email);

// other fields can be set just like with ParseObject
    //    user.put("phone", "650-253-0000");
 final String user_name=user_id;
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {

                    ParseObject user_settings = new ParseObject("Table_Settings");
                    user_settings.put("username", user_name);
                    user_settings.put("renewal_alert", 1);
                    user_settings.put("recall_alert", 1);
                    user_settings.put("availability_alert", 1);
                    user_settings.put("library_events", 1);
                    user_settings.saveInBackground();

                    LogActivity.loginflag = 1;
                    Intent intent = new Intent(CreateUser.this, LogActivity.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Registered successfully",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "UserName or Email ID already exist",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        }



}
