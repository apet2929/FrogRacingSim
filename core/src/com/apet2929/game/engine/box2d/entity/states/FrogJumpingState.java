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
        if(frog.getNumFootContacts() > 0){
            if(frog.getBody().getLinearVelocity().y < 1) land();
        }
    }

    @Override
    public void onEnter() {
        frog.changeAnimation(Frog.JUMPING);
    }

    @Override
    public void onExit() {
        super.onExit();
    }

    public void applyDragForce(){
        if(frog.getBody().getLinearVelocity().y > 0.5f){
            frog.applyForceToCenter(Direction.DOWN.getVector(), DRAG_FORCE);
        }

    }

    public void land(){
        this.entity.changeState(Frog.IDLE);
    }

    public static void jump(Frog frog, float force){
//        System.out.println("Jumping!");
        frog.applyImpulseToCenter(new Vector2(0, 1), force);
        frog.changeState(Frog.JUMPING);

    }


}
