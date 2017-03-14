package com.example.android.gridview;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.gridview.Utils.Movie;
import com.example.android.gridview.Utils.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ThirdDetails extends AppCompatActivity {
ListView listView;
    ThirdDetailsAdapter adapter;
    String trailerURL;
    int moviePosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_details);
        listView=(ListView)findViewById(R.id.third_details_list);
         adapter=new ThirdDetailsAdapter(this);
        listView.setAdapter(adapter);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        moviePosition=bundle.getInt("position");
        trailerURL= Movie.movies.get(moviePosition).getBaseUrl();
        GetTrailers getTrailers=new GetTrailers();
        getTrailers.execute();
    }

    public void updateUI(){

//ThirdDetailsAdapter adapter=new ThirdDetailsAdapter(this);
       listView.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Trailer.trailers.clear();
        adapter.notifyDataSetInvalidated();
        adapter.setTrailersNumber(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Destroyed",Toast.LENGTH_SHORT).show();
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
            if(Trailer.trailers.size()>0) {adapter.setTrailersNumber(Trailer.trailers.size());
                Log.v("adapter count",String.valueOf(adapter.getCount()));
            updateUI();}
        }



    }
}
