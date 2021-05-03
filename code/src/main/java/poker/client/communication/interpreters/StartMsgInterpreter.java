package poker.client.communication.interpreters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import poker.client.Game;
import poker.client.data.GameTable;
import poker.client.data.Player;
import poker.client.data.cards.Card;
import poker.client.data.cards.Deck;

public class StartMsgInterpreter implements MsgInterpreter {

	@Override
	public void interpret(JSONObject msg, GameTable gameTable){
		if(msg == null){
			return;
		}

		try {
			JSONArray cards = msg.getJSONArray("cards");
			JSONObject card0JSON = cards.getJSONObject(0);
			JSONObject card1JSON = cards.getJSONObject(1);
			Deck deck = Game.getDeck();

			Card card0 = deck.getCard(card0JSON.getInt("color"), card0JSON.getInt("number"));
			Card card1 = deck.getCard(card1JSON.getInt("color"), card1JSON.getInt("number"));
			gameTable.setHandCards(card0, card1);
		} catch (JSONException e){
			gameTable.setHandCards(null, null);
		}
	}

}