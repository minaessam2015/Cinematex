package com.example.android.gridview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.gridview.Utils.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by mina essam on 15-Oct-16.
 */
public class ImageAdapter extends BaseAdapter {
private Context mContext;



    public ImageAdapter(Context c){
        mContext=c;
    }

    @Override
    public int getCount() {

        return Movie.movies.size();

    }


    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;



        if(convertView==null){
            imageView=new ImageView(mContext);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(mContext).load(Movie.movies.get(position).getIconURL()).into(imageView);
        }
        else {
            imageView=(ImageView)convertView;

        }



        return imageView;
    }


}
