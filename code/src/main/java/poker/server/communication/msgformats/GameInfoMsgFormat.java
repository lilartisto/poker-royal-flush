package poker.server.communication.msgformats;

import org.json.JSONArray;
import org.json.JSONObject;
import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.data.cards.Card;

public class GameInfoMsgFormat {

    public static String getMsg(GameTable gameTable){
        JSONObject msg = new JSONObject();
        msg.put("name", "info");
        msg.put("table", getTableMap(gameTable));

        return msg.toString();
    }

    private static JSONObject getTableMap(GameTable gameTable){
        JSONObject table = new JSONObject();
        table.put("cards", getCardsArray(gameTable.getTableCards()));
        table.put("pot", gameTable.getPotValue());
        table.put("players", getPlayersArray(gameTable.getPlayers()));

        return table;
    }

    private static JSONArray getCardsArray(Card[] cards){
        JSONArray msgCards = new JSONArray();

        for (Card card : cards) {
            msgCards.put(cardToJSONObject(card));
        }

        return msgCards;
    }

    private static JSONObject cardToJSONObject(Card card){
        if(card != null){
            JSONObject msgCard = new JSONObject();
            msgCard.put("number", card.number);
            msgCard.put("color", card.color);
            return msgCard;
        }
        return null;
    }

    private static JSONArray getPlayersArray(Player[] players){
        JSONArray msgPlayers = new JSONArray();

        for (Player player : players) {
            msgPlayers.put(playerToJSONObject(player));
        }

        return msgPlayers;
    }

    private static JSONObject playerToJSONObject(Player player){
        if(player != null){
            JSONObject msgPlayer = new JSONObject();
            msgPlayer.put("nickname", player.nickname);
            msgPlayer.put("money", player.getMoney());
            msgPlayer.put("state", player.getState());
            msgPlayer.put("pot", player.getPotValue());
            return msgPlayer;
        }
        return null;
    }

}
