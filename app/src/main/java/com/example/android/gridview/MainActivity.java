package com.example.android.gridview;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gridview.Utils.DetailsDataHolder;
import com.example.android.gridview.Utils.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageAdapter imageAdapter=new ImageAdapter(this);
    TextView data;
    ProgressBar progress;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        data=(TextView) findViewById(R.id.data);
        progress=(ProgressBar)findViewById(R.id.progress);
         gridView= (GridView) findViewById(R.id.movies);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             //   Intent intent=new Intent(getApplicationContext(),details.class);

                Intent intent=new Intent(getApplicationContext(),SecondDetails.class);

             //   Intent intent =new Intent(getApplicationContext(),ThirdDetails.class);
              // intent.putExtra("url",Movie.movies.get(position).getIconURL());
               DetailsDataHolder.iconURL= Movie.movies.get(position).getIconURL();
               // intent.putExtra("overView",Movie.movies.get(position).getOverView());
                DetailsDataHolder.overView=Movie.movies.get(position).getOverView();
               // intent.putExtra("title",Movie.movies.get(position).getTitle());
                DetailsDataHolder.name=Movie.movies.get(position).getTitle();
               // intent.putExtra("language",Movie.movies.get(position).getLanguage());
                DetailsDataHolder.language=Movie.movies.get(position).getLanguage();
                //intent.putExtra("date",Movie.movies.get(position).getReleaseDate());
                DetailsDataHolder.date=Movie.movies.get(position).getReleaseDate();
                //intent.putExtra("vote",Movie.movies.get(position).getVote());
                DetailsDataHolder.vote=Movie.movies.get(position).getVote();
                intent.putExtra("id",Movie.movies.get(position).getID());
                intent.putExtra("position",position);
                //intent.putExtra("adult",Movie.movies.get(position).isAdult());
                DetailsDataHolder.adult=Movie.movies.get(position).isAdult();

             //   intent.putExtra("position",position);

                Movie.setBaseUrl(Movie.movies.get(position).getID().toString()+"/videos");

                startActivity(intent);

            }
        });
        if(connected()){
            GetMovies getMovies=new GetMovies();
            getMovies.execute();
            progress.setVisibility(View.VISIBLE);
        }
    else {
            Toast.makeText(this,"Sorry No Internet Connection",Toast.LENGTH_LONG).show();
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Movie.movies.clear();
        imageAdapter.notifyDataSetInvalidated();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        GetMovies getMovies=new GetMovies();
        switch (menuItem.getItemId()){
            case R.id.popularity:
               // Toast.makeText(getApplicationContext(),"Popularity Sorting",Toast.LENGTH_LONG).show();

               Movie.setBaseUrl("popular/");
                if(connected()){
                    gridView.setAdapter(imageAdapter);
                    getMovies.execute();
                    data.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);
                }
                else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(this,"Sorry No Internet Connection",Toast.LENGTH_LONG).show();

                }
                return true;


            case R.id.rating:
               // Toast.makeText(getApplicationContext(),"Rating Sorting",Toast.LENGTH_LONG).show();
                Movie.setBaseUrl("top_rated");
                if(connected()){
                    gridView.setAdapter(imageAdapter);
                    getMovies.execute();
                    data.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);
                }
                else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(this,"Sorry No Internet Connection",Toast.LENGTH_LONG).show();

                }
                return true;

            case R.id.favourite:

             // gridView.setAdapter(adapter);

                Intent intent=new Intent(this,FavouriteMoviesActivity.class);

                startActivity(intent);
                return true;

                default:
                    return super.onOptionsItemSelected(menuItem);
        }
    }
    // this function checks the connection of the device
    public boolean connected(){
        ConnectivityManager manager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if(info!=null&&info.isConnected()){
            return true;
        }
        else return false;
    }

    public void updateUI(boolean state){
        progress.setVisibility(View.GONE);
//        if(imageAdapter==null)
//        {
//            data.setText("Sorry, something went wrong while downloading . Try checking your internet " +
//                "connection and then try again.");
//  data.setVisibility(View.VISIBLE);}
//        else {
//            imageAdapter.notifyDataSetChanged();
//        }
       if(state) imageAdapter.notifyDataSetChanged();
        else {
           data.setText("Sorry, something went wrong while downloading . Try checking your internet " +
                 "connection and then try again.");
               data.setVisibility(View.VISIBLE);
       }
    }

    private class GetMovies extends AsyncTask<Void,Void,Void> {

        private JSONObject data;



        @Override
        protected Void doInBackground(Void ... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;
            Uri builturi=Uri.parse(Movie.getBaseUrl()).buildUpon()
                    .build();
Log.v("Movie Path",(Movie.getBaseUrl()));

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
                movieJsonStr = buffer.toString();
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

            parseJSON(movieJsonStr);
            return  null;
        }


        public void parseJSON(String json){


            try {
                Movie.movies.clear();
                String movieJsonStr=json;
                data=new JSONObject(movieJsonStr);

               // int pageNum=data.getInt("page");
                JSONArray results=data.getJSONArray("results");

                for (int i=0;i<results.length();i++){
                    JSONObject inResult=results.getJSONObject(i);

                    Movie.movies.add(new Movie(inResult.getBoolean("adult"),
                            inResult.getString("title"), inResult.getString("original_language"),
                            inResult.getString("release_date"),inResult.getString("poster_path")
                            ,inResult.getDouble("vote_average"),inResult.getString("overview"),inResult.getInt("id")));


                }

            }
            catch (JSONException e){
                Log.e("Formatin JSON "," ERROR ");
            }
        }

        @Override
        protected void onPostExecute(Void imageAdapter) {
            super.onPostExecute(imageAdapter);

                if(Movie.movies.size()>0)updateUI(true);
               else updateUI(false);
        }

    }
}
