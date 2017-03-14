package com.example.android.gridview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.gridview.Data.MovieContract;
import com.example.android.gridview.Data.MovieDbHelper;
import com.example.android.gridview.Utils.DetailsDataHolder;
import com.example.android.gridview.Utils.Movie;
import com.example.android.gridview.Utils.Review;
import com.example.android.gridview.Utils.Trailer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SecondDetails extends AppCompatActivity {
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.second_details);
//        SecondDetailsFragment secondDetailsFragment=new SecondDetailsFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.second_details_fragment,secondDetailsFragment).commit();
//    }

    ListView trailersLV;
    ListView reviewsLV;
    TrailersAdapter trailersAdapter;
    String trailerURL;
    ReviewAdapter reviewAdapter;
    String reviewURL;
    int id;
    TextView name;
    ImageView movieImage;
    TextView adult;
    ImageView favouriteImage;
    TextView date;
    TextView vote;
    TextView overView;
    TextView languageTv;

    TextView reviews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_details_fragment);

        name=(TextView) findViewById(R.id.name);
        adult=(TextView) findViewById(R.id.adult);
        movieImage=(ImageView) findViewById(R.id.image);
        favouriteImage=(ImageView) findViewById(R.id.favourite_icon);
        date=(TextView) findViewById(R.id.date);
        vote=(TextView) findViewById(R.id.vote);
        overView=(TextView)findViewById(R.id.overView);
        languageTv=(TextView) findViewById(R.id.language);
        reviews=(TextView)findViewById(R.id.reviews_textview);

        trailersLV=(ListView)findViewById(R.id.trailers_listview);
        reviewsLV=(ListView)findViewById(R.id.reviews_listview); //takecare
        reviewAdapter=new ReviewAdapter(this);
        trailersAdapter=new TrailersAdapter(this);

        trailersLV.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });


        reviewsLV.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });



        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        int position=bundle.getInt("position");


        id=bundle.getInt("id");
        MovieDbHelper helper=new MovieDbHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor= db.rawQuery(" SELECT "+ MovieContract.MovieTable.MOVIE_ID_COLUMN+ " FROM "+
                MovieContract.MovieTable.TABLE_NAME+" WHERE "+ MovieContract.MovieTable.MOVIE_ID_COLUMN+" = "+String .valueOf(id)+";", null);
        if(cursor.getCount()>0)
        {

            DetailsDataHolder.starState=true;
        }
        else {

            DetailsDataHolder.starState=false;
        }


        if(DetailsDataHolder.starState){
            favouriteImage.setImageResource(R.drawable.added);
            favouriteImage.setTag(R.drawable.added);
        }
        else {
            favouriteImage.setImageResource(R.drawable.favourite);
            favouriteImage.setTag(R.drawable.favourite);
        }
        // adult view
        if(DetailsDataHolder.adult){
            adult.setText("+18");
            adult.setBackgroundDrawable( getResources().getDrawable(R.drawable.adult_warnning) );

        }
        else {
            adult.setText("safe");
            adult.setBackgroundDrawable(getResources().getDrawable(R.drawable.adult_safe));

        }


        languageTv.setText(DetailsDataHolder.language);
        overView.setText(DetailsDataHolder.overView);
        vote.setText(String.valueOf(DetailsDataHolder.vote));
        name.setText(DetailsDataHolder.name);
        date.setText(DetailsDataHolder.date);
        Picasso.with(this).load(DetailsDataHolder.iconURL).into(movieImage);

//trailersLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//});
        Movie.setBaseUrl(Movie.movies.get(position).getID().toString()+"/videos");
        trailerURL=Movie.movies.get(position).getBaseUrl();
        trailersLV.setAdapter(trailersAdapter);
        GetTrailers getTrailer=new GetTrailers();
        getTrailer.execute();

        Movie.setBaseUrl(Movie.movies.get(position).getID().toString()+"/reviews");
        reviewURL=Movie.movies.get(position).getBaseUrl();
        reviewsLV.setAdapter(reviewAdapter);
        GetReviews getReviews=new GetReviews();
        getReviews.execute();
    }

    public void updateUI(){
        trailersAdapter.notifyDataSetChanged();
        trailersLV.setMinimumHeight(Trailer.trailers.size()*trailersLV.getHeight());
        if(Review.reviews.size()==0) {
            reviewsLV.setVisibility(View.GONE);
            reviews.setVisibility(View.GONE);
        }
        else {
            reviews.setVisibility(View.VISIBLE);
            reviewsLV.setVisibility(View.VISIBLE);
        }
    }

    public void addToFavourite(View v){

        Integer id=(Integer) favouriteImage.getTag(); // favourite image resource id
//        id = id == null ? 0 : id;
        // id=    favouriteImage.getDrawable().getConstantState()
        //   Log.v("tag = ",id.toString());
        // Log.v("drawable = ", ((Integer) R.drawable.favourite).toString());

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
        contentValues.put(MovieContract.MovieTable.MOVIE_ID_COLUMN,id);
        if(DetailsDataHolder.adult) adultState=MovieContract.ADULT;
        else adultState=MovieContract.ADULT_SAFE;
        contentValues.put(MovieContract.MovieTable.ADULT_COLUMN,adultState);
        contentValues.put(MovieContract.MovieTable.TITLE_COLUMN,DetailsDataHolder.name);
        contentValues.put(MovieContract.MovieTable.LANGUAGE_COLUMN,DetailsDataHolder.language);
        contentValues.put(MovieContract.MovieTable.RELEASE_DATE_COLUMN,DetailsDataHolder.date);
        contentValues.put(MovieContract.MovieTable.ICON_URL_COLUMN,DetailsDataHolder.iconURL);
        contentValues.put(MovieContract.MovieTable.VOTE_COLUMN,DetailsDataHolder.vote);
        contentValues.put(MovieContract.MovieTable.OVERVIEW_COLUMN,DetailsDataHolder.overView);
        long id=db.insert(MovieContract.MovieTable.TABLE_NAME,null,contentValues);
    }
    protected void removeRow(){
        MovieDbHelper movieDbHelper=new MovieDbHelper(this);
        SQLiteDatabase db=movieDbHelper.getWritableDatabase();
        db.execSQL(MovieContract.DELETE_BY_MOVIE_ID + id +" ;");
        Log.v("Delete ",MovieContract.DELETE_BY_MOVIE_ID +id+" ;");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Trailer.trailers.clear();
        Review.reviews.clear();
    }

    class GetTrailers extends AsyncTask<Void,Void,Void> {

        private JSONObject data;

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String trailerJsonStr = null;
            Uri builtUri;

            builtUri =Uri.parse(trailerURL).buildUpon()
                    .build();



            try {

                URL url=new URL(builtUri.toString());
                Log.v("URI ","Built Url : "+builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // if was error in response code
                int response = urlConnection.getResponseCode();
                // Log.v("Response Code : ",String.valueOf(response) );
                if(response!=200){
                    Log.e("Response Code : ",String.valueOf(response) );
                    return null;
                }

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                trailerJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("Cannot Read ", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            }
            finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Closing Stream", "Error closing stream", e);
                    }
                }
            }
            Log.v("trailerJsonStr ",trailerJsonStr);
            parseJSON(trailerJsonStr);

            return null;
        }
        public void parseJSON(String json){


            try {

                Log.v("Trailers size",""+ Trailer.trailers.size());                 Trailer.trailers.clear();



                data=new JSONObject(json);

                // int pageNum=data.getInt("page");
                JSONArray results=data.getJSONArray("results");

                for (int i=0;i<results.length();i++){
                    JSONObject inResult=results.getJSONObject(i);
                    Trailer.trailers.add(new Trailer(inResult.getString("name"),inResult.getString("key")));
                    Log.v("name",inResult.getString("name"));
                    Log.v("key",inResult.getString("key"));
                }

            }
            catch (JSONException e){
                Log.e("Formating JSON "," ERROR ");
            }



        }

        @Override
        protected void onPostExecute(Void a) {

            Log.v("number of trailers",""+Trailer.trailers.size());
            int number=Trailer.trailers.size();
            if(number>0){
                trailersAdapter.setTrailerNumber(number);
                // updateUI();
            }
        }



    }



    class GetReviews extends AsyncTask<Void,Void,Void>{

        private JSONObject data;

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String  reviewJsonStr = null;
            Uri builturi;

            builturi =Uri.parse(reviewURL).buildUpon()
                    .build();



            try {

                URL url=new URL(builturi.toString());
                Log.v("URI ","Built Url : "+builturi.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // if was error in response code
                int response = urlConnection.getResponseCode();
                // Log.v("Response Code : ",String.valueOf(response) );
                if(response!=200){
                    Log.e("Response Code : ",String.valueOf(response) );
                    return null;
                }

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                reviewJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("Cannot Read ", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            }
            finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Closing Stream", "Error closing stream", e);
                    }
                }
            }
            Log.v("Json ",reviewJsonStr);
            parseJSON(reviewJsonStr);
            return null;
        }
        public void parseJSON(String json){

            try {
                Review.reviews.clear();
                data=new JSONObject(json);

                // int pageNum=data.getInt("page");
                JSONArray results=data.getJSONArray("results");

                for (int i=0;i<results.length();i++){
                    JSONObject inResult=results.getJSONObject(i);
                    Review.reviews.add(new Review(inResult.getString("author"),inResult.getString("content")));

                }

            }
            catch (JSONException e){
                Log.e("Formating JSON "," ERROR ");
            }

        }

        @Override
        protected void onPostExecute(Void a) {

            super.onPostExecute(a);
            int number=Review.reviews.size();
            if(number>0){
                reviewAdapter.setReviewsNumber(number);
                //updateUI();
            }

        }
    }

}
