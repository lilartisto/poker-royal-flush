package poker.server.communication.interpreters;

import org.json.JSONObject;
import poker.properties.PlayerStateProperties;
import poker.server.data.Player;
import poker.server.gamecontrollers.CycleController;

public class FoldMsgInterpreter implements MsgInterpreter {

	public void interpret(JSONObject msg, Player player, CycleController cycleController){
		player.setState(PlayerStateProperties.AFTERFOLD);
	}

}