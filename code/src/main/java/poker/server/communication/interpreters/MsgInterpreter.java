package poker.server.communication.interpreters;

import org.json.JSONObject;
import poker.server.data.Player;
import poker.server.gamecontrollers.CycleController;

public interface MsgInterpreter {

    public void interpret(JSONObject msg, Player player, CycleController cycleController);

}