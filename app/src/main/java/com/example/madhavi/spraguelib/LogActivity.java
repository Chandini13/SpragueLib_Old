package com.example.madhavi.spraguelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;


public class LogActivity extends ActionBarActivity {

    public static String loginuser;
    public static String loginpassword;
    public static int loginflag = 0;
    private EditText username = null;
    private EditText password = null;
    private TextView attempts;
    private Button login;
    String toAdds;
    //   String toAddress;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        //Forget Password - sending email


        Button forget = (Button) findViewById(R.id.forgetpassword);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    username = (EditText) findViewById(R.id.editText1);
                    String user = username.getText().toString();
                    if (user.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Enter registered UserName for recovering password",
                                Toast.LENGTH_SHORT).show();
                    } else {
//                    final ParseQuery<ParseUser> account = ParseUser.getQuery();
//                    account.whereEqualTo("username",user);
                        ParseQuery query = ParseUser.getQuery();
                        query.whereEqualTo("username", username.getText().toString());

                        query.find();
                        ParseObject p = query.getFirst();
                        String email = p.getString("email");

                        ParseUser.requestPasswordResetInBackground(email,
                                new RequestPasswordResetCallback() {
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Toast.makeText(getApplicationContext(), "Check your registered email account for resetting password",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Enter registered UserName for recovering password",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }

                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }

            }
        });

    }


    // USER LOGIN

    public void login(View view) {

        int match = 1;
        username = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);

        login = (Button) findViewById(R.id.button1);
        loginuser = username.getText().toString();
        loginpassword = password.getText().toString();


        ParseUser.logInInBackground(loginuser, loginpassword, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    loginflag = 1;
                    Toast.makeText(getApplicationContext(), "Redirecting...",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogActivity.this, MainPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "UserName or Password incorrect ! Try Again",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
//
//


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
                LogActivity.loginflag = 0;
                Intent intent = new Intent(this, MainPage.class);
                startActivity(intent);

                break;

        }
        return true;
    }
}

