package com.apet2929.game.engine.box2d.entity.states;

import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.apet2929.game.engine.Utils.JUMPING_FORCE;
import static com.apet2929.game.engine.Utils.clamp;

public class FrogJumpChargingState extends FrogState{

    private float elapsedTime;
    public static final float MAX_CHARGE = 1.0f;
    public FrogJumpChargingState(SmartEntity entity) {
        super(entity, Frog.JUMP_CHARGING);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        elapsedTime += delta;
        if(elapsedTime > MAX_CHARGE || !Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jump();
        }
    }

    @Override
    public void onEnter() {
        super.onEnter();
    }

    @Override
    public void onExit() {
        super.onExit();
    }

    void jump(){
        float force_percent = clamp(elapsedTime, 0.3f, 1.0f);
        FrogJumpingState.jump(frog, force_percent * JUMPING_FORCE);
    }

}
