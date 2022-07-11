package com.apet2929.game.engine.box2d.entity.states.player;

import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.apet2929.game.engine.box2d.entity.states.FrogState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import static com.apet2929.game.engine.Utils.*;


public class PlayerFrogJumpingState extends com.apet2929.game.engine.box2d.entity.states.FrogJumpingState {

    public PlayerFrogJumpingState(SmartEntity entity) {
        super(entity);
    }

    @Override
    public void update(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            frog.applyForceToCenter(Direction.LEFT.getVector(), AIR_CONTROL_FORCE);
            frog.setDirection(Direction.LEFT);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            frog.applyForceToCenter(Direction.RIGHT.getVector(), AIR_CONTROL_FORCE);
            frog.setDirection(Direction.RIGHT);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            frog.changeState(Frog.JUMP_CHARGING);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT)){
            frog.changeState(Frog.GRAPPLE);
        }
        super.update(delta);
    }

    @Override
    public void land(){
        this.entity.changeState(Frog.IDLE);
    }



}
