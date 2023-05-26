package com.apet2929.game.engine.network;

import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocalNetwork extends Network {
    ArrayList<JSONObject> playerData;
    String socketID;

    public LocalNetwork(){
        playerData = new ArrayList<>();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void joinRoom(int _roomId) {
        this.roomId = _roomId;
    }

    @Override
    public void setData(JSONObject data) {

    }

    @Override
    public void putCallback(String eventId, Emitter.Listener callback) {
        if(eventId.equals("socketID")) {
            socketID = String.valueOf(Math.random());
            callback.call();
        }

        if(eventId.equals("getPlayers")) {
            callback.call(new JSONArray());
        }
    }

    @Override
    public ArrayList<JSONObject> getPlayerUpdateData() {
        return playerData;
    }

    @Override
    public void clearPlayerUpdateData() {

    }

    @Override
    public float getElapsedTime() {
        return 0;
    }

    @Override
    public String getSocketID() {
        return "0";
    }

    @Override
    public int getRoomId() {
        return roomId;
    }
}
