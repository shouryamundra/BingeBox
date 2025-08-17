package movieranker.collection;

/* Multiple implementations possible (RankedMovieList, FavoriteMovieSet). */

import movieranker.model.Movie;

public interface MovieCollection {
    // CREATE
    void addMovie(int rank, Movie movie);

    // READ
    Movie getMovieByRank(int rank);
    Movie getMovieByName(String name);
    void display();

    // FILE-HANDLING
    void saveToFile(String filename);
    void loadFromFile(String filename);


    // DELETE
    void removeMovieByRank(int rank);
    void removeMovieByName(String name);



}
