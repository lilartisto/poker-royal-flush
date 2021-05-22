package poker.client.communication.interpreters;


import org.json.JSONObject;
import poker.client.controller.GameMenuController;
import poker.client.data.GameTable;

public class MoveRequestMsgInterpreter implements MsgInterpreter {

	@Override
	public void interpret(JSONObject msg, GameTable gameTable){
		int minValue = msg.getInt("minValue");
		int playersMoney = gameTable.getMainPlayer().getMoney();
		GameMenuController controller = GameMenuController.getInstance();
		if(minValue == 0){
			controller.enableFoldCheckRaise();
		} else if(minValue >= playersMoney){
			controller.enableCallFold();
		} else {
			controller.enableFoldCallRaise();
		}
		controller.setMinMaxSlider(minValue + 1, playersMoney);
	}

}