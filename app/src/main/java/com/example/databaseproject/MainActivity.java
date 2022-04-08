package com.example.databaseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEnter, btnSign;
    EditText username, password;

    DBHelper dbHelper;
    SQLiteDatabase database;
    String adminUser = "admin";
    String adminPassword = "admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnter = (Button) findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this);


        btnSign = (Button) findViewById(R.id.btnSign);
        btnSign.setOnClickListener(this);


        username = findViewById(R.id.Login);
        password = findViewById(R.id.Password);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    username.setHint("");
                else
                    username.setHint("Username");
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    password.setHint("");
                else
                    password.setHint("Password");
            }
        });

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_LOGIN, adminUser);
        contentValues.put(DBHelper.KEY_PASSWORD, adminPassword);

        database.insert(DBHelper.TABLE_USERS, null, contentValues);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnEnter:
                Cursor loginCursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);
                boolean logged = false;
                if (loginCursor.moveToFirst()) {

                    int usernameIndex = loginCursor.getColumnIndex(DBHelper.KEY_LOGIN);
                    int passwordIndex = loginCursor.getColumnIndex(DBHelper.KEY_PASSWORD);
                    do {
                        if(username.getText().toString().equals(adminUser) && password.getText().toString().equals(adminPassword)) {
                            startActivity(new Intent(this, MainAdmin.class));
                            logged = true;
                            break;
                        }
                        if(username.getText().toString().equals(loginCursor.getString(usernameIndex)) && password.getText().toString().equals(loginCursor.getString(passwordIndex))){
                            startActivity(new Intent(this, MainUser.class));
                            logged = true;
                            break;
                        }
                    } while (loginCursor.moveToNext());
                }
                loginCursor.close();
                if (!logged)
                    Toast.makeText(this, "Введенная комбинация логина и пароля не была найдена", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnSign:
                Cursor signCursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);
                boolean finded = false;
                if (signCursor.moveToFirst()) {
                    int usernameIndex = signCursor.getColumnIndex(DBHelper.KEY_LOGIN);
                    do {
                        if (username.getText().toString().equals(signCursor.getString(usernameIndex))) {
                            Toast.makeText(this, "Введеный вами логин уже зарегистрирован", Toast.LENGTH_LONG).show();
                            finded = true;
                            break;
                        }
                    } while (signCursor.moveToNext());
                }
                if (!finded) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.KEY_LOGIN, username.getText().toString());
                    contentValues.put(DBHelper.KEY_PASSWORD, password.getText().toString());
                    database.insert(DBHelper.TABLE_USERS, null, contentValues);
                    Toast.makeText(this, "Вы успешно зарегистрировались!", Toast.LENGTH_LONG).show();
                }
                signCursor.close();
                break;

        }
        }
    }
