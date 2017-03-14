package com.example.android.gridview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gridview.Utils.Review;

/**
 * Created by mina essam on 25-Nov-16.
 */
public class ReviewAdapter extends BaseAdapter {
    private Context context;
    int pos=0;
    private int reviewsNumber=0;
      public ReviewAdapter(Context context){
          this.context=context;
      }
    @Override
    public int getCount() {
        return reviewsNumber;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder{
       public  final TextView author;
         public final TextView content;
        public final TextView noData;
      public ViewHolder(View convertView){
    author=(TextView)convertView.findViewById(R.id.author_name);
    content=(TextView)convertView.findViewById(R.id.content_review);
          noData=(TextView) convertView.findViewById(R.id.no_reviews);
    }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.review_list_item,null);
            ViewHolder viewHolder=new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        }
        ViewHolder holder=(ViewHolder) convertView.getTag();
//        if(pos>position){
//            pos=position;
//        }
       if(reviewsNumber>0){

        Log.v("pos",String.valueOf(position));
           holder.noData.setVisibility(View.GONE);
           holder.author.setVisibility(View.VISIBLE);
           holder.content.setVisibility(View.VISIBLE);
        holder.author.setText(Review.reviews.get(position).author);
        holder.content.setText(Review.reviews.get(position).content);
        Log.v("review author",Review.reviews.get(position).author);
       // pos++;
              }
        else {

           holder.content.setVisibility(View.GONE);
           holder.author.setVisibility(View.GONE);
           holder.noData.setVisibility(View.VISIBLE);
       }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"item "+position,Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    void setReviewsNumber (int number){
        reviewsNumber=number;
        notifyDataSetChanged();
    }
}
