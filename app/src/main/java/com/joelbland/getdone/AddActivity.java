package com.joelbland.getdone;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_add);
    }

    public void addItem(View view) throws ParseException {
        EditText etItem = findViewById(R.id.etTodoItem);
        String item = etItem.getText().toString();
        EditText etDeadline = findViewById(R.id.etDeadline);
        String deadline = etDeadline.getText().toString();


        String formattedDeadline;
        formattedDeadline = convertDateFormat(deadline);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        String today = localDate.toString();

        // ADAPTED FROM:
        // https://www.tutorialspoint.com/how-to-compare-two-dates-in-java
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdformat.parse(formattedDeadline);
        Date d2 = sdformat.parse(today);


        try {
            Todo todo = new Todo(0,item,formattedDeadline);
            dbManager.insert(todo);
        } catch (NumberFormatException nfe) {

        }

        etItem.setText("");
        etDeadline.setText("");
    }

    public void goBack(View view) {
        this.finish();
    }


    public String convertDateFormat(String oldDateString) {
        // DATE FORMAT CODE FOUND HERE:
        // https://stackoverflow.com/questions/3469507/how-can-i-change-the-date-format-in-java

        final String OLD_FORMAT = "MM/dd/yyyy";
        final String NEW_FORMAT = "yyyy-MM-dd";

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
