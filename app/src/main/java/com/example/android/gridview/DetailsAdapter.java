package com.example.android.gridview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.gridview.Utils.DetailsDataHolder;
import com.example.android.gridview.Utils.Review;
import com.example.android.gridview.Utils.Trailer;
import com.squareup.picasso.Picasso;

/**
 * Created by mina essam on 27-Nov-16.
 */
public class DetailsAdapter extends BaseAdapter {
    private Context context;
    private static int VIEW_SUPER_DETAILS=0;
    private static int VIEW_TRAILER=1;
    private static int VIEW_REVIEW=2;

    public DetailsAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return (Trailer.trailers.size()+ Review.reviews.size()+1);
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    static class SuperDetailsHolder{

        TextView name;
        TextView adult;
        ImageView movieImage;
        TextView date;
        TextView vote;
        TextView overView;
        TextView languageTv;
        ImageView favouriteImage;

    }
    static class TrailersHolder{
        ImageView playImage;
        TextView trailerName;
    }
  static class ReviewsHolder{
      TextView author;
      TextView content;
  }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type=getItemViewType(position);

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           if(type==VIEW_SUPER_DETAILS){
               convertView=inflater.inflate(R.layout.super_details,null);
               SuperDetailsHolder holder=new SuperDetailsHolder();
               holder.name=(TextView) convertView.findViewById(R.id.name);
               holder.adult=(TextView) convertView.findViewById(R.id.adult);
               holder.movieImage=(ImageView) convertView.findViewById(R.id.image);
               holder.favouriteImage=(ImageView) convertView.findViewById(R.id.favourite_icon);
               holder.date=(TextView) convertView.findViewById(R.id.date);
               holder.vote=(TextView) convertView.findViewById(R.id.vote);
               holder.overView=(TextView) convertView.findViewById(R.id.overView);
               holder.languageTv=(TextView) convertView.findViewById(R.id.language);
               convertView.setTag(holder);
           }else if(type==VIEW_TRAILER){
               convertView=inflater.inflate(R.layout.trailers_list_item,null);
               TrailersHolder holder=new TrailersHolder();
               holder.trailerName=(TextView) convertView.findViewById(R.id.trailer_name);
               holder.playImage=(ImageView) convertView.findViewById(R.id.trailer_image);
               convertView.setTag(holder);
           }else {
               convertView=inflater.inflate(R.layout.review_list_item,null);
               ReviewsHolder holder=new ReviewsHolder();
               holder.author=(TextView) convertView.findViewById(R.id.author_name);
               holder.content=(TextView) convertView.findViewById(R.id.content_review);
               convertView.setTag(holder);
           }


        }
        // not null converView

            if(type==VIEW_SUPER_DETAILS){
                SuperDetailsHolder holder=(SuperDetailsHolder) convertView.getTag();
                // star image
                if(DetailsDataHolder.starState){
                    holder.favouriteImage.setImageResource(R.drawable.added);
                }
                else {
                    holder.favouriteImage.setImageResource(R.drawable.favourite);
                }
                // adult view
                if(DetailsDataHolder.adult){
                   holder.adult.setText("+18");
                 holder.adult.setBackgroundDrawable( convertView.getResources().getDrawable(R.drawable.adult_warnning) );

                }
                else {
                    holder.adult.setText("safe");
                holder.adult.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.adult_safe));

                }


                holder.languageTv.setText(DetailsDataHolder.language);
                holder.overView.setText(DetailsDataHolder.overView);
                holder.vote.setText(String.valueOf(DetailsDataHolder.vote));
                holder.name.setText(DetailsDataHolder.name);
                holder.date.setText(DetailsDataHolder.date);
                Picasso.with(context).load(DetailsDataHolder.iconURL).into(holder.movieImage);

            }
            else if(type==VIEW_TRAILER){
                TrailersHolder holder=(TrailersHolder) convertView.getTag();
                holder.trailerName.setText(Trailer.trailers.get(position-1).name);
                String s=Trailer.trailers.get(position-1).name;

            }
            else {
                ReviewsHolder holder=(ReviewsHolder)convertView.getTag();
                holder.author.setText(Review.reviews.get(position-Trailer.trailers.size()).author);
                holder.content.setText(Review.reviews.get(position-Trailer.trailers.size()).content);
                String s=Review.reviews.get(position-Trailer.trailers.size()).author;

            }


        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
       if(position==0){
           Log.v("Type VIEW_SUPER_DETAILS",""+position);
           return VIEW_SUPER_DETAILS;
       }
        else if(position<=Trailer.trailers.size()){
           Log.v("Type VIEW_TRAILER",""+position);
           return VIEW_TRAILER;

       }
        else {
           Log.v("Type VIEW_REVIEW",""+position);
           return VIEW_REVIEW;
       }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }
}
