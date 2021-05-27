package poker.server.communication;

import poker.server.communication.msgformats.ConnectMsgFormat;
import poker.server.communication.msgformats.GameInfoMsgFormat;
import poker.server.data.GameTable;
import poker.server.data.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.*;
import poker.server.data.database.DataBaseController;

public class ClientConnector {

    private final HashMap<Player, Socket> playersSockets;
    private final HashMap<Player, PlayerListener> playersListeners;
    private final ServerSocket serverSocket;
    private final DataBaseController dbController;

    public ClientConnector(int port, DataBaseController dbController) throws IOException {
        playersSockets = new HashMap<>();
        playersListeners = new HashMap<>();
        serverSocket = new ServerSocket(port);
        this.dbController = dbController;
        listenForPlayers();
    }

    private void listenForPlayers() {
        Thread thread = new Thread(this::constantlyListenForPlayers);
        thread.setDaemon(true);
        thread.start();
    }

    private void constantlyListenForPlayers() {
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                String msg = listenForPlayerMsg(socket);

                if (msg != null) {
                    tryConnectToPlayer(new JSONObject(msg), socket);
                }
            } catch (Exception e) {
                String address = socket != null ? socket.getInetAddress().toString() : "";
                System.err.println("An error occurred while trying to connect to the client " + address);
            }
        }
    }

    private void tryConnectToPlayer(JSONObject msg, Socket socket) {
        try {
            String msgName = msg.getString("name");
            if (msgName.equals("connect")) {
                connectToPlayerIfItIsPossible(msg, socket);
            } else {
                throw new JSONException("Expected connect msg, received mg type: " + msgName);
            }
        } catch (JSONException e) {
            System.err.println("Received unexpected msg or msg with wrong format from: " + socket.getInetAddress() +
                    ". Error: " + e.getMessage());
        }
    }

    private void connectToPlayerIfItIsPossible(JSONObject msg, Socket socket) {
        String nickname = msg.getString("nickname");
        Player player = new Player(nickname);

        if (playersSockets.containsKey(player)) {
            sendMsg(ConnectMsgFormat.getMsg(false, "Player " + nickname + " is already playing", -1), socket);
            return;
        }

        connectToPlayerIfServerIsNotFull(nickname, socket);
    }

    private void connectToPlayerIfServerIsNotFull(String nickname, Socket socket) {
        GameTable gameTable = GameTable.getInstance();
        if (gameTable.hasFreeSeat()) {
            connectToPlayer(nickname, socket, gameTable);
        } else {
            sendMsg(ConnectMsgFormat.getMsg(false, "Server is full", -1), socket);
        }
    }

    private void connectToPlayer(String nickname, Socket socket, GameTable gameTable) {
        Player player = getPlayer(nickname);
        int seat = gameTable.addPlayer(player);

        playersSockets.put(player, socket);
        sendMsg(ConnectMsgFormat.getMsg(true, null, seat), player);

        System.out.println("Player " + nickname + " connected to server");

        sendMsgToAll(GameInfoMsgFormat.getMsg(gameTable));
        playersListeners.put(player, new PlayerListener(this, player));
    }

    private Player getPlayer(String nickname) {
        try {
            Player player = dbController.getPlayer(nickname);
            if (player == null) {
                player = new Player(nickname);
                dbController.insertPlayer(player);
            }
            return player;
        } catch (NullPointerException | SQLException e) {
            return new Player(nickname);
        }
    }

    public void sendMsg(String msg, Player player) {
        sendMsg(msg, playersSockets.get(player));
    }

    private void sendMsg(String msg, Socket socket) {
        if (msg == null) {
            return;
        }

        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(msg);
            pw.flush();
        } catch (IOException e) {
            disconnectFromPlayer(socket);
        }
    }

    public void sendMsgToAll(String msg) {
        for (Socket socket : playersSockets.values()) {
            sendMsg(msg, socket);
        }
    }

    public String listenForPlayerMsg(Player player) {
        return listenForPlayerMsg(playersSockets.get(player));
    }

    private String listenForPlayerMsg(Socket socket) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return br.readLine();
        } catch (IOException e) {
            disconnectFromPlayer(socket);
            return null;
        }
    }

    private void disconnectFromPlayer(Socket socket) {
        disconnectFromPlayer(getPlayerBySocket(socket));
    }

    public void disconnectFromPlayer(Player player) {
        if (player != null) {
            GameTable gameTable = GameTable.getInstance();
            gameTable.deletePlayer(player);
            updateDataBase(player);

            closeConnection(player);

            sendMsgToAll(GameInfoMsgFormat.getMsg(gameTable));
            System.out.println("Player " + player.nickname + " has disconnected from the server");
        }
    }

    private void closeConnection(Player player) {
        playersListeners.remove(player).stopListening();
        try {
            playersSockets.remove(player).close();
        } catch (IOException ignored) {
        }
    }

    private void updateDataBase(Player player) {
        try {
            dbController.updatePlayer(player);
        } catch (SQLException | NullPointerException ignored) {
        }
    }

    private Player getPlayerBySocket(Socket socket) {
        for (Map.Entry<Player, Socket> entry : playersSockets.entrySet()) {
            if (socket.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String getPlayerLastMsg(Player player) {
        try {
            return playersListeners.get(player).getLastMsg();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Player is not on the server");
        }
    }
}