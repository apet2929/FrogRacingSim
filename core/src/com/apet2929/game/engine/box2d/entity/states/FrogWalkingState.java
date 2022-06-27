package com.apet2929.game.engine.box2d.entity.states;

import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class FrogWalkingState extends EntityState{
    public static final float FORCE_MAG = 5000;
    public FrogWalkingState(SmartEntity entity) {
        super(entity, Frog.WALKING);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        Frog frog = (Frog) this.entity;
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            frog.applyForceToCenter(Direction.LEFT.getVector(), FORCE_MAG);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            frog.applyForceToCenter(Direction.RIGHT.getVector(), FORCE_MAG);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            this.entity.getBody().applyLinearImpulse(new Vector2(0, 1000), this.entity.getPosition(), true);
            this.entity.changeState(Frog.JUMPING);

        } else {
            this.entity.changeState(Frog.IDLE);
        }
    }

    @Override
    public void onEnter() {
        this.entity.changeAnimation(Frog.WALKING);
    }

    @Override
    public void onExit() {
        super.onExit();
    }


}
