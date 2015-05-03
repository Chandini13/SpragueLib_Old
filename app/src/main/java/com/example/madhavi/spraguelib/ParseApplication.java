package com.example.madhavi.spraguelib;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "WERJNSfLVanTwlL0Kj6KASGY5CKv4j7enWll0NHH", "Zv0LBIJlOIp7HCgbr0WoJrDmZh3Aiv7MOXQa3VaN");

        // Initialize Crash Reporting.
        // ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        //   //Parse.enableLocalDatastore(this);

        // Add your initialization code here

        //   Parse.initialize(this, "RA6NTZjUUHq6LsdWd0B7jXwQE7OXofS8xErPwyvx", "QNa12OryQAc23JWbRD3Ldhj89wCpvYGzxk8a2ctW");

        //  ParseInstallation.getCurrentInstallation().saveInBackground();


//      final ParseObject data1 = new ParseObject("TestDatabase1");
//      data1.put("no", 2);
//      data1.put("title", "Science");
//      data1.put("author", "pleeger");
//      data1.saveInBackground();


//        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestTable1");
//        query.getInBackground("0AcCu0pjMT",new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject parseObject, ParseException e) {
//                if(e==null)
//                {
//                    String user_name =  data6.getString("userid");
//                    String name=user_name;
//
//
//                }
//
//            }
//        });

//        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("TestTable1");
//        query1.whereEqualTo("userid", "2");
//        query1.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> parseObjects, ParseException e) {
//                if(e==null)
//                {
//                    Log.d("userid","password" + parseObjects.size()+ "password");
//                }
//            }
//        });

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




   /*     try {

            final ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Table_BookRental");

            final ParseQuery<ParseObject> query5 = ParseQuery.getQuery("Table_Books");


            int result = query3.count();
            if (result > 0)
            {
                for (ParseObject objects1 : query3.find())
                {
                    String book_name=objects1.getString("book_name");
                    query5.whereNotEqualTo("book_name",book_name);
                    for (ParseObject objects2 : query5.find())
                    {

                        objects2.put("Availability",0);
                        objects2.save();
                    }

                }

            }
        }
        catch(Exception e)
        {
            Log.e("Error", e.toString());
        }*/

        }
    }