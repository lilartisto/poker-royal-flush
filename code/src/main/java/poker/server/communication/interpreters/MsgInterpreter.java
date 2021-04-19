package poker.server.communication.interpreters;

import poker.server.data.Player;
import poker.server.gamecontrollers.CycleController;

public interface MsgInterpreter {

	public void interpret(String msg, Player player, CycleController cycleController);

}