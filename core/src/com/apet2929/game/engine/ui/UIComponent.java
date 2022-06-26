package com.apet2929.game.engine.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.apet2929.game.engine.states.GameStateManager;

public abstract class UIComponent {
    private final GameStateManager gsm;
    public UIComponent(GameStateManager gsm){
        this.gsm = gsm;
    }

    public abstract void draw(Batch batch);

}
