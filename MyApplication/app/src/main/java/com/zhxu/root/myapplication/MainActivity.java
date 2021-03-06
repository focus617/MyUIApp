package com.zhxu.root.myapplication;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.zhxu.root.myUtility.BaseActivity;
import com.zhxu.root.myapplication.ItemTouchHelper.MyRecyclerHelperCallback;
import com.zhxu.root.viewUtility.CircleImageView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{

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
        setContentView(R.layout.navigation_main);

        Log.d(TAG, "onCreate Method is executed");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Toolbar become unusable in case of introducing CollapsingToolbarLayout
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setTitle("My Library");
        //toolbar.setSubtitle("副标题");
        //toolbar.setLogo(R.drawable.ic_launcher);
        //toolbar.setNavigationIcon(R.drawable.ic_action_list);
        //toolbar.setTitleTextColor(Color.WHITE);

        //给页面设置工具栏
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        if (collapsingToolbar != null) {
            //设置隐藏图片时候ToolBar的颜色
            collapsingToolbar.setContentScrimColor(Color.parseColor("#11B7F3"));
            //设置工具栏标题
            collapsingToolbar.setTitle("坚持是一种信仰");
        }

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

        initNavigationView();
        initRecycleViews();
        initBottomNavigationBar();
    }

    private void initNavigationView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        if(null != navigationView ) {
            navigationView.setBackgroundColor(Color.WHITE);
            //navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK)); //set menu item: color
            //navigationView.setItemIconTintList(null);  // set menu item: icon color
            /**设置MenuItem默认选中项**/
            navigationView.getMenu().getItem(0).setChecked(true);

            //获取头布局文件
            View headerView = navigationView.getHeaderView(0);
            CircleImageView userImgView = (CircleImageView)headerView.findViewById(R.id.nav_header_photo);
            TextView  userNameView = (TextView) headerView.findViewById(R.id.nav_header_text);
            userImgView.setVisibility(View.VISIBLE);
            userNameView.setVisibility(View.VISIBLE);

            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerLayout.setBackgroundColor(R.color.lightskyblue);
            drawerLayout.setScrimColor(R.color.blueviolet);

            //设置侧滑菜单选择监听事件
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.nav_about:
                        case R.id.nav_blog:
                        case R.id.nav_ver:
                        case R.id.nav_switch:
                            String msg = menuItem.toString();
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_exit:
                            ActivityCollector.finishAll();
                            return true;
                    }

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
        //mRecyclerView.addItemDecoration(new DividerItemDecoration_LinearLayout(MainActivity.this,LinearLayoutManager.VERTICAL));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration_GridLayout(MainActivity.this));

        //设置Adapter
        recycleAdapter= new MyRecyclerAdapter(MainActivity.this, null);
        mRecyclerView.setAdapter(recycleAdapter);

        //实现拖拽功能
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyRecyclerHelperCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initBottomNavigationBar(){
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_action_home, "Home").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_action_book, "Books").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.ic_action_settings, "Music").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_action_picture, "Photo").setActiveColorResource(R.color.brown))
                .addItem(new BottomNavigationItem(R.drawable.ic_action_share, "Games").setActiveColorResource(R.color.grey))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
    }

/*    *//** * 设置默认的 *//*
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layFrame, HomeFragment.newInstance("Home"));
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance("Home"));
        fragments.add(BookFragment.newInstance("Books"));
        fragments.add(MusicFragment.newInstance("Music"));
        fragments.add(TvFragment.newInstance("Movies & TV"));
        fragments.add(GameFragment.newInstance("Games"));
        return fragments;
    }*/

    @Override
    public void onTabSelected(int position) {
/*        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    ft.replace(R.id.layFrame, fragment);
                } else {
                    ft.add(R.id.layFrame, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }*/

    }

    @Override
    public void onTabUnselected(int position) {
/*        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }*/
    }

    @Override
    public void onTabReselected(int position) {

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
                recycleAdapter.addAll();
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
                //Toast.makeText(MainActivity.this, "Add the book back", Toast.LENGTH_SHORT).show();
                recycleAdapter.add(MyRecyclerAdapter.PositionInBookList.MIDDLE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
