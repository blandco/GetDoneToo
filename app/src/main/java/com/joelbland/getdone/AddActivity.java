package com.joelbland.getdone;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_add);
    }

    public void addItem(View view) {
        EditText etItem = findViewById(R.id.etTodoItem);
        String item = etItem.getText().toString();

        try {
            Todo todo = new Todo(0, item);
            dbManager.insert(todo);
            Toast.makeText(this, "Todo Item Added", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException nfe) {
            Toast.makeText(this, "Insert error", Toast.LENGTH_LONG).show();
        }

        etItem.setText("");
    }

    public void goBack(View view) {
        this.finish();
    }

}
