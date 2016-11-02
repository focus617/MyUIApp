package com.zhxu.root.myapplication;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * Created by zhxu on 10/30/16.
 */

public class BookReaderFragment extends Fragment {
    private static final String TAG = "BookReaderFragment";
    
    private View           view      = null;
    private String         mBookName = null;
    private BufferedReader mReader   = null;

    public void setBookName(String bookName){this.mBookName = bookName;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book_reader, container, false);

        createTestFile();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

        View bookReaderLayout = view.findViewById(R.id.layout_book_reader);
        bookReaderLayout.setVisibility(View.VISIBLE);

        TextView mView_BookReader;

        mView_BookReader = (TextView) getActivity().findViewById(R.id.textview_bookreader);
        mView_BookReader.setTextSize(20);
        mView_BookReader.setTextColor(Color.WHITE);
        mView_BookReader.setBackgroundColor(Color.BLACK);

        refresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        closeBook();
    }

    public void refresh() {
        TextView mView_BookReader = (TextView) getActivity().findViewById(R.id.textview_bookreader);

        mReader = loadBook("test.txt");  //TODO - will change para to mBookName
        if(mReader ==null){
            Log.d(TAG, "onCreate - can't open the book: " + mBookName);
            getActivity().setTitle("Error");
            mView_BookReader.setText("Can't open the book: " + mBookName);
            Toast.makeText(getActivity(), "Can't open the " + mBookName, Toast.LENGTH_SHORT).show();
        } else {
            getActivity().setTitle(mBookName);
            String str = readOnePage();
            mView_BookReader.setText(str);
            Toast.makeText(getActivity(), "open the " + mBookName, Toast.LENGTH_SHORT).show();
        }
    }

    protected BufferedReader loadBook(String bookName) {
        BufferedReader rd = null;

        Log.d(TAG, "loadBook("+bookName+") is invoked");
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
            Log.d(TAG, "closeBook() is invoked");
            try {
                mReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected String readOnePage() {
        String str ="";

        Log.d(TAG, "readOnePage() is invoked");
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
            Log.d(TAG, Environment.getExternalStorageDirectory().toString());

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
}
