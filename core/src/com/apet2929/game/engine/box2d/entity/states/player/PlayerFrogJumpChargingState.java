package com.apet2929.game.engine.box2d.entity.states.player;

import com.apet2929.game.engine.InputBuffer;
import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.PlayerFrog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.apet2929.game.engine.box2d.entity.states.FrogJumpChargingState;
import com.apet2929.game.engine.box2d.entity.states.FrogJumpingState;
import com.apet2929.game.engine.box2d.entity.states.FrogState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Queue;

import static com.apet2929.game.engine.Utils.JUMPING_FORCE;
import static com.apet2929.game.engine.Utils.clamp;

public class PlayerFrogJumpChargingState extends FrogJumpChargingState {

    Queue<Float> chargeBuffer;
    public PlayerFrogJumpChargingState(SmartEntity entity) {
        super(entity);
        chargeBuffer = new Queue<>();
    }

    @Override
    public void update(float delta) {
        updateCharge(delta);

        PlayerFrog pf = (PlayerFrog) frog;

        if(frog.canJump()){
            if(pf.inputBuffer.contains(Input.Keys.SPACE, InputBuffer.InputModifier.RELEASED)) {
                this.elapsedTime = max(chargeBuffer); // to make use of input buffer, we take the max of the charge buffer
                chargeBuffer.clear();
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
        } else if(!Gdx.input.isKeyPressed(Input.Keys.SPACE)) { // released at some point in the air
            this.elapsedTime = 0;
        }

        pf.charge = getPercentCharged();
    }

    private void updateCharge(float delta) {
        this.elapsedTime += delta;
        if(elapsedTime > MAX_CHARGE) elapsedTime = MAX_CHARGE;
        chargeBuffer.addLast(this.elapsedTime);
        if(chargeBuffer.size > InputBuffer.BUFFER_FRAMES) chargeBuffer.removeFirst();
    }

    private static float max(Queue<Float> buffer) {
        float max = -1;
        for(Float f : buffer) {
            if(f > max) max = f;
        }
        return max;
    }

}
