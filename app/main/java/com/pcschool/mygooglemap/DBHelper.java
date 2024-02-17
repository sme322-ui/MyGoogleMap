package com.pcschool.mygooglemap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE "+
            "item_list(_id INTEGER PRIMARY KEY,title TEXT,price NUMERIC,"+
        "image BLOB,create_time TIMESTAMP default CURRENT_TIMESTAMP);";

    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS coffee_list";
    private static final String name = "item_list";
    private static final int factory = 1;
    private static final int version = 1;


    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onDropTable(db);
            onCreate(db);
    }

    private void onDropTable(SQLiteDatabase db) {
        db.execSQL(DROP_TABLE_SQL);
    }
}
