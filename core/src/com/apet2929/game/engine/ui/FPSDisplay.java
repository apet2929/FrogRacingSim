package com.apet2929.game.engine.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.apet2929.game.engine.Utils;
import com.apet2929.game.engine.states.GameStateManager;

public class FPSDisplay extends UIComponent {
    private final Label label;
    public FPSDisplay(GameStateManager gsm) {
        super(gsm);
        label = new Label("FPS: ", gsm.skin);
        label.getStyle().fontColor = Utils.intToFloatColor(117, 255, 184);
        label.setPosition(100, 100);
        label.setFontScale(2);
    }

    @Override
    public void draw(Batch batch) {
        label.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        label.draw(batch, 1);
    }
}
