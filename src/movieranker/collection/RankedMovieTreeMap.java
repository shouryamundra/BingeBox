package movieranker.collection;

import movieranker.model.Movie;

import java.util.TreeMap;

public class RankedMovieTreeMap implements MovieCollection{

    private final TreeMap<Integer, Movie> rankedMovies = new TreeMap<>();
    private final int maxCapacity;

    public RankedMovieTreeMap(){
        this(10);
    }

    public RankedMovieTreeMap(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("maxCapacity must be >= 1");
        }
        this.maxCapacity = maxCapacity;
    }


    private void validateRank(int rank) {
        if (rank < 1 || rank > maxCapacity) {
            throw new IllegalArgumentException("Rank must be between 1 and " + maxCapacity);
        }
    }


    @Override
    public void addMovie(int rank, Movie movie) {
        validateRank(rank);
        if (rankedMovies.size() >= maxCapacity) {
            throw new IllegalStateException("List is at full capacity (" + maxCapacity + ")");
        }
        if (rankedMovies.containsKey(rank)) {
            throw new IllegalArgumentException("Rank " + rank + " already taken");
        }
        rankedMovies.put(rank, movie);
    }

//    public void addMovie(Movie[] movie) {
//        if (rankedMovies.size() >= maxCapacity) {
//            throw new IllegalStateException("List is at full capacity (" + maxCapacity + ")");
//        }
//        // Find the first available rank
//        int rank = 1;
//        while (rankedMovies.containsKey(rank)) {
//            rank++;
//        }
//        if (rank > maxCapacity) {
//            throw new IllegalStateException("No available ranks left to add the movie");
//        }
//        for (Movie m : movie) {
//            rankedMovies.put(rank++, m);
//        }
//    }

    @Override
    public void removeMovieByRank(int rank) {
        validateRank(rank);
        if (!rankedMovies.containsKey(rank)) {
            throw new IllegalArgumentException("No movie found at rank " + rank);
        }
        rankedMovies.remove(rank);
    }

    @Override
    public void removeMovieByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Movie name cannot be null or empty");
        }

        boolean removed = rankedMovies.
                entrySet()
                .removeIf(e -> e
                        .getValue()
                        .title()
                        .equalsIgnoreCase(name)
                );

        if (!removed) {
            throw new IllegalArgumentException("No movie with title: " + name);
        }
    }

    @Override
    public Movie getMovieByRank(int rank) {
        Movie movie = rankedMovies.get(rank);
        if (movie == null) {
            throw new IllegalArgumentException("No movie found at rank " + rank);
        }
        return movie;
    }

    @Override
    public Movie getMovieByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Movie name cannot be null or empty");
        }

        return rankedMovies
                .values()
                .stream()
                .filter(movie -> movie.title().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No movie with title: " + name));
    }

    public void display() {
        if (rankedMovies.isEmpty()) {
            System.out.println("No movies ranked yet.");
            return;
        }
        System.out.println("\n🎬 Ranked Movies:");
        for (var entry : rankedMovies.entrySet()) {
            int rank = entry.getKey();
            Movie m = entry.getValue();
            System.out.printf("%d. %s (%d) - %d min [%s]%n",
                    rank, m.title(), m.yearOfRelease(), m.duration(),
                    String.join(", ", m.genre()));
        }
    }

    @Override
    public void saveToFile(String filename) {
        // Implementation for saving to file can be added here
        // This could involve serializing the TreeMap to a file
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public void loadFromFile(String filename) {
        // Implementation for loading from file can be added here
        // This could involve deserializing the TreeMap from a file
        throw new UnsupportedOperationException("Method not implemented yet");
    }
}
