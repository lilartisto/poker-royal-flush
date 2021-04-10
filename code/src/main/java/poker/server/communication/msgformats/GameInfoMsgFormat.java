package poker.server.communication.msgformats;

import org.json.JSONObject;
import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.data.cards.Card;

import java.util.HashMap;
import java.util.Map;

public class GameInfoMsgFormat {

    public static String getMsg(GameTable gameTable){
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("name", "info");
        msgMap.put("table", getTableMap(gameTable));

        return new JSONObject(msgMap).toString();
    }

    private static Map<String, Object> getTableMap(GameTable gameTable){
        Map<String, Object> tableMap = new HashMap<>();
        tableMap.put("cards", getCardsArray(gameTable.getTableCards()));
        tableMap.put("pot", gameTable.getPotValue());
        tableMap.put("players", getPlayersArray(gameTable.getPlayers()));

        return tableMap;
    }

    private static Map[] getCardsArray(Card[] cards){
        Map[] msgCards = new HashMap[cards.length];

        for(int i = 0; i < cards.length; i++){
            msgCards[i] = cardToMap(cards[i]);
        }

        return msgCards;
    }

    private static Map<String, Object> cardToMap(Card card){
        if(card != null){
            Map<String, Object> cardMap = new HashMap<>();
            cardMap.put("number", card.number);
            cardMap.put("color", card.color);
            return cardMap;
        }
        return null;
    }

    private static Map[] getPlayersArray(Player[] players){
        Map[] msgPlayers = new HashMap[players.length];

        for(int i = 0; i < players.length; i++){
            msgPlayers[i] = playerToMap(players[i]);
        }

        return msgPlayers;
    }

    private static Map<String, Object> playerToMap(Player player){
        if(player != null){
            Map<String, Object> playerMap = new HashMap<>();
            playerMap.put("nickname", player.nickname);
            playerMap.put("money", player.getMoney());
            playerMap.put("state", player.getState());
            playerMap.put("pot", player.getPotValue());
            return playerMap;
        }
        return null;
    }

}
