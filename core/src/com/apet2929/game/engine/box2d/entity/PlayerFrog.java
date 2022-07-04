package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.engine.box2d.entity.states.*;
import com.apet2929.game.engine.box2d.entity.states.player.*;
import com.apet2929.game.engine.level.Level;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

public class PlayerFrog extends Frog{

    public float charge;

    public PlayerFrog(Level level, float x, float y, String id) {
        super(level, x, y, id);
    }

    @Override
    void initStates() {
        HashMap<String, StateGenerator> states = new HashMap<>();
        states.put(IDLE, () -> new PlayerFrogIdleState(this));
        states.put(WALKING, () -> new PlayerFrogWalkingState(this));
        states.put(JUMPING, () -> new PlayerFrogJumpingState(this));
        states.put(JUMP_CHARGING, () -> new PlayerFrogJumpChargingState(this));
        states.put(GRAPPLE, () -> new PlayerFrogGrappleState(this));
        this.stateMachine = new StateMachine(states);
        this.changeState(IDLE);
    }

}
