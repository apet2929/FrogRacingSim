package com.apet2929.game.engine.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class GameStateManager  {
    private final Stack<State> states;
    public final AssetManager assetManager;

    public OrthographicCamera cam;
    public SpriteBatch sb;
    public ShapeRenderer sr;

    public BitmapFont font;
    public Skin skin;


    public GameStateManager(OrthographicCamera cam, SpriteBatch sb) {
        this.states = new Stack<>();
        this.cam = cam;
        this.sb = sb;
        this.sr = new ShapeRenderer();
        this.assetManager = new AssetManager();
//        this.skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
//        this.font = new BitmapFont(Gdx.files.internal("font1.fnt"));
//        loadAssets();
        assetManager.finishLoading();

    }

    public boolean isAssetsLoaded(){
        return this.assetManager.isFinished();
    }

    public void loadAssets(){
        loadUISkin();
        loadTextures();
    }

    private void loadTextures(){
        assetManager.load("badlogic.jpg", Texture.class);
        assetManager.load("NOT_FOUND.jpg", Texture.class);
    }

    private void loadUISkin(){
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter("metalui/metal-ui.atlas");
        assetManager.load("metalui/metal-ui.json", Skin.class, params);
    }

    public State peek(){
        return states.peek();
    }

    public State pop(){
        State state = states.pop();
        state.hide();
        return state;
    }

    public void push(State state){
        states.push(state);
        state.show();
    }

    public void update(float delta) {
        states.peek().update(delta);
    }

    public void render(){
        states.peek().draw(sb);
//        for (State state : states) {
//            state.draw(sb);
//        }

    }

    public void transitionTo(final State state){
        this.push(new TransitionState(this, 1, true).setOnFinish(() -> {

            GameStateManager.this.pop();
            GameStateManager.this.push(state);

            GameStateManager.this.transitionOut();
        }));
    }

    private void transitionOut(){
        this.push(new TransitionState(this, 1, false).setOnFinish(GameStateManager.this::pop));
    }

    public void resize(int width, int height){
        states.forEach((State state) -> state.resize(width, height));
    }

    public void dispose(){
        states.forEach(State::dispose);
    }


}
