package poker.client.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import poker.client.communication.ServerConnector;
import poker.client.data.GameTable;

public class GameMenuController {

	private GameTable gameTable;
	private ServerConnector serverConnector;
	private Button foldButton;
	private Button checkButton;
	private Button callButton;
	private Button raiseButton;
	private Slider raiseSlider;
	private Canvas canvas;

	public GameMenuController(){

	}

	public void enableButtons(boolean all){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@FXML
	private void initialize(){

	}

	@FXML
	private void foldButtonClicked(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@FXML
	private void checkButtonClicked(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@FXML
	private void callButtonClicked(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@FXML
	private void raiseButtonClicked(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@FXML
	private void sliderMoved(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

}