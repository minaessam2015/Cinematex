package com.example.android.gridview;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.gridview.Utils.Trailer;

/**
 * Created by mina essam on 20-Nov-16.
 */
public class TrailersAdapter extends BaseAdapter {
    private Context mContext;
    private  int trailerNumber=0;
    public TrailersAdapter(Context context){
        mContext=context;

    }
    @Override
    public int getCount() {
        return Trailer.trailers.size();
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
         public final ImageView image;
         public final TextView name;
        public ViewHolder(View v){
            image=(ImageView) v.findViewById(R.id.trailer_image);
            name=(TextView)v.findViewById(R.id.trailer_name);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){

           convertView= inflater.inflate(R.layout.trailers_list_item,null);
           ViewHolder viewHolder=new ViewHolder(convertView);
           // viewHolder.image=(ImageView) convertView.findViewById(R.id.trailer_image);
          //  viewHolder.name=(TextView)convertView.findViewById(R.id.trailer_name);
            convertView.setTag(viewHolder);

        }
       ViewHolder holder=(ViewHolder) convertView.getTag();
      holder.name.setText(Trailer.trailers.get(position).name);
        holder.image.setImageResource(R.drawable.play);
        final String trailerUrl=Trailer.trailers.get(position).trailerURL;
      convertView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(Intent.ACTION_VIEW,
                      Uri.parse (trailerUrl));
              try {
                  mContext.startActivity(intent);
              } catch (ActivityNotFoundException ex) {
                  Log.v("Internet intent","cannot open intent");
              }
          }
      });

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
    public void setTrailerNumber(int number){
        trailerNumber=number;
        notifyDataSetChanged();
    }
}
