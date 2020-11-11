package edu.fdu.greenchain.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.fdu.greenchain.Data.DatabaseHandler;
import edu.fdu.greenchain.Model.Day;
import edu.fdu.greenchain.R;
import edu.fdu.greenchain.Util.Constants;

public class TodayActivity extends AppCompatActivity {

    public DatabaseHandler db;
    public TextView goalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        db = new DatabaseHandler(this);

        goalText = findViewById(R.id.goalTextViewID);
        goalText.setText(db.getGoal());


        FloatingActionButton fab = findViewById(R.id.todayButtonID);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                addDateToDB();
//                Log.d("Time", formattedDate);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addDateToDB() {
        db = new DatabaseHandler(this);
//        long date = System.currentTimeMillis();
//        DateFormat dateFormat = DateFormat.getDateInstance();
//        final String formattedDate = dateFormat.format(new Date(System.currentTimeMillis()).getTime());
//        List<Day> daysfromDB = (ArrayList<Day>) db.getAllDays();
//        for(Day day : daysfromDB) {
//            if(formattedDate == day.getDate()) break;
//        }
        db.addDay();
        startActivity(new Intent(TodayActivity.this, CalendarActivity.class));
    }
}