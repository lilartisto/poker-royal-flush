package poker.server.communication;

import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.data.cards.Card;

public class MsgFormat {

	public static String startMsg(Card[] cards){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public static String gameInfoMsg(GameTable gameTable){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public static String moveRequestMsg(int move){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public static String endMsg(Player winner, Player[] players){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public static String connectMsg(boolean connected, String answer){
		String msg =
				"{" +
					"\"connected\": " + connected + ", " +
					"\"answer\": " + answer +
				"}";
		return msg;
	}
}