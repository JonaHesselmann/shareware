package de.yugimuo;

import de.yugimuo.utilities.Logger;
import de.yugimuo.websocket.Client;
import de.yugimuo.websocket.Server;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static de.yugimuo.utilities.Logger.LOG;

public class Main extends Application {

    private static final Client RECEIVER = new Client();
    private static final Server SERVER = new Server();

    public static void main(String[] args) throws IOException {
        String name = "Share programm";
        String version;
        version = "v1.0 ";
        LOG("Test", Logger.LoggerType.INFO);
        SERVER.start(1337);
        RECEIVER.startConnection("127.0.0.1", 1337);
        RECEIVER.sendMessage("hello server");
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) {
        final File[] selectedFile = {null};
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

        dragTarget.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    LOG("Selected file: " + db.getFiles().get(0).getName(), Logger.LoggerType.INFO);
                    selectedFile[0] = db.getFiles().get(0);
                    dropped.setText("Name: " + db.getFiles().get(0).getName() + " Size:" + db.getFiles().get(0).getTotalSpace());
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
        Button button = new Button();
        button.setText("Transfer");
        button.setOnAction(event ->{
            try {
                RECEIVER.sendMessage("File: " + selectedFile[0].getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        StackPane root1 = new StackPane();
        root1.getChildren().add(dragTarget);
        root1.getChildren().add(button);
        Scene scene = new Scene(root1, 500, 250);
        primaryStage.setTitle("Drag Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
