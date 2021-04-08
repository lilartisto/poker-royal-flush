package poker.client.communication.interpreters;

import org.json.JSONObject;
import poker.client.data.GameTable;

public interface MsgInterpreter {

	public void interpret(JSONObject msg, GameTable gameTable);

}