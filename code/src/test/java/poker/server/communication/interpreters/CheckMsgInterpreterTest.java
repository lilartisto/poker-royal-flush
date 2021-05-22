package poker.server.communication.interpreters;

import org.junit.jupiter.api.Test;
import poker.properties.PlayerStateProperties;
import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.gamecontrollers.CycleController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckMsgInterpreterTest {

    @Test
    public void shouldInterpretCorrectlyWhenPlayerChecked(){
        Player player = new Player("test");
        GameTable gameTable = GameTable.getInstance();
        CycleController controller = new CycleController(gameTable, null);
        MsgInterpreter interpreter = new CheckMsgInterpreter();

        interpreter.interpret(null, player, controller);

        int expectedPlayersState = PlayerStateProperties.INGAME;
        int actualPlayersState = player.getState();

        assertEquals(expectedPlayersState, actualPlayersState);
    }
}
