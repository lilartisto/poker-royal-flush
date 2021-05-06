package poker.server.data.database;

import org.junit.jupiter.api.Test;
import poker.server.data.Player;

import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataBaseControllerTest {

    @Test
    public void shouldConnectToDatabaseWhenDatabaseWorks() {
        try {
            new DataBaseController(
                    "jdbc:postgresql://localhost:7432/poker_database",
                    "poker_user",
                    "Fl94yuwHClB6eKltjLnPYQ=="
            ).closeConnection();
            assert true;
        } catch (SQLException e){
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void shouldInsertPlayerToDatabaseWhenDatabaseDoesWorks(){
        try {
            DataBaseController dbController = new DataBaseController(
                    "jdbc:postgresql://localhost:7432/poker_database",
                    "poker_user",
                    "Fl94yuwHClB6eKltjLnPYQ=="
            );

            Player expectedPlayer = new Player("M" + new Random().nextInt((int)1e6));
            expectedPlayer.setMoney(500);

            dbController.insertPlayer(expectedPlayer);

            Player actualPlayer = dbController.getPlayer(expectedPlayer.nickname);

            assertEquals(expectedPlayer.nickname, actualPlayer.nickname);
            assertEquals(expectedPlayer.getMoney(), actualPlayer.getMoney());
        } catch (SQLException e){
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void shouldUpdatePlayerToDatabaseWhenDatabaseDoesWorks(){
        try {
            DataBaseController dbController = new DataBaseController(
                    "jdbc:postgresql://localhost:7432/poker_database",
                    "poker_user",
                    "Fl94yuwHClB6eKltjLnPYQ=="
            );
            Player expectedPlayer = new Player("M" + new Random().nextInt((int)1e6));

            expectedPlayer.setMoney(500);
            dbController.insertPlayer(expectedPlayer);

            Player actualPlayer = dbController.getPlayer(expectedPlayer.nickname);

            assertEquals(expectedPlayer.nickname, actualPlayer.nickname);
            assertEquals(expectedPlayer.getMoney(), actualPlayer.getMoney());


            expectedPlayer.setMoney(100);
            dbController.updatePlayer(expectedPlayer);

            actualPlayer = dbController.getPlayer(expectedPlayer.nickname);

            assertEquals(expectedPlayer.nickname, actualPlayer.nickname);
            assertEquals(expectedPlayer.getMoney(), actualPlayer.getMoney());
        } catch (SQLException e){
            e.printStackTrace();
            assert false;
        }
    }
}
