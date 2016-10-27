package com.zhxu.root.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import myUtility.BaseActivity;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private Button button_close;
    private Button button_add;
    private ListView bookShelfView;

    public ArrayList<BookData> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate Method is executed");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Log.i(TAG, "FloatingActionButton is clicked");
            }
        });

        // prepare the book list
        bookList = new ArrayList<BookData>();
        initializeBookList();
/*        try{
            if(!load()) {
                initializeBookList();
                Log.i(TAG, "Construct the test bookList data.");
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        bookShelfView = (ListView) findViewById(R.id.listview_bookshelf);
        BookShelfListViewAdapter adapter = new BookShelfListViewAdapter(bookList, this);
        bookShelfView.setAdapter(adapter);

        bookShelfView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int id, long l) {
                Log.i(TAG, "ListView: onItemClick Method is executed");

                String message = "To be done:\n Open the selected book";
                String bookName = bookList.get(id).get_BookName();
                DisplayMessageActivity.actionStart(MainActivity.this, message, bookName);
            }
        });
    }

    private void initializeBookList() {
        BookData book;

        book = BookData.createBookData((String)"从0到1：开启商业与未来的秘密",(String)"(美)蒂尔 马斯特斯");
        bookList.add(book);

        book = BookData.createBookData((String)"智能社会",(String)"高金波");
        bookList.add(book);

        book = BookData.createBookData((String)"商业的未来",(String)"(美)海尔");
        bookList.add(book);

        book = BookData.createBookData((String)"人工智能",(String)"孙庆华");
        bookList.add(book);

        book = BookData.createBookData((String)"Java语言",(String)"(美)SUN");
        bookList.add(book);

        book = BookData.createBookData((String)"数据结构",(String)"徐大林");
        bookList.add(book);

        book = BookData.createBookData((String)"C语言",(String)"(美)SUN");
        bookList.add(book);

        book = BookData.createBookData((String)"数据库",(String)"徐大林");
        bookList.add(book);
    }

    public void save() {
        ObjectOutputStream writer = null;

        try {
            FileOutputStream out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new ObjectOutputStream(out);

            for (BookData book: bookList) {
                writer.writeObject(book);
            }
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

    public boolean load() throws ClassNotFoundException{
        ObjectInputStream reader = null;
        boolean result = false;

        try {
            FileInputStream in = openFileInput("data");
            reader = new ObjectInputStream(in);

            BookData book =null;
            try{
                while (true) {
                    book = (BookData) reader.readObject();
                    bookList.add(book);
                }
            }catch (EOFException e) {
                Log.i(TAG, "bookList is loaded");
                result = true;
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
            return result;
        }
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
        // save();
        super.onDestroy();
        Log.i(TAG, "onDestroy Method is executed");
    }

    public void onClickClose(View v) {
        Log.i(TAG, "Close_Button is clicked");
        Toast.makeText(MainActivity.this, "Bye", Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(500);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, "Enter exception");
        }
        ActivityCollector.finishAll();
    }
    public void onClickAdd(View v) {
        Log.i(TAG, "Add_Button is clicked");
/*
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);*/

/*        // 打印主线程的id
        Log.i(TAG, "MainThread id is " + Thread.currentThread().getId());
        Intent intentService = new Intent(this, MyIntentService.class);
        startService(intentService);*/

        String message = "To be done:\n adding new book into bookList";
        DisplayMessageActivity.actionStart(MainActivity.this, message, null);
    }

    // Add the menu to the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.action_search:
                Log.i(TAG, "Menu search is selected");
                return true;
            case R.id.action_settings:
                Log.i(TAG, "Menu setting is selected");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
