package poker.client.communication.interpreters;

import poker.client.data.GameTable;

public interface MsgInterpreter {

	public void interpret(String msg, GameTable gameTable);

}