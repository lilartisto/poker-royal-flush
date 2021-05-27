package poker.client.communication.interpreters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import poker.client.data.GameTable;
import poker.client.data.cards.Card;
import poker.client.data.cards.Deck;

public class StartMsgInterpreter implements MsgInterpreter {

    @Override
    public void interpret(JSONObject msg, GameTable gameTable) {
        try {
            setHandCards(Deck.getInstance(), msg.getJSONArray("cards"), gameTable);
        } catch (JSONException | NullPointerException e) {
            gameTable.setHandCards(null, null);
        }
    }

    private void setHandCards(Deck deck, JSONArray cards, GameTable gameTable) {
        JSONObject card0JSON = cards.getJSONObject(0);
        JSONObject card1JSON = cards.getJSONObject(1);

        Card card0 = deck.getCard(card0JSON.getInt("color"), card0JSON.getInt("number"));
        Card card1 = deck.getCard(card1JSON.getInt("color"), card1JSON.getInt("number"));
        gameTable.setHandCards(card0, card1);
    }

}