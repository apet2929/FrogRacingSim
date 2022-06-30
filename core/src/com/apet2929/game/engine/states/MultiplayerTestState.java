package com.apet2929.game.engine.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

public class MultiplayerTestState extends State{
    private Socket socket;

    public MultiplayerTestState(GameStateManager gsm) {
        super(gsm);

    }

    @Override
    public void show() {
        System.out.println("Connecting to server!");
        connectSocket();
        configSocketEvents();
    }

    @Override
    public void update(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            disconnectSocket();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            System.out.println("socket.connected() = " + socket.connected());
            System.out.println("socket.id() = " + socket.id());
            System.out.println();
            if(socket.connected()){
                socket.emit("testEvent", "yee");
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            gsm.push(new MultiplayerTestState(gsm));
        }

    }

    @Override
    public void draw(SpriteBatch sb) {
        ScreenUtils.clear(0.5f,0.3f, 0.2f, 1f);
    }

    @Override
    public void dispose() {
        socket.close();
    }

    public void connectSocket(){
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
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting new player id");
                    e.printStackTrace();
                }
            }
        });
    }
}
