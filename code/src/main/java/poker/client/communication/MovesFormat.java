package poker.client.communication;

import poker.properties.PlayerMoveProperties;

public class MovesFormat {

	public static String moveFormat(int type){
		String msg =
				"{" +
						"\"name\": \"move\"," +
						"\"type\": " + type +
				"}";
		return msg;
	}

	public static String raiseFormat(int value){
		String msg =
				"{" +
						"\"name\": \"move\"," +
						"\"type\": " + PlayerMoveProperties.RAISE + "," +
						"\"value\": " + value +
						"}";
		return msg;
	}

}