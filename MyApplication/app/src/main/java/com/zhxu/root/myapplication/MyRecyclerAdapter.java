package com.zhxu.root.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhxu on 11/1/16.
 */

public class MyRecyclerAdapter
        extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>
        implements View.OnClickListener
{

    private ArrayList<BookData> mBookList;
    private Context        mContext;
    private LayoutInflater inflater;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    //给RecyclerView的Item添加点击事件
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public MyRecyclerAdapter(Context context, ArrayList<BookData> bookList){
        this.mContext = context;

        if(null != bookList) {
            this.mBookList = bookList;
        }else {
            initializeBookList();
        }
        inflater=LayoutInflater.from(mContext);
    }

    private void initializeBookList() {
        BookData book;

        // prepare the book list
        this.mBookList = new ArrayList<BookData>();

        book = BookData.createBookData((String)"从0到1：开启商业与未来的秘密",(String)"(美)蒂尔 马斯特斯");
        this.mBookList.add(book);

        book = BookData.createBookData((String)"智能社会",(String)"高金波");
        this.mBookList.add(book);

        book = BookData.createBookData((String)"商业的未来",(String)"(美)海尔");
        this.mBookList.add(book);

        book = BookData.createBookData((String)"人工智能",(String)"孙庆华");
        this.mBookList.add(book);

        book = BookData.createBookData((String)"Java语言",(String)"(美)SUN");
        this.mBookList.add(book);

        book = BookData.createBookData((String)"数据结构",(String)"徐大林");
        this.mBookList.add(book);

        book = BookData.createBookData((String)"C语言",(String)"(美)SUN");
        this.mBookList.add(book);

        book = BookData.createBookData((String)"数据库",(String)"徐大林");
        this.mBookList.add(book);

        book = BookData.createBookData((String)"西陵帝国",(String)"同感");
        this.mBookList.add(book);

        book = BookData.createBookData((String)"设计模式",(String)"徐大林");
        this.mBookList.add(book);
    }

    @Override
    public int getItemCount() {

        return mBookList==null? 0: mBookList.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        // 给控件赋值
        BookData book = (BookData) mBookList.get(position);
        if (book != null) {
            holder.mBookNameTextView.setText(book.get_BookName());
            holder.mBookAuthorTextView.setText(book.get_Author());

            Bitmap cover = book.get_BookImage();
            if (cover == null){
                cover = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.general_book_cover);
            }
            holder.mBookImageView.setImageBitmap(cover);

            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(book.get_BookName());
        }
    }




    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.book_list_item, parent, false);

        //将创建的View注册点击事件
        view.setOnClickListener(MyRecyclerAdapter.this);

        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        ImageView mBookImageView = null;
        TextView  mBookNameTextView = null;
        TextView  mBookAuthorTextView = null;

        public MyViewHolder(View view) {
            super(view);

            mBookImageView =(ImageView) view.findViewById(R.id.book_image);
            mBookNameTextView = (TextView) view.findViewById(R.id.book_name);
            mBookAuthorTextView = (TextView) view.findViewById(R.id.book_author);
        }
    }


    public void add(int position) {
        BookData book = BookData.createBookData((String)"从0到1：开启商业与未来的秘密",(String)"(美)蒂尔 马斯特斯");
        mBookList.add(position, book);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mBookList.size());
    }

    public void remove(int position) {
        mBookList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mBookList.size());
    }
}
