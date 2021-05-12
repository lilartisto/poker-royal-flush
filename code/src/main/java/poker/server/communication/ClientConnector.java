package poker.server.communication;

import poker.server.Game;
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
import java.util.SortedMap;

import org.json.*;
import poker.server.data.database.DataBaseController;

public class ClientConnector {

	private HashMap<Player, Socket> playersSockets;
	private ServerSocket serverSocket;

	public ClientConnector(int port) throws IOException{
		playersSockets = new HashMap<>();
		serverSocket = new ServerSocket(port);
		listenForPlayers();
	}

	private void listenForPlayers(){
		Thread thread = new Thread(() -> {
			while(true) {
				try {
					Socket socket = serverSocket.accept();
					String msg = listenForPlayerMsg(socket);

					if (msg == null) {
						return;
					}

					JSONObject jsonMsg = new JSONObject(msg);
					connectToPlayer(jsonMsg, socket);
				} catch (IOException e) {
					System.err.println("An error occurred while trying to connect to the client");
				}
			}
		});
		//thread.setDaemon(true);
		thread.start();
	}

	private void connectToPlayer(JSONObject msg, Socket socket){
		try{
			String msgName = msg.getString("name");
			if(msgName.equals("connect")){
				String nickname = msg.getString("nickname");
				Player player = new Player(nickname);

				if(playersSockets.containsKey(player)){
					sendMsg(ConnectMsgFormat.getMsg(false, "Player " + nickname + " is already playing", -1), socket);
					return;
				}

				GameTable gameTable = Game.getGameTable();
				if(gameTable.hasFreeSeat()){
					player = getPlayer(nickname);
					playersSockets.put(player, socket);
					int seat = gameTable.addPlayer(player);
					sendMsg(ConnectMsgFormat.getMsg(true, null, seat), player);
					System.out.println("Player " + nickname + " connected to server");
				} else {
					sendMsg(ConnectMsgFormat.getMsg(false, "Server is full", -1), socket);
				}
			} else {
				throw new JSONException("Expected connect msg, received mg type: " + msgName);
			}
		} catch (JSONException e){
			System.err.println("Received unexpected msg or msg with wrong format from: " + socket.getInetAddress() + "\n"
					+ "error: " + e.getMessage());
		}
	}

	private Player getPlayer(String nickname){
		DataBaseController dbController = Game.getDataBaseController();

		if(dbController == null){
			return new Player(nickname);
		}

		try {
			Player player = dbController.getPlayer(nickname);
			if (player == null) {
				player = new Player(nickname);
				dbController.insertPlayer(player);
			}
			return player;
		} catch (SQLException e){
			return new Player(nickname);
		}
	}

	public void sendMsg(String msg, Player player){
		sendMsg(msg, playersSockets.get(player));
	}

	private void sendMsg(String msg, Socket socket){
		if(msg == null){
			throw new IllegalArgumentException("Message cannot be null");
		}

		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.println(msg);
			pw.flush();
		} catch (IOException e) {
			disconnectFromPlayer(socket);
		}
	}

	public void sendMsgToAll(String msg){
		for(Socket socket: playersSockets.values()){
			sendMsg(msg, socket);
		}
	}

	public String listenForPlayerMsg(Player player){
		return listenForPlayerMsg(playersSockets.get(player));
	}

	private String listenForPlayerMsg(Socket socket){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//StringBuilder msgBuilder = new StringBuilder(br.readLine());
			//String line;
			return br.readLine();

			//while ((line = br.readLine()).length() != 0) {
			//	msgBuilder.append("\n").append(line);
			//}

			//return msgBuilder.toString();
		} catch (IOException e){
			disconnectFromPlayer(socket);
			return null;
		}
	}

	private void disconnectFromPlayer(Socket socket){
		Player player = getPlayerBySocket(socket);

		if(player == null){
			return;
		}

		GameTable gameTable = Game.getGameTable();
		gameTable.deletePlayer(player);
		updateDataBase(player);
		playersSockets.remove(player);

		sendMsgToAll(GameInfoMsgFormat.getMsg(gameTable));

		System.out.println("Player " + player.nickname + " has been disconnected from the server");
	}

	private void updateDataBase(Player player){
		try {
			DataBaseController dbController = Game.getDataBaseController();
			dbController.updatePlayer(player);
		} catch (SQLException | NullPointerException ignored){ }
	}

	private Player getPlayerBySocket(Socket socket){
		for(Map.Entry<Player, Socket> entry: playersSockets.entrySet()){
			if(socket.equals(entry.getValue())){

				/*Player player = entry.getKey();
				Player[] players = Game.getGameTable().getPlayers();
				for(Player p: players){
					if(player.equals(p)){
						return p;
					}
				}*/

				return entry.getKey();
			}
		}
		return null;
	}
}