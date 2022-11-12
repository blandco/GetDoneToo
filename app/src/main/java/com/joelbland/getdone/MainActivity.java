package com.joelbland.getdone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        //setContentView(R.layout.activity_main);
        try {
            updateView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void updateView() throws ParseException {
        ArrayList<Todo> todos = dbManager.selectAll();

        ScrollView scrollView = new ScrollView(this);

        TableLayout layout = new TableLayout(this);
        //ConstraintLayout layout = new ConstraintLayout(this);
        layout.setPadding(60,60,60,60);
        layout.setStretchAllColumns(true);

        for(Todo todo: todos) {

            TableRow tr = new TableRow(this);



            tr.setPadding(30,15,30,15);
            TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            trParams.setMargins(0,0,0,30);

            TextView tv = new TextView(this);
            String item = todo.getItem();
            tv.setId(todo.getId());
            tv.setText(item);
            tv.setTextSize(18);

            String formattedDeadline = convertDateFormat(todo.getDeadline());
            TextView tvDeadline = new TextView(this);
            tvDeadline.setText(formattedDeadline);
            tvDeadline.setTextSize(12);


            // Change row color to red if past deadline
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            String today = localDate.toString();

            // ADAPTED FROM:
            // https://www.tutorialspoint.com/how-to-compare-two-dates-in-java
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdformat.parse(todo.getDeadline());
            Date d2 = sdformat.parse(today);

            if(d2.compareTo(d1) > 0) {
                tr.setBackgroundColor(getColor(R.color.colorPrimary));
                tv.setTextColor(getColor(R.color.white));
                tvDeadline.setTextColor(getColor(R.color.white));
            } else {
                tr.setBackgroundColor(getColor(R.color.light_grey));
            }

            // add row to layout
            tr.addView(tv);
            tr.addView(tvDeadline);
            layout.addView(tr,trParams);
        }

        scrollView.addView(layout);
        setContentView(scrollView);

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                Intent insertIntent = new Intent(this, AddActivity.class);
                this.startActivity(insertIntent);
                return true;
            case R.id.action_delete:
                Intent deleteIntent = new Intent(this, DeleteActivity.class);
                this.startActivity(deleteIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        try {
            updateView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public String convertDateFormat(String oldDateString) {
        // DATE FORMAT CODE FOUND HERE:
        // https://stackoverflow.com/questions/3469507/how-can-i-change-the-date-format-in-java

        final String OLD_FORMAT = "yyyy-MM-dd";
        final String NEW_FORMAT = "MM/dd/yyyy";

        String newDateString;

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = null;
        try {
            d = sdf.parse(oldDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d).toString();

        return newDateString;

    }

}