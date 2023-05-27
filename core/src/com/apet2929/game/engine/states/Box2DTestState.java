package com.apet2929.game.engine.states;

import com.apet2929.game.engine.network.LocalNetwork;
import com.apet2929.game.engine.network.Network;
import com.apet2929.game.engine.network.ServerNetwork;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.PlayerFrog;
import com.apet2929.game.engine.level.Level;
import com.apet2929.game.engine.level.LevelLoader;
import com.apet2929.game.engine.ui.JumpBar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

import java.util.ArrayList;
import java.util.HashMap;

import static com.apet2929.game.engine.Utils.*;

public class Box2DTestState extends State {
    boolean useServer = false;
    boolean connected;
    FitViewport viewport;
    FitViewport stageViewport;
    OrthographicCamera tiledMapCamera;
    Stage stage;
    Skin skin;
    Network network;
    HashMap<String, Frog> frogs;
    PlayerFrog frog;
    Level level;

    OrthogonalTiledMapRenderer tmr;
    Box2DDebugRenderer debugRenderer;

    Label canJumpLabel;
    JumpBar jumpBar;

    //    Temporary
    ShapeRenderer sr;

    public Box2DTestState(GameStateManager gsm) {
        super(gsm);
        initWorld();
        initNetwork();
        initViewport();
        initUI();
        sr = new ShapeRenderer();
        this.debugRenderer = new Box2DDebugRenderer();
        tmr = new OrthogonalTiledMapRenderer(level.getMap(), UNIT_SCALE);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);

        if(!this.isConnected()) return;
        networkUpdateFrogs();

        level.update(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            System.out.println("frog.canJump() = " + frog.canJump());
            System.out.println("frog.getNumFootContacts() = " + frog.getNumFootContacts());
            System.out.println(network.getElapsedTime());
            System.out.println("tiledMapCamera = " + tiledMapCamera.zoom);
            System.out.println("frog.inputBuffer = " + frog.inputBuffer.toString());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gsm.pop();
            this.dispose();
        }

        updateCamera();
        network.update(delta);
    }

    @Override
    public void draw(SpriteBatch sb) {

        ScreenUtils.clear(Color.BLACK);
        if (!this.isConnected()) return;

        viewport.apply();

        drawTiledMap();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        sb.begin();
        level.render(sb);
        sb.end();

        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (Frog frog : frogs.values()) {
            frog.drawTongue(sr);
        }
        sr.end();

        debugRenderer.render(level.getWorld(), viewport.getCamera().combined);
        drawUI();
    }

    void drawTiledMap() {
        tmr.setView((OrthographicCamera) viewport.getCamera());
        tmr.render();
    }

    void drawUI() {
        stageViewport.apply();
        canJumpLabel.setText("Can frog jump? " + frog.canJump());
        canJumpLabel.validate();
        stage.draw();

        drawGameUI(gsm.sb);
    }

    void drawGameUI(SpriteBatch sb) {
        sb.setProjectionMatrix(stageViewport.getCamera().combined);
        sb.begin();
        jumpBar.draw(sb, frog);
        sb.end();
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

    void updateCamera() {
        viewport.getCamera().position.lerp(new Vector3(frog.getPosition(), viewport.getCamera().position.z), 0.1f);
        viewport.getCamera().update();
    }

    void initWorld() {
        tiledMapCamera = new OrthographicCamera();
        tiledMapCamera.setToOrtho(false, VIEWPORT_SIZE, VIEWPORT_SIZE);
        tiledMapCamera.zoom = 20;
        this.frogs = new HashMap<>();
        int[][] tiles =
                {
                        {-1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, 4, -1, -1, -1, -1, -1, 0},
                        {-1, -1, -1, -1, -1, -1, -1, 4, -1, -1, -1, -1, -1, 0},
                        {-1, -1, -1, -1, -1, -1, -1, 4, -1, -1, -1, -1, -1, 0},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                };
        this.level = LevelLoader.Load(Level.LEVELS[0]);
        this.frog = (PlayerFrog) initFrog("", 2 * TILE_SIZE, 2 * TILE_SIZE, true);

    }

    void initUI() {
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
        stage = new Stage(stageViewport);
        this.jumpBar = new JumpBar();

//        TODO : Find a way to scale up the text
        Table root = new Table();
        stage.addActor(root);
        root.setSize(stage.getWidth(), stage.getHeight());

        root.defaults().pad(10);

        canJumpLabel = new Label("Can frog jump? false", skin);
        canJumpLabel.setAlignment(Align.bottomRight);
//        root.add(canJumpLabel);
//        canJumpLabel.setColor(1, 0, 0, 1);
        canJumpLabel.getStyle().fontColor = new Color(1, 0, 0, 1);
    }

    void initViewport() {
        viewport = new FitViewport(VIEWPORT_SIZE, VIEWPORT_SIZE);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.getCamera().position.set(0, 0, 0);
        ((OrthographicCamera) viewport.getCamera()).zoom = 0.5f;
        viewport.getCamera().update();
        stageViewport = new FitViewport(VIEWPORT_SIZE, VIEWPORT_SIZE);
    }

    void initNetwork() {
        if(useServer) {
            network = new ServerNetwork();
        }
        else {
            network = new LocalNetwork();
        }

        network.setOnTickRequested(() -> {
            network.setData(getGameData());
        });

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
                JSONObject object = (JSONObject) args[0];
                String id = getString(object, "id");
                initFrog(id, 0, 30, false);
            }
        });
        network.putCallback("getPlayers", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray objects = (JSONArray) args[0];
                System.out.println("objects = " + objects);
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

    }

    Frog initFrog(JSONObject object) {
        String id = getString(object, "id");
        Frog frog = initFrog(id, 0, 30, false);
//        frog.setState(object);
        return frog;
    }

    Frog initFrog(String id, float x, float y, boolean player) {
        if (frogs.containsKey(id)) return null;
        Frog frog;
        if (player) {
            this.frog = new PlayerFrog(level, x, y, id);
            frog = this.frog;
        } else {
            frog = new Frog(level, x, y, id);
        }
        frogs.put(id, frog);
        level.addEntity(frog);
        return frog;
    }

    void networkUpdateFrogs() {
        ArrayList<JSONObject> playerData = network.getPlayerUpdateData();
        for (JSONObject playerDatum : playerData) {
            updateFrog(playerDatum);
        }

        network.clearPlayerUpdateData();

    }

    void updateFrog(JSONObject object) {
        String id = getString(object, "id");
        if (frogs.containsKey(id)) {
            frogs.get(id).setState(object);
        } else {
            initFrog(id, 0, 0, false);
        }
    }

    void removeFrog(String id) {
        Frog frog = frogs.remove(id);
        level.removeEntity(frog);
    }

    void setRoom(int roomId) {
        network.joinRoom(roomId);
    }

    JSONObject getGameData() {
        JSONObject data = frog.toJSON();
        try {
            data.put("roomId", network.getRoomId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    boolean isConnected() {
        return this.connected && this.network.getRoomId() != -1;
    }


}
