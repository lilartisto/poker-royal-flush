package poker.server.communication.interpreters;

import org.junit.jupiter.api.Test;
import poker.properties.PlayerStateProperties;
import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.gamecontrollers.CycleController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoldMsgInterpreterTest {

    @Test
    public void shouldInterpretCorrectlyWhenPlayerFolded() {
        Player player = new Player("test");
        GameTable gameTable = GameTable.getInstance();
        CycleController controller = new CycleController(gameTable, null);
        MsgInterpreter interpreter = new FoldMsgInterpreter();

        interpreter.interpret(null, player, controller);

        int expectedPlayersState = PlayerStateProperties.AFTERFOLD;
        int actualPlayersState = player.getState();

        assertEquals(expectedPlayersState, actualPlayersState);
    }
}
