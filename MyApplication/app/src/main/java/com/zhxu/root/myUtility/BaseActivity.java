package com.zhxu.root.myUtility;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by zhxu on 10/25/16.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String Tag_BaseActivity = "BaseActivity";

    public static ActivityCollector ActivityCollector;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(Tag_BaseActivity, getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

