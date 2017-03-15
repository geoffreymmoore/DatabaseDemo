package com.moore.databasedemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // define widget variables
    private EditText lastNameET, birthdayET;
    private Button addButton, deleteButton, showButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference to the widget
        lastNameET = (EditText)findViewById(R.id.lastNameET);
        birthdayET = (EditText) findViewById(R.id.birthdayET);
        addButton = (Button)findViewById(R.id.addButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        showButton = (Button)findViewById(R.id.showButton);

    }

    // add a contact to database
    public void addClick(View view) {
        // add a new birthday record
        ContentValues values = new ContentValues();

        values.put(BirthdayProvider.NAME, ((EditText)findViewById(R.id.lastNameET)).getText().toString());
        values.put(BirthdayProvider.BIRTHDAY, ((EditText)findViewById(R.id.birthdayET)).getText().toString());

        Uri uri = getContentResolver().insert(BirthdayProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(), "Moore: " + uri.toString() + " inserted", Toast.LENGTH_LONG).show();
    }

    // delete a contact to database
    public void deleteClick(View view) {
        // delete all the records in the table of the database provider
        String URL = "content://com.moore.provider.BirthdayProv/friends";
        Uri friends = Uri.parse(URL);
        int count = getContentResolver().delete(friends,null, null);
        String countNum = "Moore: " + count + " records are deleted";

        Toast.makeText(getBaseContext(), countNum, Toast.LENGTH_LONG).show();
    }

    // show the database contents
    public void showClick(View view) {
        // Show all the records sorted by friend's name
        String URL = "content://com.moore.provider.BirthdayProv/friends";
        Uri friends = Uri.parse(URL);

        Cursor c = getContentResolver().query(friends, null, null, null, "name");
        String result = "Moore Results:";

        if(!c.moveToFirst()){
            Toast.makeText(this, result = " no content yet!", Toast.LENGTH_LONG).show();
        }else{
            do{
                result = result + "\n" + c.getString(c.getColumnIndex(BirthdayProvider.NAME)) + " with id " + c.getString(c.getColumnIndex(BirthdayProvider.ID)) + " has birthday: " + c.getString(c.getColumnIndex(BirthdayProvider.BIRTHDAY));
            } while (c.moveToNext());

            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }
}
