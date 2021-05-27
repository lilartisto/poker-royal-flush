package poker.server.communication.msgformats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.data.cards.Card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameInfoMsgFormatTest {

    @Test
    public void shouldReturnCorrectMsgWhenDataIsComplete() {
        GameTable gameTable = GameTable.getInstance();

        addPlayers(gameTable, 6);
        addCards(gameTable, 5);

        String actualMsg = GameInfoMsgFormat.getMsg(gameTable);
        System.out.println(actualMsg);
        checkCorrectness(actualMsg, gameTable);
    }

    @Test
    public void shouldReturnCorrectMsgWhenSomeCardsAreNull() {
        GameTable gameTable = GameTable.getInstance();

        addPlayers(gameTable, 6);
        addCards(gameTable, 3);

        String actualMsg = GameInfoMsgFormat.getMsg(gameTable);
        checkCorrectness(actualMsg, gameTable);
    }

    @Test
    public void shouldReturnCorrectMsgWhenSomePlayersAreNull() {
        GameTable gameTable = GameTable.getInstance();

        addPlayers(gameTable, 3);
        addCards(gameTable, 5);

        String actualMsg = GameInfoMsgFormat.getMsg(gameTable);
        checkCorrectness(actualMsg, gameTable);
    }

    @Test
    public void shouldReturnCorrectMsgWhenSomeCardsAndPlayersAreNull() {
        GameTable gameTable = GameTable.getInstance();

        addPlayers(gameTable, 2);
        addCards(gameTable, 2);

        String actualMsg = GameInfoMsgFormat.getMsg(gameTable);
        checkCorrectness(actualMsg, gameTable);
    }

    private void checkCorrectness(String msg, GameTable gameTable) {
        JSONObject jsonMsg = new JSONObject(msg);
        JSONObject jsonTable = jsonMsg.getJSONObject("table");

        assertEquals("info", jsonMsg.getString("name"));
        assertEquals(gameTable.getPotValue(), jsonTable.getInt("pot"));
        checkCardsCorrectness(jsonTable.getJSONArray("cards"), gameTable);
        checkPlayerCorrectness(jsonTable.getJSONArray("players"), gameTable);
    }

    private void checkPlayerCorrectness(JSONArray players, GameTable gameTable) {
        Player[] expectedPlayers = gameTable.getPlayers();

        for (int i = 0; i < expectedPlayers.length; i++) {
            try {
                JSONObject actualPlayer = players.getJSONObject(i);

                assertEquals(expectedPlayers[i].nickname, actualPlayer.getString("nickname"));
                assertEquals(expectedPlayers[i].getMoney(), actualPlayer.getInt("money"));
                assertEquals(expectedPlayers[i].getState(), actualPlayer.getInt("state"));
                assertEquals(expectedPlayers[i].getPotValue(), actualPlayer.getInt("pot"));
            } catch (JSONException e) {
                assertNull(expectedPlayers[i]);
            }
        }
    }

    private void checkCardsCorrectness(JSONArray cards, GameTable gameTable) {
        Card[] expectedCards = gameTable.getTableCards();

        for (int i = 0; i < expectedCards.length; i++) {
            try {
                JSONObject actualCard = cards.getJSONObject(i);

                assertEquals(expectedCards[i].color, actualCard.getInt("color"));
                assertEquals(expectedCards[i].number, actualCard.getInt("number"));
            } catch (JSONException e) {
                assertNull(expectedCards[i]);
            }
        }
    }

    private void addPlayers(GameTable gameTable, int n) {
        for (int i = 0; i < n; i++) {
            gameTable.addPlayer(new Player("nick" + i));
        }
    }

    private void addCards(GameTable gameTable, int n) {
        for (int i = 0; i < n; i++) {
            gameTable.addTableCard(new Card(i, 0));
        }
    }
}
