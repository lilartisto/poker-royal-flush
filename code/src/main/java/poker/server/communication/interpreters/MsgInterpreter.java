package poker.server.communication.interpreters;

import poker.server.data.GameTable;
import poker.server.data.Player;

public interface MsgInterpreter {

	public void interpret(String msg, GameTable gameTable, Player player);

}