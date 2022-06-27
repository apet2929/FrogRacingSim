package com.apet2929.game.engine.box2d.entity.states;


import com.apet2929.game.engine.box2d.entity.SmartEntity;

public abstract class EntityState {
//    Concept from https://www.youtube.com/watch?v=gx_qorHxBpI
    protected SmartEntity entity;
    public final String id;
    public EntityState(SmartEntity entity, String id){
        this.entity = entity;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void update(float delta){}
    public void onEnter(){}
    public void onExit(){}

}
