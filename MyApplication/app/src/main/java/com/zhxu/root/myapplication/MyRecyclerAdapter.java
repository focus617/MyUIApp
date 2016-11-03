package com.zhxu.root.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhxu.root.myapplication.ItemTouchHelper.OnDragVHListener;
import com.zhxu.root.myapplication.ItemTouchHelper.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Collections;

import static android.R.id.list;


/**
 * Created by zhxu on 11/1/16.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private ArrayList<BookData> mBookList;
    private ArrayList<BookData> mRemovedBookList;
    private Context             mContext;
    private LayoutInflater      mInflater;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public OnRecyclerViewItemClickListener getOnItemClickListener(){
        return this.mOnItemClickListener;
    }

    public MyRecyclerAdapter(Context context, ArrayList<BookData> bookList){
        this.mContext = context;

        if(null != bookList) {
            this.mBookList = bookList;
        }else {
            initializeBookList();
        }
        this.mRemovedBookList = new ArrayList<BookData>();
        this.mInflater =LayoutInflater.from(mContext);
        this.mOnItemClickListener = new OnRecyclerViewItemClickListener();
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

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
            holder.mBookRemoveIcon.setVisibility(View.INVISIBLE);
        }

        //给RecyclerView的Item添加点击事件
        if( mOnItemClickListener!= null){
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder, position);

                    /* Open the book reader activity
                    Intent intent=new Intent(context,NewsActivity.class);
                    intent.putExtra("News",newses.get(j));
                    context.startActivity(intent);*/
                }
            });

            holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder, position);
                    return false;
                }
            });
        }
    }

    //define Item Click Listener
    public class OnRecyclerViewItemClickListener implements OnItemMoveListener {

        public void onItemClick(MyViewHolder holder, int position) {
            String bookName = mBookList.get(position).get_BookName();
            //Toast.makeText(mContext, "onClick事件", Toast.LENGTH_SHORT).show();
        }


        public void onItemLongClick(MyViewHolder holder, int position) {
            String bookName = mBookList.get(position).get_BookName();
            //Toast.makeText(mContext, "onLongClick事件", Toast.LENGTH_SHORT).show();

            //holder.mBookRemoveIcon.setVisibility(View.VISIBLE);
        }

        /**
         * 拖拽排序相关
         */
        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            //Toast.makeText(mContext, "onItemMove事件", Toast.LENGTH_SHORT).show();

            BookData book = mBookList.get(fromPosition);

            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mBookList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mBookList, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            return;
        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position=viewHolder.getAdapterPosition();
            remove(position);
            notifyItemRemoved(position);
        }
    }


    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.book_card_item, parent, false);

        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements OnDragVHListener{

        CardView mCardView = null;

        ImageView mBookImageView = null;
        TextView  mBookNameTextView = null;
        TextView  mBookAuthorTextView = null;
        ImageView mBookRemoveIcon = null;

        public MyViewHolder(View view) {
            super(view);

            mCardView= (CardView) itemView.findViewById(R.id.card_view);
            mBookImageView =(ImageView) view.findViewById(R.id.book_image);
            mBookNameTextView = (TextView) view.findViewById(R.id.book_name);
            mBookAuthorTextView = (TextView) view.findViewById(R.id.book_author);
            mBookRemoveIcon =(ImageView) view.findViewById(R.id.button_remove);
        }

        public OnRecyclerViewItemClickListener getViewOnItemClickListener(){
            return getOnItemClickListener();
        }

        @Override
        public void onItemSelected() {
            //Toast.makeText(mContext,"onItemSelected事件",Toast.LENGTH_SHORT).show();

            //开始推拽排序,可以设置被拖拽Item的背景
            itemView.setBackgroundResource(R.color.colorPrimary);
        }

        @Override
        public void onItemFinish() {
            //Toast.makeText(mContext,"onItemFinish事件",Toast.LENGTH_SHORT).show();

            //推拽排序结束,也可以设置被拖拽Item的背景
            itemView.setBackgroundResource(R.color.white);
        }

    }

    public enum PositionInBookList {BEGIN, END, MIDDLE}

    public void add(PositionInBookList location) {
        BookData book;

        if(mRemovedBookList.size()==0) {
            Toast.makeText(mContext,"所有图书已经添加完了:-)",
                    Toast.LENGTH_SHORT).show();
            return;
        }else {
            int index = (int) Math.random() * mRemovedBookList.size();
            book = mRemovedBookList.get(index);
            mRemovedBookList.remove(index);
        }

        int position = 0;
        switch(location){
            case BEGIN:
                position = 0;
                break;
            case MIDDLE:
                position = mBookList.size() / 2;
                break;
            case END:
                position = mBookList.size()-1;
                break;
        }

        mBookList.add(position, book);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mBookList.size());
    }

    public void remove(int position) {
        mRemovedBookList.add(mBookList.get(position));
        mBookList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mBookList.size());
    }

    public void addAll(){
        int number = mRemovedBookList.size();
        for(int i=0; i<number; i++)
           add(PositionInBookList.BEGIN);
    }

}
