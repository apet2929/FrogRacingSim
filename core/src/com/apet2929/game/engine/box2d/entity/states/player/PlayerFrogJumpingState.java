package com.apet2929.game.engine.box2d.entity.states.player;

import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.apet2929.game.engine.box2d.entity.states.FrogState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import static com.apet2929.game.engine.Utils.DRAG_FORCE;
import static com.apet2929.game.engine.Utils.WALKING_FORCE;


public class PlayerFrogJumpingState extends com.apet2929.game.engine.box2d.entity.states.FrogJumpingState {

    public PlayerFrogJumpingState(SmartEntity entity) {
        super(entity);
    }

    @Override
    public void update(float delta) {
        applyDragForce();
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            frog.applyForceToCenter(Direction.LEFT.getVector(), WALKING_FORCE /4f);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            frog.applyForceToCenter(Direction.RIGHT.getVector(), WALKING_FORCE /4f);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            frog.changeState(Frog.GRAPPLE);
        }

        if(frog.getNumFootContacts() > 0){
            if(frog.getBody().getLinearVelocity().y < 1) land();
        }
    }

    @Override
    public void land(){
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D)){
            this.entity.changeState(Frog.WALKING);
        } else {
            this.entity.changeState(Frog.IDLE);
        }
    }


}
