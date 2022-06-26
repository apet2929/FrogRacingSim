package com.apet2929.game.engine.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TransitionState extends State {

    private float time;
    private final float duration;
    private final boolean direction;
    private Runnable onFinish;
    public TransitionState(GameStateManager gsm, float duration, boolean isDirectionIn) {
        super(gsm);
        time = 0f;
        this.duration = duration;
        this.direction = isDirectionIn;
    }

    public TransitionState setOnFinish(Runnable runnable){
        this.onFinish = runnable;
        return this;
    }

    @Override
    public void update(float delta) {
        time += delta;
        if(time > duration){
            this.onFinish.run();
        }

    }

    @Override
    public void draw(SpriteBatch sb) {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        gsm.sr.begin(ShapeRenderer.ShapeType.Filled);
        gsm.sr.setColor(0,0,0, direction ? getPercentDone() : 1 - getPercentDone());
        gsm.sr.rect(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gsm.sr.end();

        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    @Override
    public void dispose() {

    }

    public float getPercentDone(){
        return time / duration;
    }
}
