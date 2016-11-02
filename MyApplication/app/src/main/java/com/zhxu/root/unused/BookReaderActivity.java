package com.zhxu.root.unused;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
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

import com.zhxu.root.myUtility.BaseActivity;
import com.zhxu.root.myapplication.R;

public class BookReaderActivity extends BaseActivity {

    private static final String TAG = "BookReaderActivity";

    public final static String EXTRA_MESSAGE = "extral_message";
    public final static String BOOK_NAME = "book_name";

    private TextView mView_BookReader;

    private String         mBookName = null;
    private BufferedReader mReader   = null;

    public static void actionStart(Context context, String message, String bookName) {
        Intent intent = new Intent(context, BookReaderActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(BOOK_NAME, bookName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_book_reader);

        mView_BookReader = (TextView) findViewById(R.id.textview_bookreader);
        mView_BookReader.setTextSize(20);
        mView_BookReader.setTextColor(Color.WHITE);
        mView_BookReader.setBackgroundColor(Color.BLACK);

        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        mBookName = intent.getStringExtra(BOOK_NAME);

        mReader = loadBook("test.txt");  //TODO - will change para to mBookName
        if(mReader ==null){
            Log.i(TAG, "onCreate - can't open the book: " + mBookName);
            setTitle("Error");
            mView_BookReader.setText("Can't open the book: " + mBookName);
        } else {
            setTitle(mBookName);
            String str = readOnePage();
            mView_BookReader.setText(str);
        }
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        closeBook();

        super.onDestroy();
        Log.i(TAG, "onDestroy Method is executed");
    }

    // Add the menu to the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.i(TAG, "onCreateOptionsMenu Method is executed");
        return super.onCreateOptionsMenu(menu);
    }




    protected BufferedReader loadBook(String bookName) {
        BufferedReader rd = null;

        Log.i(TAG, "loadBook("+bookName+") is invoked");
        try {
            File urlFile = new File(Environment.getExternalStorageDirectory() + "/" +bookName);
            InputStreamReader in = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            rd = new BufferedReader(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rd;
    }

    protected void closeBook() {
        if (mReader != null) {
            Log.i(TAG, "closeBook() is invoked");
            try {
                mReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected String readOnePage() {
        String str ="";

        Log.i(TAG, "readOnePage() is invoked");
        try {
            String mimeTypeLine = null;

            while ((mimeTypeLine = mReader.readLine())!= null) {
                str = str + mimeTypeLine +"\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    protected void createTestFile() {
        BufferedWriter writer = null;

        try {
            File urlFile = new File(Environment.getExternalStorageDirectory() + "/test.txt");
            Log.i(TAG, Environment.getExternalStorageDirectory().toString());

            FileOutputStream out = new FileOutputStream(urlFile);
            writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

            writer.write("This is a test txt file. The purpose is to test the txt mReader.");
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

    /*public void onClickNewActivity(View v) {
        Log.i(TAG, "New_Activity_Button is clicked");
        Toast.makeText(this, "Start the PopupMenu Activity", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, TestPopupMenuActivity.class);
        startActivity(intent);
    }*/

}
