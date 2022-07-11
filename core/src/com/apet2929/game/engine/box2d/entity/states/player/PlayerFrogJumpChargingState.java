package com.apet2929.game.engine.box2d.entity.states.player;

import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.PlayerFrog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.apet2929.game.engine.box2d.entity.states.FrogJumpChargingState;
import com.apet2929.game.engine.box2d.entity.states.FrogJumpingState;
import com.apet2929.game.engine.box2d.entity.states.FrogState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.apet2929.game.engine.Utils.JUMPING_FORCE;
import static com.apet2929.game.engine.Utils.clamp;

public class PlayerFrogJumpChargingState extends FrogJumpChargingState {


    public PlayerFrogJumpChargingState(SmartEntity entity) {
        super(entity);
    }

    @Override
    public void update(float delta) {
        this.elapsedTime += delta;
        if(getPercentCharged() > 1 || !Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(frog.canJump()) {
                if(Gdx.input.isKeyPressed(Input.Keys.D)){
                    System.out.println("Jumping right!");
                    jump(Direction.RIGHT);
                } else if(Gdx.input.isKeyPressed(Input.Keys.A)){
                    System.out.println("Jumping left!");
                    jump(Direction.LEFT);
                } else {
                    jump(Direction.UP);
                }
            }
            elapsedTime = 0;

        }
        ((PlayerFrog)frog).charge = getPercentCharged();
    }

}
