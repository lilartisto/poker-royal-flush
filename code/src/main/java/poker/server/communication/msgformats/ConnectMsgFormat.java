package poker.server.communication.msgformats;

public class ConnectMsgFormat {

    public static String getMsg(boolean connected, String answer, int seat){
        String msg =
                "{" +
                        "\"connected\": " + connected + ", " +
                        "\"answer\": " + answer + ", " +
                        "\"seat\": " + seat +
                        "}";
        return msg;
    }

}
