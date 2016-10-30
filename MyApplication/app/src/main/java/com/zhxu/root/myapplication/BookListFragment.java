package com.zhxu.root.myapplication;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 * Created by zhxu on 10/30/16.
 *
 * This ListFragment displays a list of books, with a clickable view on each item whichs displays
 * a {@link android.support.v7.widget.PopupMenu PopupMenu} when clicked, allowing the user to
 * remove or open the item from the list.
 */

public class BookListFragment extends ListFragment {
    private static final String TAG = "BookListFragment";

    private ArrayList<BookData> bookList = null;

    public ArrayList<BookData> getBookList() {
        return bookList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);


        // prepare the book list
        bookList = new ArrayList<BookData>();

        try{
            if(!loadBookList()) {
                initializeBookList();
                Log.d(TAG, "Construct the test bookList data.");
            } else{
                Log.d(TAG, "Load the test bookList data from SDCARD.");
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Set the ListAdapter
        setListAdapter(new BookShelfListViewAdapter(bookList));
    }


/*    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }*/

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

    private void saveBookList() {
        ObjectOutputStream writer = null;

        Log.d(TAG, "saveBookList() is invoked");
        try {
            FileOutputStream out = getActivity().openFileOutput("data", Context.MODE_PRIVATE);
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

    private boolean loadBookList() throws ClassNotFoundException{
        ObjectInputStream reader = null;
        boolean result = false;

        Log.d(TAG, "loadBookList() is invoked");
        try {
            FileInputStream in = getActivity().openFileInput("data");
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
        }
        return result;
    }

    @Override
    public void onPause(){
        Log.d(TAG, "onPause");
        saveBookList();
    }

    @Override
    public void onListItemClick(ListView listView, final View view, int position, long id) {
            Log.d(TAG, "onListItemClick");

            String bookName = bookList.get((int)id).get_BookName();
            // Show a toast if the user clicks on an item
            Toast.makeText(getContext(), "Item Clicked: " + bookName, Toast.LENGTH_SHORT).show();

            // We need to post a Runnable to show the popup to make sure that the PopupMenu is
            // correctly positioned. The reason being that the view may change position before the
            // PopupMenu is shown.
           view.post(new Runnable() {
                @Override
                public void run() {
                    showPopupMenu(view);
                }
            });
    }

    class BookShelfListViewAdapter extends ArrayAdapter<BookData> {

        BookShelfListViewAdapter(ArrayList<BookData> bookList) {
            super(getActivity(), R.layout.book_list_item, bookList);
        }

        @Override
        public int getCount(){
            return bookList==null? 0: bookList.size();
        }

        @Override
        public BookData getItem(int position) {
            return bookList==null? null : bookList.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }


        private class ViewHolder{
            ImageView bookImageView = null;
            TextView  bookNameTextView = null;
            TextView  bookAuthorTextView = null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View view;
            ViewHolder viewHolder = null;

            // 装载View
            if (convertView == null){
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                view = layoutInflater.inflate(R.layout.book_list_item, null);

                viewHolder = new ViewHolder();
                // 获取控件
                viewHolder.bookImageView =(ImageView) view.findViewById(R.id.book_image);
                viewHolder.bookNameTextView = (TextView) view.findViewById(R.id.book_name);
                viewHolder.bookAuthorTextView = (TextView) view.findViewById(R.id.book_author);
                // 存入缓存
                view.setTag(viewHolder);
            } else {
                // 如果有缓存布局，就重用
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            // 给控件赋值
            BookData book = (BookData) getItem(position);
            if (book != null) {
                viewHolder.bookNameTextView.setText(book.get_BookName());
                viewHolder.bookAuthorTextView.setText(book.get_Author());

                Bitmap cover = book.get_BookImage();
                if (cover == null){
                    cover = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.general_book_cover);
                }
                viewHolder.bookImageView.setImageBitmap(cover);
            }
            return view;
        }
    }

    private void showPopupMenu(final View view) {
        //final PopupListFragment.PopupAdapter adapter = (PopupListFragment.PopupAdapter) getListAdapter();

        // Retrieve the clicked item from view's tag
        //final String item = (String) view.getTag();

        // Create a PopupMenu, giving it the clicked view for an anchor
        PopupMenu popup = new PopupMenu(getActivity(), view);

        // Inflate our menu resource into the PopupMenu's Menu
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

        // Set a listener so we are notified if a menu item is clicked
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_remove:
                        // Remove the item from the adapter
                        //adapter.remove(item);
                        return true;
                }
                return false;
            }
        });

        // Finally show the PopupMenu
        popup.show();
    }
}


