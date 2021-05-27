package poker.server.communication.msgformats;

import org.json.JSONArray;
import org.json.JSONObject;
import poker.server.data.cards.Card;

public class StartMsgFormat {

    public static String getMsg(Card[] cards) {
        JSONObject msg = new JSONObject();

        msg.put("name", "start");
        msg.put("cards", cardsToJSONArray(cards));

        return msg.toString();
    }

    private static JSONArray cardsToJSONArray(Card[] cards) {
        JSONArray cardsJSON = new JSONArray();

        for (Card card : cards) {
            cardsJSON.put(cardToJSONObject(card));
        }

        return cardsJSON;
    }

    private static JSONObject cardToJSONObject(Card card) {
        if (card != null) {
            JSONObject cardJSON = new JSONObject();

            cardJSON.put("number", card.number);
            cardJSON.put("color", card.color);

            return cardJSON;
        } else {
            return null;
        }
    }

}
