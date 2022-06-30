package com.apet2929.game.engine.states;

import com.apet2929.game.engine.Network;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.PlayerFrog;
import com.apet2929.game.engine.box2d.entity.Wall;
import com.apet2929.game.engine.box2d.entity.Ball;
import com.apet2929.game.engine.level.Level;
import com.apet2929.game.engine.level.LevelLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.apet2929.game.engine.Utils.*;

public class Box2DTestState extends State {
    boolean connected;

    FitViewport viewport;
    FitViewport stageViewport;

    Stage stage;
    Skin skin;

    Network network;
    HashMap<String, Frog> frogs;
    PlayerFrog frog;
    Level level;
    Box2DDebugRenderer debugRenderer;

    Label canJumpLabel;

//    Temporary
    ShapeRenderer sr;
    public Box2DTestState(GameStateManager gsm) {
        super(gsm);
        initNetwork();
        initViewport();
        initWorld();
        initUI();
        sr = new ShapeRenderer();
        this.debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        if(!connected) return;
//        ball.getBody().applyForce(100.0f, 0.0f, ball.getBody().getPosition().x, ball.getBody().getPosition().y, true);
        level.update(delta);


        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            System.out.println("frog.canJump() = " + frog.canJump());
            System.out.println("frog.getNumFootContacts() = " + frog.getNumFootContacts());
            System.out.println(network.getElapsedTime());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            gsm.pop();
            this.dispose();
        }

        updateCamera();
        network.update(delta);
    }

    @Override
    public void draw(SpriteBatch sb) {

        ScreenUtils.clear(Color.BLACK);
        if(!connected) return;

        viewport.apply();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        sb.begin();
        level.render(sb);
        sb.end();

        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        frog.drawTongue(sr);
        sr.end();

        debugRenderer.render(level.getWorld(), viewport.getCamera().combined);
        drawUI();
    }

    void drawUI(){
        stageViewport.apply();
        canJumpLabel.setText("Can frog jump? " + frog.canJump());
        canJumpLabel.validate();
        stage.draw();
    }

    @Override
    public void dispose() {
        network.dispose();
        sr.dispose();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stageViewport.update(width, height);
    }

    void updateCamera(){
        viewport.getCamera().position.lerp(new Vector3(frog.getPosition(), viewport.getCamera().position.z), 0.1f);
        viewport.getCamera().update();
    }

    void initWorld(){
        this.frogs = new HashMap<>();
        int[][] tiles =
        {
                {-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,0, -1,-1,-1,-1,-1,0},
                {-1,-1,-1,-1,-1,-1,-1,-1, -1,-1,-1,-1,-1,0},
                {-1,-1,-1,-1,-1,-1,-1,-1, -1,-1,-1,-1,-1,0},
                {-1,-1,-1,-1,-1,-1,-1,-1, -1,-1,-1,-1,-1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        };
        this.level = LevelLoader.Load(tiles);
        this.frog = (PlayerFrog) initFrog("", 0, 30, true);

    }

    void initUI(){
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
        stage = new Stage(stageViewport);

//        TODO : Find a way to scale up the text
        Table root = new Table();
        stage.addActor(root);
        root.setSize(stage.getWidth(), stage.getHeight());

        root.defaults().pad(10);

        canJumpLabel = new Label("Can frog jump? false", skin);
        canJumpLabel.setAlignment(Align.bottomRight);
        root.add(canJumpLabel);
//        canJumpLabel.setColor(1, 0, 0, 1);
        canJumpLabel.getStyle().fontColor = new Color(1, 0,0,1);
    }

    void initViewport(){
        viewport = new FitViewport(VIEWPORT_SIZE, VIEWPORT_SIZE);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.getCamera().position.set(35, 40, 0);
        viewport.getCamera().update();
        stageViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    void initNetwork() {
        network = new Network();
        network.putCallback("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                connected = true;
                System.out.println("Initializing Player Frog!");
                frog.setID(network.getSocketID());
                frogs.put(network.getSocketID(), frog);
            }
        });
        network.putCallback("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                initFrog((JSONObject) args[0]);
            }
        });
        network.putCallback("getPlayers", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray objects = (JSONArray) args[0];
                    for (int i = 0; i < objects.length(); i++) {
                        initFrog(getArrayAt(objects, i));
                    }
                }
            });
        network.putCallback("playerDisconnected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject object = (JSONObject) args[0];
                removeFrog(getString(object, "id"));
            }
        });
        network.putCallback("tick", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject object = (JSONObject) args[0];
                String playerID = getString(object, "id");
                float x = getDouble(object, "x").floatValue();
                float y = getDouble(object, "y").floatValue();
                if(frogs.containsKey(playerID)){
                    updateFrog(playerID, x, y);
                }
            }
        });

        network.setOnTickRequested(() -> {
            network.setData(getGameData());
        });
    }

    Frog initFrog(JSONObject object){
        float x = getDouble(object, "x").floatValue();
        float y = getDouble(object, "y").floatValue();
        String id = getString(object, "id");
        return initFrog(id, x, y, false);
    }

    Frog initFrog(String id, float x, float y, boolean player){
        if(frogs.containsKey(id)) return null;
        Frog frog;
        if(player) {
            this.frog = new PlayerFrog(level, x, y, id);
            frog = this.frog;
        } else {
            frog = new Frog(level, x, y, id);
        }
        frogs.put(id, frog);
        level.addEntity(frog);
        return frog;
    }

    void updateFrog(JSONObject object){
        float x = getDouble(object, "x").floatValue();
        float y = getDouble(object, "y").floatValue();
        String id = getString(object, "id");
        updateFrog(id, x, y);
    }

    void updateFrog(String id, float x, float y) {
        if(frogs.containsKey(id)){
            frogs.get(id).setPosition(new Vector2(x, y));
        } else {
            initFrog(id, x, y, false);
        }
    }

    void removeFrog(String id){
        Frog frog = frogs.remove(id);
        level.removeEntity(frog);
    }

    JSONObject getGameData(){
        JSONObject data = new JSONObject();
        try{
            data.put("x", frog.getPosition().x);
            data.put("y", frog.getPosition().y);
        } catch (JSONException e){
            Gdx.app.log("SocketIO", "Error compiling game state to JSON");
            e.printStackTrace();
        }
        return data;
    }


}
