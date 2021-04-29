package poker.client.communication.interpreters;


import org.json.JSONObject;
import poker.client.Game;
import poker.client.controller.GameMenuController;
import poker.client.data.GameTable;
import poker.client.data.Player;

public class MoveRequestMsgInterpreter implements MsgInterpreter {

	@Override
	public void interpret(JSONObject msg, GameTable gameTable){
		int minValue = msg.getInt("minValue");
		int playersMoney = Game.getPlayer().getMoney();
		GameMenuController controller = Game.getGameMenuController();

		if(minValue == 0){
			controller.enableFoldCheckRaise();
		} else if(minValue >= playersMoney){
			controller.enableCallFold();
		} else {
			controller.enableFoldCallRaise();
		}
	}

}