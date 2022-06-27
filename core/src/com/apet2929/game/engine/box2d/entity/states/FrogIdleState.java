package com.apet2929.game.engine.box2d.entity.states;

import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import static com.apet2929.game.engine.box2d.entity.states.FrogWalkingState.FORCE_MAG;

public class FrogIdleState extends EntityState{
    public FrogIdleState(SmartEntity entity) {
        super(entity, Frog.IDLE);
    }

    @Override
    public void update(float delta) {
        Frog frog = (Frog) this.entity;

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            frog.applyImpulseToCenter(Direction.LEFT.getVector(), FORCE_MAG);
            walk();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            frog.applyImpulseToCenter(Direction.RIGHT.getVector(), FORCE_MAG);
            walk();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            this.entity.getBody().applyLinearImpulse(new Vector2(0, 1000), this.entity.getPosition(), true);
            this.entity.changeState(Frog.JUMPING);
        }
    }

    @Override
    public void onEnter() {
        this.entity.changeAnimation(Frog.IDLE);
    }

    @Override
    public void onExit() {

    }
    void walk(){
        this.entity.changeState(Frog.WALKING);
    }
}
