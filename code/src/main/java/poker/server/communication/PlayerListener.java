package poker.server.communication;

import org.json.JSONException;
import org.json.JSONObject;
import poker.server.data.Player;

public class PlayerListener {

    private final ClientConnector clientConnector;
    private final Player player;
    private boolean listen;
    private String lastMsg;

    public PlayerListener(ClientConnector clientConnector, Player player){
        this.clientConnector = clientConnector;
        this.player = player;
        listen = true;
        startConstantlyListenForMsg();
    }

    private void startConstantlyListenForMsg(){
        Thread listeningThread = new Thread(this::constantlyListenForMsg);
        listeningThread.setDaemon(true);
        listeningThread.start();
    }

    private void constantlyListenForMsg(){
        while(listen){
            lastMsg = clientConnector.listenForPlayerMsg(player);

            if(disconnectMsg(lastMsg)){
                clientConnector.disconnectFromPlayer(player);
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e){
                return;
            }
        }
    }

    private boolean disconnectMsg(String msg){
        JSONObject jsonObject = new JSONObject(msg);

        try {
            String name = jsonObject.getString("name");
            return name.equals("disconnect");
        } catch (JSONException e){
            return false;
        }
    }

    public void stopListening(){
        listen = false;
    }

    public String getLastMsg(){
        String msg = lastMsg;
        lastMsg = null;
        return msg;
    }
}
