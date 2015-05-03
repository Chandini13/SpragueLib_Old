package com.example.madhavi.spraguelib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    // DATABASE and Version
    public static final String DB_NAME = "SPRAGUE DATABASE";
    public static final int DB_VERSION = 4;

    //other variables
    public static final String DELIMITER = "######";
    static int renew;
    static int recall;
    static int avail;
    static int event;
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;
    ArrayList<String> productRows;

   //Login User Capturing
    LogActivity log = new LogActivity();
    String user=log.loginuser;
    String password=log.loginpassword;

    //DB Table for user preferences

    public static final String DB_TABLE = "MY_PREFERENCES";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (user_id STRING, renew INTEGER, recall INTEGER,availability INTEGER,event INTEGER );";


    //DB Table for user borrowed books

       public static final String DB_TABLE2 = "BOOK_RENTAL";
    private static final String CREATE_TABLE2 = "CREATE TABLE " + DB_TABLE2 + " (user_id String, book_name String,return_date DATE);";

    //DB Table for user credentials

    public static final String DB_TABLE3 = "LOGIN_CREDENTIALS";
    private static final String CREATE_TABLE3 = "CREATE TABLE " + DB_TABLE3 + " (user_id String,password String);";

    //DB Table for Library Events

    public static final String DB_TABLE4 = "LIBRARY_EVENTS";
    private static final String CREATE_TABLE4 = "CREATE TABLE " + DB_TABLE4 + " (valid_date String ,event String );";


    public static final String DB_TABLE5 = "SERVICES";
    private static final String CREATE_TABLE5 = "CREATE TABLE " + DB_TABLE5
            + " (serviceId int, serviceType String, fullName String, department String, fromDate String, toDate String);";

    public static final String DB_TABLE_BOOKS = "BOOK_CATALOG";
    private static final String CREATE_TABLE_BOOKS= "CREATE TABLE " + DB_TABLE_BOOKS + " (book_id INTEGER PRIMARY KEY, title String, author String," +
            " description String, version String, comments String);";


    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DatabaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow(int ren, int rec, int avl,int evt) {
//        ContentValues newProduct = new ContentValues();
 //       newProduct.put("user_id", user);//login user ID
 //       newProduct.put("renew", ren);
 //       newProduct.put("recall", rec);
  //      newProduct.put("availability", avl);
        try {

           // db.insertOrThrow(DB_TABLE, null, newProduct);

            db.execSQL("UPDATE " + DB_TABLE + " SET renew= '" + ren + "',recall= '"+rec+ "' , availability = '" + avl + "' ,event= '" + evt + "'WHERE user_id ='" + user+ "'");
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
            return false;
        }
        db.close();
        return true;
    }

 /*   public ArrayList<String> retrieveRows() {
        productRows = new ArrayList<String>();
        try {

            String[] columns = new String[]{"user_id", "renew", "recall", "availability","event"};
            //String[] columns = new String[]{"*"};
            Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {

                productRows.add(cursor.getInt(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + "," + cursor.getString(3)+ "," cursor.getString(4));
                cursor.moveToNext();
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            //   db.close();
            return productRows;
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
            return productRows;
        }
    }*/

//Retrieving User Preferences

    public void retrievePreferences()
    {

        ArrayList<String> productrows = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DB_TABLE + " WHERE user_id= '"+user+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            renew=Integer.parseInt(cursor.getString(1));
            recall=Integer.parseInt(cursor.getString(2));
            avail=Integer.parseInt(cursor.getString(3));
            event=Integer.parseInt(cursor.getString(4));
            cursor.moveToNext();

        }

    }
  /*  public int updateRow(String str) {
        StringTokenizer tokens = new StringTokenizer(str, ",");
        String ID = tokens.nextToken();
        String RENEW = tokens.nextToken();
        String RECALL = tokens.nextToken();
        String AVAILABILITY = tokens.nextToken();
        db.execSQL("UPDATE " + DB_TABLE + " SET renew='" + RENEW + "' WHERE code =" + ID+ "'");
        return 0;
    }
*/
   public ArrayList<String> listRows() {
        try {
            productRows = new ArrayList<String>();

            String[] columns = new String[]{"user_id", "renew", "recall", "availability","event"};
            Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {

                productRows.add(cursor.getString(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + "," + cursor.getString(3)+","+cursor.getString(4));
                cursor.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
        }
        return productRows;

    }

    public boolean insert_rental_details( String id, String book, String ret) {
        ContentValues newProduct = new ContentValues();

        newProduct.put("user_id", id);
        newProduct.put("book_name", book);
        newProduct.put("return_date", ret);
        try {
            db.insertOrThrow(DB_TABLE2, null, newProduct);
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
            return false;
        }
        db.close();
        return true;
    }

    public ArrayList<String> listRental()
    {
      ArrayList<String> productrows = new ArrayList<>();
        try {

            String[] columns1 = new String[]{"user_id", "book_name", "return_date"};
            Cursor cursor1 = db.query(DB_TABLE2, columns1, null, null, null, null, null);
            cursor1.moveToFirst();
            while (cursor1.isAfterLast() == false) {
                productrows.add( cursor1.getString(0) + "," + cursor1.getString(1) + "," + cursor1.getString(2));
                cursor1.moveToNext();
            }
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();

        }
        return productrows;
    }

    //Checking the settings for Notification display

    public ArrayList<String> compare() {

        int result=0;
        ArrayList<String> productrows = new ArrayList<>();

        String selectQuery = "SELECT  renew FROM " + DB_TABLE + " WHERE user_id = '"+user+ "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false)
        {
            result=cursor.getInt(0);
            cursor.moveToNext();
        }
        if(result==1)
        {
            productrows.add("FOLLOWING BOOKS NEED TO BE RETURNED:");
            String selectQuery3 = "SELECT  book_name,return_date FROM " + DB_TABLE2 + " WHERE return_date <= CURRENT_DATE AND user_id= '"+user+"'";
            Cursor cursor3 = db.rawQuery(selectQuery3, null);
            cursor3.moveToFirst();
            while (cursor3.isAfterLast() == false) {
                productrows.add(cursor3.getString(0)+" is supposed to be returned by: "+cursor3.getString(1));
                cursor3.moveToNext();
            }
        }


        String selectQuery1 = "SELECT  event FROM " + DB_TABLE + " WHERE user_id = '"+user+ "'";
        int result1=0;
        Cursor cursor1 = db.rawQuery(selectQuery1, null);
        cursor1.moveToFirst();
        while (cursor1.isAfterLast() == false)
        {
           result1=cursor1.getInt(0);
            cursor1.moveToNext();
        }
        if(result1==1)
        {
        productrows.add("LIBRARY NEWS AND EVENTS");
        String selectQuery2 = "SELECT  event FROM " + DB_TABLE4 + " WHERE valid_date >= CURRENT_DATE";
        Cursor cursor2 = db.rawQuery(selectQuery2, null);
        cursor2.moveToFirst();
        while (cursor2.isAfterLast() == false) {
            productrows.add(cursor2.getString(0));
            cursor2.moveToNext();
        }
        }
if(result==0&&result1==0)
{
    productrows.add("No Notifications");
}
        return productrows;
    }

    // Is login credentials valid.If valid "admin" or "other users"
 public int isValidUser() {
    int match = 0;
     try {

         LogActivity log = new LogActivity();
         String user = log.loginuser;
         String password = log.loginpassword;

         ArrayList<String> row = new ArrayList<>();
         String selectQuery = "SELECT * FROM " + DB_TABLE3 + " WHERE user_id = '"+user+ "' AND password = '"+password+ "'";

         Cursor cursor = db.rawQuery(selectQuery, null);
         cursor.moveToFirst();
         while (cursor.isAfterLast() == false) {
             if(cursor.getString(0).equals("admin"))
             match = 1;
             else match=2;
             cursor.moveToNext();
         }

     } catch (Exception e) {
         Log.e("error", e.toString());
         e.printStackTrace();

     }
     return match;
 }
    //Adding users by their credentials
    public boolean addUser(String user_id,String password)
    {
        int renew=1;
        int recall=1;
        int availability=1;
        int event=1;
        ContentValues newProduct = new ContentValues();
        newProduct.put("user_id", user_id);
        newProduct.put("password", password);
        ContentValues newProduct1 = new ContentValues();
        newProduct1.put("user_id", user_id);
        newProduct1.put("renew", renew);
        newProduct1.put("recall", recall);
        newProduct1.put("availability", availability);
        newProduct1.put("event", event);
        try {
            db.insertOrThrow(DB_TABLE3, null, newProduct);
            db.insertOrThrow(DB_TABLE,null,newProduct1);
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
            return false;
        }
        db.close();
        return true;
    }
    public ArrayList<String> listUsers()
    {
        ArrayList<String> productrows = new ArrayList<>();
        try {
            String sortorder = "user_id DESC";
            String[] columns1 = new String[]{ "user_id", "password"};
            Cursor cursor1 = db.query(DB_TABLE3, columns1, null, null, null, null, null);
            cursor1.moveToFirst();
            while (cursor1.isAfterLast() == false) {
                productrows.add(cursor1.getString(0) + "," + cursor1.getString(1));
                cursor1.moveToNext();
            }
        } catch (Exception e) {
            Log.e("error", e.toString());
            e.printStackTrace();

        }
        return productrows;
    }

    public void deleteEntry(String user_id,String book_name)
    {
try {
    db.delete(DB_TABLE2, "user_id = '" + user_id + "' AND book_name = '" + book_name + "'", null);
}
catch(Exception e)
{
    Log.e("error", e.toString());
    e.printStackTrace();
}
    }

    public void addNews(String valid,String event)
    {

        ContentValues newProduct = new ContentValues();
        newProduct.put("valid_date", valid);
        newProduct.put("event", event);

        try {
            db.insertOrThrow(DB_TABLE4, null, newProduct);

        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();

        }
        db.close();

    }

    public void addServices(String serviceType,String fullName,String department, String fromDate,String toDate)
    {

        ContentValues newService = new ContentValues();
        newService.put("service", serviceType);
        newService.put("full_Name", fullName);
        newService.put("dept", department);
        newService.put("from", fromDate);
        newService.put("to", toDate);

        try {
            db.insertOrThrow(DB_TABLE5, null, newService);

        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();

        }
        db.close();

    }

    //Search catalog
    public List<String> searchCatalog(String catalogSearchInput)
    {
        System.out.println("Inside DBManager searchcatalog");
        String selectQuery = "SELECT book_id, author, title, description FROM " + DB_TABLE_BOOKS + " WHERE LOWER(title) like '%"+catalogSearchInput.toLowerCase()+ "%'";
        List<String> recordsList = new ArrayList<String>();
        StringBuilder record = null;
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
                System.out.println("Adding a record");
                record =  new StringBuilder("");
                record.append(cursor.getInt(0));//book id
                record.append(DELIMITER);
                record.append(cursor.getString(1));//author
                record.append(DELIMITER);
                record.append(cursor.getString(2));//title
                record.append(DELIMITER);
                record.append(cursor.getString(3));//description
                record.append(DELIMITER);
                recordsList.add(record.toString());
            }
        } catch (Exception e) {
            Log.e("Error while searching catalog ", e.toString());
            e.printStackTrace();
            db.close();
            throw e;
        }
        db.close();
        return recordsList;

    }


    public void loadData(SQLiteDatabase db){
        System.out.println("Loading Data*********************************");
        System.out.println(InsertScripts.insert_book_catalog_1);
        System.out.println("Db is "+db);
        try {
            db.execSQL(InsertScripts.insert_book_catalog_1);
            db.execSQL(InsertScripts.insert_book_catalog_2);
            db.execSQL(InsertScripts.insert_book_catalog_3);
            db.execSQL(InsertScripts.insert_book_catalog_4);
        } catch (Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
        }
    }


    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context c){
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_TABLE2);
                db.execSQL(CREATE_TABLE3);
                db.execSQL(CREATE_TABLE4);
                db.execSQL(CREATE_TABLE5);
                db.execSQL(CREATE_TABLE_BOOKS);
                loadData(db);
            } catch (Exception e) {
                Log.e("Error in inserting rows ", e.toString());
                e.printStackTrace();

            }
        }


        public void droptable()
        {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE2);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE3);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE4);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE5);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_BOOKS);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Products table","Upgrading database i.e. dropping table and recreating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE2);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE3);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE4);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE5);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_BOOKS);

            onCreate(db);
        }
    }
}
