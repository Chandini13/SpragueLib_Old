package com.example.madhavi.spraguelib;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class Services extends ActionBarActivity {
    private RadioButton laptopRentalButton;
    private RadioButton studyRoomRentalButton;
    private RadioButton bookRenewalButton;
    private EditText name;
    private EditText department;
    private EditText fromDate;
    private EditText toDate;
    private Button saveButton;
    private TextView saved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        saved = (TextView) findViewById(R.id.textView8);
        saved.setVisibility(View.INVISIBLE);

        laptopRentalButton = (RadioButton) findViewById(R.id.radioButton);
        final RadioGroup rdGroup = (RadioGroup) findViewById(R.id.radioServiceType);
        studyRoomRentalButton = (RadioButton) findViewById(R.id.radioButton2);
        bookRenewalButton = (RadioButton) findViewById(R.id.radioButton3);
        name = (EditText) findViewById(R.id.editText);
        department = (EditText) findViewById(R.id.editText3);
        fromDate = (EditText) findViewById(R.id.editText2);
        toDate = (EditText) findViewById(R.id.editText4);
        saveButton = (Button) findViewById(R.id.button8);
        final DatabaseManager dbManager = new DatabaseManager(Services.this);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saved.setVisibility(View.VISIBLE);
                String nameStr = name.getText().toString();
                String deptStr = department.getText().toString();
                String fromdtStr = fromDate.getText().toString();
                String todtStr = toDate.getText().toString();
                int selectedId = rdGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioServiceTypeButton = (RadioButton) findViewById(selectedId);
                String serviceType = radioServiceTypeButton.getText().toString();
                //addServices(String serviceType,String fullName,String department, String fromDate,String toDate)
                dbManager.addServices(serviceType, nameStr, deptStr, fromdtStr, todtStr);

            }
        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                    // laptopRental is selected
                    break;
            case R.id.radioButton2:
                if (checked)
                    // StudyRoomRental is selected
                    break;
            case R.id.radioButton3:
                if (checked)
                    // Book Renewal is selected
                    break;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_services, menu);
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
