package poker.client.communication;

import poker.properties.PlayerMoveProperties;

public class MsgFormats {

	public static String moveFormat(int type){
		return
			"{" +
					"\"name\": \"move\"," +
					"\"type\": " + type +
			"}";
	}

	public static String raiseFormat(int value){
		return
				"{" +
						"\"name\": \"move\"," +
						"\"type\": " + PlayerMoveProperties.RAISE + "," +
						"\"value\": " + value +
				"}";
	}

	public static String disconnectFormat(){
		return 
			"{" +
					"\"name\": \"disconnect\"" +
			"}";
	}

}