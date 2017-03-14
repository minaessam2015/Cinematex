package com.example.android.gridview.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mina essam on 20-Nov-16.
 */
public  class Trailer {
   public static List<Trailer> trailers=new ArrayList<>();
   public String name;
  public   String KEY;
  public   String trailerURL="https://www.youtube.com/watch?v=";
    public Trailer(String name,String Key){
        this.name=name;
        this.KEY=Key;
        this.trailerURL+=KEY;
    }
}