package com.apet2929.game.engine.box2d.entity.states;

import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import static com.apet2929.game.engine.box2d.entity.states.FrogWalkingState.FORCE_MAG;

public class FrogJumpingState extends EntityState{

    public FrogJumpingState(SmartEntity entity) {
        super(entity, Frog.JUMPING);
    }

    @Override
    public void update(float delta) {
        Frog frog = (Frog) this.entity;
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            frog.applyForceToCenter(Direction.LEFT.getVector(), FORCE_MAG/4f);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            frog.applyForceToCenter(Direction.RIGHT.getVector(), FORCE_MAG/4f);
        }
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onExit() {
        super.onExit();
    }
}
