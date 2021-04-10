package poker.server.communication;

import poker.server.Game;
import poker.server.communication.msgformats.ConnectMsgFormat;
import poker.server.data.GameTable;
import poker.server.data.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import org.json.*;

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
				//TODO
				// player exist ? sendMsg( MsgFormat.connectMsg - player exist and plays)
				Player player = new Player(nickname);
				int seat = Game.getGameTable().addPlayer(player);
				if(seat >= 0){
					playersSockets.put(player, socket);
					sendMsg(ConnectMsgFormat.getMsg(true, null, seat), player);
					System.out.println("Player " + nickname + " connected to server");
				} else {
					sendMsg(ConnectMsgFormat.getMsg(false, "Server is full", seat), socket);
				}
			} else {
				throw new JSONException("Expected connect msg, received mg type: " + msgName);
			}
		} catch (JSONException e){
			System.err.println("Received unexpected msg or msg with wrong format from: " + socket.getInetAddress() + "\n"
					+ "error: " + e.getMessage());
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
			e.printStackTrace();
		}
	}

	public void sendMsgToAll(String msg){
		for(Socket socket: playersSockets.values()){
			sendMsg(msg, socket);
		}
	}

	public String listenForPlayerMsg(Player player){
		throw new UnsupportedOperationException("Not implemented yet");
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
			//TODO
			//	rebuild catch
			e.printStackTrace();
			return null;
		}
	}

}