package poker.server.communication.msgformats;

public class MoveRequestMsgFormat {

    public static String getMsg(int minValue){
        String msg =
                "{" +
                    "\"name\": \"request\"," +
                    "\"minValue\": " + minValue +
                "}";
        return msg;
    }

}
