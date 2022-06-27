package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.engine.Animation;
import com.apet2929.game.engine.box2d.entity.states.StateMachine;

import java.util.HashMap;

public abstract class SmartEntity extends Entity {
    public StateMachine stateMachine;
    protected HashMap<String, Animation> animations;
    protected Animation currentAnimation;

    public SmartEntity(EntityType entityType){
        super(entityType);
        this.initStates();
    }

    public void changeState(String name){
        stateMachine.change(name);
    }

    public String getState(){
        return stateMachine.getCurrent().getId();
    }

    abstract void initStates();

    public void update(float delta){
        this.currentAnimation.update(delta);
        this.sprite.setRegion(this.currentAnimation.getFrame());
        this.stateMachine.update(delta);
    }

    public void changeAnimation(String name) {
        if(this.currentAnimation != null) this.currentAnimation.reset();
        this.currentAnimation = animations.get(name);
    }
}
