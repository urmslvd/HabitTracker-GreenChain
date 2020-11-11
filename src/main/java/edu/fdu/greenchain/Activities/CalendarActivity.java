package edu.fdu.greenchain.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

import edu.fdu.greenchain.Data.DatabaseHandler;
import edu.fdu.greenchain.Model.Day;
import edu.fdu.greenchain.R;
import edu.fdu.greenchain.Util.Constants;

import static com.github.sundeepk.compactcalendarview.CompactCalendarView.FILL_LARGE_INDICATOR;

public class CalendarActivity extends AppCompatActivity {

    ArrayList<Event> days = new ArrayList<>();
    CompactCalendarView compactCalendar;
    private DatabaseHandler dbdelete = new DatabaseHandler(this);
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        DatabaseHandler db = new DatabaseHandler(this);

//        String date;
//        date = java.lang.System.currentTimeMillis();

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setEventIndicatorStyle(FILL_LARGE_INDICATOR);
        compactCalendar.setUseThreeLetterAbbreviation(true);


//      Getting the days from the DB, and adding events to days list
        List<Day> daysfromDB = (ArrayList<Day>) db.getAllDays();
        for(Day day : daysfromDB) {
            Log.d("zxc",day.getDate());
            long date = Long.parseLong(day.getDate());

            Event event = new Event(Color.GREEN, date, "Complete");
            days.add(event);
        }

        for(Event e : days) {
            compactCalendar.addEvent(e);
        }

        deleteButton = findViewById(R.id.deleteButtonID);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbdelete.clearDatabase();
                startActivity(new Intent(CalendarActivity.this, MainActivity.class));
            }
        });
    }
}