package de.yugimuo;

import de.yugimuo.utilities.Logger;
import de.yugimuo.websocket.Client;
import de.yugimuo.websocket.Server;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static de.yugimuo.utilities.Logger.LOG;

public class Main extends Application {

    public static void main(String[] args) throws IOException {

        Client reciver = new Client();
        Server server = new Server();
        String name = "Share programm";
        String version;
        version = "v1.0 ";
        LOG("Test", Logger.LoggerType.INFO);
        server.start(1337);
        reciver.startConnection("127.0.0.1", 1337);
        reciver.sendMessage("hello server");
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Drag a file to me.");
        Label dropped = new Label("");
        VBox dragTarget = new VBox();
        dragTarget.getChildren().addAll(label, dropped);
        dragTarget.setOnDragOver(event -> {
            if (event.getGestureSource() != dragTarget && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        dragTarget.setOnDragDropped(event -> {

            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                LOG("Selected file: " + db.getFiles().get(0).getName(), Logger.LoggerType.INFO);
                dropped.setText("Name: " + db.getFiles().get(0).getName() + " Size:" + db.getFiles().get(0).getTotalSpace());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });


        StackPane root1 = new StackPane();
        root1.getChildren().add(dragTarget);
        Scene scene = new Scene(root1, 500, 250);
        primaryStage.setTitle("Drag Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
