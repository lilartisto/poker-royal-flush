package poker.server.communication.interpreters;

import org.junit.jupiter.api.Test;
import poker.properties.PlayerStateProperties;
import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.gamecontrollers.CycleController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallMsgInterpreterTest {

    @Test
    public void shouldInterpretCorrectlyWhenPlayerCalled() {
        Player player = new Player("test");
        GameTable gameTable = GameTable.getInstance();
        CycleController controller = new CycleController(gameTable, null);
        MsgInterpreter interpreter = new CallMsgInterpreter();

        int playerStartMoney = 100;
        int pot = 5;

        player.setMoney(playerStartMoney);
        controller.setMinPot(pot);
        interpreter.interpret(null, player, controller);

        int expectedPlayersPot = pot;
        int actualPlayersPot = player.getPotValue();

        int expectedPlayersMoney = playerStartMoney - pot;
        int actualPlayersMoney = player.getMoney();

        int expectedPlayersState = PlayerStateProperties.INGAME;
        int actualPlayersState = player.getState();

        assertEquals(expectedPlayersPot, actualPlayersPot);
        assertEquals(expectedPlayersMoney, actualPlayersMoney);
        assertEquals(expectedPlayersState, actualPlayersState);
    }
}
