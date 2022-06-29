package com.apet2929.game.engine.box2d.entity.states;

import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import static com.apet2929.game.engine.Utils.DRAG_FORCE;
import static com.apet2929.game.engine.Utils.WALKING_FORCE;


public class FrogJumpingState extends FrogState {

    public FrogJumpingState(SmartEntity entity) {
        super(entity, Frog.JUMPING);
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
    public void onEnter() {
    }

    @Override
    public void onExit() {
        super.onExit();
    }

    void applyDragForce(){
        if(frog.getBody().getLinearVelocity().y > 0.5f){
            frog.applyForceToCenter(Direction.DOWN.getVector(), DRAG_FORCE);
        }
    }

    void land(){
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D)){
            this.entity.changeState(Frog.WALKING);
        } else {
            this.entity.changeState(Frog.IDLE);
        }
    }

    public static void jump(Frog frog, float force){
        System.out.println("Jumping!");
        frog.applyImpulseToCenter(new Vector2(0, 1), force);
        frog.changeState(Frog.JUMPING);
    }


}
