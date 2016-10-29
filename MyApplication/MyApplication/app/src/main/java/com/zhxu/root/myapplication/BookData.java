package com.zhxu.root.myapplication;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by zhxu on 10/24/16.
 * Purpose:  manage electrical book object
 */

public class BookData implements Serializable{
    private Integer m_id;           //编码
    private String  m_BookName;     //书名
    private String  m_Author;       //作者
    private Bitmap  m_BookImage;    //封面

    private BookData(String bookName, String bookAuthor) {
        this.m_BookName = bookName;
        this.m_Author = bookAuthor;
        this.m_id = 0;
        this.m_BookImage = null;
    }

    public static BookData createBookData(String bookName, String bookAuthor) {
        return new BookData(bookName, bookAuthor);
    }

    public Integer get_id() {
        return m_id;
    }

    protected void set_id(Integer id) {
        this.m_id = id;
    }

    public String get_BookName() {
        return m_BookName;
    }

    public void set_BookName(String bookName) {
        this.m_BookName = bookName;
    }

    public String get_Author() {
        return m_Author;
    }

    public void set_Author(String author) {
        this.m_Author = author;
    }

    public Bitmap get_BookImage() {
        return m_BookImage;
    }

    public void set_BookImage(Bitmap bookImage) {
        this.m_BookImage = bookImage;
    }

    public String toString(){
        return "Book[ID:" + m_id + ", Book Name:" + m_BookName + ", Book Author:" + m_Author + "]";
    }
}
