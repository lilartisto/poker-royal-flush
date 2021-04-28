package poker.client.communication;

import javafx.application.Platform;
import org.json.JSONObject;
import poker.client.communication.interpreters.*;
import poker.client.Game;
import poker.client.data.GameTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ServerConnector {

	private final Socket server;

	public ServerConnector(String host, int port, String nickname) throws Exception {
		server = new Socket(host, port);
		connect(nickname);
		startConstantlyListenForMsgs();
	}

	private void connect(String nickname) throws Exception {
		HashMap<String, Object> msgMap = new HashMap<>();
		msgMap.put("name", "connect");
		msgMap.put("nickname", nickname);
		JSONObject msgJSON = new JSONObject(msgMap);
		sendMsg(msgJSON.toString());
		JSONObject answer = new JSONObject(listenForMsg());
		checkIfConnected(answer);

		Game.setGameTable(new GameTable(answer.getInt("seat"), Game.getPlayer()));
	}

	private void checkIfConnected(JSONObject msgJSON) throws Exception {
		boolean connected = msgJSON.getBoolean("connected");
		if(!connected){
			throw new Exception(msgJSON.getString("answer"));
		}
	}

	private String listenForMsg(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(server.getInputStream()));
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

	private void startConstantlyListenForMsgs(){
		Thread listenThread = new Thread(() -> {
			while (true){
				String msg = listenForMsg();
				System.out.println(msg);
				interpretMsg(msg);
				Platform.runLater(() -> Game.getTableView().draw());
			}
		});
		listenThread.setDaemon(true);
		listenThread.start();
	}

	private void interpretMsg(String msg){
		JSONObject jsonMsg = new JSONObject(msg);
		String msgName = jsonMsg.getString("name");
		MsgInterpreter msgInterpreter = getMsgInterpreter(msgName);

		if(msgInterpreter != null){
			msgInterpreter.interpret(jsonMsg, Game.getGameTable());
		}
	}

	private MsgInterpreter getMsgInterpreter(String msgName){
		return switch (msgName) {
			case "info" -> new GameInfoMsgInterpreter();
			case "request" -> new MoveRequestMsgInterpreter();
			case "start" -> new StartMsgInterpreter();
			case "end" -> new EndMsgInterpreter();
			default -> null;
		};
	}

	public void sendMsg(String msg){
		if(msg == null){
			throw new IllegalArgumentException("Message cannot be null");
		}

		try {
			PrintWriter pw = new PrintWriter(server.getOutputStream());
			pw.println(msg);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}