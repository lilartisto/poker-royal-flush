package poker.client.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import poker.client.Game;
import poker.client.communication.MovesFormat;
import poker.client.communication.ServerConnector;
import poker.client.data.GameTable;
import poker.client.view.TableView;
import poker.properties.PlayerMoveProperties;
import poker.properties.PlayerStateProperties;

import java.io.IOException;

public class GameMenuController {

	private GameTable gameTable;
	private ServerConnector serverConnector;

	@FXML
	private Button foldButton;
	@FXML
	private Button checkButton;
	@FXML
	private Button callButton;
	@FXML
	private Button raiseButton;
	@FXML
	private Slider raiseSlider;
	@FXML
	private Text raiseText;
	@FXML
	private Canvas canvas;
	@FXML
	private Pane thisPane;

	@FXML
	private void initialize(){
		raiseSlider.setMin(1);
		raiseSlider.setMax(10);

		serverConnector = Game.getServerConnector();
		Game.setTableView(new TableView(canvas));
	}

	@FXML
	private void foldButtonClicked(){
		makeMove(PlayerMoveProperties.FOLD);
		disableButtons();
	}

	@FXML
	private void checkButtonClicked(){
		makeMove(PlayerMoveProperties.CHECK);
		disableButtons();
	}

	@FXML
	private void callButtonClicked(){
		makeMove(PlayerMoveProperties.CALL);
		disableButtons();
	}

	private void makeMove(int move){
		serverConnector.sendMsg(MovesFormat.moveFormat(move));
	}

	@FXML
	private void raiseButtonClicked(){
		serverConnector.sendMsg(MovesFormat.raiseFormat(getRaiseSliderValue()));
		disableButtons();
	}

	private int getRaiseSliderValue(){
		return (int)Math.round(raiseSlider.getValue());
	}

	@FXML
	private void sliderMoved() {
		raiseText.setText(getRaiseSliderValue() + " $");
	}

	private void disableButtons(){
		foldButton.setDisable(true);
		checkButton.setDisable(true);
		callButton.setDisable(true);
		raiseButton.setDisable(true);
	}

	public void enableFoldCheckRaise(){
		foldButton.setDisable(false);
		checkButton.setDisable(false);
		raiseButton.setDisable(false);
	}

	public void enableCallFold(){
		callButton.setDisable(false);
		foldButton.setDisable(false);
	}

	public void enableFoldCallRaise(){
		foldButton.setDisable(false);
		callButton.setDisable(false);
		raiseButton.setDisable(false);
	}

	public void setMinMaxSlider(int min, int max){
		raiseSlider.setMin(min);
		raiseSlider.setMax(max);
	}

	public void resetApp(String reason){
		Platform.runLater(() -> {
			printAlert(reason);
			switchToStartPane();
		});
	}

	private void printAlert(String msg) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(msg);
		alert.showAndWait();
	}

	private void switchToStartPane() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/startPane.fxml"));
			Pane pane = fxmlLoader.load();
			Scene scene = new Scene(pane);
			Stage stage = (Stage) thisPane.getScene().getWindow();
			stage.setScene(scene);
		} catch (IOException e) {
			printAlert(e.getMessage());
		}
	}

}