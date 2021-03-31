package poker.client.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import poker.client.communication.ServerConnector;
import poker.client.data.GameTable;

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

	public GameMenuController(){

	}

	public void enableButtons(boolean all){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@FXML
	private void initialize(){
		raiseSlider.setMin(1);
		raiseSlider.setMax(10);
	}

	@FXML
	private void foldButtonClicked(){
		System.out.println("foldButton");
		//throw new UnsupportedOperationException("Not implemented yet");
	}

	@FXML
	private void checkButtonClicked(){
		System.out.println("checkButton");
		//throw new UnsupportedOperationException("Not implemented yet");
	}

	@FXML
	private void callButtonClicked(){
		System.out.println("callButton");
		//throw new UnsupportedOperationException("Not implemented yet");
	}

	@FXML
	private void raiseButtonClicked(){
		System.out.println("raiseButton");
		//throw new UnsupportedOperationException("Not implemented yet");
	}

	@FXML
	private void sliderMoved(){
		raiseText.setText(Math.round(raiseSlider.getValue()) + " $");
		//throw new UnsupportedOperationException("Not implemented yet");
	}

}