package com.example.android.gridview.Utils;

/**
 * Created by mina essam on 04-Nov-16.
 */


import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulodichone on 3/11/15.
 */
public class Movie {
  public static   List<Movie> movies=new ArrayList<>();


    public Movie(boolean adult,String title,String language,String releaseDate,String posterPath,double vote,String
                 overView,int id){
        this.adult=adult;
        this.title=title;
        this.language=langCheck(language);
        this.releaseDate=setDate(releaseDate);
        this.posterPath=posterPath;
        this.vote=vote;
        this.overView=overView;
        ICON_URL = "http://image.tmdb.org/t/p/w500"+posterPath;
        this.id=id;
    }
    public String setDate(String date){
        int x=0;
        int last=0;
        String start;
        String middle;
        String end=null;
        for(int i=1;i<date.length()-1;i++){
            if(date.charAt(i)=='-'){
                if(x==0){
                    end =new String(date.substring(0,i));
                    x++;
                    last=i+1;
                }
                else if(x==1){
                    middle=new String(date.substring(last,i));

                    start=new String(date.substring(i+1,date.length()));
                    return start+"-"+middle+"-"+end;
                }

            }
        }
        return null;
    }
   public  String getIconURL(){
       return this.ICON_URL;
   }
    public  String getOverView(){
        return this.overView;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getVote() {
        return vote;
    }

    public String getLanguage() {
        return language;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public boolean isAdult() {
        return adult;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public String getPosterPath() {
        return posterPath;
    }
    public Integer getID(){return id;}

    public static String sortBy="popular";
    public   String posterPath="/xfWac8MTYDxujaxgPVcRD9yZaul.jpg";

    public static void setBaseUrl(String  sort) {
        BASE_URL = "http://api.themoviedb.org/3/movie/"+sort+"?api_key=80d165558137e1d2cd4d07092d2292df";

    }
    public String langCheck(String s){
        if(s.equals("en")){
            return  "English";

        }
        else if(s.equals("fr")){
            return "French";
        }
        else if(s.equals("ja")){
            return "Japanese";
        }
        else if(s.equals("ge")){
            return "German";
        }
        else if(s.equals("ch")){
            return "Chinese";
        }
        else if(s.equals("tu")){
            return "Turkey";
        }
        else return "Arabic";
    }

    public static   String  BASE_URL = "http://api.themoviedb.org/3/movie/"+sortBy+"?api_key=80d165558137e1d2cd4d07092d2292df";
    public   String ICON_URL = "http://image.tmdb.org/t/p/w500"+posterPath; // + 10d.png
   public  int images=5;
   public boolean adult;
   public    String overView;
   public   String releaseDate;
   public   String title;
    public   String language;
   public double vote;
 public int id;
}
