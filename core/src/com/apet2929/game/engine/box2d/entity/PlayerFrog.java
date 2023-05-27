package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.engine.InputBuffer;
import com.apet2929.game.engine.box2d.entity.states.*;
import com.apet2929.game.engine.box2d.entity.states.player.*;
import com.apet2929.game.engine.level.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

public class PlayerFrog extends Frog{

    public float charge;
    public InputBuffer inputBuffer;

    public PlayerFrog(Level level, float x, float y, String id) {
        super(level, x, y, id);
        inputBuffer = new InputBuffer();
    }

    @Override
    public void update(float delta) {
        updateBuffer();
        super.update(delta);
    }

    @Override
    void initStates() {
        HashMap<String, StateGenerator> states = new HashMap<>();
        states.put(IDLE, () -> new PlayerFrogIdleState(this));
        states.put(JUMPING, () -> new PlayerFrogJumpingState(this));
        states.put(JUMP_CHARGING, () -> new PlayerFrogJumpChargingState(this));
        states.put(GRAPPLE, () -> new PlayerFrogGrappleState(this));
        this.stateMachine = new StateMachine(states);
        this.changeState(IDLE);
    }

    public void updateBuffer() {
        int[] keys = {Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D,
                Input.Keys.SPACE, Input.Keys.SHIFT_RIGHT,
        };

        for(int key : keys) {
            boolean pressed = inputBuffer.contains(key, InputBuffer.InputModifier.PRESSED);
            boolean held = inputBuffer.contains(key, InputBuffer.InputModifier.HELD);
            if(Gdx.input.isKeyPressed(key)) {
                if(pressed || held) {
                    inputBuffer.put(key, InputBuffer.InputModifier.HELD);
                } else inputBuffer.put(key, InputBuffer.InputModifier.PRESSED);
            } else {
                if(pressed || held) inputBuffer.put(key, InputBuffer.InputModifier.RELEASED);
            }
        }
        inputBuffer.update();
    }

}
