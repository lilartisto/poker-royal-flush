package poker.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import poker.client.communication.ServerConnector;

import java.io.IOException;

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

    @FXML
    private void connectButtonClicked() {
        try {
            validData();
            if (connectToServer()) {
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gamePane.fxml"));
            Pane mainPane = fxmlLoader.load();
            Scene scene = new Scene(mainPane);
            Stage stage = (Stage) thisPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            printAlert(e.getMessage());
        }
    }

    private void printAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information dialog");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private boolean connectToServer() {
        try {
            ServerConnector.init(hostTextField.getText(), Integer.parseInt(portTextField.getText()), nicknameTextField.getText());
            return true;
        } catch (Exception e) {
            printAlert("Cannot connect to server " + e.getMessage());
        }
        return false;
    }
}