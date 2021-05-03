package poker.client.communication.interpreters;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poker.client.Game;
import poker.client.data.GameTable;
import poker.client.data.Player;
import poker.client.data.cards.Card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StartMsgInterpreterTest {

    private MsgInterpreter interpreter;
    private Player player;
    private GameTable gameTable;

    @BeforeEach
    public void setUp(){
        interpreter = new StartMsgInterpreter();
        player = new Player("test");
        gameTable = new GameTable(0);
        gameTable.setPlayer(player, 0);
    }

    @Test
    public void shouldDoNothingWhenMsgIsNull(){
        interpreter.interpret(null, new GameTable(0));
        Card[] actualCards = player.getHandCards();

        assertNull(actualCards[0]);
        assertNull(actualCards[1]);
    }

    @Test
    public void shouldInterpretMsgCorrectWhenMsgIsCorrect(){
        String msg = "{\"name\":\"start\",\"cards\":[{\"color\":3,\"number\":12},{\"color\":0,\"number\":8}]}";

        interpreter.interpret(new JSONObject(msg), gameTable);
        Card[] actualCards = player.getHandCards();

        assertEquals(3, actualCards[0].color);
        assertEquals(12, actualCards[0].number);
        assertEquals(0, actualCards[1].color);
        assertEquals(8, actualCards[1].number);
    }

    @Test
    public void shouldInterpretMsgCorrectWhenGameTableIsNull(){
        String msg = "{\"name\":\"start\",\"cards\":[{\"color\":1,\"number\":2},{\"color\":3,\"number\":4}]}";

        interpreter.interpret(new JSONObject(msg), gameTable);
        Card[] actualCards = player.getHandCards();

        assertEquals(1, actualCards[0].color);
        assertEquals(2, actualCards[0].number);
        assertEquals(3, actualCards[1].color);
        assertEquals(4, actualCards[1].number);
    }

    @Test
    public void shouldDoNothingWhenCardsAreNull(){
        String msg = "{\"name\":\"start\",\"cards\":[null,null]}";

        interpreter.interpret(new JSONObject(msg), gameTable);
        Card[] actualCards = player.getHandCards();

        assertNull(actualCards[0]);
        assertNull(actualCards[1]);
    }

    @Test
    public void shouldDoNothingWhenCardsDoNotContainNumber(){
        String msg = "{\"name\":\"start\",\"cards\":[{\"color\":1},{\"color\":3,\"number\":4}]}";

        interpreter.interpret(new JSONObject(msg), gameTable);
        Card[] actualCards = player.getHandCards();

        assertNull(actualCards[0]);
        assertNull(actualCards[1]);
    }

    @Test
    public void shouldDoNothingWhenCardsDoNotContainColor(){
        String msg = "{\"name\":\"start\",\"cards\":[{\"color\":1,\"number\":2},{\"number\":4}]}";

        interpreter.interpret(new JSONObject(msg), gameTable);
        Card[] actualCards = player.getHandCards();

        assertNull(actualCards[0]);
        assertNull(actualCards[1]);
    }
}
