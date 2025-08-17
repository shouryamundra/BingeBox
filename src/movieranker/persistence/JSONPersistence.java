package movieranker.persistence;

import movieranker.model.Movie;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class JSONPersistence {
    public static void save(Map<Integer, Movie> movies, String filename, int maxCapacity) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("{\n");
            writer.write("  \"maxCapacity\": " + maxCapacity + ",\n");
            writer.write("  \"movies\": [\n");

            int i = 0;
            for (var entry : movies.entrySet()) {
                int rank = entry.getKey();
                Movie m = entry.getValue();
                writer.write("    {\"rank\": " + rank +
                        ", \"title\": \"" + m.title() +
                        "\", \"year\": " + m.yearOfRelease() +
                        ", \"duration\": " + m.duration() +
                        ", \"genre\": \"" + String.join("/", m.genre()) + "\"}");
                if (++i < movies.size()) writer.write(",");
                writer.write("\n");
            }

            writer.write("  ]\n");
            writer.write("}\n");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save to file: " + filename, e);
        }
    }

    public static Map<Integer, Movie> load(String filename, int maxCapacity) {
        Map<Integer, Movie> loaded = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("{\"rank\"")) {
                    // Very naive parsing (good enough for our simple format)
                    int rank = Integer.parseInt(line.split("\"rank\":")[1].split(",")[0].trim());
                    String title = line.split("\"title\":")[1].split(",")[0].replace("\"", "").trim();
                    int year = Integer.parseInt(line.split("\"year\":")[1].split(",")[0].trim());
                    int duration = Integer.parseInt(line.split("\"duration\":")[1].split(",")[0].trim());
                    String[] genres = line.split("\"genre\":")[1].split("}")[0]
                            .replace("\"", "").trim().split("/");

                    if (rank <= maxCapacity) {
                        loaded.put(rank, new Movie(title, genres, year, duration));
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load file: " + filename, e);
        }
        return loaded;
    }
}
