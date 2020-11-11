package edu.fdu.greenchain.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.fdu.greenchain.Data.DatabaseHandler;
import edu.fdu.greenchain.Model.Day;
import edu.fdu.greenchain.R;

public class MainActivity extends AppCompatActivity {
    private Button next;
    private EditText goal;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);
        byPassActivity();

//        DateFormat dateFormat = DateFormat.getDateInstance();
//        final String formattedDate = dateFormat.format(new Date(System.currentTimeMillis()).getTime());
//        byPassActivity(formattedDate);

        next = findViewById(R.id.nextButtonID);
        goal = findViewById(R.id.enterGoalEditTextID);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!goal.getText().toString().isEmpty()) {
                    addGoalToDB(goal.getText().toString());
                    startActivity(new Intent(MainActivity.this, TodayActivity.class));
                }else {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }
            }
        });
    }

    private void addGoalToDB(String goal) {
        db.addGoal(goal);
    }

    public void byPassActivity() {
        //If database is not empty, we go to TodayActivity directly
        if (db.isGoal()) {
            startActivity(new Intent(MainActivity.this, TodayActivity.class));
            finish();
        }
    }

//    public void byPassActivity(String checkDate) {
//        //If date is the same, we go to Calendar Activity directly
//        List<Day> daysfromDB = (ArrayList<Day>) db.getAllDays();
//        for(Day day : daysfromDB) {
//            DateFormat dateFormat = DateFormat.getDateInstance();
//            final String formattedDate = dateFormat.format(new Date(Long.parseLong(day.getDate())).getTime());
//
//            if(checkDate == formattedDate) {
//                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
//            }
//        }
//    }
}