package poker.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import poker.client.communication.MsgFormats;
import poker.client.communication.ServerConnector;

public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        Pane startPane = FXMLLoader.load(getClass().getResource("/fxml/startPane.fxml"));
        Scene scene = new Scene(startPane);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Poker Royal Flush");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        ServerConnector serverConnector = Game.getServerConnector();

        if(serverConnector != null){
            serverConnector.disconnect();
        }

        super.stop();
    }
}