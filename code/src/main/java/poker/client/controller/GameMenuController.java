package poker.client.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import poker.client.Game;
import poker.client.communication.MovesFormat;
import poker.client.communication.ServerConnector;
import poker.client.data.GameTable;
import poker.client.view.TableView;
import poker.properties.PlayerMoveProperties;
import poker.properties.PlayerStateProperties;

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

	public void enableButtons(boolean all){
		throw new UnsupportedOperationException("Not implemented yet");
	}

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
	}

	@FXML
	private void checkButtonClicked(){
		makeMove(PlayerMoveProperties.CHECK);
	}

	@FXML
	private void callButtonClicked(){
		makeMove(PlayerMoveProperties.CALL);
	}

	private void makeMove(int move){
		serverConnector.sendMsg(MovesFormat.moveFormat(move));
	}

	@FXML
	private void raiseButtonClicked(){
		serverConnector.sendMsg(MovesFormat.raiseFormat(getRaiseSliderValue()));
	}

	private int getRaiseSliderValue(){
		return (int)Math.round(raiseSlider.getValue());
	}

	@FXML
	private void sliderMoved() {
		raiseText.setText(getRaiseSliderValue() + " $");
		//throw new UnsupportedOperationException("Not implemented yet");
	}

}