package movieranker.app;

import movieranker.collection.RankedMovieTreeMap;
import movieranker.model.Movie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MovieRankerGUI extends JFrame {
    private final RankedMovieTreeMap rankedMovies = new RankedMovieTreeMap(10);
    private final JTextArea displayArea = new JTextArea(15, 50);

    public MovieRankerGUI() {
        setTitle("Movie Ranker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(700, 500));

        // Input panel with BoxLayout
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField rankField = new JTextField(10);
        JTextField titleField = new JTextField(20);
        JTextField yearField = new JTextField(10);
        JTextField durationField = new JTextField(10);
        JTextField genreField = new JTextField(20);

        inputPanel.add(createLabeledField("Rank:", rankField));
        inputPanel.add(createLabeledField("Title:", titleField));
        inputPanel.add(createLabeledField("Year:", yearField));
        inputPanel.add(createLabeledField("Duration (min):", durationField));
        inputPanel.add(createLabeledField("Genre(s) (comma separated):", genreField));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton addButton = new JButton("Add Movie");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(buttonPanel);

        add(inputPanel, BorderLayout.NORTH);

        // Display area
        displayArea.setEditable(false);
        displayArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Button actions
        addButton.addActionListener(e -> {
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
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        });

        saveButton.addActionListener(e -> {
            rankedMovies.saveToFile("src/movieranker/assets/ranked_movies.json");
        });

        loadButton.addActionListener(e -> {
            rankedMovies.loadFromFile("src/movieranker/assets/ranked_movies.json");
            updateDisplay();
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createLabeledField(String label, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(field);
        return panel;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MovieRankerGUI::new);
    }
}