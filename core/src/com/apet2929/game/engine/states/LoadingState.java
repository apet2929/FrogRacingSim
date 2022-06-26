package com.apet2929.game.engine.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.apet2929.game.engine.Utils;

public class LoadingState extends State{

    float progress = 0.05f;
    Timer timer = new Timer();
    boolean finished = false;

    public LoadingState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void update(float delta) {
        if(!finished) {
            progress += (delta / 2);
            progress = Utils.clamp(progress, 0.05f, 1f);
            if (progress == 1) {
                finished = true;
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 5);
            }
        }
    }

    @Override
    public void draw(SpriteBatch sb) {
        Utils.clearScreen(253, 245, 242);
        gsm.sr.setAutoShapeType(true);
        gsm.sr.begin(ShapeRenderer.ShapeType.Line);
        drawLoadingBar();
        gsm.sr.end();
    }

    private void drawLoadingBar(){
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        gsm.sr.setColor(Utils.intToFloatColor(36, 41, 47));
        gsm.sr.rect(w * 0.1f, h * 0.45f, w * 0.8f, h * 0.1f);
        gsm.sr.set(ShapeRenderer.ShapeType.Filled);
        gsm.sr.rect(w * 0.12f, h * 0.455f,  progress * w * 0.76f, h * 0.09f);
    }

    private void finish(){
        gsm.transitionTo(new TestState(gsm, Utils.intToFloatColor(230,172,93)));
    }

    @Override
    public void dispose() {

    }
}
