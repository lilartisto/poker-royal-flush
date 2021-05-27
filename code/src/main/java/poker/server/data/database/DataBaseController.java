package poker.server.data.database;

import poker.server.data.Player;

import java.sql.*;

public class DataBaseController {

    private final Connection connection;

    public DataBaseController(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public void insertPlayer(Player player) throws SQLException {
        String sql = "INSERT INTO T_PLAYERS (nickname, money) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, player.nickname);
        statement.setInt(2, player.getMoney());

        if (statement.executeUpdate() <= 0) {
            throw new SQLException("Cannot insert " + player.nickname + " to database");
        }
    }

    public void updatePlayer(Player player) throws SQLException {
        String sql = "UPDATE T_PLAYERS SET money = ? WHERE nickname = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, player.getMoney());
        statement.setString(2, player.nickname);

        if (statement.executeUpdate() <= 0) {
            throw new SQLException("Cannot update " + player.nickname + " in database");
        }
    }

    public Player getPlayer(String nickname) throws SQLException {
        String sql = "SELECT * FROM T_PLAYERS WHERE nickname = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nickname);

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            int money = result.getInt("money");
            Player player = new Player(nickname);
            player.setMoney(money);
            return player;
        } else {
            return null;
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}