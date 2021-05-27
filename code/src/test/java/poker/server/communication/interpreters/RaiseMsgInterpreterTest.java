package poker.server.communication.interpreters;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import poker.client.communication.MsgFormats;
import poker.properties.PlayerStateProperties;
import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.gamecontrollers.CycleController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RaiseMsgInterpreterTest {

    @Test
    public void shouldInterpretCorrectlyWhenPlayerRaised() {
        Player player = new Player("test");
        GameTable gameTable = GameTable.getInstance();
        CycleController controller = new CycleController(gameTable, null);
        MsgInterpreter interpreter = new RaiseMsgInterpreter();

        int pot = 5;
        int raise = 10;
        controller.setMinPot(pot);
        interpreter.interpret(new JSONObject(MsgFormats.raiseFormat(raise)), player, controller);

        int expectedControllersPot = raise;
        int actualControllersPot = controller.getMinPot();

        int expectedPlayersPot = raise;
        int actualPlayersPot = player.getPotValue();

        int expectedPlayersMoney = -1 * raise;
        int actualPlayersMoney = player.getMoney();

        int expectedPlayersState = PlayerStateProperties.INGAME;
        int actualPlayersState = player.getState();

        assertEquals(expectedControllersPot, actualControllersPot);
        assertEquals(expectedPlayersPot, actualPlayersPot);
        assertEquals(expectedPlayersMoney, actualPlayersMoney);
        assertEquals(expectedPlayersState, actualPlayersState);
    }
}
