package com.zhxu.root.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zhxu.root.myUtility.BaseActivity;

public class MainActivity extends BaseActivity{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate Method is executed");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(Color.BLUE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Log.i(TAG, "FloatingActionButton is clicked");
            }
        });
    }

     @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.i(TAG, "onStart Method is executed");
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Log.i(TAG, "onRestart Method is executed");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.i(TAG, "onResume Method is executed");
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.i(TAG, "onPause Method is executed");
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.i(TAG, "onStop Method is executed");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub

        Log.i(TAG, "onDestroy Method is executed");

        Toast.makeText(this, "Bye", Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(500);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, "Enter exception");
        }

        super.onDestroy();
    }

    // Add the menu to the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // It is also possible add items here. Use a generated id from
        // resources (ids.xml) to ensure that all menu ids are distinct.
        MenuItem refreshItem = menu.add(0, R.id.menu_refresh, 0, R.string.action_refresh);
        refreshItem.setIcon(R.drawable.ic_action_refresh);

        // Need to use MenuItemCompat methods to call any action item related methods
        MenuItemCompat.setShowAsAction(refreshItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);

        Log.i(TAG, "onCreateOptionsMenu Method is executed");
        return super.onCreateOptionsMenu(menu);
    }

    // Response to action menu/button pressing
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.i(TAG, "onOptionsItemSelected Method is executed");

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_search:
                Toast.makeText(MainActivity.this, "Searching will be added", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Menu search is selected");
                return true;
            case R.id.menu_refresh:
                Toast.makeText(MainActivity.this, "Refreshing will be added", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Menu refresh is selected");
                return true;
            case R.id.menu_settings:
                Toast.makeText(MainActivity.this, "Setting will be added", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Menu setting is selected");
                return true;
            case R.id.menu_close:
                Toast.makeText(MainActivity.this, "Close the APP", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Menu close is selected");
                ActivityCollector.finishAll();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
