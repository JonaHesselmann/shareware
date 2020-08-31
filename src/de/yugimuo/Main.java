package de.yugimuo;

import de.yugimuo.websocket.Client;
import de.yugimuo.websocket.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        Client reciver = new Client();
        Server server = new Server();
        String name = "Share programm";
        String version;
        version = "v1.0 ";
        Main.launch();
    }
}
