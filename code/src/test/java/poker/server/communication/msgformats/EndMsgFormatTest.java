package poker.server.communication.msgformats;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poker.properties.PlayerStateProperties;
import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.data.cards.Card;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EndMsgFormatTest {

    private GameTable gameTable;
    private Set<Player> winners;

    @BeforeEach
    public void setUp(){
        gameTable = new GameTable();
        winners = new HashSet<>();
    }

    @Test
    public void shouldReturnCorrectMsgWhenTwoPlayersAreInGameAndOneIsAfterFold(){
        Player player1 = new Player("player1");
        player1.setState(PlayerStateProperties.INGAME);
        player1.setHandCards(new Card(3, 2), new Card(6, 0));
        winners.add(player1);

        Player player2 = new Player("player2");
        player2.setState(PlayerStateProperties.INGAME);
        player2.setHandCards(new Card(7, 1), new Card(11, 3));
        winners.add(player2);

        Player player3 = new Player("player3");
        player3.setState(PlayerStateProperties.AFTERFOLD);
        player3.setHandCards(new Card(3, 1), new Card(8, 2));

        int potValue = 25;
        int numberOfWinners = 2;

        gameTable.setPotValue(potValue);
        gameTable.addPlayer(player1);
        gameTable.addPlayer(player2);
        gameTable.addPlayer(player3);

        String msg = EndMsgFormat.getMsg(gameTable, winners);

        checkMsg(new JSONObject(msg), potValue/numberOfWinners);
    }

    @Test
    public void shouldReturnCorrectMsgWhenOnePlayerIsInGame(){
        Player player1 = new Player("player1");
        player1.setState(PlayerStateProperties.INGAME);
        player1.setHandCards(new Card(3, 2), new Card(6, 0));

        Player player2 = new Player("player2");
        player2.setState(PlayerStateProperties.INMOVE);
        player2.setHandCards(new Card(7, 1), new Card(11, 3));
        winners.add(player2);

        int potValue = 100;
        int numberOfWinners = 1;

        gameTable.setPotValue(potValue);
        gameTable.addPlayer(player1);
        gameTable.addPlayer(player2);

        String msg = EndMsgFormat.getMsg(gameTable, winners);

        checkMsg(new JSONObject(msg), potValue/numberOfWinners);
    }

    private void checkMsg(JSONObject msg, int prize){
        assertEquals("end", msg.getString("name"));
        assertEquals(prize, msg.getInt("prize"));
        checkPlayers(msg.getJSONArray("players"));
    }

    private void checkPlayers(JSONArray playersJSON){
        Player[] players = gameTable.getPlayers();

        for(int i = 0; i < players.length; i++){
            if(players[i] != null && players[i].getState() != PlayerStateProperties.AFTERFOLD){
                checkPlayer(players[i], playersJSON.getJSONObject(i));
            } else {
                assertEquals(JSONObject.NULL, playersJSON.get(i));
            }
        }
    }

    private void checkPlayer(Player expectedPlayer, JSONObject actualPlayerJSON){
        assertEquals(winners.contains(expectedPlayer), actualPlayerJSON.getBoolean("winner"));

        Card[] expectedCards = expectedPlayer.getHandCards();
        JSONArray actualCardsJSON = actualPlayerJSON.getJSONArray("cards");

        assertEquals(expectedCards.length, actualCardsJSON.length());

        for(int i = 0; i < expectedCards.length; i++){
            JSONObject actualCardJSON = actualCardsJSON.getJSONObject(i);
            assertEquals(expectedCards[i].number, actualCardJSON.getInt("number"));
            assertEquals(expectedCards[i].color, actualCardJSON.getInt("color"));
        }
    }
}
