package com.joelbland.getdone;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todoDBtoo";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TODO = "todo";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "create table " + TABLE_TODO;
        sqlCreate += " (id integer primary key autoincrement, todo_item text,deadline text)";

        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_TODO);
        onCreate(db);
    }

    public void insert(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_TODO;
        sqlInsert += " values(null, '" + todo.getItem() + "', '" + todo.getDeadline() +"')";

        db.execSQL(sqlInsert);
        db.close();
    }

    public ArrayList<Todo> selectAll() {
        String sqlQuery = "select * from " + TABLE_TODO + " order by deadline";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);

        ArrayList<Todo> items = new ArrayList<Todo>();
        while (cursor.moveToNext()) {
            Todo currentItem = new Todo(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            items.add(currentItem);
        }
        db.close();
        return items;
    }

    public void deleteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete = "delete from  " + TABLE_TODO + " where id = " + id;

        db.execSQL(sqlDelete);
        db.close();
    }
}
