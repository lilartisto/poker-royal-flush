package poker.server.communication;

import poker.server.data.Player;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ClientConnector {

	private HashMap<Player, Socket> playersSockets;
	private ServerSocket serverSocket;

	public ClientConnector(){

	}

	public void listenForPlayers(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void connectToPlayer(Socket socket){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void sendMsg(String msg, Player player){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void sendMsgToAll(String msg){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public String listenForPlayerMsg(Player player){
		throw new UnsupportedOperationException("Not implemented yet");
	}

}