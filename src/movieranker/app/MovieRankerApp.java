package movieranker.app;

public class MovieRankerApp {

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int maxCapacity = 1;
//        RankedMovieTreeMap rankedMovieTreeMap = new RankedMovieTreeMap(maxCapacity);
//
//        System.out.println("🎬 Welcome to Movie Ranker!");
//        System.out.println("Enter your top " + maxCapacity + " movies (rank 1 = best).");
//
//        int rank = 1;
//        while (rank <= maxCapacity) {
//            System.out.println("Enter movie title for rank " + rank + ":");
//            String title = scanner.nextLine().trim();
//            if (title.isEmpty()) {
//                System.out.println("Title cannot be empty. Please try again.");
//                continue;
//            }
//
//            System.out.println("Enter movie genre (comma-separated):");
//            String[] genre = scanner.nextLine().trim().split(",");
//
//            System.out.println("Enter year of release:");
//            int yearOfRelease;
//            try {
//                yearOfRelease = Integer.parseInt(scanner.nextLine().trim());
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid year. Please enter a valid number.");
//                continue;
//            }
//
//            System.out.println("Enter movie duration in minutes:");
//            int duration;
//            try {
//                duration = Integer.parseInt(scanner.nextLine().trim());
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid duration. Please enter a valid number.");
//                continue;
//            }
//
//            try {
//                rankedMovieTreeMap.addMovie(rank, new movieranker.model.Movie(title, genre, yearOfRelease, duration));
//                rank++;
//            } catch (IllegalArgumentException | IllegalStateException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        rankedMovieTreeMap.saveToFile("src/movieranker/assets/ranked_movies.json");
//    }
        MovieRankerGUI.main(args);
    }
}

