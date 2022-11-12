package com.joelbland.getdone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = new DatabaseManager(this);
        updateView();
    }

    public void updateView() {
        ArrayList<Todo> todos = databaseManager.selectAll();

        ScrollView scrollView = new ScrollView(this);

        TableLayout layout = new TableLayout(this);
        layout.setPadding(60,60,60,60);
        layout.setStretchAllColumns(true);

        for(Todo todo: todos) {
            TableRow tr = new TableRow(this);
            tr.setBackgroundColor(getColor(R.color.light_grey));
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

            tr.addView(tv);
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
                //TextView tvDelete = findViewById(R.id.tvDelete);
                //LinearLayout layoutDelete = findViewById(R.id.layoutDelete);
                //layoutDelete.setScaleY(1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateView();
    }
}