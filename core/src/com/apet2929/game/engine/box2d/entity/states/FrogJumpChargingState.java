package com.apet2929.game.engine.box2d.entity.states;

import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.apet2929.game.engine.Utils.JUMPING_FORCE;
import static com.apet2929.game.engine.Utils.clamp;

public class FrogJumpChargingState extends FrogState {

    public float elapsedTime;
    public static final float MAX_CHARGE = 1.0f;
    public FrogJumpChargingState(SmartEntity entity) {
        super(entity, Frog.JUMP_CHARGING);
    }

    @Override
    public void onEnter() {
        super.onEnter();
    }

    @Override
    public void onExit() {
        super.onExit();
    }

    public void jump(Direction direction){
        float force_percent = clamp(getPercentCharged(), 0.3f, 1.0f);
        FrogJumpingState.jump(frog, force_percent * JUMPING_FORCE, direction);
    }

    public float getPercentCharged(){
        return elapsedTime / MAX_CHARGE;
    }

}
