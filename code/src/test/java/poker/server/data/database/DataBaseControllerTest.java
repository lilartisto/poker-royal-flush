package poker.server.data.database;

import org.junit.jupiter.api.Test;

public class DataBaseControllerTest {

    @Test
    public void shouldConnectToDatabase(){
        new DataBaseController("jdbc:postgresql://localhost:5432/poker", "poker_modifier", "7hd42caj").closeConnection();
    }
}
