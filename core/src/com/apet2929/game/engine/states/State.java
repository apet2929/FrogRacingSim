package com.apet2929.game.engine.states;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public abstract class State extends ScreenAdapter {
    protected GameStateManager gsm;

    public State(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void update(float delta);

    public abstract void draw(SpriteBatch sb);

    public abstract void dispose();

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }
}