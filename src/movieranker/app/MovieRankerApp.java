package movieranker.app;

import movieranker.collection.MovieCollection;
import movieranker.collection.RankedMovieTreeMap;
import movieranker.model.Movie;

public class MovieRankerApp {

    public static void main(String[] args) {
        System.out.println("Welcome to MovieRanker!");

         MovieCollection collection = new RankedMovieTreeMap();
         collection.addMovie(1, new Movie("Inception", new String[]{"Sci-Fi", "Thriller"}, 2010, 148));
         collection.display();
    }
}
