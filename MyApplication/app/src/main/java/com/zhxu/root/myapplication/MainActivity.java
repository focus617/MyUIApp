package com.zhxu.root.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.zhxu.root.myUtility.BaseActivity;

public class MainActivity extends BaseActivity{

    private static final String TAG = "MainActivity";

    FragmentManager.OnBackStackChangedListener listener; //Fragment监听器

    private ActionBar mActionBar;
    private Handler handler = new Handler();

    private DrawerLayout drawerLayout;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter recycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate Method is executed");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("My Library");
        //toolbar.setSubtitle("副标题");
        //toolbar.setLogo(R.drawable.ic_launcher);
        //toolbar.setNavigationIcon(android.R.drawable.ic_input_delete);
        //toolbar.setTitleTextColor(Color.WHITE);

/*        mActionBar = getSupportActionBar();
        if (null != mActionBar) {
            hideActionBar();
        }*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(Color.BLUE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Log.d(TAG, "FloatingActionButton is clicked");
            }
        });

        //下面是练习Fragment监听的代码
        FragmentManager manager = getSupportFragmentManager();
        listener = new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // TODO Auto-generated method stub
                Log.d(TAG, "Fragment: backstack changed");
            }
        };
        manager.addOnBackStackChangedListener(listener);

        //initNavigationView();
        initRecycleViews();
    }

/*    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (null != mActionBar) {
                hideActionBar();
            }
        }
        return super.dispatchTouchEvent(ev);
    }*/

    private void hideActionBar() {
        mActionBar.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mActionBar.isShowing()) {
                    mActionBar.hide();
                }
            }
        }, 3000);
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.d(TAG, "onStart Method is executed");
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Log.d(TAG, "onRestart Method is executed");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.d(TAG, "onResume Method is executed");
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.d(TAG, "onPause Method is executed");
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.d(TAG, "onStop Method is executed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy Method is executed");

        // TODO Auto-generated method stub
        Toast.makeText(this, "Bye", Toast.LENGTH_SHORT).show();

        //下面是练习Fragment监听的代码
        FragmentManager manager = getSupportFragmentManager();
        manager.removeOnBackStackChangedListener(listener);

/*        try {
            Thread.sleep(500);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d(TAG, "Enter exception");
        }*/
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

        Log.d(TAG, "onCreateOptionsMenu Method is executed");
        return super.onCreateOptionsMenu(menu);
    }

    // Response to action menu/button pressing
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.d(TAG, "onOptionsItemSelected Method is executed");

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_search:
                Toast.makeText(MainActivity.this, "Searching will be added", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Menu search is selected");
                return true;
            case R.id.menu_refresh:
                Toast.makeText(MainActivity.this, "Refreshing will be added", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Menu refresh is selected");
                return true;
            case R.id.menu_settings:
                Toast.makeText(MainActivity.this, "Setting will be added", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Menu setting is selected");
                return true;
            case R.id.menu_close:
                Toast.makeText(MainActivity.this, "Close the APP", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Menu close is selected");
                ActivityCollector.finishAll();
                return true;
            case android.R.id.home:
                //打开抽屉侧滑菜单
                drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);
                return super.onOptionsItemSelected(item);
            case R.id.menu_add:
                recycleAdapter.add(1);
                break;
            case R.id.menu_remove:
                recycleAdapter.remove(1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initNavigationView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        if(null != navigationView ) {
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            //设置侧滑菜单选择监听事件
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    menuItem.setChecked(true);
                    //关闭抽屉侧滑菜单
                    drawerLayout.closeDrawers();
                    return true;
                }
            });
        }
    }

    private void initRecycleViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setBackgroundColor(Color.WHITE);

        //设置布局显示方式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, true);
        //GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        
        mRecyclerView.setLayoutManager(layoutManager);
 
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        //设置添加删除item时候的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置分隔线  
        mRecyclerView.addItemDecoration(new DividerItemDecoration_LinearLayout(MainActivity.this, LinearLayoutManager.VERTICAL));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration_GridLayout(MainActivity.this));

        //设置Adapter
        recycleAdapter= new MyRecyclerAdapter(MainActivity.this, null);
        mRecyclerView.setAdapter( recycleAdapter);

        recycleAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view , String data) {
                Toast.makeText(MainActivity.this,"您点击了："+data, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
