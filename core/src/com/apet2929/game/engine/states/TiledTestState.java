package com.apet2929.game.engine.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TiledTestState extends State{
    TiledMap map;
    OrthogonalTiledMapRenderer tmr;
    OrthographicCamera camera;
//    FitViewport viewport;

    public TiledTestState(GameStateManager gsm) {
        super(gsm);
        map = new TmxMapLoader().load("levels/test.tmx");
        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        tmr = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void update(float delta) {
        int speed = 1;
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            camera.translate(new Vector2(0, speed));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            camera.translate(new Vector2(-speed, 0));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            camera.translate(new Vector2(0, -speed));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            camera.translate(new Vector2(speed, 0));
        }
        camera.zoom += 0.01f;
        camera.update();
//        gsm.cam.translate();
    }

    @Override
    public void draw(SpriteBatch sb) {
        tmr.setView(camera);
        tmr.render();
    }

    @Override
    public void dispose() {
        map.dispose();
        tmr.dispose();
    }
}
