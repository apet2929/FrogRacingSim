package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.engine.Animation;
import com.apet2929.game.engine.box2d.entity.states.StateMachine;

import java.util.HashMap;

public abstract class SmartEntity extends Entity{
    public StateMachine stateMachine;
    protected HashMap<String, Animation> animations;
    protected Animation currentAnimation;
}
