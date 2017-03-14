package com.example.android.gridview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.gridview.Data.MovieContract;
import com.example.android.gridview.Data.MovieContract.MovieTable;
import com.example.android.gridview.Data.MovieDbHelper;
import com.example.android.gridview.Utils.DetailsDataHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by mina essam on 15-Oct-16.
 */
public class details extends AppCompatActivity {
    Integer id;
    String iconURL;

    TextView name;
    ImageView movieImage;
    TextView adult;
    ImageView favouriteImage;
    TextView date;
    TextView vote;
    TextView overView;
    TextView languageTv;
//    ImageView favouriteImage;
//   int position;
//    String reviewURL;
//    String trailerURL;
//    ListView listView;

//   DetailsAdapter adapter=new DetailsAdapter(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);


Intent intent =getIntent();
       Bundle bundle=intent.getExtras();

id=bundle.getInt("id");
     iconURL= bundle.getString("url");
    name=(TextView)findViewById(R.id.name);
        name.setText(bundle.getString("title"));
        movieImage=(ImageView)findViewById(R.id.image);
        Picasso.with(this).load(iconURL).into(movieImage);

        adult=(TextView) findViewById(R.id.adult);
        if(bundle.getBoolean("adult")){
            adult.setText("+18");
            adult.setBackgroundDrawable(getResources().getDrawable(R.drawable.adult_warnning) );

        }
        else {
            adult.setText("safe");
            adult.setBackgroundDrawable(getResources().getDrawable(R.drawable.adult_safe));

        }
        date=(TextView)findViewById(R.id.date);
        date.setText(bundle.getString("date"));
        vote=(TextView)findViewById(R.id.vote);
        vote.setText(bundle.getString("date"));
        overView=(TextView)findViewById(R.id.overView);
        overView.setText(bundle.getString("overView"));
        languageTv=(TextView)findViewById(R.id.language);
        languageTv.setText(bundle.getString("language"));

        favouriteImage=(ImageView)findViewById(R.id.favourite_icon);

        if(bundle.getBoolean("star")){
            favouriteImage.setImageResource(R.drawable.added);
            favouriteImage.setTag(R.drawable.added);
        }
        else {
            favouriteImage.setImageResource(R.drawable.favourite);
            favouriteImage.setTag(R.drawable.favourite);
        }

//        MovieDbHelper helper=new MovieDbHelper(this);
//        SQLiteDatabase db=helper.getReadableDatabase();
//
//
//
//
//      Intent intent=getIntent();
//        Bundle bundle=intent.getExtras();

//        position=bundle.getInt("position");
//        url=bundle.getString("url");
//
//        overview =bundle.getString("overView");
//        ImageView image= (ImageView) findViewById(R.id.image);
//       // ImageAdapter imageAdapter=new ImageAdapter(getApplicationContext());
//        Picasso.with(this).load(url).into(image);
//
//        TextView overView=(TextView)findViewById(R.id.overView);
//        overView.setText(overview);
//
//        TextView name=(TextView)findViewById(R.id.name);
//        title=bundle.getString("title");
//        name.setText(title);
//
//       TextView path=(TextView)findViewById(R.id.language);
//        language=bundle.getString("language");
//        path.setText(language);
//
//        TextView date=(TextView)findViewById(R.id.date);
//        releaseDate=bundle.getString("date");
//        date.setText(releaseDate);
//
//        TextView vote=(TextView)findViewById(R.id.vote);
//        movieVote=bundle.getDouble("vote");
//        vote.setText(Double.toString(movieVote));


//         id=bundle.getInt("id");
//         position=bundle.getInt("position");

//        TextView adult=(TextView)findViewById(R.id.adult);
//        boolean ad=bundle.getBoolean("adult");
//        if(ad){
//            adult.setText("+18");
//                adult.setBackgroundDrawable( getResources().getDrawable(R.drawable.adult_warnning) );
//            adultState=MovieContract.ADULT;
//        }
//        else{
//            adult.setText("safe");
//            adult.setBackgroundDrawable(getResources().getDrawable(R.drawable.adult_safe));
//            adultState=MovieContract.ADULT_SAFE;
//        }

        // checking the star image
 // favouriteImage=(ImageView)findViewById(R.id.favourite_icon);


//        Cursor cursor= db.rawQuery(" SELECT "+MovieTable.MOVIE_ID_COLUMN+ " FROM "+
//                MovieTable.TABLE_NAME+" WHERE "+MovieTable.MOVIE_ID_COLUMN+" = "+id.toString()+";", null);
//        if(cursor.getCount()>0)
//        {
//
////            favouriteImage.setImageResource(R.drawable.added);
////            favouriteImage.setTag(R.drawable.added);
//            DetailsDataHolder.starState=true;
//        }
//else {
////            favouriteImage.setImageResource(R.drawable.favourite);
////            favouriteImage.setTag(R.drawable.favourite);
//            DetailsDataHolder.starState=false;
//        }


//        Movie.setBaseUrl(Movie.movies.get(position).getID().toString()+"/videos");
//        trailerURL=Movie.movies.get(position).getBaseUrl();
//        Log.v("Trailer URL",trailerURL);
//        GetTrailers getTrailers=new GetTrailers();
//        getTrailers.execute();
//
//        Movie.setBaseUrl(Movie.movies.get(position).getID().toString()+"/reviews");
//        reviewURL=Movie.movies.get(position).getBaseUrl();
//        Log.v("Review URL",reviewURL);
//        GetReviews getReviews=new GetReviews();
//      //  getReviews.execute();
//        listView=(ListView)findViewById(R.id.details_listview);
//      //  listView.setAdapter(adapter);





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
//    public void updateUI(){
//        DetailsAdapter adapter=new DetailsAdapter(this);
//        listView.setAdapter(adapter);
//    }

     public void addToFavourite(View v){

         Integer id=(Integer) favouriteImage.getTag(); // favourite image resource id
         id = id == null ? 0 : id;
         Log.v("tag = ",id.toString());
         Log.v("drawable = ", ((Integer) R.drawable.favourite).toString());
         if(id==R.drawable.favourite)
         {
        favouriteImage.setImageResource(R.drawable.added);
             favouriteImage.setTag(R.drawable.added);
       insertRow();
         }
         else {
             favouriteImage.setImageResource(R.drawable.favourite);
             favouriteImage.setTag(R.drawable.favourite);
             removeRow();

         }
    }
    protected void insertRow(){
        int adultState;
        MovieDbHelper movieDbHelper=new MovieDbHelper(this);
        SQLiteDatabase db=movieDbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(MovieTable.MOVIE_ID_COLUMN,id);
        if(DetailsDataHolder.adult) adultState=MovieContract.ADULT;
        else adultState=MovieContract.ADULT_SAFE;
        contentValues.put(MovieTable.ADULT_COLUMN,adultState);
        contentValues.put(MovieTable.TITLE_COLUMN,DetailsDataHolder.name);
        contentValues.put(MovieTable.LANGUAGE_COLUMN,DetailsDataHolder.language);
        contentValues.put(MovieTable.RELEASE_DATE_COLUMN,DetailsDataHolder.date);
        contentValues.put(MovieTable.ICON_URL_COLUMN,DetailsDataHolder.iconURL);
        contentValues.put(MovieTable.VOTE_COLUMN,DetailsDataHolder.vote);
        contentValues.put(MovieTable.OVERVIEW_COLUMN,DetailsDataHolder.overView);
        long id=db.insert(MovieTable.TABLE_NAME,null,contentValues);
    }
    protected void removeRow(){
        MovieDbHelper movieDbHelper=new MovieDbHelper(this);
        SQLiteDatabase db=movieDbHelper.getWritableDatabase();
        db.execSQL(MovieContract.DELETE_BY_MOVIE_ID + id +" ;");
        Log.v("Delete ",MovieContract.DELETE_BY_MOVIE_ID +id+" ;");
    }
//    protected  void getReview(View v){
//        Movie.setBaseUrl(Movie.movies.get(position).getID().toString()+"/reviews");
//
//        Intent intent=new Intent(this,ReviewsActivity.class);
//        intent.putExtra("URL",Movie.movies.get(position).getBaseUrl());
//        startActivity(intent);
//    }

//    class GetTrailers extends AsyncTask<Void,Void,Void>{
//
//        private JSONObject data;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//
//            // Will contain the raw JSON response as a string.
//            String trailerJsonStr = null;
//            Uri builtUri;
//
//           builtUri =Uri.parse(trailerURL).buildUpon()
//                    .build();
//
//
//
//            try {
//
//                URL url=new URL(builtUri.toString());
//                Log.v("URI ","Built Url : "+builtUri.toString());
//
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//                // if was error in response code
//                int response = urlConnection.getResponseCode();
//                // Log.v("Response Code : ",String.valueOf(response) );
//                if(response!=200){
//                    Log.e("Response Code : ",String.valueOf(response) );
//                    return null;
//                }
//
//                // Read the input stream into a String
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//
//                    return null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//
//                    buffer.append(line + "\n");
//                }
//
//                if (buffer.length() == 0) {
//
//                    return null;
//                }
//                trailerJsonStr = buffer.toString();
//            } catch (IOException e) {
//                Log.e("Cannot Read ", "Error ", e);
//                // If the code didn't successfully get the weather data, there's no point in attemping
//                // to parse it.
//                return null;
//            }
//            finally{
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e("Closing Stream", "Error closing stream", e);
//                    }
//                }
//            }
//            Log.v("trailerJsonStr ",trailerJsonStr);
//            parseJSON(trailerJsonStr);
//
//            return null;
//        }
//        public void parseJSON(String json){
//
//
//            try {
//
//   Log.v("Trailers size",""+ Trailer.trailers.size());                 Trailer.trailers.clear();
//
//
//
//                data=new JSONObject(json);
//
//                // int pageNum=data.getInt("page");
//                JSONArray results=data.getJSONArray("results");
//
//                for (int i=0;i<results.length();i++){
//                    JSONObject inResult=results.getJSONObject(i);
//                    Trailer.trailers.add(new Trailer(inResult.getString("name"),inResult.getString("key")));
//                    Log.v("name",inResult.getString("name"));
//                    Log.v("key",inResult.getString("key"));
//                }
//
//            }
//            catch (JSONException e){
//                Log.e("Formating JSON "," ERROR ");
//            }
//
//
//
//        }
//
//        @Override
//        protected void onPostExecute(Void a) {
//
//            Log.v("number of trailers",""+Trailer.trailers.size());
//            if(Trailer.trailers.size()>0) updateUI();
//        }
//
//
//
//        }
//
//    class GetReviews extends AsyncTask<Void,Void,Void>{
//
//        private JSONObject data;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//
//            // Will contain the raw JSON response as a string.
//            String  reviewJsonStr = null;
//            Uri builturi;
//
//            builturi =Uri.parse(reviewURL).buildUpon()
//                    .build();
//
//
//
//            try {
//
//                URL url=new URL(builturi.toString());
//                Log.v("URI ","Built Url : "+builturi.toString());
//
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//                // if was error in response code
//                int response = urlConnection.getResponseCode();
//                // Log.v("Response Code : ",String.valueOf(response) );
//                if(response!=200){
//                    Log.e("Response Code : ",String.valueOf(response) );
//                    return null;
//                }
//
//                // Read the input stream into a String
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//
//                    return null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//
//                    buffer.append(line + "\n");
//                }
//
//                if (buffer.length() == 0) {
//
//                    return null;
//                }
//                reviewJsonStr = buffer.toString();
//            } catch (IOException e) {
//                Log.e("Cannot Read ", "Error ", e);
//                // If the code didn't successfully get the weather data, there's no point in attemping
//                // to parse it.
//                return null;
//            }
//            finally{
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e("Closing Stream", "Error closing stream", e);
//                    }
//                }
//            }
//            Log.v("Json ",reviewJsonStr);
//            parseJSON(reviewJsonStr);
//            return null;
//        }
//        public void parseJSON(String json){
//
//            try {
//                Review.reviews.clear();
//                data=new JSONObject(json);
//
//                // int pageNum=data.getInt("page");
//                JSONArray results=data.getJSONArray("results");
//
//                for (int i=0;i<results.length();i++){
//                    JSONObject inResult=results.getJSONObject(i);
//                    Review.reviews.add(new Review(inResult.getString("author"),inResult.getString("content")));
//
//                }
//
//            }
//            catch (JSONException e){
//                Log.e("Formating JSON "," ERROR ");
//            }
//
//        }
//
//        @Override
//        protected void onPostExecute(Void a) {
//
//            super.onPostExecute(a);
//
//            Log.v("adapder count",String.valueOf(adapter.getCount()));
//
//            Log.v("number of reviews",String.valueOf(Review.reviews.size()));
//           if(Review.reviews.size()>0) updateUI();
//
//        }
//    }
    }



