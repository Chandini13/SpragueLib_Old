package com.example.madhavi.spraguelib;
//testing
import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, "WERJNSfLVanTwlL0Kj6KASGY5CKv4j7enWll0NHH", "Zv0LBIJlOIp7HCgbr0WoJrDmZh3Aiv7MOXQa3VaN");

        //  ParseInstallation.getCurrentInstallation().saveInBackground();


//          ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
//        // // Optionally enable public read access.
        defaultACL.setPublicReadAccess(true);
//         ParseACL.setDefaultACL(defaultACL, true);

        try {

            //Updating Book availability based on rental service

            final ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Table_BookRental");

            final ParseQuery<ParseObject> query1= ParseQuery.getQuery("Table_Books");

            query1.find();
            query2.find();
            int result = query1.count();
            if (result > 0)
                {
                    for (ParseObject objects1 : query1.find())
                {
                    String book_name=objects1.getString("book_name");
                    query2.whereEqualTo("book_name", book_name);
                    int result2 = query2.count();

                        objects1.put("Availability",(result2 ==0)?1:0);
                        objects1.save();


                }

                }
            }
            catch(Exception e)
            {
                Log.e("Error", e.toString());
            }




        }
    }
