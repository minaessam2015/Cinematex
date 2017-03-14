package com.example.android.gridview;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.android.gridview.Data.MovieContract;
import com.squareup.picasso.Picasso;

/**
 * Created by mina essam on 25-Nov-16.
 */
public class MovieCursorAdapter extends CursorAdapter {

    public static int NORMAL_STATE=0;
    public static int NO_DATABASE=1;
    public static int DATABASE_REMOVED=2;

    public MovieCursorAdapter(Context context,Cursor cursor){

        super(context,cursor,0);

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.v("newView Method","I'm HERE");
        return LayoutInflater.from(context).inflate(R.layout.image_grid_item,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

  Log.v("bindView Method","I'm HERE");

        ImageView imageView=(ImageView)view.findViewById(R.id.movie_image);
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(MovieContract.MovieTable.ICON_URL_COLUMN))).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setTag(cursor.getInt(cursor.getColumnIndex(MovieContract.MovieTable.ID)));


    }



}
