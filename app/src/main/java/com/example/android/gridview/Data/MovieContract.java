package com.example.android.gridview.Data;


import android.provider.BaseColumns;

/**
 * Created by mina essam on 24-Nov-16.
 */
public final class MovieContract {
    public MovieContract(){}
    public static class MovieTable implements BaseColumns {
    public final static String ID=BaseColumns._ID;
        public static final String TABLE_NAME="favouriteMovies";
        public final static String MOVIE_ID_COLUMN="movieID";
        public final static String ADULT_COLUMN="adult";
        public static final String TITLE_COLUMN="title";
        public static final String LANGUAGE_COLUMN="language";
        public static final String RELEASE_DATE_COLUMN="releaseDate";
        public static final String ICON_URL_COLUMN="iconURL";
        public static final String VOTE_COLUMN="vote";
        public static final String OVERVIEW_COLUMN="overview";

    }
    public static final String INTEGER="INTEGER";
    public static final String TEXT="TEXT";
    public static final String REAL="REAL";
    public static final String PRIMARY_KEY="PRIMARY KEY";
    public static final String AUTO_INCREMENT="AUTOINCREMENT";
    public static final String NOT_NULL="NOT NULL";
    public static final String CREATE_TABLE ="CREATE TABLE "+ MovieTable.TABLE_NAME+" ("+MovieTable.ID+" "
            +INTEGER
            +" "+PRIMARY_KEY+" "+AUTO_INCREMENT +", " +MovieTable.MOVIE_ID_COLUMN+
            " "+INTEGER+" "+NOT_NULL+","+ MovieTable.ADULT_COLUMN+" "+INTEGER+" "+NOT_NULL+" , "+MovieTable.TITLE_COLUMN+" "+TEXT
           +" "+NOT_NULL +","+MovieTable.LANGUAGE_COLUMN+" "+TEXT+" "+NOT_NULL+","+MovieTable.RELEASE_DATE_COLUMN+" "+TEXT
            +" "+NOT_NULL+","+MovieTable.ICON_URL_COLUMN+" "+TEXT+" "+NOT_NULL+","+MovieTable.VOTE_COLUMN+" "+REAL+
            " "+NOT_NULL+","+MovieTable.OVERVIEW_COLUMN+" "+TEXT+" "+NOT_NULL+");";
    public static final String DELETE_BY_MOVIE_ID ="DELETE FROM "+MovieTable.TABLE_NAME+"  WHERE "+MovieTable.MOVIE_ID_COLUMN+
            " = ";

    public static final String DELETE_TABLE="DROP TABLE IF EXIST "+MovieTable.TABLE_NAME+" ;" ;
    public static final String SELECT_ALL="SELECT * FROM "+ MovieTable.TABLE_NAME +" ;";

    public static final int ADULT_SAFE=0;
    public static final int  ADULT=1;
}
