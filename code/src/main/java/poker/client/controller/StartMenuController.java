package poker.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class StartMenuController {

    @FXML
    private Pane thisPane;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private TextField ipTextField;
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
            connectToServer();
            switchToMainPane();
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
            errorMsg += "Port number is incorrect";
        }

        if(errorMsg.length() > 0){
            throw new IllegalArgumentException(errorMsg);
        }
    }

    private boolean validNickname() {
        int nicknameLength = nicknameTextField.getText().length();
        return nicknameLength >= 1 && nicknameLength <= 10;
    }

    private boolean validHost() {
        try {
            int host = Integer.parseInt(hostTextField.getText());
            return host >= 0 && host <= 65535;
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

    private void connectToServer() {
        System.err.println("NOT IMPLEMENTED: poker.client.controller.StartMenuController.connectToServer()");
    }
}