package poker.server.communication.msgformats;

public class MoveRequestMsgFormat {

    public static String getMsg(int minValue) {
        return
            "{" +
                "\"name\": \"request\"," +
                "\"minValue\": " + minValue +
            "}";
    }

}
