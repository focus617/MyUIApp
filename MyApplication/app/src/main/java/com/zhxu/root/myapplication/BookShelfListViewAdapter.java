package com.zhxu.root.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zhxu on 10/24/16.
 */

public class BookShelfListViewAdapter extends BaseAdapter {
    private ArrayList<BookData> bookList = null;
    private Context  context = null;

    public BookShelfListViewAdapter(ArrayList<BookData> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public int getCount(){
        return bookList==null? 0: bookList.size();
    }

    @Override
    public Object getItem(int position) {
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
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
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
                cover = BitmapFactory.decodeResource(context.getResources(), R.drawable.general_book_cover);
            }
            viewHolder.bookImageView.setImageBitmap(cover);
        }
        return view;
    }
}
