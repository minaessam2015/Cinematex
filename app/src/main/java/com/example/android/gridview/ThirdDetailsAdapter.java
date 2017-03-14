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
import com.example.android.gridview.Utils.Trailer;
import com.squareup.picasso.Picasso;

/**
 * Created by mina essam on 29-Nov-16.
 */
public class ThirdDetailsAdapter extends BaseAdapter {
    private static int DETAILS_TYPE=0;
    private static int TRAILERS_TYPE =1;
    private Context context;
    private static int trailersNumber=0;
    private static int pos=0;
    public ThirdDetailsAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
       return trailersNumber+1;
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

        public final TextView name;
         public final TextView adult;
        public final ImageView movieImage;
        public final TextView date;
        public final TextView vote;
        public final TextView overView;
        public final TextView languageTv;
        public final ImageView favouriteImage;
        public SuperDetailsHolder(View v){
           name=(TextView) v.findViewById(R.id.name);
            adult=(TextView) v.findViewById(R.id.adult);
           movieImage=(ImageView) v.findViewById(R.id.image);
            favouriteImage=(ImageView) v.findViewById(R.id.favourite_icon);
           date=(TextView) v.findViewById(R.id.date);
           vote=(TextView) v.findViewById(R.id.vote);
            overView=(TextView) v.findViewById(R.id.overView);
            languageTv=(TextView) v.findViewById(R.id.language);
        }

    }
    static class TrailersHolder{
        ImageView playImage;
        TextView trailerName;
        public TrailersHolder(View v){
           trailerName=(TextView) v.findViewById(R.id.trailer_name);
           playImage=(ImageView) v.findViewById(R.id.trailer_image);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type=getItemViewType(position);
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (type == DETAILS_TYPE) {
                convertView = inflater.inflate(R.layout.super_details, null);
                SuperDetailsHolder holder=new SuperDetailsHolder(convertView);
                convertView.setTag(holder);

            } else if(type==TRAILERS_TYPE||trailersNumber>0) {
                convertView = inflater.inflate(R.layout.trailers_list_item, null);
                TrailersHolder holder = new TrailersHolder(convertView);
                convertView.setTag(holder);
            }
        }

        if(type==DETAILS_TYPE){
           SuperDetailsHolder holder=(SuperDetailsHolder) convertView.getTag();

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

        }else if(type==TRAILERS_TYPE||trailersNumber>0) {
            Log.v("Trailers_type","I was forced to call");
       TrailersHolder holder=(TrailersHolder)convertView.getTag();

            holder.trailerName.setText(Trailer.trailers.get(pos).name);
           pos++;
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return (position==0)? DETAILS_TYPE: TRAILERS_TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    public void setTrailersNumber(int trailersNumber) {
        this.trailersNumber = trailersNumber;
        pos=0;
        notifyDataSetChanged();
    }
}
