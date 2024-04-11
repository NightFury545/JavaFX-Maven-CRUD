package org.nightfury.presentation.GUIcontroller;

import java.sql.SQLException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.nightfury.business_logic.service.serviceImpl.MovieServiceImpl;
import org.nightfury.persistence.repository.exception.AlreadyExistsException;

public class MovieGUIController {

    private final MovieServiceImpl movieService = new MovieServiceImpl();
    @Getter
    private final Button loadDataIntoDataBaseButton = new Button();
    @Getter
    private final Button downloadDataFromDataBaseButton = new Button();
    @Getter
    private final ProgressBar progressBar = new ProgressBar();
    @Getter
    private final TextField textField = new TextField();

    public MovieGUIController() throws SQLException {
        configure();
    }

    private void configure() {
        this.loadDataIntoDataBaseButton.setOnAction((event) -> {
            new Thread(() -> {
                try {
                    movieService.loadDataIntoDataBase(Integer.parseInt(textField.getText()));
                    Platform.runLater(() -> {
                        new Alert(AlertType.INFORMATION, "Дані успішно завантажено в БД!").show();
                    });
                } catch (SQLException | AlreadyExistsException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });

        this.downloadDataFromDataBaseButton.setOnAction((event) -> {
            new Thread(() -> {
                try {
                    movieService.downloadDataFromDataBase(this.progressBar);
                    Platform.runLater(() -> {
                        new Alert(AlertType.INFORMATION, "Дані успішно завантажено із БД!").show();
                    });
                } catch (SQLException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });

        loadDataIntoDataBaseButton.setStyle(
            "-fx-min-width: 200px; -fx-min-height: 50px; -fx-translate-y: -110px");
        loadDataIntoDataBaseButton.setText("Завантажити дані в БД");
        downloadDataFromDataBaseButton.setStyle("-fx-min-width: 200px; -fx-min-height: 50px");
        downloadDataFromDataBaseButton.setText("Завантажити дані із БД");
        progressBar.setStyle("-fx-min-height: 20px; -fx-min-width: 200px; -fx-translate-y: 55px;");
        progressBar.progressProperty().setValue(0);
        textField.setStyle("-fx-max-width: 200px; -fx-min-height: 20px; -fx-translate-y: -55px");
        textField.setPromptText("Введіть кількість записів...");
    }

}
