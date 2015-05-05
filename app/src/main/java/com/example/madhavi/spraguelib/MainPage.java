package com.example.madhavi.spraguelib;

///checking Git
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;


public class MainPage extends ActionBarActivity {
    private Button catalogButton;

    private Button favoritesButton;
    private Button setttingsButton;
    private Button libraryinfoButton;
    private Button notificationsButton;
    private Button servicesButton;
    private Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        catalogButton = (Button)findViewById(R.id.button);
        favoritesButton = (Button)findViewById(R.id.button2);
        setttingsButton = (Button)findViewById(R.id.button3);
        libraryinfoButton = (Button)findViewById(R.id.button4);
        notificationsButton=(Button)findViewById(R.id.button5);
        servicesButton = (Button)findViewById(R.id.button6);
     //   helpButton = (Button)findViewById(R.id.button7);

        catalogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this.getApplicationContext(), Catalog.class);
                startActivity(i);
            }
        });
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LogActivity.loginflag==0)
                {
                    MyDialogFragment dialog;
                    dialog = new MyDialogFragment();
                    dialog.show(getFragmentManager(), "MyDialogFragmentTag");
                }
                else {
                    Intent i = new Intent(MainPage.this.getApplicationContext(), Favorites.class);
                    startActivity(i);
                }
            }
        });
        setttingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(LogActivity.loginflag==0)
                {
                    MyDialogFragment dialog;
                    dialog = new MyDialogFragment();
                    dialog.show(getFragmentManager(), "MyDialogFragmentTag");
                }
                else {
                    Intent i = new Intent(MainPage.this.getApplicationContext(), MainActivity.class);
                    startActivity(i);
                     }
            }
        });
        libraryinfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this.getApplicationContext(), LibraryInfo.class);
                startActivity(i);
            }
        });
        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LogActivity.loginflag==0)
                {
                    MyDialogFragment dialog = new MyDialogFragment();
                    dialog.show(getFragmentManager(), "MyDialogFragmentTag");
                }
                else {
                    Intent i = new Intent(MainPage.this.getApplicationContext(), Notifications.class);
                    startActivity(i);
                }
            }
        });
        servicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LogActivity.loginflag==0)
                {
                    MyDialogFragment dialog;
                    dialog = new MyDialogFragment();
                    dialog.show(getFragmentManager(), "MyDialogFragmentTag");
                }
                else {
                    Intent i = new Intent(MainPage.this.getApplicationContext(), Services.class);
                    startActivity(i);
                }
            }
        });
//        helpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainPage.this.getApplicationContext(), Help.class);
//                startActivity(i);
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(LogActivity.loginflag==1)
        {
            getMenuInflater().inflate(R.menu.menu_loggeduser, menu);
        }
        else
        {
            getMenuInflater().inflate(R.menu.menu_login_screen, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {


        switch (item.getItemId()) {
            case R.id.register:

                Intent intent=new Intent(this,CreateUser.class);
                startActivity(intent);

                break;
            case R.id.login:
                Intent intent1=new Intent(this,LogActivity.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                ParseUser.logOut();
                Intent intent2=new Intent(this,MainPage.class);
                startActivity(intent2);
                LogActivity.loginflag=0;

                break;
        }
        return true;
    }
}
