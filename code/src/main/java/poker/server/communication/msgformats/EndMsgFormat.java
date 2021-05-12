package poker.server.communication.msgformats;

import org.json.JSONArray;
import org.json.JSONObject;
import poker.properties.PlayerStateProperties;
import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.data.cards.Card;

import java.util.Set;

public class EndMsgFormat {

    public static String getMsg(GameTable gameTable, Set<Player> winners){
        int n = winners.size();

        if(n == 0){
            return null;
        }

        JSONObject jsonMsg = new JSONObject();

        jsonMsg.put("name", "end");
        jsonMsg.put("prize", gameTable.getPotValue()/n);
        jsonMsg.put("players", playersToJSONArray(gameTable, winners));

        return jsonMsg.toString();
    }

    private static JSONArray playersToJSONArray(GameTable gameTable, Set<Player> winners){
        JSONArray playersJSONArray = new JSONArray();
        Player[] players = gameTable.getPlayers();

        for(Player player: players){
            if(player != null && player.getState() != PlayerStateProperties.AFTERFOLD){
                playersJSONArray.put(playerToJSONObject(player, winners.contains(player)));
            } else {
                playersJSONArray.put(JSONObject.NULL);
            }
        }

        return playersJSONArray;
    }

    private static JSONObject playerToJSONObject(Player player, boolean winner){
        JSONObject playerJSON = new JSONObject();

        playerJSON.put("winner", winner);
        playerJSON.put("cards", cardsToJSONArray(player.getHandCards()));

        return playerJSON;
    }

    private static JSONArray cardsToJSONArray(Card[] cards){
        JSONArray cardsJSONArray = new JSONArray();

        for(Card card: cards) {
            cardsJSONArray.put(cardToJSONObject(card));
        }

        return cardsJSONArray;
    }

    private static JSONObject cardToJSONObject(Card card){
        JSONObject cardJSON = new JSONObject();

        cardJSON.put("number", card.number);
        cardJSON.put("color", card.color);

        return cardJSON;
    }

}
