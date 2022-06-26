package com.apet2929.game.engine.box2d.entity.states;

import com.apet2929.game.engine.box2d.entity.Entity;

public abstract class EntityState {
//    Concept from https://www.youtube.com/watch?v=gx_qorHxBpI
    protected Entity entity;
    public EntityState(Entity entity){
        this.entity = entity;
    }

    public void update(float delta){}
    public void onEnter(){}
    public void onExit(){}

}
