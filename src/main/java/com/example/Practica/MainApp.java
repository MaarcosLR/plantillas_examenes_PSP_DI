package com.example.Practica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainApp extends Application {

    private ArrayList<String> productList; // Cambia el tipo según sea necesario

    public MainApp() {
        this.productList = new ArrayList<>(); // Inicializa la lista aquí
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProductView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Stock Management Application");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
