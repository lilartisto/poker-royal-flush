package poker.client.communication;

import javafx.application.Platform;
import org.json.JSONException;
import org.json.JSONObject;
import poker.client.communication.interpreters.*;
import poker.client.controller.GameMenuController;
import poker.client.data.GameTable;
import poker.client.view.TableView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnector {

    private static ServerConnector instance;

    public static void init(String host, int port, String nickname) throws Exception {
        instance = new ServerConnector(host, port, nickname);
    }

    public static ServerConnector getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Instance is not initialized");
        } else {
            return instance;
        }
    }

    private final Socket server;
    private final BufferedReader serverReader;

    private TableView tableView;

    private ServerConnector(String host, int port, String nickname) throws Exception {
        server = new Socket(host, port);

        serverReader = new BufferedReader(new InputStreamReader(server.getInputStream()));

        connect(nickname);
        startConstantlyListenForMsgs();
    }

    private void connect(String nickname) throws Exception {
        sendMsg(MsgFormats.connectFormat(nickname));
        String answerMsg = listenForMsg();

        if (answerMsg != null) {
            JSONObject answerJSON = new JSONObject(answerMsg);
            checkIfConnected(answerJSON);

            GameTable.getInstance().seat(answerJSON.getInt("seat"));
        } else {
            throw new Exception("Client has not received message from server");
        }
    }

    private void checkIfConnected(JSONObject msgJSON) throws Exception {
        boolean connected = msgJSON.getBoolean("connected");
        if (!connected) {
            throw new Exception(msgJSON.getString("answer"));
        }
    }

    private String listenForMsg() {
        try {
            return serverReader.readLine();
        } catch (IOException e) {
            if (server.isClosed()) {
                return null;
            }
            GameMenuController.getInstance().resetApp("Application lost connection with server");
            return null;
        }
    }

    private void startConstantlyListenForMsgs() {
        Thread listenThread = new Thread(() -> {
            while (true) {
                String msg = listenForMsg();
                if (msg != null) {
                    interpretMsg(msg);
                }
            }
        });
        listenThread.setDaemon(true);
        listenThread.start();
    }

    private void interpretMsg(String msg) {
        try {
            JSONObject jsonMsg = new JSONObject(msg);
            String msgName = jsonMsg.getString("name");
            MsgInterpreter msgInterpreter = getMsgInterpreter(msgName);

            interpretMsgAndDrawView(msgInterpreter, jsonMsg, msgName);
        } catch (JSONException ignored) {
        }
    }

    private void interpretMsgAndDrawView(MsgInterpreter msgInterpreter, JSONObject jsonMsg, String msgName) {
        if (msgInterpreter != null) {
            msgInterpreter.interpret(jsonMsg, GameTable.getInstance());
            if (msgName.equals("end")) {
                Platform.runLater(() -> tableView.drawEnding());
            } else {
                Platform.runLater(() -> tableView.draw());
            }
        }
    }

    private MsgInterpreter getMsgInterpreter(String msgName) {
        return switch (msgName) {
            case "info" -> new GameInfoMsgInterpreter();
            case "request" -> new MoveRequestMsgInterpreter();
            case "start" -> new StartMsgInterpreter();
            case "end" -> new EndMsgInterpreter();
            default -> null;
        };
    }

    public void sendMsg(String msg) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        try {
            PrintWriter pw = new PrintWriter(server.getOutputStream());
            pw.println(msg);
            pw.flush();
        } catch (IOException e) {
            if (server.isClosed()) {
                return;
            }
            GameMenuController.getInstance().resetApp("Application lost connection with server");
        }
    }

    public void disconnect() {
        sendMsg(MsgFormats.disconnectFormat());
        try {
            server.close();
        } catch (IOException ignored) {
        }
    }

    public void setTableView(TableView tableView) {
        this.tableView = tableView;
    }
}