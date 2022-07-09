package com.apet2929.game.engine;

import com.apet2929.game.engine.states.GameStateManager;
import com.apet2929.game.engine.states.MultiplayerTestState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.apet2929.game.engine.Utils.NET_TIME_PER_TICK;

public class Network {

    private Socket socket;
    private HashMap<String, JSONObject> connectedPlayers;
    private JSONObject data;
    private ArrayList<JSONObject> playerUpdateData;
    private Runnable onTickRequested;
    private float elapsedTime;
    private int roomId;

    public Network() {
        connectedPlayers = new HashMap<>();
        connectSocket();
        configSocketEvents();
        elapsedTime = 0;
        playerUpdateData = new ArrayList<>();
        roomId = -1;
    }

    public void update(float delta){
        elapsedTime += delta;

        if(elapsedTime >= NET_TIME_PER_TICK){
            tick();
            elapsedTime = 0;
        }
    }

    public void tick(){
        onTickRequested.run();
        socket.emit("tick", data);
    }

    public void dispose() {
        socket.close();
    }

    void connectSocket(){
        try{
            socket = IO.socket("http://localhost:8081/");
            socket.connect();
        } catch (Exception e){
            System.err.println(e);
        }
    }

    public void disconnectSocket(){
        try{
            socket.disconnect();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void configSocketEvents(){
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Connected");
            }
        });
        socket.on("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO", "My ID: " + id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting ID");
                    e.printStackTrace();
                }
            }
        }).on("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO", "New Player Connect: " + id);
                    connectedPlayers.put(id, data);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting new player id");
                    e.printStackTrace();
                }
            }
        }).on("playerDisconnected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO", "Player Disconnected: " + id);
                    connectedPlayers.remove(id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting disconnected player id");
                    e.printStackTrace();
                }
            }
        }).on("tick", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                playerUpdateData.add(data);
            }
        });
    }

    public void joinRoom(int _roomId){


        if(this.socket.connected()){
            tryJoinRoom(roomId);
        } else {
            // wait until connection and then try to join the room
            socket.on("socketID", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    tryJoinRoom(_roomId);
                }
            });

        }


    }

    void tryJoinRoom(int _roomId){
        Gdx.app.log("SocketIO", "Trying to join room!");
        JSONObject joinRoomData = getJoinRoomData(_roomId);
        socket.emit("joinRoom", joinRoomData).on("joinRoomSuccess", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Joining room id: " + _roomId + " success!");
                roomId = _roomId;
            }
        }).on("joinFailure", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Joining room id: " + _roomId + " failure.");
                roomId = -1;
            }
        });
    }

    public void setData(JSONObject data){
        this.data = data;

    }

    public void putCallback(String eventId, Emitter.Listener callback){
        socket.on(eventId, callback);
    }

    public String getSocketID(){
        return socket.id();
    }

    public void setOnTickRequested(Runnable onTickRequested) {
        this.onTickRequested = onTickRequested;
    }

    public ArrayList<JSONObject> getPlayerUpdateData() {
        return playerUpdateData;
    }

    public void clearPlayerUpdateData(){
        playerUpdateData.clear();
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public int getRoomId(){
        return this.roomId;
    }

    JSONObject getJoinRoomData(int roomId){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("roomId", roomId);
            jsonObject.put("id", socket.id());
            return jsonObject;
        } catch (JSONException e){
            Gdx.app.error("SocketIO", "Getting joinRoom data fail!");
            e.printStackTrace();
        }
        return null;
    }
}
