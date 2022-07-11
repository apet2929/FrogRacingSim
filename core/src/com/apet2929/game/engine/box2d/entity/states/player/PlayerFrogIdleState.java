package com.apet2929.game.engine.box2d.entity.states.player;

import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.apet2929.game.engine.box2d.entity.states.FrogIdleState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;

import static com.apet2929.game.engine.Utils.HOP_SPEED_X;
import static com.apet2929.game.engine.Utils.HOP_SPEED_Y;

public class PlayerFrogIdleState extends FrogIdleState {
    public PlayerFrogIdleState(SmartEntity entity) {
        super(entity);
    }

    @Override
    public void update(float delta) {
        if(frog.canJump()){
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                frog.changeState(Frog.JUMP_CHARGING);
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.A)){
                doWalkingHop(this.frog, Direction.LEFT);
                frog.setDirection(Direction.LEFT);
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.D)){
                doWalkingHop(this.frog, Direction.RIGHT);
                frog.setDirection(Direction.RIGHT);
            }
        }
    }

    public static void doWalkingHop(Frog frog, Direction direction) {
        if(direction == Direction.LEFT){
            Body body = frog.getBody();
            body.setLinearVelocity(-HOP_SPEED_X, HOP_SPEED_Y );
        } else if(direction == Direction.RIGHT){
            Body body = frog.getBody();
            body.setLinearVelocity(HOP_SPEED_X, HOP_SPEED_Y);
        } else {
            throw new IllegalArgumentException("Illegal argument in doWalkingHop()! [PlayerFrogIdleState]");
        }
        frog.changeState(Frog.JUMPING);

    }
}
