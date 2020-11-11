package edu.fdu.greenchain.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.fdu.greenchain.Model.Day;
import edu.fdu.greenchain.Model.Goal;
import edu.fdu.greenchain.Util.Constants;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context ctx;
    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_DAY_TABLE = "CREATE TABLE " + Constants.TABLE_DAY + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.KEY_DATE + " TEXT);";

        db.execSQL(CREATE_DAY_TABLE);

        String CREATE_GOAL_TABLE = "CREATE TABLE " + Constants.TABLE_GOAL + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.KEY_GOAL + " TEXT);";

        db.execSQL(CREATE_GOAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_DAY);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_GOAL);

        onCreate(db);
    }

    //Get the goal
    public String getGoal() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_GOAL, new String [] {Constants.KEY_GOAL},
                null, null, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        String goal = cursor.getString(cursor.getColumnIndex(Constants.KEY_GOAL));

        return goal;

    }

    //Add Goal
    public void addGoal(String goal){
        SQLiteDatabase db = this.getWritableDatabase();

        //Content values allows us to create key-value pairs
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GOAL, goal);

        //Insert row, into table
        db.insert(Constants.TABLE_GOAL, null, values);

        Log.d("Saved", "Goal saved!");
    }

    //Add Day
    public void addDay(){
        SQLiteDatabase db = this.getWritableDatabase();

        //Content values allows us to create key-value pairs
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_DATE, String.valueOf((System.currentTimeMillis())));

        //Insert row, into table
        db.insert(Constants.TABLE_DAY, null, values);

        Log.d("Saved", "Saved from DB handler");
        Log.d("Time", String.valueOf(System.currentTimeMillis()));
    }

    //Get a day
//    public Day getDay(int id){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor cursor = db.query(Constants.TABLE_DAY, new String[] {Constants.KEY_ID, Constants.KEY_DATE},
//                Constants.KEY_ID + "=?",
//                new String[] {String.valueOf(id)}, null, null, null, null);
//
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Day day = new Day();
//        day.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
//
//        //Convert timestamp to something readable
//        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
//        String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))).getTime());
//
//        day.setDate(formattedDate);
//
//        return day;
//    }

    //Get all days
    public List<Day> getAllDays(){

        SQLiteDatabase db = this.getReadableDatabase();

        List<Day> daysList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_DAY, new String[] {
                Constants.KEY_ID, Constants.KEY_DATE}, null, null,
                null, null, Constants.KEY_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Day day = new Day();
                day.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                day.setDate(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE)));
                //day.setDate(Constants.KEY_DATE);
                //day.setDate(Long.parseLong(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE))));
                daysList.add(day);

                //Convert timestamp to something readable
//                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
//                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))).getTime());
//                day.setDate(formattedDate);

                //Add to the daysList array
            }while (cursor.moveToNext());
        }
        return daysList; //This is bunch of strings
    }

    //Get Count
    public int getDaysCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_DAY;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    //Get if goal is present
    public boolean isGoal() {
        boolean result;
        String countQuery = "SELECT * FROM " + Constants.TABLE_GOAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.getCount() > 0) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    //Delete Database
    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();

        String clearDBQuery1 = "DELETE FROM " + Constants.TABLE_GOAL;
        db.execSQL(clearDBQuery1);

        String clearDBQuery2 = "DELETE FROM " + Constants.TABLE_DAY;
        db.execSQL(clearDBQuery2);
    }
}
