package poker.server.communication.interpreters;

import poker.server.data.Player;
import poker.server.gamecontrollers.CycleController;

public class CheckMsgInterpreter implements MsgInterpreter {

	@Override
	public void interpret(String msg, Player player, CycleController cycleController){
		throw new UnsupportedOperationException("Not implemented yet");
	}

}