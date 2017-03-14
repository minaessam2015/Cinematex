package com.example.android.gridview.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mina essam on 25-Nov-16.
 */
public class Review {
    public static String author;
    public static String content;
   public static List<Review> reviews=new ArrayList<>();
    public Review(String author,String content){
        this.author=author;
        this.content=content;
    }

}
