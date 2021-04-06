package poker.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;
import poker.client.Game;
import poker.client.communication.ServerConnector;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.HashMap;

public class StartMenuController {

    @FXML
    private Pane thisPane;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private TextField portTextField;
    @FXML
    private TextField hostTextField;
    @FXML
    private Text errorText;

    public StartMenuController() {

    }

    @FXML
    private void connectButtonClicked() {
        try {
            validData();
            if(connectToServer()){
                switchToMainPane();
            }
        } catch (IllegalArgumentException e) {
            errorText.setText(e.getMessage());
        }
    }

    private void validData() {
        String errorMsg = "";

        if (!validNickname()) {
            errorMsg += "Nickname's length must be in 1 - 10\n";
        }
        if (!validHost()) {
            errorMsg += "Server IP is incorrect\n";
        }
        if (!validPort()) {
            errorMsg += "Port number is incorrect";
        }

        if (errorMsg.length() > 0) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    private boolean validNickname() {
        int nicknameLength = nicknameTextField.getText().length();
        return nicknameLength >= 1 && nicknameLength <= 10;
    }

    private boolean validHost() {
        return hostTextField.getText().length() > 0;
    }

    private boolean validPort() {
        try {
            int port = Integer.parseInt(portTextField.getText());
            return port >= 0 && port <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void switchToMainPane() {
        try {
            Pane mainPane = FXMLLoader.load(getClass().getResource("/fxml/mainPane.fxml"));
            Scene scene = new Scene(mainPane);
            Stage stage = (Stage) thisPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            printAlert(e.getMessage());
        }
    }

    private void printAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private boolean connectToServer() {
        try {
            ServerConnector serverConnector = new ServerConnector(hostTextField.getText(), Integer.parseInt(portTextField.getText()), nicknameTextField.getText());
            Game.setSERVERCONNCETOR(serverConnector);
            return true;
        }catch (UnknownHostException | ConnectException exc){
            printAlert("Cannot connect to server");
        } catch (Exception e) {
            printAlert(e.getMessage());
        }
        return false;
    }
}