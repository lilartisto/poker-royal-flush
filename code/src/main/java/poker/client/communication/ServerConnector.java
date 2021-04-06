package poker.client.communication;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ServerConnector {

	private Socket server;

	public ServerConnector(String host, int port, String nickname) throws Exception {
		server = new Socket(host, port);
		connect(nickname);
	}

	private void connect(String nickname) throws Exception {
		HashMap<String, Object> msgMap = new HashMap<>();
		msgMap.put("name", "connect");
		msgMap.put("nickname", nickname);
		JSONObject msgJSON = new JSONObject(msgMap);
		sendMsg(msgJSON.toString());
		String answer = listenForMsg();
		checkIfConnected(answer);
	}

	private void checkIfConnected(String msg) throws Exception {
		JSONObject msgJSON = new JSONObject(msg);
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

	public void constantlyListenForMsgs(){
		throw new UnsupportedOperationException("Not implemented yet");
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