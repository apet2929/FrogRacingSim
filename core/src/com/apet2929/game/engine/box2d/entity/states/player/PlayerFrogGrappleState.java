package com.apet2929.game.engine.box2d.entity.states.player;

import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.apet2929.game.engine.box2d.entity.states.FrogGrappleState;
import com.apet2929.game.engine.box2d.entity.states.FrogState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import static com.apet2929.game.engine.Utils.GRAPPLE_FORCE;
import static com.apet2929.game.engine.Utils.MAX_GRAPPLE_LENGTH;

public class PlayerFrogGrappleState extends FrogGrappleState {
    public PlayerFrogGrappleState(SmartEntity entity) {
        super(entity);
    }

    @Override
    public void update(float delta) {
        updateTonguePos();
        pullToTarget();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            release();
        }
    }

}
