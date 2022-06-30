package com.apet2929.game.engine.box2d.entity.states.player;

import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.apet2929.game.engine.box2d.entity.states.FrogState;
import com.apet2929.game.engine.box2d.entity.states.FrogWalkingState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.apet2929.game.engine.Utils.WALKING_FORCE;

public class PlayerFrogWalkingState extends FrogWalkingState {
    public PlayerFrogWalkingState(SmartEntity entity) {
        super(entity);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        Frog frog = (Frog) this.entity;
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && frog.canJump()){
            frog.changeState(Frog.JUMP_CHARGING);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            frog.applyForceToCenter(Direction.LEFT.getVector(), WALKING_FORCE);
            frog.setDirection(Direction.LEFT);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            frog.applyForceToCenter(Direction.RIGHT.getVector(), WALKING_FORCE);
            frog.setDirection(Direction.RIGHT);
        }
        else if(frog.getNumFootContacts() == 0){
            frog.changeState(Frog.JUMPING);
        }
        else {
            this.entity.changeState(Frog.IDLE);
        }

    }


}
