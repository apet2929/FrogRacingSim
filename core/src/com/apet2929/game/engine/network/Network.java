package com.apet2929.game.engine.network;

import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.apet2929.game.engine.Utils.NET_TIME_PER_TICK;

public abstract class Network {
    private float elapsedTime;
    private Runnable onTickRequested;
    protected int roomId;

    public abstract void dispose();

    public abstract void joinRoom(int _roomId);
    public abstract void setData(JSONObject data);
    public abstract void putCallback(String eventId, Emitter.Listener callback);
    public abstract ArrayList<JSONObject> getPlayerUpdateData();
    public abstract void clearPlayerUpdateData();
    public abstract int getRoomId();
    public void update(float delta){
        elapsedTime += delta;

        if(elapsedTime >= NET_TIME_PER_TICK){
            tick();
            elapsedTime = 0;
        }
    }

    public void tick(){
        onTickRequested.run();
    }
    public void setOnTickRequested(Runnable onTickRequested) {
        this.onTickRequested = onTickRequested;
    }
    public float getElapsedTime() {
        return elapsedTime;
    }

    public abstract String getSocketID();


}
