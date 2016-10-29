package com.zhxu.root.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;

import myUtility.BaseActivity;

public class DisplayMessageActivity extends BaseActivity {

    private static final String TAG = "DisplayMessageActivity";

    public final static String EXTRA_MESSAGE = "extral_message";
    public final static String BOOK_NAME = "book_name";

    public static void actionStart(Context context, String message, String bookName) {
        Intent intent = new Intent(context, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(BOOK_NAME, bookName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String bookName = intent.getStringExtra(BOOK_NAME);
        message = message + "\nBook Name is "+ bookName;

        TextView textView = new TextView(this);
        textView.setTextSize(30);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);

    }

    // Add the menu to the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.i(TAG, "onCreateOptionsMenu Method is executed");
        return super.onCreateOptionsMenu(menu);
    }
}
