package poker.client.communication.interpreters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poker.client.data.GameTable;
import poker.client.data.Player;
import poker.client.data.cards.Card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameInfoMsgInterpreterTest {

    private MsgInterpreter interpreter;
    private GameTable gameTable;

    @BeforeEach
    public void setUp(){
        interpreter = new GameInfoMsgInterpreter();
        gameTable = GameTable.getInstance();
    }

    @Test
    public void shouldInterpretCorrectWhenMsgIsComplete(){
        JSONObject msg = createMsg(6, 5, 350);

        interpreter.interpret(msg, gameTable);

        checkCorrectness(msg.getJSONObject("table"), gameTable);
    }

    @Test
    public void shouldInterpretCorrectWhenSomePlayersAreNull(){
        JSONObject msg = createMsg(3, 5, 320);

        interpreter.interpret(msg, gameTable);

        checkCorrectness(msg.getJSONObject("table"), gameTable);
    }

    @Test
    public void shouldInterpretCorrectWhenSomeCardsAreNull(){
        JSONObject msg = createMsg(6, 2, 30);

        interpreter.interpret(msg, gameTable);

        checkCorrectness(msg.getJSONObject("table"), gameTable);
    }

    @Test
    public void shouldInterpretCorrectWhenCardsAndPlayersAreNull(){
        JSONObject msg = createMsg(0, 0, 1250);

        interpreter.interpret(msg, gameTable);

        checkCorrectness(msg.getJSONObject("table"), gameTable);
    }

    private void checkCorrectness(JSONObject expectedTable, GameTable actualTable){
        assertEquals(expectedTable.getInt("pot"), actualTable.getPotValue());
        checkPlayersCorrectness(expectedTable.getJSONArray("players"), actualTable.getPlayers());
        checkCardsCorrectness(expectedTable.getJSONArray("cards"), actualTable.getTableCards());
    }

    private void checkPlayersCorrectness(JSONArray expectedPlayers, Player[] actualPlayers){
        int expectedLength = expectedPlayers.length();
        assert expectedLength == actualPlayers.length;

        for(int i = 0; i < expectedLength; i++){
            try {
                JSONObject expectedPlayer = expectedPlayers.getJSONObject(i);

                assertEquals(expectedPlayer.getString("nickname"), actualPlayers[i].nickname);
                assertEquals(expectedPlayer.getInt("state"), actualPlayers[i].getState());
                assertEquals(expectedPlayer.getInt("money"), actualPlayers[i].getMoney());
                assertEquals(expectedPlayer.getInt("pot"), actualPlayers[i].getPotValue());
            } catch (JSONException e){
                assertNull(actualPlayers[i]);
            }
        }
    }

    private void checkCardsCorrectness(JSONArray expectedCards, Card[] actualCards){
        int expectedLength = expectedCards.length();
        assert expectedLength == actualCards.length;

        for(int i = 0; i < expectedLength; i++){
            try {
                JSONObject expectedCard = expectedCards.getJSONObject(i);

                assertEquals(expectedCard.getInt("color"), actualCards[i].color);
                assertEquals(expectedCard.getInt("number"), actualCards[i].number);
            } catch (JSONException e){
                assertNull(actualCards[i]);
            }
        }
    }

    private JSONObject createMsg(int playersAmount, int cardsAmount, int pot){
        JSONObject msg = new JSONObject();
        JSONObject table = new JSONObject();
        JSONArray players = new JSONArray();
        JSONArray cards = new JSONArray();

        addPlayer(players, playersAmount);
        addCards(cards, cardsAmount);

        table.put("pot", pot);
        table.put("players", players);
        table.put("cards", cards);

        msg.put("name", "info");
        msg.put("table", table);

        return msg;
    }

    private void addPlayer(JSONArray array, int n){
        for(int i = 0; i < 6; i++){
            if(i < n) {
                JSONObject player = new JSONObject();
                player.put("nickname", "nick" + i);
                player.put("state", i % 3);
                player.put("money", i * 10);
                player.put("pot", i);
                array.put(player);
            } else {
                array.put(JSONObject.NULL);
            }
        }
    }

    private void addCards(JSONArray array, int n){
        for(int i = 0; i < 5; i++){
            if(i < n) {
                JSONObject card = new JSONObject();
                card.put("color", i % 4);
                card.put("number", i);
                array.put(card);
            } else {
                array.put(JSONObject.NULL);
            }
        }
    }
}
