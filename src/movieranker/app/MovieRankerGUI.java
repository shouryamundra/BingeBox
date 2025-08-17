// File: src/movieranker/app/MovieRankerGUI.java
package movieranker.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import movieranker.collection.RankedMovieTreeMap;
import movieranker.model.Movie;

public class MovieRankerGUI extends Application {
    private final RankedMovieTreeMap rankedMovies = new RankedMovieTreeMap(Integer.MAX_VALUE);
    private final TextArea displayArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("BINGE BOX: Movie Ranker");

        // Input fields
        TextField rankField = new TextField();
        TextField titleField = new TextField();
        TextField yearField = new TextField();
        TextField durationField = new TextField();
        TextField genreField = new TextField();

        // Layout for input fields
        VBox inputBox = new VBox(10,
                createLabeledField("Rank:", rankField),
                createLabeledField("Title:", titleField),
                createLabeledField("Year:", yearField),
                createLabeledField("Duration (min):", durationField),
                createLabeledField("Genre(s) (comma separated):", genreField)
        );
        inputBox.setPadding(new Insets(15));

        // Buttons
        Button addButton = new Button("Add Movie");
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");
        HBox buttonBox = new HBox(10, addButton, saveButton, loadButton);
        buttonBox.setPadding(new Insets(0, 0, 10, 0));

        // Display area
        displayArea.setEditable(false);
        displayArea.setStyle("-fx-font-family: 'monospace'; -fx-font-size: 14;");
        displayArea.setPrefHeight(300);

        // Main layout
        VBox mainBox = new VBox(10, inputBox, buttonBox, displayArea);
        mainBox.setPadding(new Insets(20));

        // Button actions
        addButton.setOnAction(e -> {
            try {
                int rank = Integer.parseInt(rankField.getText());
                String title = titleField.getText();
                int year = Integer.parseInt(yearField.getText());
                int duration = Integer.parseInt(durationField.getText());
                String[] genres = genreField.getText().split(",");
                for (int i = 0; i < genres.length; i++) genres[i] = genres[i].trim();
                rankedMovies.addMovie(rank, new Movie(title, genres, year, duration));
                updateDisplay();
            } catch (Exception ex) {
                showAlert("Invalid input: " + ex.getMessage());
            }
        });

        saveButton.setOnAction(e -> rankedMovies.saveToFile("src/movieranker/assets/ranked_movies.json"));
        loadButton.setOnAction(e -> {
            rankedMovies.loadFromFile("src/movieranker/assets/ranked_movies.json");
            updateDisplay();
        });

        Scene scene = new Scene(mainBox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createLabeledField(String label, TextField field) {
        Label lbl = new Label(label);
        lbl.setMinWidth(150);
        HBox box = new HBox(10, lbl, field);
        box.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        return box;
    }

    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        rankedMovies.getRankedMovies().forEach((rank, movie) -> {
            sb.append(rank).append(". ")
                    .append(movie.title()).append(" (")
                    .append(movie.yearOfRelease()).append(") - ")
                    .append(movie.duration()).append(" min [")
                    .append(String.join(", ", movie.genre())).append("]\n");
        });
        displayArea.setText(sb.toString());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}