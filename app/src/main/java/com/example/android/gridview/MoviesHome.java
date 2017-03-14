package com.example.android.gridview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MoviesHome extends AppCompatActivity {
    private boolean mIsTwoPane=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_home);
        GridviewFragment gridviewFragment=new GridviewFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_movies,gridviewFragment).commit();

        if(findViewById(R.id.second_details_fragment)!=null){
            mIsTwoPane=true;
            SecondDetailsFragment secondDetailsFragment=new SecondDetailsFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.second_details_fragment,secondDetailsFragment).commit();

        }
    }
}
