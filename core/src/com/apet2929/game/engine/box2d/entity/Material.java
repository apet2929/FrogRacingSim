package com.apet2929.game.engine.box2d.entity;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

public enum Material {
    STEEL(1f, 0.5f, 0.1f),
    GROUND(1f, 0.5f, 0.1f),
    WOOD(0.5f, 0.7f, 0.3f),
    RUBBER(1f, 0f, 1f),
    STONE(7f, 0.5f, 0.3f);

    public float density;
    public float friction;
    public float restitution;
    Material(float density, float friction, float restitution){
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
    }

    public FixtureDef makeFixture(Shape shape){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = this.density;
        fixtureDef.friction = this.friction;
        fixtureDef.restitution = this.restitution;
        return fixtureDef;
    }
}
