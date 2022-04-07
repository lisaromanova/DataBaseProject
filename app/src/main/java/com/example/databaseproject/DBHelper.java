package com.example.databaseproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "StudentsDB";
    public static final String TABLE_CONTACTS = "Students";
    public static final String TABLE_USERS = "User";

    public static final String KEY_ID = "_id";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_NAME = "name";
    public static final String KEY_DISC = "disciplina";
    public static final String KEY_MARK = "mark";

    public static final String KEY_ID1 = "_id";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID
                + " integer primary key," + KEY_SURNAME + " text," + KEY_NAME + " text,"+ KEY_DISC + " text," + KEY_MARK + " text"+")");
        db.execSQL("create table " + TABLE_USERS + "(" + KEY_ID1
                + " integer primary key," + KEY_LOGIN + " text," + KEY_PASSWORD + " text"+")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        db.execSQL("drop table if exists " + TABLE_USERS);
        onCreate(db);

    }
}
