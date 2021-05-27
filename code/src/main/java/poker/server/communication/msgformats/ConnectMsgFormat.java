package poker.server.communication.msgformats;

public class ConnectMsgFormat {

    public static String getMsg(boolean connected, String answer, int seat){
       return
            "{" +
                "\"connected\": " + connected + ", " +
                "\"answer\": " + answer + ", " +
                "\"seat\": " + seat +
            "}";
    }

}
