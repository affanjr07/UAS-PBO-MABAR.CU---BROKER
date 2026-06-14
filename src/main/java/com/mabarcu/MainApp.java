package com.mabarcu;

import com.mabarcu.backend.BackendApplication;
import com.mabarcu.services.AppData;
import com.mabarcu.services.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApp extends Application {

    private static Stage mainStage;
    private static ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        try {
            springContext = new SpringApplicationBuilder(BackendApplication.class)
                    .headless(false)
                    .run();

            Database.init(springContext);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Spring Boot backend gagal start. Cek port 8085 atau database H2 di folder data.",
                    e
            );
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        AppData.init();

        mainStage = stage;
        stage.setTitle("MABAR.CU - JavaFX + Spring Boot + H2");

        setRoot("LoginView");

        stage.show();
    }

    public static void setRoot(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/com/mabarcu/views/" + fxmlName + ".fxml")
            );

            Parent root = loader.load();

            Scene scene = new Scene(root, 1200, 760);
            scene.getStylesheets().add(
                    MainApp.class.getResource("/com/mabarcu/styles/style.css").toExternalForm()
            );

            mainStage.setScene(scene);
            mainStage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();

            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("MABAR.CU Error");
                alert.setHeaderText("Gagal membuka halaman: " + fxmlName);
                alert.setContentText(
                        e.getClass().getSimpleName()
                                + ": "
                                + (e.getMessage() == null ? "Lihat terminal Maven." : e.getMessage())
                );
                alert.showAndWait();
            } catch (Exception ignored) {
            }

            throw new RuntimeException("Gagal membuka halaman: " + fxmlName, e);
        }
    }

    @Override
    public void stop() {
        try {
            if (AppData.currentUser != null) {
                Database.setOffline(AppData.currentUser.getId());
            }
        } catch (Exception ignored) {
        }

        if (springContext != null) {
            springContext.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}