package poker.server.communication.msgformats;

import org.json.JSONArray;
import org.json.JSONObject;
import poker.server.data.cards.Card;

public class StartMsgFormat {

    public static String getMsg(Card[] cards){
        JSONObject msg = new JSONObject();

        msg.put("name", "start");

        JSONObject card1;
        JSONObject card2;

        if(cards[0] != null && cards[1] != null) {
            card1 = new JSONObject();
            card2 = new JSONObject();
            card1.put("number", cards[0].number);
            card1.put("color", cards[0].color);
            card2.put("number", cards[1].number);
            card2.put("color", cards[1].color);
        } else {
            card1 = null;
            card2 = null;
        }

        msg.put("cards", new JSONArray(new JSONObject[]{card1, card2}));

        return msg.toString();
    }

}
