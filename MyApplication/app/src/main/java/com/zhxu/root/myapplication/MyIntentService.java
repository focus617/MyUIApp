package com.zhxu.root.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by root on 10/23/16.
 */

public class MyIntentService extends IntentService {

        private static final String SvrTAG = "TestPopupMenuActivity";

        public MyIntentService() {
            super("MyIntentService");
            // TODO Auto-generated constructor stub
            Log.i(SvrTAG, "MyIntentService: created");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            // TODO Auto-generated method stub
            Log.i(SvrTAG, "SMyIntentService: Thread id is " + Thread.currentThread().getId());
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.i(SvrTAG, "MyIntentService: onDestroy executed");
        }
}
