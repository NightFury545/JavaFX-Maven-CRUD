package org.nightfury.presentation.GUIstarter;

import atlantafx.base.theme.PrimerDark;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.nightfury.presentation.GUIcontroller.MovieGUIController;

public class AppGUI extends Application {

    @Override
    public void start(Stage stage) throws SQLException {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane, 500, 500);
        stage.setTitle("JavaFXMavenCRUD");
        stage.setScene(scene);
        stage.show();
        MovieGUIController movieGUIController = new MovieGUIController();
        stackPane.getChildren().addAll(movieGUIController.getDownloadDataFromDataBaseButton(),
            movieGUIController.getLoadDataIntoDataBaseButton(),
            movieGUIController.getProgressBar(),
            movieGUIController.getTextField());
    }

    public void launchGUI() {
        launch();
    }
}
