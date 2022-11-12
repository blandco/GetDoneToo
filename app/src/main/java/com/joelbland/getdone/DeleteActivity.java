package com.joelbland.getdone;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        updateView();
    }

    private void updateView() {
        ArrayList<Todo> todos = dbManager.selectAll();

        ButtonHandler bh = new ButtonHandler();

        ScrollView scrollView = new ScrollView(this);

        RelativeLayout layout = new RelativeLayout(this);

        // Delete Message
        TextView deleteMsg = new TextView(this);
        deleteMsg.setText("CLICK LIST ITEM TO REMOVE");
        deleteMsg.setPadding(15,30,15,30);
        deleteMsg.setTextSize(18);
        deleteMsg.setBackgroundColor(getColor(R.color.white));
        deleteMsg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        RelativeLayout.LayoutParams deleteMsgParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layout.addView(deleteMsg, deleteMsgParams);

        // Table setup
        TableLayout table = new TableLayout(this);
        table.setPadding(60,180,60,180);
        table.setStretchAllColumns(true);

        for(Todo todo: todos) {

            TableRow tr = new TableRow(this);
            tr.setBackgroundColor(getColor(R.color.delete_button));
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

            tv.setOnClickListener(bh);

            tr.addView(tv);
            table.addView(tr,trParams);

        }

        scrollView.addView(table);
        layout.addView(scrollView);

        // Back Button
        Button btnBack = new Button(this);
        btnBack.setText("Back");
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DeleteActivity.this.finish();
            }
        });
        RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        layout.addView(btnBack, btnParams);

        setContentView(layout);
    }

    private class ButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int todoId = v.getId();
            dbManager.deleteById(todoId);
            updateView();
            Toast.makeText(DeleteActivity.this, "Todo Item Deleted", Toast.LENGTH_SHORT).show();
        }
    }

}
