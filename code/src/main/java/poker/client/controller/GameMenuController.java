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
		//TODO
		// text sie nie przesuwa gdy klikniemy raz mysza tzn. nie przeciagniemy jej

		raiseText.setText(getRaiseSliderValue() + " $");
		//throw new UnsupportedOperationException("Not implemented yet");
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

}