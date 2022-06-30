package com.apet2929.game.engine.box2d.entity.states.player;

import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.apet2929.game.engine.box2d.entity.states.FrogJumpChargingState;
import com.apet2929.game.engine.box2d.entity.states.FrogJumpingState;
import com.apet2929.game.engine.box2d.entity.states.FrogState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.apet2929.game.engine.Utils.JUMPING_FORCE;
import static com.apet2929.game.engine.Utils.clamp;

public class PlayerFrogJumpChargingState extends FrogJumpChargingState {


    public PlayerFrogJumpChargingState(SmartEntity entity) {
        super(entity);
    }

    @Override
    public void update(float delta) {
        this.elapsedTime += delta;
        if(elapsedTime > MAX_CHARGE || !Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jump();
        }
    }

}
