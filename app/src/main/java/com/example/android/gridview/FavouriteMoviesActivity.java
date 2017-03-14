package com.example.android.gridview;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gridview.Data.MovieContract;
import com.example.android.gridview.Data.MovieDbHelper;

public class FavouriteMoviesActivity extends AppCompatActivity {
    MovieCursorAdapter adapter;
    GridView gridView;
    int cursorCount;
    TextView databaseError;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView=(GridView)findViewById(R.id.movies);
        MovieDbHelper helper=new MovieDbHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();
         c=db.rawQuery("SELECT  * FROM "+ MovieContract.MovieTable.TABLE_NAME, null);
        adapter=new MovieCursorAdapter(this,c);
        gridView.setAdapter(adapter);
        Cursor cursor=getDatabase();
        cursorCount=cursor.getCount();

        if(cursorCount>0)
        {

            adapter =new MovieCursorAdapter(this,cursor);
            gridView.setAdapter(adapter);
       }
        else {
           databaseError=(TextView)findViewById(R.id.database_miss);
            databaseError.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            databaseError.setText("You have Not added any Movies to favourites");
            Log.v("NO_DATABASE","HERE");
        }

        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       adapter.notifyDataSetInvalidated();
    }

    private Cursor getDatabase(){
        MovieDbHelper helper=new MovieDbHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery(MovieContract.SELECT_ALL,null);
        return cursor;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor cursor=getDatabase();
        int secondCursorCount=cursor.getCount();
        if(secondCursorCount!=cursorCount) // change has happened to database
        {
            if(secondCursorCount>0){
               MovieCursorAdapter adapter=new MovieCursorAdapter(this,cursor);
                gridView.setAdapter(adapter);
            }
            else {
               databaseError=(TextView)findViewById(R.id.database_miss);
                databaseError.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.GONE);
                databaseError.setText("You Removed All Favourites");
            }

        }
        else   // no change to database , so don't change adapter
        {
           return;
        }
       gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
    }

    // // TODO: 26-Nov-16
    protected void openDetails(View v){
        Integer integer=(Integer) v.getTag(); // movie _id from cursor adapter from database
        MovieDbHelper helper=new MovieDbHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+ MovieContract.MovieTable.TABLE_NAME+" "
                +"WHERE "+MovieContract.MovieTable.ID+" = "+integer.toString()+";",null);

        cursor.moveToFirst();
        if(cursor.moveToFirst()){

            Intent intent = new Intent(getApplicationContext(), details.class);


            intent.putExtra("url", cursor.getString(cursor.getColumnIndex(MovieContract.MovieTable.ICON_URL_COLUMN)));
            intent.putExtra("overView", cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieTable.OVERVIEW_COLUMN)));
            intent.putExtra("title", cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieTable.TITLE_COLUMN)));
            intent.putExtra("language", cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieTable.LANGUAGE_COLUMN)));
            intent.putExtra("date", cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieTable.RELEASE_DATE_COLUMN)));
            intent.putExtra("vote", cursor.getDouble(cursor.getColumnIndexOrThrow(MovieContract.MovieTable.VOTE_COLUMN)));
            intent.putExtra("id", cursor.getInt(cursor.getColumnIndexOrThrow(MovieContract.MovieTable.MOVIE_ID_COLUMN)));
            intent.putExtra("adult", cursor.getInt(cursor.getColumnIndexOrThrow(MovieContract.MovieTable.ADULT_COLUMN)));
            intent.putExtra("star",true);
            startActivity(intent);
        }
        else{

            Log.v("Data", "OUT");
            Toast.makeText(this,"NO Favourites",Toast.LENGTH_LONG).show();
        }
    }
}
