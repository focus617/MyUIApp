package com.zhxu.root.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import myUtility.BaseActivity;

public class BookReaderActivity extends BaseActivity {

    private static final String TAG = "BookReaderActivity";

    public final static String EXTRA_MESSAGE = "extral_message";
    public final static String BOOK_NAME = "book_name";

    private Button button_close;
    private Button button_add;
    private TextView bookReader;

    public static void actionStart(Context context, String message, String bookName) {
        Intent intent = new Intent(context, BookReaderActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(BOOK_NAME, bookName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reader);

        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String bookName = intent.getStringExtra(BOOK_NAME);
        setTitle(bookName);

        bookReader = (TextView) findViewById(R.id.textview_bookreader);
        bookReader.setTextSize(20);
        bookReader.setTextColor(Color.WHITE);
        bookReader.setBackgroundColor(Color.BLACK);
        bookReader.setText(message);

    /* Replaced by above new TextView
        TextView textView = new TextView(this);
        textView.setTextSize(30);
        textView.setTextColor(R.color.white);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
        */

    }

    // Add the menu to the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.i(TAG, "onCreateOptionsMenu Method is executed");
        return super.onCreateOptionsMenu(menu);
    }

    public void onClickOpen(View v) {
        Log.i(TAG, "Open_Button is clicked");

        bookReader.setText(load());
    }

    public void onClickSave(View v) {
        Log.i(TAG, "Save_Button is clicked");
        Toast.makeText(BookReaderActivity.this, "Create a new file", Toast.LENGTH_SHORT).show();

        save();
    }

    public void save() {
        BufferedWriter writer = null;

        try {
            File urlFile = new File(Environment.getExternalStorageDirectory() + "/test.txt");
            Log.i(TAG, Environment.getExternalStorageDirectory().toString());

            FileOutputStream out = new FileOutputStream(urlFile);
            writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

            writer.write("This is a test txt file. The purpose is to test the txt reader.");
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String load() {
        BufferedReader reader = null;
        String str ="";

        Log.i(TAG, "load() is invoked");
        try {
            File urlFile = new File(Environment.getExternalStorageDirectory() + "/test.txt");
            InputStreamReader in = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            reader = new BufferedReader(in);

            String mimeTypeLine = null;

            while ((mimeTypeLine = reader.readLine())!= null) {
                str = str + mimeTypeLine;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return str;
        }
    }

}
