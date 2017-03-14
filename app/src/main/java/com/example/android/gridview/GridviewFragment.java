package com.example.android.gridview;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class GridviewFragment extends Fragment {
    @Nullable

    ImageAdapter imageAdapter;
    TextView data;
    ProgressBar progress;
    GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragment =inflater.inflate(R.layout.activity_gridview_fragment,container,false);
        imageAdapter=new ImageAdapter(getActivity().getApplicationContext());
        data=(TextView) fragment.findViewById(R.id.data);
        progress=(ProgressBar) fragment.findViewById(R.id.progress);
        gridView= (GridView) fragment.findViewById(R.id.movies);
        gridView.setAdapter(imageAdapter);

        GetMovies getMovies=new GetMovies();
        getMovies.execute();
        progress.setVisibility(View.VISIBLE);

        return fragment;
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
