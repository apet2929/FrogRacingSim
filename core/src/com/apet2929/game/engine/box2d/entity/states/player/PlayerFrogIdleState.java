package com.apet2929.game.engine.box2d.entity.states.player;

import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.apet2929.game.engine.box2d.entity.states.FrogIdleState;
import com.apet2929.game.engine.box2d.entity.states.FrogState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerFrogIdleState extends FrogIdleState {
    public PlayerFrogIdleState(SmartEntity entity) {
        super(entity);
    }

    @Override
    public void update(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && frog.canJump()){

            frog.changeState(Frog.JUMP_CHARGING);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
//            frog.applyImpulseToCenter(Direction.LEFT.getVector(), WALKING_FORCE);
            walk();
            frog.setDirection(Direction.LEFT);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
//            frog.applyImpulseToCenter(Direction.RIGHT.getVector(), WALKING_FORCE);
            walk();
            frog.setDirection(Direction.RIGHT);
        }
        else if(frog.getNumFootContacts() == 0){
            frog.changeState(Frog.JUMPING);
        }

    }
}
