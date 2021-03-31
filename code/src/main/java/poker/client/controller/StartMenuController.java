package poker.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class StartMenuController {

	@FXML
	private TextField nicknameTextField;
	@FXML
	private TextField ipTextField;
	@FXML
	private TextField hostTextField;
	@FXML
	private Text errorText;

	public StartMenuController(){

	}

	@FXML
	private void connectButtonClicked(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

}