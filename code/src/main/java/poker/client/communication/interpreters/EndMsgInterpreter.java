package poker.client.communication.interpreters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import poker.client.controller.GameMenuController;
import poker.client.data.GameTable;
import poker.client.data.Player;
import poker.client.data.cards.Card;
import poker.client.data.cards.Deck;

public class EndMsgInterpreter implements MsgInterpreter {

    @Override
    public void interpret(JSONObject msg, GameTable gameTable) {
        try {
            gameTable.setPotValue(msg.getInt("prize"));
            interpretPlayers(msg.getJSONArray("players"), gameTable.getPlayers());
        } catch (JSONException | NullPointerException ignored) {
        }

        if (mainPlayerHasNoMoney(gameTable)) {
            disconnectFromServer();
        }
    }

    private void interpretPlayers(JSONArray playersJSON, Player[] players) {
        if (playersJSON.length() == players.length) {
            for (int i = 0; i < players.length; i++) {
                if (players[i] != null && playersJSON.get(i) != JSONObject.NULL) {
                    JSONObject playerJSON = playersJSON.getJSONObject(i);
                    if (playerJSON != null) {
                        interpretPlayer(playerJSON, players[i]);
                    } else {
                        players[i].setHandCards(null, null);
                    }
                }
            }
        }
    }

    private void interpretPlayer(JSONObject playerJSON, Player player) throws JSONException {
        try {
            player.setWinner(playerJSON.getBoolean("winner"));
            setCards(playerJSON.getJSONArray("cards"), player);
        } catch (JSONException ignored) {
        }
    }

    private void setCards(JSONArray cards, Player player) throws JSONException {
        try {
            Card card0 = getCard(cards.getJSONObject(0));
            Card card1 = getCard(cards.getJSONObject(1));
            player.setHandCards(card0, card1);
        } catch (JSONException ignored) {
        }
    }

    private Card getCard(JSONObject card) throws JSONException {
        int color = card.getInt("color");
        int number = card.getInt("number");
        return Deck.getInstance().getCard(color, number);
    }

    private boolean mainPlayerHasNoMoney(GameTable gameTable) {
        Player mainPlayer = gameTable.getMainPlayer();
        return mainPlayer.getMoney() <= 0 && !mainPlayer.isWinner();
    }

    private void disconnectFromServer() {
        GameMenuController.getInstance().resetApp("You lost all your money");
    }
}