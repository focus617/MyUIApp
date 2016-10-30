package com.zhxu.root.myapplication;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhxu on 10/30/16.
 */

public class BookReaderFragment extends Fragment {
    private static final String TAG = "BookReaderFragment";
    private TextView mView_BookReader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_book_reader, container, false);

/*        mView_BookReader = (TextView) getActivity().findViewById(R.id.textview_bookreader);
        mView_BookReader.setTextSize(20);
        mView_BookReader.setTextColor(Color.WHITE);
        mView_BookReader.setBackgroundColor(Color.BLACK);
        mView_BookReader.setText("Hello World!");*/

    }
}
